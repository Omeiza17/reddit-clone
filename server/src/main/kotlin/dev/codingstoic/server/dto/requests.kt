package dev.codingstoic.server.dto

import com.fasterxml.jackson.annotation.JsonCreator

data class PostRequest @JsonCreator constructor(
    var id: Long?, var subredditName: String?, var postName: String?,
    var url: String?,
    var description: String?,
)
