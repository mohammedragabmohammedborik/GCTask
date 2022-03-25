package com.mohammedragab.gctask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mohammedragab.gctask.data.Carmodel
import com.mohammedragab.gctask.mainview.DetailsCarModel
import com.mohammedragab.gctask.mainview.ItemList
import com.mohammedragab.gctask.mainview.ShowEmptyMessage
import com.mohammedragab.gctask.presentationlayer.SearchViewModel
import com.mohammedragab.gctask.ui.theme.GCTaskTheme
import com.mohammedragab.gctask.utility.Utilities

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
    var currentScreen by rememberSaveable { mutableStateOf("") }
    val navController = rememberNavController()
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
                NavHost(
                    navController = navController,
                    startDestination = Utilities.BOOKINGSEARCH,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Utilities.BOOKINGSEARCH) {
                        currentScreen= stringResource(R.string.booking_search)
                        SearchScreen(searchViewModel = searchViewModel, navController = navController)
                    }
                    composable("${Utilities.CARSCREENDETAILS}/{brand}/{color}/{currency}/{model}/{plate_number}/{unit_price}",
                        arguments=listOf(
                            navArgument("brand") {
                                type = NavType.StringType
                            },
                            navArgument("color") {
                                type = NavType.StringType
                            },
                            navArgument("currency") {
                                type = NavType.StringType
                            },
                            navArgument("model") {
                                type = NavType.IntType
                            },
                            navArgument("plate_number") {
                                type = NavType.StringType
                            },
                            navArgument("unit_price") {
                                type = NavType.FloatType
                            }
                        )) {backStackEntry->
                        currentScreen= stringResource(R.string.car_model_details)
                        val brandName=backStackEntry.arguments?.getString("brand")
                        val color=backStackEntry.arguments?.getString("color")
                        val currency=backStackEntry.arguments?.getString("currency")
                        val model=backStackEntry.arguments?.getInt("model")
                        val plate_number=backStackEntry.arguments?.getString("plate_number")
                        val unit_price=backStackEntry.arguments?.getFloat("unit_price")

                        DetailsCarModel(Carmodel(brandName!!,color!!,currency!!,model!!,plate_number!!,unit_price!!))
                    }

                    composable("${Utilities.EmptyScreen}/{message}",
                        arguments=listOf(
                        navArgument("message") {
                            type = NavType.StringType
                        }
                    )) {backStackEntry->
                        currentScreen= stringResource(R.string.booking_search)
                        val message=backStackEntry.arguments?.getString("message")
                        message?.let {

                            ShowEmptyMessage(it)
                        }
                    }

                }

            }
        }

    }
}

@Composable
fun SearchScreen(searchViewModel: SearchViewModel,navController: NavHostController){
   // val input=LocalContext.current.resources.assets.open("cars_success.json")
    val jsonStringCarSuccess = LocalContext.current.resources.assets.open("cars_success.json").bufferedReader().use{
        it.readText()
    }

    rememberSaveable{
        mutableListOf(searchViewModel.getAlCarAvailble(jsonStringCarSuccess))
    }

    ItemList(
            searchViewModel.carsmodelList!!
        , Utilities.colorList, buttonText = stringResource(R.string.search),onButtonSearchClicked ={searchViewModel.searchForAvailableCar(it,jsonStringCarSuccess)}
    , onItemClicked = {carmodel-> navigatToOtherScreen(navController = navController,
                "${Utilities.CARSCREENDETAILS}/${carmodel.brand}/${carmodel.color}/${carmodel.currency}/${carmodel.model}/${carmodel.plate_number}/${carmodel.unit_price}")}
        ,searchViewModel::updateCheckForEmptyData)

    var meesagee by rememberSaveable { mutableStateOf("") }


    meesagee=   searchViewModel.messageEmptyData
    if (meesagee.isNotEmpty()){
        searchViewModel.updateCheckForEmptyData("")
        navigatToOtherScreen(navController = navController,"${Utilities.EmptyScreen}/$meesagee")

    }


}
private fun navigatToOtherScreen(navController: NavHostController, screenName: String) {
    navController.navigate(screenName)
}

