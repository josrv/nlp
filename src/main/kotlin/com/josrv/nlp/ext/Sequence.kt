package com.josrv.nlp.ext

fun <T> Sequence<T>.computeCountsTo(dest: MutableMap<T, Int>) {
    fold(dest) { acc, t ->
        acc[t] = (acc[t] ?: 0) + 1
        acc
    }
}
