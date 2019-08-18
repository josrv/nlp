package com.josrv.nlp

import com.josrv.nlp.ext.computeCountsTo
import com.josrv.nlp.ext.tokenize
import java.nio.file.Files
import java.nio.file.Paths

class NGramCalculator {
    fun trainModel(sentences: List<String>): LanguageModel {

        val (unigramCounts, bigramCounts) = sentences
            .fold(mutableMapOf<String, Int>() to mutableMapOf<Pair<String, String>, Int>()) { (unigramCounts, bigramCounts), s ->
                val tokens = s.tokenize()

                tokens.computeCountsTo(unigramCounts)
                tokens.zipWithNext().computeCountsTo(bigramCounts)

                unigramCounts to bigramCounts
            }

        val bigramProbabilities = bigramCounts.mapValues { (bigram, count) ->
            val (w1, _) = bigram

            count.toDouble() / unigramCounts[w1]!!
        }

        return BigramModel(bigramProbabilities)
    }

}

fun main(args: Array<String>) {

    val bigramModel = NGramCalculator().trainModel(Files.readAllLines(Paths.get(args[0])))

    val sentence = bigramModel.generateSentence()

    val testSet = "And on the seventh day God came to the end of all his work; and on the seventh day he took his rest from all the work which he had done."

    val testSetPerplexity = bigramModel.perplexity(testSet)
    println(testSetPerplexity)
}
