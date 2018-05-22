package net.appitiza.task.ui.detailed

import android.support.annotation.StringRes
import net.appitiza.task.base.BaseView
import net.appitiza.task.model.detailedmodel.ListCategory

interface DetailedView : BaseView {

    fun updateDetailed(detailedList: ListCategory)

    fun showError(error: String)

    fun showError(@StringRes errorResId: Int) {
        this.showError(getContext().getString(errorResId))
    }

    fun showLoading()

    fun hideLoading()
}