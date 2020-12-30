package dev.codingstoic.server.entity

import lombok.Builder
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import dev.codingstoic.server.execption.SpringRedditException
import java.util.*


@Entity
class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var postName: String? = null
    var url: String? = null

    @Lob
    var description: String? = null
    var voteCount: Int? = null
    var createdDate: Instant? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subreddit_id", referencedColumnName = "id")
    var subreddit: Subreddit? = null

    constructor()
    constructor(
        id: Long?,
        postName: String?,
        url: String?,
        description: String?,
        voteCount: Int?,
        createdDate: Instant?,
        user: User?,
        subreddit: Subreddit?
    ) {
        this.id = id
        this.postName = postName
        this.url = url
        this.description = description
        this.voteCount = voteCount
        this.createdDate = createdDate
        this.user = user
        this.subreddit = subreddit
    }
}

@Entity
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(unique = true)
    @NotBlank(message = "username is required")
    var userName: String? = null
    var email: @Email @NotBlank(message = "username is required") String? = null
    var password: @NotBlank(message = "username is required") String? = null
    var createdDate: Instant? = null
    var isEnabled: Boolean? = null

    constructor()
    constructor(
        id: Long?, userName: String?, email: String?, password: String?, createdDate: Instant?, isEnabled:
        Boolean?
    ) {
        this.id = id
        this.userName = userName
        this.email = email
        this.password = password
        this.createdDate = createdDate
        this.isEnabled = isEnabled
    }
}

@Entity
class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var name: @NotBlank(message = "Community name is required") String? = null
    var description: @NotBlank(message = "Description is required") String? = null

    @OneToMany(fetch = FetchType.LAZY)
    var posts: List<Post>? = null
    var createdDate: Instant? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User? = null

    constructor()
    constructor(
        id: Long?, name: @NotBlank(message = "Community name is required") String?,
        description: @NotBlank(message = "Description is required") String?,
        posts: List<Post>?, createdDate: Instant?, user: User?,
    ) {
        this.id = id
        this.name = name
        this.description = description
        this.posts = posts
        this.createdDate = createdDate
        this.user = user
    }

}

@Entity
class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @NotEmpty
    var text: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "id")
    var post: Post? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    var user: User? = null
    var createdDate: Instant? = null

    constructor()
    constructor(
        id: Long?, text: @NotBlank(message = "Comment text is required") String?,
        post: Post?, createdDate: Instant?, user: User?
    ) {
        this.id = id
        this.text = text
        this.post = post
        this.user = user
        this.createdDate = createdDate
    }
}

@Entity
@Table(name = "token")
class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var token: String? = null

    @OneToOne(fetch = FetchType.LAZY)
    var user: User? = null
    var expiryDate: Instant? = null

    constructor()
    constructor(id: Long?, token: String?, user: User?, expiryDate: Instant?) {
        this.id = id
        this.token = token
        this.user = user
        this.expiryDate = expiryDate
    }

}

@Entity
class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var token: String? = null
    var createdDate: Instant? = null

    constructor()
    constructor(id: Long?, token: String?, createdDate: Instant?) {
        this.id = id
        this.token = token
        this.createdDate = createdDate
    }
}


@Entity
class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var voteType: VoteType? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "id")
    var post: Post? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    var user: User? = null

    constructor()
    constructor(id: Long?, voteType: VoteType?, post: Post?, user: User?) {
        this.id = id
        this.voteType = voteType
        this.post = post
        this.user = user
    }
}

enum class VoteType(direction: Int) {
    UPVOTE(1), DOWNVOTE(-1);

    val direction = 0

    companion object {
        fun lookup(direction: Int?): VoteType {
            return Arrays.stream(values())
                .filter { value -> value.direction == direction }
                .findAny()
                .orElseThrow { SpringRedditException("Vote not found") }
        }
    }
}
