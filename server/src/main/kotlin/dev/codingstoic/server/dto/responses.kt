package dev.codingstoic.server.dto

import com.fasterxml.jackson.annotation.JsonCreator

data class PostResponse @JsonCreator constructor(
    val id: Long?,
    val voteCount: Int?,
    val url: String?,
    val postName: String?,
    val description: String?
)
