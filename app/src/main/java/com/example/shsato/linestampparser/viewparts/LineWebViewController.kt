package com.example.shsato.linestampparser.viewparts

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.shsato.linestampparser.R
import com.example.shsato.linestampparser.api.APILine

/**
 * Lineスタンプショップ表示のコントローラクラス
 */
class LineWebViewController : View.OnClickListener, ILineWebViewController {

    companion object {
        val URL: String = "https://store.line.me"
    }

    private var mWebView: WebView? = null

    private var mButtonGetUrl: View? = null

    private var mButtonBack: View? = null

    override fun init(root: View?) {
        root?.let {
            mWebView      = it.findViewById(R.id.line_web_view) as WebView
            mButtonBack   = it.findViewById(R.id.button_back)
            mButtonGetUrl = it.findViewById(R.id.button_get_url)
        }

        mButtonBack?.setOnClickListener(this)
        mButtonGetUrl?.setOnClickListener(this)

        mWebView?.let {
            it.setWebViewClient(object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                }

            })

            it.loadUrl(URL)
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.button_get_url -> {
                APILine().start(mWebView?.url!!, { list: MutableList<String> ->
                })
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