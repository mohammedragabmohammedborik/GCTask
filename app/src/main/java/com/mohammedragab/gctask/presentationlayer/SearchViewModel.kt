package com.mohammedragab.gctask.presentationlayer

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mohammedragab.gctask.R
import com.mohammedragab.gctask.data.Carmodel
import com.mohammedragab.gctask.data.CarsResponse
import com.mohammedragab.gctask.data.SearchRequest
import com.mohammedragab.gctask.data.SearchStatus.GenralStatus
import com.mohammedragab.gctask.data.SearchStatus.SearchStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class SearchViewModel: ViewModel() {
//    private var _availableCarList= MutableLiveData(SearchStatus())
//    val availableCarList: LiveData<SearchStatus> = _availableCarList
    private val _suggestedDestinations = MutableStateFlow<SearchStatus>(SearchStatus(null,null))
    val suggestedDestinations: MutableStateFlow<SearchStatus>
        get() = _suggestedDestinations
    var todoItems = mutableStateListOf<Carmodel>()
        private set
//    var todoItems = mutableStateOf<SearchStatus>(SearchStatus(null,null))
//        private set

    // event: addItem
    fun searchForAvailableCar(item: SearchRequest,json:String) {
        viewModelScope.launch {
            Log.w("TAG", "TV: ${item}")


           // if (item.unitPrice!!.length!=0 || item.color!="SelectColor"){
                
                val carsFilterList= getAlCarAvailble(json).let {list->
                    list.filter { it.unit_price.toString().trim().contains(item.unitPrice!!)||it.color.contains(item.color!!,ignoreCase=true) }
                }
                // todoItems.value=SearchStatus(carsFilterList,null)
                todoItems.swapList(carsFilterList!!)

                // _suggestedDestinations.value=SearchStatus(carsFilterList,null)
         //   }
        }


    }
    //
    fun getAlCarAvailble(json:String):List<Carmodel> {
            val responseSuccess=convertJsonStringToObject(json)
            if (responseSuccess!=null){
                when(responseSuccess.status?.code){
                    200->{
                       // _suggestedDestinations.value=SearchStatus(responseSuccess.cars,null)
                        todoItems.swapList(responseSuccess.cars!!)
                        return  responseSuccess.cars!!
                    }
                    204->{
                        _suggestedDestinations.value=SearchStatus(null,responseSuccess.status.message)

                    }
                    else->{

                    }

                }
            
        }

        return listOf()


    }
    suspend fun  readData(){}

    fun convertJsonStringToObject(jsonString: String?): CarsResponse? {
        val gson = Gson()
        if (jsonString.isNullOrBlank()) return  null
        return gson.fromJson(jsonString, CarsResponse::class.java)

    }
    fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
        clear()
        addAll(newList)
    }

}