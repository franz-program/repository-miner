package uf.projects

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import uf.projects.consumer.api.github.codesearch.CodeSearchItem
import uf.projects.consumer.api.github.codesearch.CodeSearchService

fun main() {
    val context = AnnotationConfigApplicationContext(AppConfig::class.java)
    val codeSearchService = context.getBean(CodeSearchService::class.java)
    val query = "order=desc&q=import pettingzoo extension:py"
    var searchResults = mutableListOf<CodeSearchItem>()

    searchResults = codeSearchService.runSearch(query)
    print(searchResults.size)
    context.close()
}