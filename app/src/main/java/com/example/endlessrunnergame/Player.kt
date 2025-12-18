package com.example.endlessrunnergame

class Player( private val cars: List<Car>) {
        var lane = 1
        fun moveLeft() {if (lane > 0) lane-- ; update()}
        fun moveRight() {if (lane < 2) lane++ ; update()}
        fun resetToCenter() { lane = 1; update() }
        private fun update(){
                cars.forEach { it.invisible() }
                cars[lane].visible()
        }
}