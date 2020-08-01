package com.yogendra.imgurmediamvvm.model


data class Response(
    val data: List<PostsData>,
    val success: Boolean,//true or false
    val status: Int //200,404,500
)
