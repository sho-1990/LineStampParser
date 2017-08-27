package com.example.shsato.linestampparser.viewparts

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.webkit.*
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

        class JsRelay {
            @JavascriptInterface
            fun getStampUrl(imageUrl: String?) {
                Log.d("test", imageUrl!!.toString())
            }
        }
    }

    private var mWebView: WebView? = null

    private var mButtonGetUrl: View? = null

    open fun willReceivedTitle(view: WebView?, title: String?) {}

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    override fun init(root: View?) {

        root?.let {
            mWebView      = it.findViewById(R.id.line_web_view) as WebView
            mButtonGetUrl = it.findViewById(R.id.button_get_url)
        }

        mButtonGetUrl?.setOnClickListener(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        mWebView?.let {

            it.settings.javaScriptEnabled = true
            it.setWebViewClient(object : WebViewClient() {
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

            it.setWebChromeClient(object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    willReceivedTitle(view, title)
                }
            })

            it.addJavascriptInterface(JsRelay(), "android")

            it.loadUrl(URL)
        }
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
}