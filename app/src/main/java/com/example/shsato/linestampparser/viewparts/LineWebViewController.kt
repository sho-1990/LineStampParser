package com.example.shsato.linestampparser.viewparts

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.shsato.linestampparser.R

/**
 * Lineスタンプショップ表示のコントローラクラス
 */
open class LineWebViewController : View.OnClickListener, ILineWebViewController {

    private companion object {
        val URL: String = "https://store.line.me"

        val JS_GET_STAMP: String = "javascript:" +
                "var sec = document.getElementsByClassName('mdCMN04ImgBox');" +
                "var d = sec[0].lastElementChild;" +
                "d = d.lastElementChild;" +
                "var e = d.firstElementChild;" +
                "android.getStampUrl(e.src);"
    }

    private var mWebView: WebView? = null

    private var mButtonGetUrl: View? = null

    private var mModalStamp: ViewGroup? = null

    private var mSelectStampUrl: String? = null

    open fun willReceivedTitle(view: WebView?, title: String?) {}

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    override fun init(root: View?) {

        root?.let {
            mWebView      = it.findViewById(R.id.line_web_view) as WebView
            mButtonGetUrl = it.findViewById(R.id.button_get_url)
            mModalStamp = it.findViewById(R.id.modal_stamp) as ViewGroup
        }

        mButtonGetUrl?.setOnClickListener(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        setupModal(mModalStamp)
        setupWebView(mWebView)

    }

    override fun back() {
        mWebView?.let {
            if (it.canGoBack()) {
                it.goBack()
            }
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.button_get_url -> {
                mWebView?.loadUrl(JS_GET_STAMP)
            }
        }
    }

    private fun setupModal(modal: ViewGroup?) {
        modal?.let {
            it.visibility = View.GONE
            val close: Button = it.findViewById(R.id.button_close) as Button
            close.setOnClickListener {
                view: View? ->
                it.visibility = View.GONE
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(webView: WebView?) {
        webView?.let {
            webView.settings.javaScriptEnabled = true
            webView.setWebViewClient(object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    Log.d("test", request.toString())
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                }

            })

            webView.setWebChromeClient(object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    willReceivedTitle(view, title)
                }
            })

            webView.addJavascriptInterface(JsRelay(), "android")

            webView.loadUrl(URL)
        }
    }

    inner class JsRelay {
        @JavascriptInterface
        fun getStampUrl(imageUrl: String?) {
            mSelectStampUrl = imageUrl
            handleStamp(imageUrl)
        }

        private fun handleStamp(imageUrl: String?) {
            Handler(Looper.getMainLooper()).post {
                mModalStamp?.let {
                    it.visibility = View.VISIBLE
                    val stampView: ImageView = it.findViewById(R.id.image_stamp) as ImageView
                    Glide.with(mModalStamp?.context).load(imageUrl).into(stampView)
                }
            }
        }
    }
}
