package dev.codingstoic.server.service

import com.github.marlonlom.utilities.timeago.TimeAgo
import dev.codingstoic.server.dto.PostRequest
import dev.codingstoic.server.dto.PostResponse
import dev.codingstoic.server.entity.Post
import dev.codingstoic.server.execption.PostNotFoundException
import dev.codingstoic.server.execption.SubredditNotFoundException
import dev.codingstoic.server.repository.*
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
@Transactional
class PostService(
    val postRepository: PostRepository,
    val subredditRepository: SubredditRepository,
    val authService: AuthService,
    val userRepository: UserRepository,
    val commentRepository: CommentRepository,
    val voteRepository: VoteRepository,
) {
    fun save(postRequest: PostRequest) {
        postRepository.save(mapToPost(postRequest))
    }

    @Transactional(readOnly = true)
    fun getPost(id: Long): PostResponse {
        val post = postRepository.findById(id).orElseThrow { PostNotFoundException(id.toString()) }
        return mapToDto(post)
    }

    @Transactional(readOnly = true)
    fun getAllPosts(): List<PostResponse> {
        return postRepository.findAll().map { mapToDto(it) }.toList()
    }

    @Transactional(readOnly = true)
    fun getPostBySubreddit(subredditId: Long): List<PostResponse> {
        val subreddit =
            subredditRepository.findById(subredditId)
                .orElseThrow { return@orElseThrow SubredditNotFoundException(subredditId.toString()) }
        val allPostBySubreddit = postRepository.findAllBySubreddit(subreddit)
        return allPostBySubreddit.map { mapToDto(it) }.toList()
    }

    @Transactional(readOnly = true)
    fun getPostsByUsername(username: String): List<PostResponse> {
        val user = userRepository.findByUserName(username)
            .orElseThrow { return@orElseThrow UsernameNotFoundException(username) }
        return postRepository.findByUser(user).map { mapToDto(it) }.toList()
    }

    private fun mapToDto(post: Post): PostResponse {
        return PostResponse(
            id = post.id,
            url = post.url,
            subredditName = post.subreddit?.name,
            userName = post.user?.userName,
            description = post.description,
            postName = post.postName,
            voteCount = post.voteCount,
            duration = TimeAgo.using(post.createdDate!!.toEpochMilli()),
            commentCount = commentRepository.findAllByPost(post).size,
            downVote = false,
            upVote = false,
        )
    }

    private fun mapToPost(postRequest: PostRequest): Post {
        val subreddit = subredditRepository.findByName(postRequest.subredditName!!)
            .orElseThrow { return@orElseThrow SubredditNotFoundException(postRequest.subredditName) }
        val currentUser = authService.getCurrentUser()
        val post = Post()
        post.description = postRequest.description
        post.postName = postRequest.postName
        post.url = postRequest.url
        post.user = currentUser
        post.subreddit = subreddit
        post.createdDate = Instant.now()
        post.voteCount = 0
        return post
    }
}
