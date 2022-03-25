package com.mohammedragab.gctask.data

import java.io.Serializable
data class Carmodel(
    
    val brand: String,
    val color: String,
    val currency: String,
    val model: Int,
    val plate_number: String,
    val unit_price: Double
):Serializable