package io.github.clouderhem.feishure.hook.miniapp

import android.content.Intent
import android.os.Bundle
import android.util.ArrayMap
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers.callMethod
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.XposedHelpers.getObjectField
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam
import io.github.clouderhem.feishure.base.BaseHook

object FeishuMiniApp : BaseHook() {

    override fun packages(): List<String> {
        return listOf("com.ss.android.lark")
    }

    override fun hook(lpparam: LoadPackageParam) {
        miniAppStartLog(lpparam)
    }

    fun miniAppStartLog(lpparam: LoadPackageParam) {
        var clazz =
            lpparam.classLoader.loadClass("com.ss.android.lark.gadget.container.standard.MiniappHostBase")

        findAndHookMethod(clazz, "onCreate", Bundle::class, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                var activity = param.thisObject
                if (activity == null) {
                    logI("activity instance is null")
                    return
                }
                var intent = callMethod(activity, "getIntent") as Intent
                var mMap = getObjectField(intent.extras, "mMap") as ArrayMap<*, *>

                logI("miniapp intent extras: ")
                for (entry in mMap.entries) {
                    logI("k: ${entry.key}, v: ${entry.value}")
                }
            }
        })
    }

}