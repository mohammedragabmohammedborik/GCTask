package com.mohammedragab.gctask.data.SearchStatus

import com.mohammedragab.gctask.data.Carmodel

class SearchStatus() : GenralStatus() {
    class SuccessResponse(carList: List<Carmodel>?) : GenralStatus()
    class ErrorCode(message: String) : GenralStatus()
    class Error(message: String) : GenralStatus()
}
