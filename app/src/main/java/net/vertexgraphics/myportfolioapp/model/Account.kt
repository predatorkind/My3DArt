package net.vertexgraphics.myportfolioapp.model

import androidx.annotation.DrawableRes

class Account (
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    @DrawableRes val avatar: Int,

        ){
    val fullName: String = "$firstName $lastName"
}