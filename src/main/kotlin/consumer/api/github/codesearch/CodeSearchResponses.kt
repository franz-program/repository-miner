package uf.projects.consumer.api.github.codesearch


import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CodeSearchRateLimitResponse(
    val message: String,
    val documentationUrl: String,
    val status: String
)

class CodeSearchResponse(
    val items: List<CodeSearchItem>
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CodeSearchItem(
    val name: String,
    val path: String,
    val sha: String,
    val url: String,
    val gitUrl: String,
    val htmlUrl: String,
    val repository: FoundRepository,
    val score: Double
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class FoundRepository(
    val id: Long,
    val fullName: String,
    val htmlUrl: String,
    val fork: Boolean
)