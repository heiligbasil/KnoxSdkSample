package com.heiligbasil.knoxsdksample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager

/**
 * This BroadcastReceiver handles ELM activation results. It has to be registered in manifest file like so:
 *
 *
 * <pre>
 * `
 * <receiver android:name=".SampleLicenseReceiver">
 * <intent-filter>*
 * <action android:name="com.samsung.android.knox.intent.action.LICENSE_STATUS"></action></intent-filter>
</receiver> *
` *
</pre> *
 *
 * @author Samsung R&D Canada Technical Support
 */
class SampleLicenseReceiver : BroadcastReceiver() {
    private val DEFAULT_ERROR_CODE = -1

    private fun showToast(context: Context, msg_res: Int) {
        Toast.makeText(context, context.resources.getString(msg_res), Toast.LENGTH_SHORT).show()
    }

    private fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onReceive(context: Context, intent: Intent) {
        val msgRes: Int

        val action = intent.action

        if (action == null) {
            // No intent action is available
            showToast(context, R.string.no_intent_action)
        } else if (action == KnoxEnterpriseLicenseManager.ACTION_LICENSE_STATUS) {
            //License Result type is obtained - Activation or Deactivation
            val resultType = intent.getIntExtra(KnoxEnterpriseLicenseManager.EXTRA_LICENSE_RESULT_TYPE, -1)
            if (resultType == KnoxEnterpriseLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) {
                // License activation result error code is obtained
                val errorCode = intent.getIntExtra(KnoxEnterpriseLicenseManager.EXTRA_LICENSE_ERROR_CODE, DEFAULT_ERROR_CODE)
                if (errorCode == KnoxEnterpriseLicenseManager.ERROR_NONE) {
                    // License activated successfully
                    showToast(context, R.string.kpe_activated_succesfully)
                    Log.d(TAG, context.getString(R.string.kpe_activated_succesfully))
                } else {
                    // License Activation failed
                    msgRes = when (errorCode) {
                        KnoxEnterpriseLicenseManager.ERROR_INTERNAL -> R.string.err_kpe_internal
                        KnoxEnterpriseLicenseManager.ERROR_INTERNAL_SERVER -> R.string.err_kpe_internal_server
                        KnoxEnterpriseLicenseManager.ERROR_INVALID_LICENSE -> R.string.err_kpe_licence_invalid_license
                        KnoxEnterpriseLicenseManager.ERROR_INVALID_PACKAGE_NAME -> R.string.err_kpe_invalid_package_name
                        KnoxEnterpriseLicenseManager.ERROR_LICENSE_TERMINATED -> R.string.err_kpe_licence_terminated
                        KnoxEnterpriseLicenseManager.ERROR_NETWORK_DISCONNECTED -> R.string.err_kpe_network_disconnected
                        KnoxEnterpriseLicenseManager.ERROR_NETWORK_GENERAL -> R.string.err_kpe_network_general
                        KnoxEnterpriseLicenseManager.ERROR_NOT_CURRENT_DATE -> R.string.err_kpe_not_current_date
                        KnoxEnterpriseLicenseManager.ERROR_NULL_PARAMS -> R.string.err_kpe_null_params
                        KnoxEnterpriseLicenseManager.ERROR_UNKNOWN -> R.string.err_kpe_unknown
                        KnoxEnterpriseLicenseManager.ERROR_USER_DISAGREES_LICENSE_AGREEMENT -> R.string.err_kpe_user_disagrees_license_agreement
                        else -> {
                            // Unknown error code
                            val errorStatus = intent.getStringExtra(KnoxEnterpriseLicenseManager.EXTRA_LICENSE_STATUS)
                            val msg = context.resources.getString(R.string.err_kpe_code_unknown, Integer.toString(errorCode), errorStatus)
                            showToast(context, msg)
                            Log.d(TAG, msg)
                            return
                        }
                    }

                    // Display License Activation error message
                    showToast(context, msgRes)
                    Log.d(TAG, context.getString(msgRes))
                }
            } else if (resultType == KnoxEnterpriseLicenseManager.LICENSE_RESULT_TYPE_DEACTIVATION) {
                val errorCode = intent.getIntExtra(KnoxEnterpriseLicenseManager.EXTRA_LICENSE_ERROR_CODE, DEFAULT_ERROR_CODE)
                if (errorCode == KnoxEnterpriseLicenseManager.ERROR_NONE) {
                    // License deactivated successfully
                    showToast(context, R.string.kpe_deactivated_succesfully)
                    Log.d(TAG, context.getString(R.string.kpe_deactivated_succesfully))
                } else {
                    // License Activation failed
                    msgRes = when (errorCode) {
                        KnoxEnterpriseLicenseManager.ERROR_INTERNAL -> R.string.err_kpe_internal
                        KnoxEnterpriseLicenseManager.ERROR_INTERNAL_SERVER -> R.string.err_kpe_internal_server
                        KnoxEnterpriseLicenseManager.ERROR_INVALID_LICENSE -> R.string.err_kpe_licence_invalid_license
                        KnoxEnterpriseLicenseManager.ERROR_INVALID_PACKAGE_NAME -> R.string.err_kpe_invalid_package_name
                        KnoxEnterpriseLicenseManager.ERROR_LICENSE_TERMINATED -> R.string.err_kpe_licence_terminated
                        KnoxEnterpriseLicenseManager.ERROR_NETWORK_DISCONNECTED -> R.string.err_kpe_network_disconnected
                        KnoxEnterpriseLicenseManager.ERROR_NETWORK_GENERAL -> R.string.err_kpe_network_general
                        KnoxEnterpriseLicenseManager.ERROR_NOT_CURRENT_DATE -> R.string.err_kpe_not_current_date
                        KnoxEnterpriseLicenseManager.ERROR_NULL_PARAMS -> R.string.err_kpe_null_params
                        KnoxEnterpriseLicenseManager.ERROR_UNKNOWN -> R.string.err_kpe_unknown
                        KnoxEnterpriseLicenseManager.ERROR_USER_DISAGREES_LICENSE_AGREEMENT -> R.string.err_kpe_user_disagrees_license_agreement
                        else -> {
                            // Unknown error code
                            val errorStatus = intent.getStringExtra(KnoxEnterpriseLicenseManager.EXTRA_LICENSE_STATUS)
                            val msg = context.resources.getString(R.string.err_kpe_code_unknown, Integer.toString(errorCode), errorStatus)
                            showToast(context, msg)
                            Log.d(TAG, msg)
                            return
                        }
                    }

                    // Display License Activation error message
                    showToast(context, msgRes)
                    Log.d(TAG, context.getString(msgRes))
                }
            }
        }
    }

    companion object {
        const val TAG = "SampleLicenseReceiver"
    }
}