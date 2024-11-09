package uf.projects.search

import org.kohsuke.github.GHContentSearchBuilder
import org.kohsuke.github.GHDirection
import org.kohsuke.github.GitHub
import org.springframework.stereotype.Service

@Service
class SearchService {

    private val ghClient = GitHub.connectAnonymously()

    fun runSearch() {

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