package com.josrv.nlp.ext

object TextNormalization {
    val punctuation = Regex("[^A-Za-z ]")
}

fun String.tokenize(): Sequence<String> =
    sequenceOf("^") +
            replace(TextNormalization.punctuation, "")
                .splitToSequence(" ")
                .map { it.toLowerCase() } +
            sequenceOf("$")
