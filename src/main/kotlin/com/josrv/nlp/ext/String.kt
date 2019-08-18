package com.josrv.nlp.ext

import com.josrv.nlp.TextNormalization

fun String.tokenize(): Sequence<String> =
    sequenceOf("^") +
            replace(TextNormalization.punctuation, "")
                .splitToSequence(" ")
                .map { it.toLowerCase() } +
            sequenceOf("$")
