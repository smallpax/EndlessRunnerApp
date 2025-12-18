package com.example.endlessrunnergame

import android.os.*
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.AlertDialog
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    // cars
    private lateinit var leftCar: AppCompatImageView
    private lateinit var centerCar: AppCompatImageView
    private lateinit var rightCar: AppCompatImageView

    // Input buttons
    private lateinit var leftBtn: ImageButton
    private lateinit var rightBtn: ImageButton

    // hearts
    private lateinit var hearts: Array<AppCompatImageView>

    // Player
    private lateinit var player: Player

    // Obstacles Matrix
    private lateinit var obstacles: Array<Array<AppCompatImageView>>

    // Game logic
    private lateinit var gameManager: GameManager

    // Game Loop
    private val handler = Handler(Looper.getMainLooper())
    private var isRunning = false

    private val tickDelayMs: Long = 450L

    private val tickRunnable = object : Runnable {
        override fun run() {

            val result = gameManager.tick(player.lane)

            renderObstacles(gameManager.getObstacleMatrix())

            if (result.didCrash) {
                renderHearts(gameManager.lives)
//                                vibrateOnCrash()
            }

            if (result.isGameOver) {
                vibrateOnCrash()
                Toast.makeText(this@MainActivity, " CRASH!", Toast.LENGTH_SHORT).show()
                stopGameLoop()
                showGameOverDialog()
                return
            }



            if (isRunning) {
                handler.postDelayed(this, tickDelayMs)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left, systemBars.top, systemBars.right, systemBars.bottom
            )
            insets
        }
        gameInit()

    }

    override fun onResume() {
        super.onResume()
        startGameLoop()
    }

    override fun onPause() {
        super.onPause()
        stopGameLoop()
    }

    private fun findViews() {
        // cars
        leftCar = findViewById(R.id.Col_0_Car)
        centerCar = findViewById(R.id.Col_1_Car)
        rightCar = findViewById(R.id.Col_2_Car)

        // buttons
        leftBtn = findViewById(R.id.main_BTN_left)
        rightBtn = findViewById(R.id.main_BTN_right)

        // hearts
        hearts = arrayOf(
            findViewById(R.id.main_IMG_heart0),
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2)
        )

        // obstacles (lane, row)
        obstacles = arrayOf(
            arrayOf(
                findViewById(R.id.main_IMG_obstacle_00),
                findViewById(R.id.main_IMG_obstacle_01),
                findViewById(R.id.main_IMG_obstacle_02),
                findViewById(R.id.main_IMG_obstacle_03),
                findViewById(R.id.main_IMG_obstacle_04),
                findViewById(R.id.main_IMG_obstacle_05),
            ), arrayOf(
                findViewById(R.id.main_IMG_obstacle_10),
                findViewById(R.id.main_IMG_obstacle_11),
                findViewById(R.id.main_IMG_obstacle_12),
                findViewById(R.id.main_IMG_obstacle_13),
                findViewById(R.id.main_IMG_obstacle_14),
                findViewById(R.id.main_IMG_obstacle_15),
            ), arrayOf(
                findViewById(R.id.main_IMG_obstacle_20),
                findViewById(R.id.main_IMG_obstacle_21),
                findViewById(R.id.main_IMG_obstacle_22),
                findViewById(R.id.main_IMG_obstacle_23),
                findViewById(R.id.main_IMG_obstacle_24),
                findViewById(R.id.main_IMG_obstacle_25),
            )
        )
    }

    private fun gameInit() {
        gameManager = GameManager()
        findViews()
        initPlayer()
        initButtons()

        hideAllObstacles()
        renderHearts(3)

        renderObstacles(gameManager.getObstacleMatrix())
    }


    private fun initPlayer() {
        player = Player(
            listOf(
                Car(leftCar), Car(centerCar), Car(rightCar)
            )
        )
        player.resetToCenter()
    }

    private fun initButtons() {
        leftBtn.setOnClickListener {
            player.moveLeft()
        }
        rightBtn.setOnClickListener {
            player.moveRight()
        }
    }

    // UI init
    private fun hideAllObstacles() {
        for (lane in obstacles.indices) {
            for (row in obstacles[lane].indices) {
                obstacles[lane][row].visibility = android.view.View.INVISIBLE
            }
        }
    }

    private fun renderHearts(lives: Int) {
        for (i in hearts.indices) {
            hearts[i].visibility =
                if (i < lives) android.view.View.VISIBLE else android.view.View.INVISIBLE
        }
    }

    private fun renderObstacles(matrix: Array<BooleanArray>) {
        for (lane in matrix.indices) {
            for (row in matrix[lane].indices) {
                obstacles[lane][row].visibility =
                    if (matrix[lane][row]) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    // Game loop
    private fun startGameLoop() {
        if (isRunning) return
        isRunning = true
        handler.postDelayed(tickRunnable, tickDelayMs)
    }

    private fun stopGameLoop() {
        isRunning = false
        handler.removeCallbacks(tickRunnable)
    }

    private fun vibrateOnCrash() {
        val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vm = getSystemService(VibratorManager::class.java)
            vm?.defaultVibrator
        } else {
            @Suppress("DEPRECATION") getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }

        if (vibrator == null || !vibrator.hasVibrator()) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION") vibrator.vibrate(150)
        }
    }

    private fun showGameOverDialog() {
        AlertDialog.Builder(this).setTitle("Game Over").setMessage("Restart?").setCancelable(false)
            .setPositiveButton("Restart") { _, _ ->
                gameManager.resetGame()
                renderHearts(GameManager.START_LIVES)
                renderObstacles(gameManager.getObstacleMatrix())
                player.resetToCenter()
                startGameLoop()
            }.setNegativeButton("Exit") { _, _ ->
                finish()
            }.show()
    }

}





