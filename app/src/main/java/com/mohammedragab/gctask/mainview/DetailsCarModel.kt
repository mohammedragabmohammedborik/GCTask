package com.mohammedragab.gctask.mainview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mohammedragab.gctask.data.Carmodel
import com.mohammedragab.gctask.R


@Composable
fun DetailsCarModel(carmodel: Carmodel){
    Column(
        modifier = Modifier
            .padding(12.dp)
            )
    {
        Text(
            text = carmodel.brand,
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
            Row(
                modifier = Modifier
                    .weight(1.0f, false)){
                Text(text = stringResource(R.string.unit_price),fontWeight = FontWeight.Black)
                Text(
                    text = carmodel.unit_price.toString()
                )
            }
            Row(
                modifier = Modifier
                    .weight(1.0f,false), horizontalArrangement = Arrangement.End){
                Text(text = stringResource(R.string.car_color),fontWeight = FontWeight.Black)
                Text(
                    text = carmodel.color
                )
            }
        Row(
            modifier = Modifier
                .weight(1.0f,false), horizontalArrangement = Arrangement.End){
            Text(text = stringResource(R.string.currency),fontWeight = FontWeight.Black)
            Text(
                text = carmodel.currency.toString().trim()) }
        Row(
            modifier = Modifier
                .weight(1.0f,false), horizontalArrangement = Arrangement.End){
            Text(text = stringResource(R.string.model),fontWeight = FontWeight.Black)
            Text(
                text = carmodel.model.toString()
            )
        }
        Row(
            modifier = Modifier
                .weight(1.0f), horizontalArrangement = Arrangement.End){
            Text(text = stringResource(R.string.plate_number),fontWeight = FontWeight.Black)
            Text(
                text = carmodel.plate_number


            )
        }



    }
}