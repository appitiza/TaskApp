package net.appitiza.task.ui.detailed

import android.support.annotation.StringRes
import net.appitiza.task.base.BaseView
import net.appitiza.task.model.StoreDetails

interface DetailedView : BaseView {

    fun updateDetailed(stores: StoreDetails)

    fun showError(error: String)

    fun showError(@StringRes errorResId: Int) {
        this.showError(getContext().getString(errorResId))
    }

    fun showLoading()

    fun hideLoading()
}