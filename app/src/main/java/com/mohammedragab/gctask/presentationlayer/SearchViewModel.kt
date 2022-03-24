package com.mohammedragab.gctask.presentationlayer

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
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
            val responseSuccess=convertJsonStringToObject(json)
            if (responseSuccess!=null){
                when(responseSuccess.status?.code){
                    200->{
                        //todoItems.removeAll(carsFilterList)
                        if (item.unitPrice!!.length!=0){
                            Log.w("", "searchForAvailableCar:1 ${responseSuccess.cars} " )
                           val carsFilterList= responseSuccess.cars?.let {
                               it.filter { it.unit_price.toString().contains(item.unitPrice) }
                           }
                            Log.w("TAG", "TV: ${carsFilterList}")
                           // todoItems.value=SearchStatus(carsFilterList,null)
                            todoItems.addAll(carsFilterList!!)

                            // _suggestedDestinations.value=SearchStatus(carsFilterList,null)
                        }


                    }
                    204->{
                        _suggestedDestinations.value=SearchStatus(null,responseSuccess.status.message)

                    }
                    else->{

                    }

                }
            }
        }



    }
    //
    fun getAlCarAvailble(json:String) {

        viewModelScope.launch {
            val responseSuccess=convertJsonStringToObject(json)
            if (responseSuccess!=null){
                when(responseSuccess.status?.code){
                    200->{
                       // _suggestedDestinations.value=SearchStatus(responseSuccess.cars,null)
                        todoItems.addAll(responseSuccess.cars!!)



                    }
                    204->{
                        _suggestedDestinations.value=SearchStatus(null,responseSuccess.status.message)

                    }
                    else->{

                    }

                }
            }
        }



    }
    suspend fun  readData(){

    }

    fun convertJsonStringToObject(jsonString: String?): CarsResponse? {
        val gson = Gson()
        if (jsonString.isNullOrBlank()) return  null
        return gson.fromJson(jsonString, CarsResponse::class.java)

    }

}