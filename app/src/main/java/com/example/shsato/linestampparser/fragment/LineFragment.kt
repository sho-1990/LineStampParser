package com.example.shsato.linestampparser.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shsato.linestampparser.R
import com.example.shsato.linestampparser.viewparts.LineWebViewController

/**
 * Lineにアクセスするフラグメント
 */
class LineFragment : Fragment() {


    private val mController: LineWebViewController = LineWebViewController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_line, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mController.init(view)
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
