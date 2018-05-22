package net.appitiza.task.ui.store

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.appitiza.task.R
import net.appitiza.task.base.BasePresenter
import net.appitiza.task.model.storemodel.StoreDetails
import net.appitiza.task.network.StoreApi
import net.appitiza.task.ui.detailed.DetailedActivity
import javax.inject.Inject


class StorePresenter(storeView: StoreView) : BasePresenter<StoreView>(storeView) {
    private val EXTRASTORE: String = "storedata"
    @Inject
    lateinit var storeApi: StoreApi

    private var subscription: Disposable? = null

    override fun onStoreViewCreated(action: String, langId: Int, countryId: Int, areaId: Int, rId: Int) {
        loadStore(action,
                langId,
                countryId,
                areaId,
                rId)
    }

    fun moveToDetailed(view : View,context: Context,stores: StoreDetails) {

        val intent = Intent(context, DetailedActivity::class.java)
        intent.putExtra(EXTRASTORE, stores)
        val p1 = Pair(view, context.getString(R.string.image_store_detailed))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, p1)
        context.startActivity(intent, options.toBundle())


    }

    private fun loadStore(action: String,
                          langId: Int,
                          countryId: Int,
                          areaId: Int,
                          rId: Int) {
        view.showLoading()
        subscription = storeApi
                .getStore(action,
                        langId,
                        countryId,
                        areaId,
                        rId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { stores -> view.updateStore(stores) },
                        { view.showError(R.string.unknown_error)}
                )

    }


    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}