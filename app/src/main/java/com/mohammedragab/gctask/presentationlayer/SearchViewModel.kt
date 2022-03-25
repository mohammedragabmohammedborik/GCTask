package com.mohammedragab.gctask.presentationlayer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mohammedragab.gctask.data.Carmodel
import com.mohammedragab.gctask.data.CarsResponse
import com.mohammedragab.gctask.data.SearchRequest
import com.mohammedragab.gctask.utility.Utilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    var carsmodelList = mutableStateListOf<Carmodel>()
        private set

    var messageEmptyData by mutableStateOf<String>("")
        private set

    fun updateCheckForEmptyData(message:String){
        messageEmptyData=message
    }

    // here is a search for all conditions
    // and check if empty list we should call to get response if empty data
    fun searchForAvailableCar(item: SearchRequest,json:String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.w("TAG", "TV: ${item}")
            val carsFilterList: List<Carmodel>
            // search with price and color
            if (item.unitPrice!!.isNotEmpty() && item.color!=Utilities.SELECTCOLOR){
                carsFilterList= getAlCarAvailble(json).let {list->
                    list.filter { it.unit_price.toString().trim().contains(item.unitPrice!!)||it.color.equals(item.color!!,ignoreCase=true) }
                }
                carsmodelList.swapList(carsFilterList!!)
            }
            // search with price
            else if (item.unitPrice!!.isNotEmpty()){
                carsFilterList= getAlCarAvailble(json).let {list->
                    list.filter { it.unit_price.toString().trim().contains(item.unitPrice!!) }
                }
                carsmodelList.swapList(carsFilterList!!)


            }
            else if (item.color!=Utilities.SELECTCOLOR &&item.unitPrice!!.isNotEmpty() ){
                carsFilterList= getAlCarAvailble(json).let {list->
                    list.filter { it.unit_price.toString().trim().contains(item.unitPrice!!) }
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
                        carsmodelList.swapList(responseSuccess.cars!!)
                        messageEmptyData=""
                        return  responseSuccess.cars!!
                    }
                    204->{
                        messageEmptyData= responseSuccess.status?.let {  "cmcmcmcmcmccmcmcm"}.toString()
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

    // extention function
    fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
        clear()
        addAll(newList)
    }

}