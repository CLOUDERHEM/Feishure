package io.github.clouderhem.feishure.base

import de.robv.android.xposed.XposedBridge

abstract class BaseHook : IHook {

    override fun name(): String {
        return this.javaClass.simpleName
    }

    override fun packages(): List<String> {
        return listOf()
    }

    protected fun logE(msg: String, e: Exception) {
        XposedBridge.log("[Feishure][E][${name()}]$msg")
        XposedBridge.log(e)
    }

    protected fun logI(msg: String) {
        XposedBridge.log("[Feishure][I][${name()}]$msg")
    }

}