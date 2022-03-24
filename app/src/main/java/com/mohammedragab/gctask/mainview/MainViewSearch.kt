package com.mohammedragab.gctask.mainview
import android.util.Log
import com.mohammedragab.gctask.R

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mohammedragab.gctask.data.Carmodel
import com.mohammedragab.gctask.data.SearchRequest


@Composable
fun CarItem(carmodel: Carmodel){
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        ItemContent(carmodel)
    }

}

@Composable
fun ItemContent(carmodel: Carmodel){
    var clic by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
                .clickable(onClick = {

                })

        ) {
            Text(
                text = carmodel.brand,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Row( modifier = Modifier.fillMaxWidth()){
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1.0f, false)){
                    Text(text = stringResource(R.string.unit_price),fontWeight = FontWeight.Black)
                    Text(
                        text = carmodel.unit_price.toString()
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1.0f), horizontalArrangement = Arrangement.End){
                    Text(text = stringResource(R.string.car_color),fontWeight = FontWeight.Black)
                    Text(
                        text = carmodel.color

                    )
                }
            }


        }
    }

}

@Composable
fun ItemList(carModelList:List<Carmodel>,colorList:List<String>, onButtonSearchClicked: (SearchRequest) -> Unit) {
    Column(
        // we are using column to align our
        // imageview to center of the screen.
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()){
       // ColorSelection(listOf())
        TxtField()
        TodoItemEntryInput(colorList = colorList, onButtonSearchClicked = onButtonSearchClicked)

        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {

            items(items = carModelList) { carModel ->
                CarItem(carmodel = carModel)
            }
        }
    }

}
    @Composable
    fun TxtField() {
        // we are creating a variable for
        // getting a value of our text field.
        Column(
            // we are using column to align our
            // imageview to center of the screen.
            modifier = Modifier,

            // below line is used for specifying
            // vertical arrangement.
            verticalArrangement = Arrangement.Top)
        {
           // TextFieldValue()
            var text by rememberSaveable { mutableStateOf("text") }

            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                label = { Text("search with price") }
            ,
                placeholder = { Text(text = "search with price1") }
            , modifier = Modifier
                    .padding(all = 16.dp)
            ,keyboardOptions=KeyboardOptions(keyboardType = KeyboardType.Number)

            )

        }
    }

@Composable
fun DropDownList(
    list: List<String>,
    selectedString: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column() {

    }
    Button(onClick = { expanded = !expanded }){
        Text ("DropDown")
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = null,
        )
    }
    DropdownMenu(
        modifier = Modifier.fillMaxWidth(),
        expanded = false,
        onDismissRequest = { expanded=false },
    ) {
        list.forEach {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    expanded=false
                    selectedString(it)
                }
            ) {
                Text(it, modifier = Modifier.padding(8.dp))
            }
        }
    }
}
@Composable
fun TodoItemEntryInput(colorList:List<String>, onButtonSearchClicked: (SearchRequest) -> Unit, buttonText: String = "Add") {
    val (text, onTextChange) = rememberSaveable { mutableStateOf("") }
    val (selectedColor,onSelectedItem) = rememberSaveable{ mutableStateOf("SelectColor") }

    Log.w("TAG", "TodoItemEntryInput: $text ....$onTextChange" )
    val submit = {
        onButtonSearchClicked(SearchRequest(text, selectedColor))
            onTextChange("")
        onSelectedItem("SelectColor")

    }
    Row(Modifier.fillMaxWidth()) {
        SearchInputPrice(
            text = text,
            onTextChange = onTextChange,
            submit = submit

        )
        ColorSelection(colorList = colorList,selectedColor=selectedColor, onSelectedItem = onSelectedItem)
        SearchButton(modifier =Modifier.weight(1f),onClick = submit, text = buttonText, enabled = true)


    }
}

@Composable
fun SearchInputPrice(
    text: String,
    onTextChange: (String) -> Unit,
    submit: () -> Unit
) {

            InputText(
                text = text,
                onTextChange = onTextChange,
                modifier = Modifier
                    .padding(end = 8.dp).width(150.dp),
                onImeAction = submit
            )




}
@Composable
fun ColorSelection(colorList:List<String>,selectedColor:String,onSelectedItem:(String)->Unit){
  //  var selected by remember { mutableStateOf("DropDown") }

    Column() {
        var expanded by remember { mutableStateOf(false) }

        val suggestions = listOf("Item1", "Item2", "Item3")

        Button( onClick = { expanded = !expanded }){
            Text (selectedColor)
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            colorList.forEach { label ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onSelectedItem(label)
                   // selected=label
                    //do something ...
                }) {
                    Text(text = label)
                }
            }
        }

    }


}
