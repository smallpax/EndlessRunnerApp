package com.example.endlessrunnergame

import kotlin.random.Random

class GameManager {

        companion object {
                const val LANES = 3
                const val ROWS = 6
                const val START_LIVES = 3
        }

        data class TickResult(val didCrash: Boolean, val isGameOver: Boolean)

        var lives: Int = START_LIVES
                private set

        private val occupied: Array<BooleanArray> =
                Array(LANES) { BooleanArray(ROWS) { false } }

        fun getObstacleMatrix(): Array<BooleanArray> =
                Array(LANES) { lane -> occupied[lane].clone() }

        fun resetGame() {
                lives = START_LIVES
                clearObstacles()
        }

        fun tick(playerLane: Int): TickResult {
                shiftObstaclesDown()
                spawnObstacleAtTop()

                val safeLane = playerLane.coerceIn(0, LANES - 1)
                val didCrash = checkCollision(safeLane)
                val isGameOver = (lives == 0)

                return TickResult(didCrash, isGameOver)
        }


        private fun clearObstacles() {
                for (lane in 0 until LANES) {
                        for (row in 0 until ROWS) {
                                occupied[lane][row] = false
                        }
                }
        }

        private fun shiftObstaclesDown() {
                for (lane in 0 until LANES) {
                        for (row in ROWS - 1 downTo 1) {
                                occupied[lane][row] = occupied[lane][row - 1]
                        }
                        occupied[lane][0] = false
                }
        }

        private fun spawnObstacleAtTop() {
                if (Random.nextBoolean()) {
                        val lane = Random.nextInt(LANES)
                        occupied[lane][0] = true
                }
        }

        private fun checkCollision(playerLane: Int): Boolean {
                val bottomRow = ROWS - 1
                val hit = occupied[playerLane][bottomRow]
                if (hit) {
                        lives--
                        occupied[playerLane][bottomRow] = false
                }
                return hit
        }
}
