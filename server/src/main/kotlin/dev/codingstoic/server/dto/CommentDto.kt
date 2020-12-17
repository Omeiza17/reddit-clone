package dev.codingstoic.server.dto

import com.fasterxml.jackson.annotation.JsonCreator

data class CommentDto @JsonCreator constructor(
    var id: Long?, var postId: Long?, var text: String?,
    var userName: String?, var createdDate: String?,
)
