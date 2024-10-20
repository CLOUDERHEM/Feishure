package io.github.clouderhem.feishure.hook

import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam
import io.github.clouderhem.feishure.base.BaseHook
import io.github.clouderhem.feishure.base.IHook
import io.github.clouderhem.feishure.hook.launcher.TsmClient

object HookEntry : BaseHook() {

    private val hooks = ArrayList<IHook>()

    override fun hook(lpparam: LoadPackageParam) {

        registerHooks()

        for (hook in hooks) {
            if (!isTargetPackage(hook, lpparam.packageName)) {
                continue
            }
            logI("attached to ${lpparam.packageName}")
            try {
                hook.hook(lpparam)
            } catch (e: Exception) {
                logE("failed hooking, hook: ${hook.javaClass.name}, error:", e)
            }
        }
    }

    private fun isTargetPackage(hook: IHook, nowPkg: String): Boolean {
        for (targetPkg in hook.packages()) {
            if (targetPkg == nowPkg) {
                return true
            }
        }
        return false
    }

    private fun registerHooks() {
        hooks.add(TsmClient)
        // hooks.add(FeishuMiniApp)
    }
}
