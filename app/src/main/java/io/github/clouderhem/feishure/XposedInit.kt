package io.github.clouderhem.feishure

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam
import io.github.clouderhem.feishure.hook.HookEntry
import io.github.clouderhem.feishure.util.CommonUtils
import org.luckypray.dexkit.DexKitBridge

class XposedInit : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        CommonUtils.setDexKitBridge(createDexKitBridge(lpparam))

        HookEntry.hook(lpparam)
    }

    private fun createDexKitBridge(lpparam: LoadPackageParam): DexKitBridge {
        System.loadLibrary("dexkit")
        return DexKitBridge.create(lpparam.appInfo.sourceDir)
    }
}
