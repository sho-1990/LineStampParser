package com.example.shsato.linestampparser.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.example.shsato.linestampparser.R
import com.example.shsato.linestampparser.viewparts.LineWebViewController

/**
 * Lineにアクセスするフラグメント
 */
class LineFragment : Fragment() {


    private lateinit var mController: LineWebViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_line, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mController = object: LineWebViewController() {
            override fun willReceivedTitle(view: WebView?, title: String?) {
                super.willReceivedTitle(view, title)
                val act: AppCompatActivity = activity as AppCompatActivity
                title?.let {
                    act.supportActionBar?.title = it
                }
            }
        }

        mController.init(view)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                mController.back()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        /**
         * @return A new instance of fragment LineFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): LineFragment {
            val fragment = LineFragment()
            return fragment
        }
    }
}
