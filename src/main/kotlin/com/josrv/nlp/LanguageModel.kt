package com.josrv.nlp

interface LanguageModel {
    fun generateSentence(): String
    fun perplexity(testSentence: String): Double
}
