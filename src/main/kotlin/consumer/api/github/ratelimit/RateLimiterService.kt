package uf.projects.consumer.api.github.ratelimit

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import uf.projects.consumer.api.github.GHClient
import java.time.Instant


@Service
class RateLimiterService(private val ghClient: GHClient) {

    fun getRateLimitStatus(): RateLimitStatus {
        val responseType = object : ParameterizedTypeReference<RateLimitStatus>() {}
        return ghClient.sendGetRequest("/rate_limit", responseType)!!
    }

    fun getWaitTimeInSecondsCodeSearch(): Long {
        val rateLimitStatus = getRateLimitStatus()
        val resetUnixTime = rateLimitStatus.getCodeSearchResetUnixTime()
        val currentUnixTime = Instant.now().epochSecond
        return (resetUnixTime - currentUnixTime) * 1000
    }
}

class RateLimitStatus(private val resources: ResourcesStatus){
    fun getCodeSearchResetUnixTime(): Long {
        return resources.codeSearch.reset
    }
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ResourcesStatus(val codeSearch: ResourceStatus)

data class ResourceStatus(val limit: Int, val remaining: Int, val reset: Long)

