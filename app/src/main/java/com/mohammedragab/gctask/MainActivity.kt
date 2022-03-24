package com.mohammedragab.gctask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.mohammedragab.gctask.data.Carmodel
import com.mohammedragab.gctask.mainview.ItemList
import com.mohammedragab.gctask.presentationlayer.SearchViewModel
import com.mohammedragab.gctask.ui.theme.GCTaskTheme

class MainActivity : ComponentActivity() {
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GCTaskTheme {
                val context = LocalContext.current
             // context.resources.openRawResource(R.raw.cc)
                //resources.openRawResource(R.raw.)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    SearchScreen(searchViewModel = searchViewModel)

                }
            }
        }
    }
}

@Composable
fun SearchScreen(searchViewModel: SearchViewModel){
    val input=LocalContext.current.resources.assets.open("cars_success.json")

    searchViewModel.readJSONDataFromFile(input)

    ItemList(
        listOf(Carmodel("bmw","Red","EG"
            ,2,"",20.4),Carmodel("bmw","Red","EG"
            ,2,"",20.4),Carmodel("bmw","Red","EG"
            ,2,"",20.4),Carmodel("bmw","Red","EG"
            ,2,"",20.4),Carmodel("bmw","Red","EG"
            ,2,"",20.4),Carmodel("bmw","Red","EG"
            ,2,"",20.4)), listOf("Red","Blue","Red"), onButtonSearchClicked ={searchViewModel.searchForAvailableCar(it)}
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GCTaskTheme {
        ItemList(
            listOf(Carmodel("bmw","Red","EG"
                ,2,"",20.4),Carmodel("bmw","Red","EG"
                ,2,"",20.4),Carmodel("bmw","Red","EG"
                ,2,"",20.4),Carmodel("bmw","Red","EG"
                ,2,"",20.4),Carmodel("bmw","Red","EG"
                ,2,"",20.4),Carmodel("bmw","Red","EG"
                ,2,"",20.4)), listOf("Red","Blue","Red"),{})
    }
}