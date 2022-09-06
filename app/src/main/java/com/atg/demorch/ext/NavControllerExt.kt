package com.atg.demorch.ext

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController?.safeNavigate(
    destinationId: Int,
    bundle: (() -> Bundle?)? = null,
    navOptions: (() -> NavOptions)? = null
) {
    this?.currentDestination?.getAction(destinationId)?.let {
        navigate(destinationId, bundle?.invoke(), navOptions?.invoke())
    }
}
fun NavController?.safeNavigate(
    destinationId: Int,
    bundleData: Bundle? = null,
) {
    safeNavigate(
        destinationId = destinationId,
        bundle = { bundleData }
    )
}