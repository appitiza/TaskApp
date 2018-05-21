package net.appitiza.task.ui.store

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.appitiza.task.R
import net.appitiza.task.R.id.iv_item
import net.appitiza.task.base.BasePresenter
import net.appitiza.task.model.storeModel.StoreDetails
import net.appitiza.task.network.StoreApi
import net.appitiza.task.ui.detailed.DetailedActivity
import net.appitiza.task.utility.BASE_HOST_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Inject


class StorePresenter(storeView: StoreView) : BasePresenter<StoreView>(storeView) {
    val EXTRA_STORE: String = "store_data"
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

    fun moveToDetailed(view : View, rId: Int,context: Context,stores: StoreDetails) {

        val intent = Intent(context, DetailedActivity::class.java)
        intent.putExtra(EXTRA_STORE, stores)
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


    private fun  handleError(error: Throwable) {

        Log.d("Error", error.localizedMessage)

    }
    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}