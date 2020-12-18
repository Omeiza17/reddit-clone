package dev.codingstoic.server.dto

import com.fasterxml.jackson.annotation.JsonCreator
import dev.codingstoic.server.entity.VoteType

data class VoteDto @JsonCreator constructor(var voteType: VoteType?, var postId: Long?)