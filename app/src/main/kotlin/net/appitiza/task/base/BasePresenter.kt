package net.appitiza.task.base

import net.appitiza.task.injection.component.DaggerPresenterInjector
import net.appitiza.task.injection.component.PresenterInjector
import net.appitiza.task.injection.module.ContextModule
import net.appitiza.task.injection.module.NetworkModule
import net.appitiza.task.ui.detailed.DetailedPresenter
import net.appitiza.task.ui.post.PostPresenter
import net.appitiza.task.ui.store.StorePresenter

abstract class BasePresenter<out V : BaseView>(protected val view: V) {
    private val injector: PresenterInjector = DaggerPresenterInjector
            .builder()
            .baseView(view)
            .contextModule(ContextModule)
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    open fun onViewCreated(){}

    open fun onStoreViewCreated(action: String,
                                langId: Int,
                                countryId: Int,
                                areaId: Int,
                                rId: Int){}
    open fun onDetailedViewCreated(action: String, rId: Int,cusinetype: Int, langId: Int, countryId: Int){}
    open fun onViewDestroyed(){}

    private fun inject() {
        when (this) {
            is StorePresenter -> injector.inject(this)
            is PostPresenter -> injector.inject(this)
            is DetailedPresenter-> injector.inject(this)
        }
    }
}