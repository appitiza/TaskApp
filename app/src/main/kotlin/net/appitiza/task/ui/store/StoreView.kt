package net.appitiza.task.ui.store

import android.support.annotation.StringRes
import net.appitiza.task.base.BaseView
import net.appitiza.task.model.RestaurantAreaInfo

interface StoreView : BaseView {

    fun updateStore(stores: List<RestaurantAreaInfo>)

    fun showError(error: String)

    fun showError(@StringRes errorResId: Int) {
        this.showError(getContext().getString(errorResId))
    }

    fun showLoading()

    fun hideLoading()
}