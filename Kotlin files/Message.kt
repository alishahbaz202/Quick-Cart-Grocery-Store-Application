package com.alikhan.projecttrial

import java.io.Serializable

data class Message(
    val userId: String = "",
    val messageText: String = "",
    val timestamp: Long = 0,
    val audioUrl: String? = null,
    val imageUrl: String? = null,
    var key: String? = "",
    val username: String = ""

) : Serializable