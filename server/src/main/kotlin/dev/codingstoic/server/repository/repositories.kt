package dev.codingstoic.server.repository

import dev.codingstoic.server.entity.*
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface PostRepository : JpaRepository<Post, Long> {
    fun findAllBySubreddit(subreddit: Subreddit): List<Post>
    fun findByUser(user: User): List<Post>
}

interface UserRepository : JpaRepository<User, Long> {
    fun findByUserName(userName: String): Optional<User>
}

interface VoteRepository : JpaRepository<Vote, Long>
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByPost(post: Post): List<Comment>
    fun findAllByUser(user: User): List<Comment>
}

interface SubredditRepository : JpaRepository<Subreddit, Long> {
    fun findByName(name: String): Optional<Subreddit>
}

interface VerificationTokenRepository : JpaRepository<VerificationToken, Long> {
    fun findByToken(token: String): Optional<VerificationToken>
}
