package org.hyperskill.stopwatch

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.stopwatch.colorgenerator.ColorGenerator
import org.hyperskill.stopwatch.colorgenerator.RandomColorGenerator
import org.hyperskill.stopwatch.notifications.TimerNotification
import org.hyperskill.stopwatch.timer.HandlerTimer
import org.hyperskill.stopwatch.timer.Timer
import org.hyperskill.stopwatch.timer.TimerCallback
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var timer: Timer
    private var upperLimit = Long.MAX_VALUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Init timer and interaction
        timer = HandlerTimer()

        findViewById<Button>(R.id.settingsButton)
            .setOnClickListener {
                val contentView = LayoutInflater.from(this).inflate(R.layout.settings_dialog, null, false)
                AlertDialog.Builder(this)
                    .setMessage("Set upper limit in seconds")
                    .setView(contentView)
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        val editText = contentView.findViewById<EditText>(R.id.upperLimitEditText)
                        upperLimit = editText.text.toString().toLong()
                    }
                    .setNegativeButton(android.R.string.cancel) { _, _ ->}
                    .show()
            }

        findViewById<Button>(R.id.startButton)
            .setOnClickListener {
                timer.start()
            }
        findViewById<Button>(R.id.resetButton)
            .setOnClickListener {
                timer.reset()
            }

        // Init notifications (channel and prepare notification for timer)
        val timerNotification = TimerNotification(this)
        // Init timer callback
        val settings = findViewById<Button>(R.id.settingsButton)
        val textTimer = findViewById<TextView>(R.id.textView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val colorGenerator: ColorGenerator = RandomColorGenerator(Random(System.currentTimeMillis()))

        timer.setCallback(object : TimerCallback {
            override fun onStart() {
                progressBar.visibility = View.VISIBLE
                settings.isEnabled = false
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onUpdate(difference: Int) {
                if (upperLimit < difference) {
                    textTimer.setTextColor(Color.RED)
                    if (upperLimit > 0)
                        timerNotification.notifyOnUpperLimit(this@MainActivity)
                }
                textTimer.text = buildString {
                    append((difference / 60).toString().padStart(2, '0'))
                    append(':')
                    append((difference % 60).toString().padStart(2, '0'))
                }
                progressBar
                    .indeterminateTintList = ColorStateList.valueOf(colorGenerator.generateColor())
            }

            override fun onStop() {
                textTimer.setTextColor(Color.BLACK)
                progressBar.visibility = View.INVISIBLE
                textTimer.text = getString(R.string.timer_start)
                settings.isEnabled = true
            }
        })
    }
}