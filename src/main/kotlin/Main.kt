package uf.projects

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import uf.projects.search.SearchService

fun main() {
    val context = AnnotationConfigApplicationContext(AppConfig::class.java)
    val searchService = context.getBean(SearchService::class.java)
    searchService.runSearch()
    context.close()
}