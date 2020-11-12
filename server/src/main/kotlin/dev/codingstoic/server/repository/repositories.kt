package dev.codingstoic.server.repository

import dev.codingstoic.server.entity.*
import org.springframework.data.jpa.repository.JpaRepository


interface PostRepository : JpaRepository<Post, Long>
interface UserRepository : JpaRepository<User, Long>
interface VoteRepository : JpaRepository<Vote, Long>
interface CommentRepository : JpaRepository<Comment, Long>
interface SubredditRepository : JpaRepository<Subreddit, Long>
interface VerificationTokenRepository : JpaRepository<VerificationToken, Long>
