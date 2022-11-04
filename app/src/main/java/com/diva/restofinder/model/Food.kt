package com.diva.restofinder.model

import com.google.firebase.database.Exclude

data class Food (
    var name:String? = null,
     var address:String? = null,
     var phone:String? = null,
     var imageUrl:String? = null,
     var description:String? = null,
     @get:Exclude
     @set:Exclude
     var key:String? = null
)