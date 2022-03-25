package com.mohammedragab.gctask.data

import java.io.Serializable

data class CarsResponse(val status:Status?=null,val cars:List<Carmodel>?=null):Serializable
