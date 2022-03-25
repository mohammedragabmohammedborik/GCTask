package com.mohammedragab.gctask

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.mohammedragab.gctask.data.Carmodel
import com.mohammedragab.gctask.mainview.DetailsCarModel
import com.mohammedragab.gctask.mainview.ItemList
import com.mohammedragab.gctask.presentationlayer.SearchViewModel
import com.mohammedragab.gctask.ui.theme.GCTaskTheme

class MainActivity : ComponentActivity() {
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainViewScreen(searchViewModel = searchViewModel)

        }
    }
}
@Composable
fun MainViewScreen(searchViewModel: SearchViewModel)
{
    var currentScreen by rememberSaveable { mutableStateOf("Booking Search") }
    GCTaskTheme() {



        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(text = currentScreen)
                },
                backgroundColor = colorResource(id = R.color.purple_700)
            )
        }) { innerPadding ->

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {

                currentScreen= searchViewModel.screenNameState.value
                val carModels= searchViewModel.carmodelsState.value
                if(!currentScreen.isNullOrEmpty()){
                    DetailsCarModel(carModels)
                }else{
                    SearchScreen(searchViewModel = searchViewModel)

                }

                Log.w("TAG", "MainViewScreen:$currentScreen " )
                Log.w("TAG", "MainViewScreen:$carModels" )


            }
        }
    }
}

@Composable
fun SearchScreen(searchViewModel: SearchViewModel){
   // val input=LocalContext.current.resources.assets.open("cars_success.json")
    val json_string = LocalContext.current.resources.assets.open("cars_success.json").bufferedReader().use{
        it.readText()
    }
    rememberSaveable{
        mutableListOf(searchViewModel.getAlCarAvailble(json_string))
    }


        ItemList(
            searchViewModel.carsmodelList!!
        , listOf("Red","Blue","Green","Yellow"), onButtonSearchClicked ={searchViewModel.searchForAvailableCar(it,json_string)}
    , onItemClicked = searchViewModel::getCarModelToNavigate)

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GCTaskTheme {
//        ItemList(
//            listOf(Carmodel("bmw","Red","EG"
//                ,2,"",20.4),Carmodel("bmw","Red","EG"
//                ,2,"",20.4),Carmodel("bmw","Red","EG"
//                ,2,"",20.4),Carmodel("bmw","Red","EG"
//                ,2,"",20.4),Carmodel("bmw","Red","EG"
//                ,2,"",20.4),Carmodel("bmw","Red","EG"
//                ,2,"",20.4)), listOf("Red","Blue","Red"),{},)

    }
}