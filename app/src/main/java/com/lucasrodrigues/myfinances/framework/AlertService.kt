package com.lucasrodrigues.myfinances.framework

import android.app.Activity
import com.tapadoo.alerter.Alerter
import java.lang.ref.WeakReference

class AlertService(
    activity: Activity,
    private val resourceService: ResourceService
) {
    private val activity = WeakReference(activity)

    fun hideLoadingAlert() {
        activity.get()?.let { activity ->
            activity.runOnUiThread {
                if (Alerter.isShowing)
                    Alerter.hide()
            }
        }
    }

    fun showLoadingAlert(message: String) {
        activity.get()?.let { activity ->
            activity.runOnUiThread {
                Alerter.create(activity)
                    .setText(message)
                    .enableProgress(true)
                    .enableInfiniteDuration(true)
                    .setProgressColorRes(android.R.color.white)
                    .show()
            }
        }
    }

    fun sendErrorAlert(message: String) {
        hideLoadingAlert()

        activity.get()?.let { activity ->
            activity.runOnUiThread {
                Alerter.create(activity)
                    .setText(message)
                    .setDuration(5000)
                    .setBackgroundColorRes(android.R.color.holo_red_dark)
                    .enableSwipeToDismiss()
                    .show()
            }
        }
    }

    fun sendErrorAlert(error: Throwable) {
        hideLoadingAlert()

        activity.get()?.let { activity ->
            activity.runOnUiThread {
                Alerter.create(activity)
                    .setText(resourceService.getErrorMessage(error))
                    .setDuration(5000)
                    .setBackgroundColorRes(android.R.color.holo_red_dark)
                    .enableSwipeToDismiss()
                    .show()
            }
        }
    }

    fun sendSuccessAlert(message: String) {
        hideLoadingAlert()

        activity.get()?.let { activity ->
            activity.runOnUiThread {
                Alerter.create(activity)
                    .setText(message)
                    .setDuration(5000)
                    .setBackgroundColorRes(android.R.color.holo_green_dark)
                    .enableSwipeToDismiss()
                    .show()
            }
        }
    }

    fun sendInfoAlert(message: String) {
        hideLoadingAlert()

        activity.get()?.let { activity ->
            activity.runOnUiThread {
                Alerter.create(activity)
                    .setText(message)
                    .setDuration(5000)
                    .setBackgroundColorRes(android.R.color.holo_blue_light)
                    .enableSwipeToDismiss()
                    .show()
            }
        }
    }
}