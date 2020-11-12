package dev.codingstoic.server.repository

import dev.codingstoic.server.entity.*
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface PostRepository : JpaRepository<Post, Long>
interface UserRepository : JpaRepository<User, Long> {
    fun findByUserName(userName: String): Optional<User>
}

interface VoteRepository : JpaRepository<Vote, Long>
interface CommentRepository : JpaRepository<Comment, Long>
interface SubredditRepository : JpaRepository<Subreddit, Long>
interface VerificationTokenRepository : JpaRepository<VerificationToken, Long> {
    fun findByToken(token: String): Optional<VerificationToken>
}
