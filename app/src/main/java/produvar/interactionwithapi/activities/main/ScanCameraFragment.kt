package produvar.interactionwithapi.activities.main

import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_scan_barcode.*
import android.view.*
import kotlinx.android.synthetic.main.activity_main.*
import produvar.interactionwithapi.BarcodeScanner
import produvar.interactionwithapi.R
import produvar.interactionwithapi.activities.tagInfo.TagInfoActivity


class ScanCameraFragment : Fragment() {

    lateinit var scanner: BarcodeScanner
    lateinit var mainActivity: MainActivity


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scan_barcode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (activity is MainActivity) {
            mainActivity = activity as MainActivity
        } else return

        button_back.setOnClickListener { mainActivity.swipeFragment() }
        scanner = BarcodeScanner(mainActivity, mainActivity.camera_preview, {
            val intent = Intent(activity, TagInfoActivity::class.java)
            intent.putExtra("barcode", it)
            mainActivity.startActivity(intent)
        })

        super.onViewCreated(view, savedInstanceState)
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (view != null) {
            if (isVisibleToUser) {
                scanner.setUp()
            } else scanner.release()
        }
    }

    override fun onPause() {
        super.onPause()
        scanner.release()
    }


}