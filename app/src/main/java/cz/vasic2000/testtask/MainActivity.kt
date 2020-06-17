package cz.vasic2000.testtask

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        web_view.webViewClient = MyWebViewClient()
        Listeners()
    }

    private fun Listeners() {
        btnStart.setOnClickListener {
            startProgress()
        }

        text_privace_policy.setOnClickListener {
            Toast.makeText(applicationContext, "Approve", Toast.LENGTH_SHORT).show()
        }

        btnTest1.setOnClickListener {
            val handler = Handler()

            Thread(Runnable {
                val doc: Document = Jsoup.connect("http://178.128.242.32/test").get()
                val strUr = doc.body().text()

                handler.post {
                    if (!strUr.equals(""))
                        callWebView(strUr.toString())
                    else
                        callWebView("file:///android_asset/tinder.jpg")
                }
            }).start()
        }

        btnTest2.setOnClickListener {
            val handler = Handler()

            Thread(Runnable {
                val doc: Document = Jsoup.connect("http://178.128.242.32/test2").get()
                val strUr = doc.body().text()


                handler.post {
                    if (!strUr.equals(""))
                        callWebView(strUr.toString())
                    else
                        callWebView("file:///android_asset/tinder.jpg")
                }
            }).start()
        }
    }

    private fun callWebView(url: String) {
        var settings =  web_view.settings
        val display =
            (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        val scale = 3549/display.width
        web_view.setInitialScale(scale)
        web_view.loadUrl(url)
    }

    private fun startProgress() {
        var progressStatus = 0
        val handler = Handler()
        Thread(Runnable {
            handler.post {
                text_privace_policy.visibility = View.INVISIBLE
                progress_bar.visibility = View.VISIBLE
                text_view.visibility = View.VISIBLE
                btnStart.visibility = View.INVISIBLE
            }
            while (progressStatus < 100) {
                progressStatus += 1
                try {
                    Thread.sleep(50)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                handler.post {
                    progress_bar.progress = progressStatus
                    text_view.text = progressStatus.toString()
                }
            }
            handler.post {
                stopProgress()
            }
        }).start()
    }

    private fun stopProgress() {
        progress_bar.visibility = View.INVISIBLE
        text_privace_policy.visibility = View.INVISIBLE
        text_view.visibility = View.INVISIBLE
        btnTest1.visibility = View.VISIBLE
        btnTest2.visibility = View.VISIBLE
        web_view.visibility = View.VISIBLE
        web_view.settings.javaScriptEnabled
    }

    override fun onBackPressed() {
        if (web_view.canGoBack()) {
            web_view.goBack()
        } else {
            super.onBackPressed()
        }
    }
}

