package io.github.clouderhem.feishure.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import de.robv.android.xposed.XposedHelpers

object ContextUtils {

    @SuppressLint("PrivateApi")
    fun getContext(): Context? {
        return try {
            val activityThreadClass = Class.forName("android.app.ActivityThread")
            var context = getCurrentAppContext(activityThreadClass)
            if (context == null) {
                context = getSystemContext(activityThreadClass)
            }
            return context
        } catch (_: Throwable) {
            null
        }
    }

    private fun getCurrentAppContext(activityThreadClass: Class<*>): Context? {
        return XposedHelpers.callStaticMethod(
            activityThreadClass,
            "currentApplication"
        ) as Application?
    }

    private fun getSystemContext(activityThreadClass: Class<*>): Context? {
        var activityThread = XposedHelpers.callMethod(activityThreadClass, "currentActivityThread")

        var context = XposedHelpers.callMethod(activityThread, "getSystemContext") as Context?
        if (context == null) {
            context = XposedHelpers.callMethod(activityThread, "getSystemUiContext") as Context?
        }
        return context
    }
}
