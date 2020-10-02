package com.heiligbasil.knoxsdksample

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.samsung.android.knox.EnterpriseDeviceManager
import kotlin.system.exitProcess

class Utils(private val textView: TextView, private val TAG: String) {
    /** Check Knox API level on device, if it does not meet minimum requirement, end user
     * cannot use the application  */
    fun checkApiLevel(apiLevel: Int, context: Context) {
        if (EnterpriseDeviceManager.getAPILevel() < apiLevel) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val msg = context.resources.getString(R.string.api_level_message, EnterpriseDeviceManager.getAPILevel(), apiLevel)
            builder.setTitle(R.string.app_name)
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton("CLOSE") { _, _ -> exitProcess(0) }
                    .show()
        } else {
            return
        }
    }

    /** Log results to a textView in application UI  */
    fun log(text: String?) {
        textView.append(text)
        textView.append("\n\n")
        textView.invalidate()
        Log.d(TAG, text ?: "")
    }

    /** Process the exception  */
    fun processException(ex: Exception?, TAG: String?) {
        if (ex != null) {
            // Present the exception message
            val msg = "${ex.javaClass.canonicalName}: ${ex.message}"
            textView.append(msg)
            textView.append("\n\n")
            textView.invalidate()
            Log.e(TAG, msg)
        }
    }
}