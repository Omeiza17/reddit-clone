package dev.codingstoic.server.controller

import dev.codingstoic.server.dto.PostRequest
import dev.codingstoic.server.dto.PostResponse
import dev.codingstoic.server.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/api/posts"])
class PostController(val postService: PostService) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPost(@RequestBody postRequest: PostRequest): ResponseEntity<Unit> {
        postService.save(postRequest)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllPosts(): ResponseEntity<List<PostResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts())
    }

    @GetMapping(path = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPost(@PathVariable id: Long): ResponseEntity<PostResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id))
    }

    @GetMapping(path = ["subreddit/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPostBySubreddit(@PathVariable id: Long): ResponseEntity<List<PostResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostBySubreddit(id))
    }

    @GetMapping(path = ["user/{name}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPostByUsername(@PathVariable name: String): ResponseEntity<List<PostResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(name))
    }
}
