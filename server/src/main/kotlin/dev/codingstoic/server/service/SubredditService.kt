package dev.codingstoic.server.service

import dev.codingstoic.server.dto.SubredditDto
import dev.codingstoic.server.entity.Subreddit
import dev.codingstoic.server.repository.SubredditRepository
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.streams.toList

@Slf4j
@Service
class SubredditService(val subredditRepository: SubredditRepository) {

    @Transactional
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
        return SubredditDto(description = subreddit.description!!, name = subreddit.name!!, id =
        subreddit.id!!, numberOfPost = subreddit.posts!!.size)
    }
}
