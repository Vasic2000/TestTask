package cz.vasic2000.testtask

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startProgress()
    }

    private fun startProgress() {
        var progressStatus = 0;

        val handler = Handler()

        Thread(Runnable {
            while (progressStatus < 100) {
                progressStatus += 1
                try {
                    Thread.sleep(50)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                handler.post({
                    progress_bar.progress = progressStatus
                    text_view.text = progressStatus.toString()
                })
            }
            handler.post({
                stopProgress()
            })
        }).start()
    }

    private fun stopProgress() {
        progress_bar.visibility = View.INVISIBLE
        button.visibility = View.VISIBLE
    }
}