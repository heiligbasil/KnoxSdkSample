package com.heiligbasil.knoxsdksample

/**
 * Provide a valid KLM license as a constant. This technique is used for demonstration purposes
 * only.
 * Consider using more secure approach for passing your license key in a production scenario.
 * Visit https://seap.samsung.com/license-keys/about for details
 */
interface Constants {
    companion object {
        /**
         * Note::
         * In the new licensing model, the three types of licenses available are:
         * - KPE Development Key
         * - KPE Commercial Key - Standard
         * - KPE Commercial Key - Premium
         *
         * The KPE Development and KPE Commercial Key - Standard can be generated from SEAP while a KPE
         * Commercial Key can be bought from a reseller.
         *
         * You can read more about licenses at: https://seap.samsung.com/license-keys/about-licenses
         *
         * TODO Enter the KPE license key for Knox SDK. You can obtain it from https://seap.samsung.com/sdk.
         * TODO Implement a secure mechanism to pass KPE key to your application
         */
        const val KPE_LICENSE_KEY = "PRIVATE"
    }
}