package com.josrv.nlp

import com.josrv.nlp.collections.SampledSet
import com.josrv.nlp.ext.tokenize
import kotlin.math.pow

typealias Bigram = Pair<String, String>

class BigramModel(
    private val bigramProbabilities: Map<Bigram, Double>
) : LanguageModel {

    private val unigramContinuations: Map<String, SampledSet<String>> =
        bigramProbabilities.entries.groupBy(
            { it.key.first }, { it.key.second to it.value }
        ).mapValues { (_, v) -> SampledSet.of(*v.toTypedArray()) }

    override fun generateSentence(): String {
        return generateSequence("^") { t ->
            val nextToken = unigramContinuations[t]?.sample() ?: error("unigramContinuations is in a wrong state")

            nextToken.takeIf { it != "$" }
        }.drop(1).joinToString(" ").capitalize() + "."
    }

    override fun perplexity(testSentence: String): Double {
        val (result, count) = testSentence
            .tokenize()
            .zipWithNext()
            .filter { bigramProbabilities.containsKey(it) }
            .map { bigramProbabilities[it]!! }
            .fold(1.0 to 0) { (acc, count), prob ->
                (acc + 1.0 / prob) to (count + 1)
            }

        return result.pow(1.0 / count)
    }
}
