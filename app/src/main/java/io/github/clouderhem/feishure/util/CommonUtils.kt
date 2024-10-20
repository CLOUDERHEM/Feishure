package io.github.clouderhem.feishure.util

import org.luckypray.dexkit.DexKitBridge

object CommonUtils {
    private lateinit var dexKitBridge: DexKitBridge

    fun setDexKitBridge(dexKitBridge: DexKitBridge) {
        this.dexKitBridge = dexKitBridge
    }

    fun getDexKitBridge(): DexKitBridge {
        return dexKitBridge
    }
}