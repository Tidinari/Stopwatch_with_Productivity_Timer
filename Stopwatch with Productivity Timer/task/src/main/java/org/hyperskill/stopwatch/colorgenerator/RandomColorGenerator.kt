package org.hyperskill.stopwatch.colorgenerator

import kotlin.random.Random

class RandomColorGenerator(private val random: Random): ColorGenerator {

    override fun generateColor(): Int {
        // Magic numbers: -16777216 is black and -1 is white
        return random.nextInt(-16777216, -1)
    }
}