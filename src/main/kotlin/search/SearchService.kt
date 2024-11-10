package uf.projects.search

import org.kohsuke.github.GHContentSearchBuilder
import org.kohsuke.github.GHDirection
import org.kohsuke.github.GitHub
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SearchService {

    @Value("\${spring.data.pat}")
    private var ghToken: String? = null

    fun runSearch() {

        val ghClient = GitHub.connectUsingOAuth(ghToken)
        val searchPages = ghClient.searchContent()
            .q("import pettingzoo")
            .sort(GHContentSearchBuilder.Sort.INDEXED)
            .order(GHDirection.DESC)
            .extension("py")
            .list()

        searchPages.
                forEach(::println)
    }

}