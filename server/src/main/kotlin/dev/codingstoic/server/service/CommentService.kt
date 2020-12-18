package dev.codingstoic.server.service

import com.github.marlonlom.utilities.timeago.TimeAgo
import dev.codingstoic.server.dto.CommentDto
import dev.codingstoic.server.entity.Comment
import dev.codingstoic.server.entity.NotificationEmail
import dev.codingstoic.server.entity.Post
import dev.codingstoic.server.entity.User
import dev.codingstoic.server.execption.PostNotFoundException
import dev.codingstoic.server.repository.CommentRepository
import dev.codingstoic.server.repository.PostRepository
import dev.codingstoic.server.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import kotlin.streams.toList


@Service
class CommentService(
    val postRepository: PostRepository, val authService: AuthService,
    val commentRepository: CommentRepository,
    val userRepository: UserRepository,
    val mailContentBuilder: MailContentBuilder,
    val mailService: MailService,
) {
    private val POST_URL = ""

    @Transactional
    fun save(commentDto: CommentDto) {
        val post = postRepository.findById(commentDto.postId!!)
            .orElseThrow { return@orElseThrow PostNotFoundException(commentDto.postId.toString()) }
        val currentUser = authService.getCurrentUser()
        commentRepository.save(mapToComment(commentDto, post, currentUser!!))

        val message = mailContentBuilder.build(post.user?.userName + "posted a comment on your post." + POST_URL)
        sendCommentNotification(message, post.user!!)
    }

    private fun sendCommentNotification(message: String, user: User) {
        mailService.sendMail(
            NotificationEmail(
                user.userName + " Commented on your post", user.email!!, message
            )
        )
    }

    private fun mapToDto(comment: Comment): CommentDto {
        return CommentDto(
            id = comment.id,
            text = comment.text,
            createdDate = TimeAgo.using(
                comment.createdDate!!.toEpochMilli()
            ),
            postId = comment.post?.id,
            userName = comment.user?.userName,
        )
    }

    private fun mapToComment(commentDto: CommentDto, post: Post, user: User): Comment {
        val comment = Comment()
        comment.post = post
        comment.user = user
        comment.text = commentDto.text
        comment.createdDate = Instant.now()
        return comment
    }

    @Transactional(readOnly = true)
    fun getAllCommentsForPost(postId: Long): List<CommentDto> {
        val post = postRepository.findById(postId)
            .orElseThrow { return@orElseThrow PostNotFoundException(postId.toString()) }
        return commentRepository.findAllByPost(post)
            .stream().map { mapToDto(it) }.toList()
    }

    @Transactional(readOnly = true)
    fun getAllCommentsForUser(userName: String): List<CommentDto> {
        val user = userRepository.findByUserName(userName)
            .orElseThrow { return@orElseThrow UsernameNotFoundException(userName) }
        return commentRepository.findAllByUser(user).stream().map {mapToDto(it)}.toList()
    }
}
