package uf.projects.consumer.api.github

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GHClient {

    val BASE_URL = "https://api.github.com"

    @Value("\${spring.data.pat}")
    private var ghToken: String = ""

    fun <T> sendGetRequest(urlPath: String, responseType: ParameterizedTypeReference<T>): T? {
        val url = "$BASE_URL$urlPath"
        val headers = HttpHeaders().apply { setBearerAuth(ghToken) }
        return RestTemplate().exchange(
            url,
            HttpMethod.GET,
            HttpEntity<String>(headers),
            responseType
        ).body
    }
}