package com.josrv.nlp.collections

class SampledSet<T : Any> private constructor() : HashMap<T, Double>() {

    fun sample(): T {
        val rand = Math.random()
        val iterator = entries.iterator()

        return generateSequence(0.0 to iterator.next()) { (sum, el) ->
            val res = sum + el.value
            if (res > rand) null else (res to iterator.next())
        }.last().second.key
    }

    companion object {
        fun <T : Any> of(vararg pairs: Pair<T, Double>): SampledSet<T> {
            return pairs.associateTo(SampledSet()) { it }
        }
    }
}
