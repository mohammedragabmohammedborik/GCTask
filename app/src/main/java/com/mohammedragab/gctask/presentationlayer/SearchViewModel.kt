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
    var carsmodelList = mutableStateListOf<Carmodel>()
        private set
    var carmodelsState = mutableStateOf<Carmodel>(Carmodel("","","",0,"",0.0))
        private set
    var screenNameState= mutableStateOf<String>("")

    // event: addItem
    fun searchForAvailableCar(item: SearchRequest,json:String) {
        viewModelScope.launch {
            Log.w("TAG", "TV: ${item}")

          var   carsFilterList= listOf<Carmodel>()

            // search with price and color
            if (item.unitPrice!!.length!=0 && item.color!="SelectColor"){
                
                carsFilterList= getAlCarAvailble(json).let {list->
                    list.filter { it.unit_price.toString().trim().contains(item.unitPrice!!)||it.color.equals(item.color!!,ignoreCase=true) }
                }
                // todoItems.value=SearchStatus(carsFilterList,null)
                carsmodelList.swapList(carsFilterList!!)


        }
            // search with price
            else if (item.unitPrice!!.length!=0){
                carsFilterList= getAlCarAvailble(json).let {list->
                    list.filter { it.unit_price.toString().trim().contains(item.unitPrice!!) }
                }
                carsmodelList.swapList(carsFilterList!!)

            }
            else if (!item.color.isNullOrEmpty()){
                carsFilterList= getAlCarAvailble(json).let {list->
                    list.filter { it.color.equals(item.color!!,ignoreCase=true) }
                }
                carsmodelList.swapList(carsFilterList!!)

            }else{
                carsFilterList= getAlCarAvailble(json)
                carsmodelList.swapList(carsFilterList!!)

            }

        }


    }
    //
    fun getAlCarAvailble(json:String):List<Carmodel> {
            val responseSuccess=convertJsonStringToObject(json)
            if (responseSuccess!=null){
                when(responseSuccess.status?.code){
                    200->{
                       // _suggestedDestinations.value=SearchStatus(responseSuccess.cars,null)
                        carsmodelList.swapList(responseSuccess.cars!!)
                        return  responseSuccess.cars!!
                    }
                    204->{
                      //  _suggestedDestinations.value=SearchStatus(null,responseSuccess.status.message)

                    }
                    else->{

                    }

                }
            
        }

        return listOf()


    }

    fun convertJsonStringToObject(jsonString: String?): CarsResponse? {
        val gson = Gson()
        if (jsonString.isNullOrBlank()) return  null
        return gson.fromJson(jsonString, CarsResponse::class.java)

    }
    fun getCarModelToNavigate(carModel:Carmodel,screenName:String){
        carmodelsState.value=carModel
        screenNameState.value=screenName

    }
    fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
        clear()
        addAll(newList)
    }

}