package net.appitiza.task.ui.store

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.appitiza.task.R
import net.appitiza.task.base.BasePresenter
import net.appitiza.task.network.PostApi
import net.appitiza.task.network.StoreApi
import net.appitiza.task.ui.post.PostView
import javax.inject.Inject

class StorePresenter(storeView: StoreView) : BasePresenter<StoreView>(storeView) {
    @Inject
    lateinit var storeApi: StoreApi

    private var subscription: Disposable? = null

    override fun onViewCreated() {
        loadStore()
    }


    fun loadStore() {
        view.showLoading()
        subscription = storeApi
                .getStore()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { storeList -> view.updateStore(storeList) },
                        { view.showError(R.string.unknown_error) }
                )
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}