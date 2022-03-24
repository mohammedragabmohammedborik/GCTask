package com.mohammedragab.gctask.presentationlayer

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.mohammedragab.gctask.data.Carmodel
import com.mohammedragab.gctask.data.CarsResponse
import com.mohammedragab.gctask.data.SearchRequest
import com.mohammedragab.gctask.data.SearchStatus.GenralStatus
import com.mohammedragab.gctask.data.SearchStatus.SearchStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class SearchViewModel: ViewModel() {
//    private var _availableCarList= MutableLiveData(SearchStatus())
//    val availableCarList: LiveData<SearchStatus> = _availableCarList
    private val _suggestedDestinations = MutableStateFlow<SearchStatus>(SearchStatus(null,null))
    val suggestedDestinations: StateFlow<SearchStatus>
        get() = _suggestedDestinations

    var todoItems = mutableListOf<Carmodel>()
        private set

    // event: addItem
    fun searchForAvailableCar(item: SearchRequest,json:String) {
        Log.w("TAG", "searchForAvailableCar1 : ${item}")

        Log.w("TAG", "searchForAvailableCar: ${item.unitPrice} , ${item.color}")
        val responseSuccess=convertJsonStringToObject(json)
        Log.w("TAG", "searchForAvailableCar1  jsonresponse : ${json}")


        Log.w("TAG", "searchForAvailableCar1  response : ${responseSuccess}")

        if (responseSuccess!=null){
            Log.w("TAG", "responseSuccess1 : ${responseSuccess.status?.code}")

            when(responseSuccess.status?.code){
                200->{
                    if (!item.unitPrice.isNullOrEmpty()){
                        Log.w("TAG", "responseSuccess2 : ${responseSuccess.status?.code}")
                        _suggestedDestinations.value=SearchStatus(responseSuccess.cars,null)
                        todoItems.addAll(responseSuccess.cars!!)
                    }else{
                        Log.w("TAG", "responseSuccess3 : ${responseSuccess.status?.code}")
                        todoItems.addAll(responseSuccess.cars!!)

                        _suggestedDestinations.value=SearchStatus(responseSuccess.cars,null)

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
    suspend fun  readData(){

    }

    fun convertJsonStringToObject(jsonString: String?): CarsResponse? {
        val gson = Gson()
        if (jsonString.isNullOrBlank()) return  null
        return gson.fromJson(jsonString, CarsResponse::class.java)

    }

}