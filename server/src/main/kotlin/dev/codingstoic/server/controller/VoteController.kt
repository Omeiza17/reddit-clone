package dev.codingstoic.server.controller

import dev.codingstoic.server.dto.VoteDto
import dev.codingstoic.server.service.VoteService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/votes"])
class VoteController(val voteService: VoteService) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun vote(@RequestBody voteDto: VoteDto): ResponseEntity<Unit> {
        voteService.vote(voteDto)
        return ResponseEntity(HttpStatus.OK)
    }
}
