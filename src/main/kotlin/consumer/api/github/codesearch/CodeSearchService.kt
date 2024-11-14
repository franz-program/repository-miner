package uf.projects.consumer.api.github.codesearch

import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import uf.projects.consumer.api.github.GHClient
import uf.projects.consumer.api.github.ratelimit.RateLimiterService


@Service
class CodeSearchService(
    private val ghClient: GHClient,
    private val rateLimiterService: RateLimiterService
) {

    val STARTING_PAGE = 1
    val ITEMS_PER_PAGE = 100

    fun runSearch(query: String): MutableList<CodeSearchItem> {
        var page = STARTING_PAGE
        val items = mutableListOf<CodeSearchItem>()
        while(true){
            try{
                items.addAll(runQuery("$query&page=$page&per_page=$ITEMS_PER_PAGE"))
                page++
            } catch(e: HttpClientErrorException.Forbidden){
                println("Rate limit exceeded. Next reset in ${rateLimiterService.getWaitTimeInSecondsCodeSearch()} seconds")
                break
            } catch(e: Exception){
                println("Error: ${e.message}")
                break
            }
        }

        return items
    }

    private fun runQuery(query: String): List<CodeSearchItem> {
        val responseType = object : ParameterizedTypeReference<CodeSearchResponse>() {}
        return ghClient.sendGetRequest<CodeSearchResponse>("/search/code?$query", responseType)!!.items
    }

}