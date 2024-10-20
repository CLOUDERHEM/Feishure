package io.github.clouderhem.feishure.base

import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

interface IHook {

    fun hook(lpparam: LoadPackageParam)

    /**
     * target pkg
     */
    fun packages(): List<String>

    fun name(): String
}
