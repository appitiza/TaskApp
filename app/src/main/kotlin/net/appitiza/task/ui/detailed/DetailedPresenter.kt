package net.appitiza.task.ui.detailed

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.appitiza.task.base.BasePresenter
import net.appitiza.task.network.DetailedApi
import net.appitiza.task.network.StoreApi
import javax.inject.Inject

class DetailedPresenter(detailedView: DetailedView) : BasePresenter<DetailedView>(detailedView) {
    @Inject
    lateinit var detailedApi: DetailedApi

    private var subscription: Disposable? = null

    override fun onDetailedViewCreated(action: String, rId: Int, cusinetype: Int, langId: Int, countryId: Int) {
        loadDetailed(action, rId, cusinetype, langId, countryId)
    }

    fun loadDetailed(action: String, rId: Int, cusinetype: Int, langId: Int, countryId: Int) {
        view.showLoading()
        subscription = detailedApi
                .getDetailed(action, rId, cusinetype, langId, countryId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { stores -> view.updateDetailed(stores) },
                        { this::handleError }
                )


    }

    private fun handleError(error: Throwable) {

        Log.d("Error", error.localizedMessage)

    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}