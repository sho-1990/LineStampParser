package com.example.shsato.linestampparser.viewparts

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.shsato.linestampparser.R

/**
 * Lineスタンプショップ表示のコントローラクラス
 */
class LineWebViewController : View.OnClickListener, ILineWebViewController {

    private companion object {
        val URL: String = "https://store.line.me"

        val JS_GET_STAMP: String = "javascript:" +
                "var sec = document.getElementById('FnStickerDetail');" +
                "if (sec.childElementCount > 2) {" +
                "  var d = sec.lastElementChild;" +
                "  var e = d.firstElementChild;" +
                "  console.log(d.tagName);" +
                "  console.log(e.tagName);" +
                "  android.getStampUrl(e.toString());" +
                "}"


        class JsRelay {
            @JavascriptInterface
            fun getStampUrl(url: String?) {
                Log.d("test", url!!.toString())
            }
        }
    }

    private var mWebView: WebView? = null

    private var mButtonGetUrl: View? = null

    private var mButtonBack: View? = null

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    override fun init(root: View?) {

        root?.let {
            mWebView      = it.findViewById(R.id.line_web_view) as WebView
            mButtonBack   = it.findViewById(R.id.button_back)
            mButtonGetUrl = it.findViewById(R.id.button_get_url)
        }

        mButtonBack?.setOnClickListener(this)
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

            it.addJavascriptInterface(JsRelay(), "android")

            it.loadUrl(URL)
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.button_get_url -> {
                mWebView?.loadUrl(JS_GET_STAMP)
            }
            R.id.button_back -> {
                mWebView?.run {
                    if (canGoBack()) {
                        goBack()
                    }
                }
            }
        }
    }
}