package net.appitiza.task.ui.startup

import android.support.annotation.StringRes
import net.appitiza.task.base.BaseView
import net.appitiza.task.model.storemodel.StoreDetails

interface StartUpView : BaseView {


    fun showError(error: String)

    fun showError(@StringRes errorResId: Int) {
        this.showError(getContext().getString(errorResId))
    }

    fun showLoading()

    fun hideLoading()
}