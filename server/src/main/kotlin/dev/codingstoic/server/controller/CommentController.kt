package dev.codingstoic.server.controller

import dev.codingstoic.server.dto.CommentDto
import dev.codingstoic.server.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(path = ["/api/comments"])
class CommentController(val commentService: CommentService) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createComment(@RequestBody commentDto: CommentDto): ResponseEntity<Unit> {
        commentService.save(commentDto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/by-post/{postId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllCommentsForPost(@PathVariable postId: Long): ResponseEntity<List<CommentDto>> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(commentService.getAllCommentsForPost(postId))
    }

    @GetMapping("/by-user/{userName}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllCommentsForUser(@PathVariable userName: String): ResponseEntity<List<CommentDto>> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(commentService.getAllCommentsForUser(userName))
    }
}
