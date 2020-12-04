package dev.codingstoic.server.service

import dev.codingstoic.server.dto.PostRequest
import dev.codingstoic.server.dto.PostResponse
import dev.codingstoic.server.entity.Post
import dev.codingstoic.server.execption.SpringRedditException
import dev.codingstoic.server.repository.PostRepository
import dev.codingstoic.server.repository.SubredditRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
@Transactional
class PostService(
    val postRepository: PostRepository,
    val subredditRepository: SubredditRepository,
    val authService: AuthService
) {
    fun save(postRequest: PostRequest) {
        postRepository.save(mapToPost(postRequest))
    }

    fun getPost(id: Long): PostResponse {
        val post = postRepository.findById(id).orElseThrow { SpringRedditException("Post with $id not found") }
        return mapToDto(post)
    }

    private fun mapToDto(post: Post): PostResponse {
        return PostResponse(post.id!!, post.voteCount, post.url, post.postName, post.description)
    }

    private fun mapToPost(postRequest: PostRequest): Post {
        val subreddit = subredditRepository.findByName(postRequest.subredditName!!)
            .orElseThrow { SpringRedditException("Subreddit with name ${postRequest.subredditName} not found") }
        val currentUser = authService.getCurrentUser()
        val post = Post()
        post.description = postRequest.description
        post.postName = postRequest.postName
        post.url = postRequest.url
        post.user = currentUser;
        post.subreddit = subreddit
        post.createdDate = Instant.now()
        post.voteCount = 0
        return post
    }
}
