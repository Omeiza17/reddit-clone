package dev.codingstoic.server.mapper


/*
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    fun mapSubredditToDto(subreddit: Subreddit?): SubredditDto?

    fun mapPosts(numberOfPosts: List<Post?>): Int? {
        return numberOfPosts.size
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    fun mapDtoToSubreddit(subredditDto: SubredditDto?): Subreddit?
}
*/
