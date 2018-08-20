package com.booklibrary.haruharu.booklibrary

import com.google.gson.annotations.SerializedName

object Model {
    // login user model
    data class User(
            @SerializedName("id")
            var id: Long? = null,
            @SerializedName("email")
            var email: String,
            var password: String,
            @SerializedName("score")
            var score: Long? = null,
            @SerializedName("created_at")
            var created_at: Long? = null

    )
    // login resultdata model
    data class ResultData(
            @SerializedName("status")
            var staus: String,
            @SerializedName("roomId")
            var roomId: Long,
//            @SerializedName("spentTypeIds")
//            var spentTypeIds:String,
            @SerializedName("user")
            var user: User,
            @SerializedName("authentication_token")
            var authentication_token: String
    )
    // login result data model
    data class Result(
            @SerializedName("error")
            var error: String
            ,
            @SerializedName("message")
            var message: String
            ,
            @SerializedName("data")
            var data: ResultData
    )
    // naver search book api request model
    data class NaverRequest(
            @SerializedName("query")
            var query:String,
            @SerializedName("display")
            var display:String,
            @SerializedName("start")
            var start:String,
            @SerializedName("sort")
            var sort:String,
            @SerializedName("d_titl")
            var d_titl:String,
            @SerializedName("d_auth")
            var d_auth:String,
            @SerializedName("d_catg")
            var d_catg:String
    ){
        constructor(query:String) : this(query=query,display="",start="",sort="",d_titl="",d_auth="",d_catg = "")
    }
    data class BookItem(
            var no:Int=-1,
            var title:String,
            var link:String,
            var image:String,
            var author:String,
            var price:Int,
            var discount:Int,
            var publisher:String,
            var description:String,
            var numOfPage:Int = 0,
            var readedPage:Int = 0
    )
    data class NaverResponse(
        var total:Int,
        var start:Int,
        var display:Int,
        var items:ArrayList<BookItem>
    )
}