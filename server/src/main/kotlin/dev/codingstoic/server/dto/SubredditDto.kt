package dev.codingstoic.server.dto

import com.fasterxml.jackson.annotation.JsonCreator

data class SubredditDto @JsonCreator constructor(var id: Long, var name: String, var description: String, var
numberOfPost: Int)
