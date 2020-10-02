package com.heiligbasil.knoxsdksample

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class SampleAdminReceiver : DeviceAdminReceiver() {
    fun showToast(context: Context?, msg: CharSequence?) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onEnabled(context: Context, intent: Intent) {
        val message = context.resources.getString(R.string.admin_enabled)
        showToast(context, message)
    }

    override fun onDisabled(context: Context, intent: Intent) {
        val message = context.resources.getString(R.string.admin_disabled)
        showToast(context, message)
    }
}