package dev.codingstoic.server.service

import dev.codingstoic.server.dto.VoteDto
import dev.codingstoic.server.entity.Post
import dev.codingstoic.server.entity.Vote
import dev.codingstoic.server.entity.VoteType
import dev.codingstoic.server.execption.PostNotFoundException
import dev.codingstoic.server.execption.SpringRedditException
import dev.codingstoic.server.repository.PostRepository
import dev.codingstoic.server.repository.VoteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class VoteService(
    val postRepository: PostRepository,
    val voteRepository: VoteRepository,
    val authService: AuthService
) {

    @Transactional
    fun vote(voteDto: VoteDto) {
        val post = postRepository.findById(voteDto.postId!!)
            .orElseThrow { PostNotFoundException("Post Not Found with ID - " + voteDto.postId) }
        val voteByPostAndUser: Optional<Vote> =
            voteRepository.findTopByPostAndUserOrderByVoteTypeDesc(post, authService.getCurrentUser())
        if (voteByPostAndUser.isPresent &&
            voteByPostAndUser.get().voteType == voteDto.voteType
        ) {
            throw SpringRedditException(
                ("You have already "
                        + voteDto.voteType) + "'d for this post"
            )
        }
        if (VoteType.UPVOTE == voteDto.voteType) {
            post.voteCount = post.voteCount!! + 1
        } else {
            post.voteCount = post.voteCount!! - 1
        }
        voteRepository.save(mapToVote(voteDto, post))
        postRepository.save(post)
    }

    private fun mapToVote(voteDto: VoteDto, post: Post): Vote {
        val vote = Vote()
        vote.voteType = voteDto.voteType
        vote.post = post
        vote.user = authService.getCurrentUser()
        return vote
    }
}
