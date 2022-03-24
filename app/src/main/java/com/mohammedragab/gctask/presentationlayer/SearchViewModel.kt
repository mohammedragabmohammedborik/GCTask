package com.mohammedragab.gctask.presentationlayer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.mohammedragab.gctask.data.Carmodel
import com.mohammedragab.gctask.data.CarsResponse
import com.mohammedragab.gctask.data.SearchRequest
import com.mohammedragab.gctask.data.SearchStatus.GenralStatus
import com.mohammedragab.gctask.data.SearchStatus.SearchStatus
import com.mohammedragab.gctask.data.SearchStatus.SearchStatus.SuccessResponse
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class SearchViewModel: ViewModel() {
    private var _availableCarList= MutableLiveData(SearchStatus())
    val availableCarList: LiveData<SearchStatus> = _availableCarList

    // event: addItem
    fun searchForAvailableCar(item: SearchRequest,inputStream:InputStream) {
        Log.w("TAG", "searchForAvailableCar: ${item.unitPrice} , ${item.color}")
        val responseSuccess=convertJsonStringToObject(readJSONDataFromFile(inputStream =inputStream ))
        if (responseSuccess!=null){
            when(responseSuccess.status?.code){
                200->{
                    if (item!=null){

                    }else _availableCarList.value= SuccessResponse(responseSuccess.cars)

                }
                204->{

                }
                else->{

                }

            }
        }
    }
    suspend fun  readData(){

    }

     fun readJSONDataFromFile(inputStream: InputStream):String? {
        val builder = StringBuilder()
   try {
            var jsonString: String? = null
            val bufferedReader = BufferedReader(
                InputStreamReader(inputStream, "UTF-8")
            )
            while (bufferedReader.readLine().also { jsonString = it } != null) {
                builder.append(jsonString)
            }
 } catch (ex:Exception){
  ex.printStackTrace()

  }
   finally {
       if (inputStream != null) {
             inputStream.close()
         }
     }
         Log.w("TAG", "readJSONDataFromFile: ${String(builder)}")
        return String(builder)
    }

    fun convertJsonStringToObject(jsonString: String?): CarsResponse? {
        val gson = Gson()
        if (jsonString.isNullOrBlank()) return  null
        return gson.fromJson(jsonString, CarsResponse::class.java)

    }

//    // event: removeItem
//    fun removeItem(item: TodoItem) {
//        /* ... */
//    }
}