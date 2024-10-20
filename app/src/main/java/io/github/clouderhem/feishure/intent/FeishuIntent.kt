package io.github.clouderhem.feishure.intent

import android.content.ComponentName
import android.content.Intent

object FeishuIntent {

    fun getMainAppIntent(): Intent {
        var intent = Intent()
        intent.setComponent(
            ComponentName(
                "com.ss.android.lark", "com.ss.android.lark.main.app.MainActivity"
            )
        )
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return intent
    }
}