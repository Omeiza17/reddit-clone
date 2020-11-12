package dev.codingstoic.server.entity

import javax.persistence.*
import javax.validation.constraints.NotBlank
import java.time.Instant
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty


@Entity
class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null
    private var postName: String? = null
    private var url: String? = null

    @Lob
    private var description: String? = null
    private var voteCount: Int? = null
    private var createdDate: Instant? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private var user: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subreddit_id", referencedColumnName = "id")
    private var subreddit: Subreddit? = null

    constructor()
    constructor(id: Long?, postName: String?, url: String?, description: String?, voteCount: Int?, createdDate: Instant?, user: User?, subreddit: Subreddit?) {
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
    var userName: @NotBlank(message = "Community name is required") String? = null
    var email: @Email @NotBlank(message = "Community name is required") String? = null
    var password: @NotBlank(message = "Community name is required") String? = null
    var createdDate: Instant? = null
    var isEnabled: Boolean? = null

    constructor()
    constructor(id: Long?, userName: String?, email: String?, password: String?, createdDate: Instant?, isEnabled:
    Boolean?) {
        this.id = id
        this.userName = userName
        this.email = email
        this.password = password
        this.createdDate = createdDate
        this.isEnabled = isEnabled
    }
}

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
