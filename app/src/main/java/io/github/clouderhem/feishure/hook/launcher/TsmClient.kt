package io.github.clouderhem.feishure.hook.launcher

import android.widget.Toast
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge.hookAllMethods
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam
import io.github.clouderhem.feishure.base.BaseHook
import io.github.clouderhem.feishure.intent.FeishuIntent
import io.github.clouderhem.feishure.util.ContextUtils
import java.time.LocalTime

object TsmClient : BaseHook() {

    private var delayTimeSec: Long = 7

    override fun packages(): List<String> {
        return listOf("com.miui.tsmclient")
    }

    override fun hook(lpparam: LoadPackageParam) {
        var loadClass =
            lpparam.classLoader.loadClass("com.miui.tsmclient.ui.quick.DoubleClickActivity")

        hookAllMethods(loadClass, "onCreate", object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                var context = ContextUtils.getContext()
                if (context == null) {
                    logE("context is null", Exception("null context"))
                    return
                }
                if (!isPunchInTime()) {
                    Toast.makeText(context, "当前时间段无需打开 '飞书'", Toast.LENGTH_SHORT).show()
                    return
                }
                Toast.makeText(context, "$delayTimeSec 秒后将打开 '飞书'", Toast.LENGTH_LONG).show()

                Thread {
                    Thread.sleep(delayTimeSec * 1000)
                    context.startActivity(FeishuIntent.getMainAppIntent())
                }.start()
            }
        })
    }


    private fun isPunchInTime(): Boolean {
        val cur = LocalTime.now()

        val morningStart = LocalTime.of(8, 30)
        val morningEnd = LocalTime.of(9, 30)

        val eveningStart = LocalTime.of(18, 0)
        val eveningEnd = LocalTime.of(20, 30)

        return (cur.isAfter(morningStart) && cur.isBefore(morningEnd)) || (cur.isAfter(eveningStart) && cur.isBefore(
            eveningEnd
        ))
    }
}