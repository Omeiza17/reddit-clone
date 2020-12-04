package dev.codingstoic.server.service

import dev.codingstoic.server.dto.SubredditDto
import dev.codingstoic.server.entity.Subreddit
import dev.codingstoic.server.execption.SpringRedditException
import dev.codingstoic.server.repository.SubredditRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.streams.toList

@Service
@Transactional
class SubredditService(val subredditRepository: SubredditRepository) {

    fun save(subredditDto: SubredditDto): SubredditDto {
        val subreddit = mapSubredditDto(subredditDto)
        val savedSubreddit = subredditRepository.save(subreddit)
        subredditDto.id = savedSubreddit.id!!
        return subredditDto
    }

    private fun mapSubredditDto(subredditDto: SubredditDto): Subreddit {
        val subreddit = Subreddit()
        subreddit.description = subredditDto.description
        subreddit.name = subredditDto.name
        return subreddit
    }

    @Transactional(readOnly = true)
    fun getAllSubreddits(): List<SubredditDto> {
        return subredditRepository.findAll().stream().map { mapToDto(it) }.toList()
    }

    private fun mapToDto(subreddit: Subreddit): SubredditDto {
        return SubredditDto(
            description = subreddit.description!!, name = subreddit.name!!, id =
            subreddit.id!!, numberOfPosts = subreddit.posts!!.size
        )
    }

    @Transactional(readOnly = true)
    fun getSubreddit(id: Long): SubredditDto {
        val subreddit =
            subredditRepository.findById(id).orElseThrow { SpringRedditException("No subreddit found with id $id") }
        return mapToDto(subreddit = subreddit)
    }
}
