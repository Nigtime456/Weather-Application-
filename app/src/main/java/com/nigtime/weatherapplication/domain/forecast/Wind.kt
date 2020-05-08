/*
 * Сreated by Igor Pokrovsky. 2020/5/8
 */

package com.nigtime.weatherapplication.domain.forecast

class Wind constructor(speed: Double, dirDegree: Int, dirAbbreviated: Int) {


    enum class CardinalDirection {
        N, NNE, NE, ENE,
        E, ESE, SE, SSE,
        S, SSW, SW, WSW,
        W, WNW, NW, NNW;

        /**
        directions:
        N   -> 348.75 - 11.25
        NNE -> 11.25  - 35.75
        NE  -> 35.75  - 56.25
        ENE -> 56.25  - 78.75
        E   -> 78.75  - 101.25
        ESE -> 101.25 - 123.75
        SE  -> 123.75 - 146.25
        SSE -> 146.25 - 168.75
        S   -> 168.75 - 191.25
        SSW -> 191.25 - 213.75
        SW  -> 213.75 - 236.25
        WSW -> 236.25 - 258.75
        W   -> 258.75 - 281.25
        WNW -> 281.25 - 303.75
        NW  -> 303.75 - 326.25
        NNW -> 326.25 - 348.75
         */

        companion object {
            fun fromDegrees(degrees: Int): CardinalDirection {
                val num = ((degrees + 11.25) / 22.5).toInt() % 16
                return values()[num]
            }
        }
    }
}