package dev.codingstoic.server.entity

import javax.persistence.*
import javax.validation.constraints.NotBlank
import java.time.Instant
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty


@Entity
class Post(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
        @NotBlank(message = "Post Name cannot be empty or Null") var postName: String,
        var url: String?,
        @Lob var description: String?,
        var voteCount: Int,
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "userId", referencedColumnName = "id")
        var user: User,
        var createdDate: Instant, @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "subredditId", referencedColumnName = "id")
        var subreddit: Subreddit
)

@Entity
class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
        @NotBlank(message = "Community name is required") var userName: String,
        @NotBlank(message = "Community name is required") var password: String,
        @Email @NotBlank(message = "Community name is required") var email: String,
        var createdDate: Instant,
        var enabled: Boolean
)

@Entity
class Subreddit(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
        @NotBlank(message = "Community name is required") var name: String,
        @NotBlank(message = "Description is required") var description: String,
        @OneToMany(fetch = FetchType.LAZY) var posts: List<Post>,
        var createdDate: Instant,
        @ManyToOne(fetch = FetchType.LAZY) var user: User,
)

@Entity
@Table(name = "token")
class VerificationToken(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
        var token: String,
        @OneToOne(fetch = FetchType.LAZY) var user: User,
        var expiryDate: Instant,
)

@Entity
class Vote(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
        var voteType: VoteType,
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "postId", referencedColumnName = "id") var post: Post,
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "userId", referencedColumnName = "id") var user: User,
)

@Entity
class Comment(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
        @NotEmpty var text: String,
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "postId", referencedColumnName = "id") var post: Post,
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "userId", referencedColumnName = "id") var user: User,
        var createdDate: Instant
)

enum class VoteType(val direction: Int) {
    DOWNVOTE(-1), UPVOTE(1),
}
