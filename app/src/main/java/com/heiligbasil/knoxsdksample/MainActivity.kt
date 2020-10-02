package com.heiligbasil.knoxsdksample

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.samsung.android.knox.EnterpriseDeviceManager
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager

/**
 * This activity displays the main UI of the application. This is a simple application to restrict
 * use of the device camera using the Samsung Knox SDK.
 * Read more about the Knox SDK here:
 * https://seap.samsung.com/sdk
 *
 *
 * Please insert valid KPE License key to [Constants].
 *
 *
 * @author Samsung R&D Canada Technical Support
 */
class MainActivity : AppCompatActivity() {
    private var mToggleAdminBtn: Button? = null
    private var mDPM: DevicePolicyManager? = null
    private var mDeviceAdmin: ComponentName? = null
    private var mUtils: Utils? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logView = findViewById<TextView>(R.id.logview_id)
        logView.movementMethod = ScrollingMovementMethod()
        mUtils = Utils(logView, TAG)

        // Check if device supports Knox SDK
        mUtils?.checkApiLevel(22, this)
        mDPM = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
        mDeviceAdmin = ComponentName(this@MainActivity, SampleAdminReceiver::class.java)
        mToggleAdminBtn = findViewById(R.id.toggleAdminBtn)
        mToggleAdminBtn?.setOnClickListener(View.OnClickListener { view: View? -> toggleAdmin() })

        val activateLicenceBtn = findViewById<Button>(R.id.activateLicenseBtn)
        activateLicenceBtn.setOnClickListener { view: View? -> activateLicense() }

        val deactivateLicenceBtn = findViewById<Button>(R.id.deactivateLicenseBtn)
        deactivateLicenceBtn.setOnClickListener { view: View? -> deactivateLicense() }

        val toggleCameraBtn = findViewById<Button>(R.id.toggleCameraBtn)
        toggleCameraBtn.setOnClickListener { view: View? -> toggleCameraState() }
    }

    override fun onResume() {
        super.onResume()
        refreshButtons()
    }

    /** Handle result of device administrator activation request  */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DEVICE_ADMIN_ADD_RESULT_ENABLE) {
            when (resultCode) {
                RESULT_CANCELED -> mUtils?.log(getString(R.string.admin_cancelled))
                RESULT_OK -> {
                    mUtils?.log(getString(R.string.admin_enabled))
                    refreshButtons()
                }
            }
        }
    }

    /** Present a dialog to activate device administrator for this application  */
    private fun toggleAdmin() {
        val adminState = mDeviceAdmin?.let { mDPM?.isAdminActive(it) } ?: false
        if (adminState) {
            mUtils?.log(getString(R.string.deactivating_admin))
            // Deactivate application as device administrator
            mDPM?.removeActiveAdmin(ComponentName(this, SampleAdminReceiver::class.java))
            mToggleAdminBtn?.text = getString(R.string.activate_admin)
        } else {
            try {
                mUtils?.log(getString(R.string.activating_admin))
                // Ask the user to add a new device administrator to the system
                val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdmin)
                // Start the add device administrator activity
                startActivityForResult(intent, DEVICE_ADMIN_ADD_RESULT_ENABLE)
            } catch (e: Exception) {
                mUtils?.processException(e, TAG)
            }
        }
    }

    /**
     * Note that embedding your license key in code is unsafe and is done here for
     * demonstration purposes only.
     * Please visit https://seap.samsung.com/license-keys/about. for more details about license
     * keys.
     */
    private fun activateLicense() {

        // Instantiate the EnterpriseLicenseManager class to use the activateLicense method
        val klmManager = KnoxEnterpriseLicenseManager.getInstance(this.applicationContext)
        try {
            // KPE License Activation TODO Add license key to Constants.java
            klmManager.activateLicense(Constants.KPE_LICENSE_KEY)
            mUtils?.log(resources.getString(R.string.activate_license_progress))
        } catch (e: Exception) {
            mUtils?.processException(e, TAG)
        }
    }

    /**
     * Deactivate the license. Doing this frees up a seat in a license with limited seats.
     */
    private fun deactivateLicense() {
        // Instantiate the EnterpriseLicenseManager class to use the activateLicense method
        val klmManager = KnoxEnterpriseLicenseManager.getInstance(this.applicationContext)
        try {
            // KPE License Activation TODO Add license key to Constants.java
            klmManager.deActivateLicense(Constants.KPE_LICENSE_KEY)
            mUtils?.log(resources.getString(R.string.deactivate_license_progress))
        } catch (e: Exception) {
            mUtils?.processException(e, TAG)
        }
    }

    /**
     * Toggle the restriction of the device camera on/off. When set to disabled, the end user will
     * be unable to use the device cameras.
     */
    private fun toggleCameraState() {
        // Instantiate the EnterpriseDeviceManager class
        val enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(this.applicationContext)
        // Get the RestrictionPolicy class where the setCameraState method lives
        val restrictionPolicy = enterpriseDeviceManager.restrictionPolicy
        val cameraEnabled = restrictionPolicy.isCameraEnabled(false)
        try {
            // Toggle the camera state on/off
            restrictionPolicy.setCameraState(!cameraEnabled)
            mUtils?.log(resources.getString(R.string.camera_state, !cameraEnabled))
        } catch (e: Exception) {
            mUtils?.processException(e, TAG)
        }
    }

    /**
     * Update button state
     */
    private fun refreshButtons() {
        val adminState = mDeviceAdmin?.let { mDPM?.isAdminActive(it) }
        if (adminState?.not() != false) {
            mToggleAdminBtn?.text = getString(R.string.activate_admin)
        } else {
            mToggleAdminBtn?.text = getString(R.string.deactivate_admin)
        }
    }

    companion object {
        const val TAG = "MainActivity"
        private const val DEVICE_ADMIN_ADD_RESULT_ENABLE = 1
    }
}