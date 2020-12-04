package dev.codingstoic.server.controller

import dev.codingstoic.server.dto.SubredditDto
import dev.codingstoic.server.service.SubredditService
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
@RequestMapping(path = ["/api/subreddit"])
class SubredditController(val subredditService: SubredditService) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createSubreddit(@RequestBody subredditDto: SubredditDto): ResponseEntity<SubredditDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditDto))
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllSubreddits(): ResponseEntity<List<SubredditDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getAllSubreddits())
    }

    @GetMapping(path = ["/{id}"])
    fun getSubreddit(@PathVariable id: Long): ResponseEntity<SubredditDto> {
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getSubreddit(id))
    }
}
