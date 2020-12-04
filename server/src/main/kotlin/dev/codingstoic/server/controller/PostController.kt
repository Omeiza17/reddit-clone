package dev.codingstoic.server.controller

import dev.codingstoic.server.dto.PostRequest
import dev.codingstoic.server.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/posts"])
class PostController(val postService: PostService) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPost(@RequestBody postRequest: PostRequest): ResponseEntity<Any> {
        postService.save(postRequest)
        return ResponseEntity(HttpStatus.CREATED)
    }
}
