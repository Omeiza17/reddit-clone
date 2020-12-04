package dev.codingstoic.server.dto

import com.fasterxml.jackson.annotation.JsonCreator

data class PostResponse @JsonCreator constructor(
    val id: Long?,
    val voteCount: Int?,
    val commentCount: Int?,
    val url: String?,
    val postName: String?,
    val description: String?,
    val userName: String?,
    val subredditName: String?,
    val duration: String?,
    val upVote: Boolean?,
    val downVote: Boolean?,
)
