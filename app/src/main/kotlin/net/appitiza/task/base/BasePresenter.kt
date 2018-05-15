package net.appitiza.task.base

import net.appitiza.task.injection.component.DaggerPresenterInjector
import net.appitiza.task.injection.component.PresenterInjector
import net.appitiza.task.injection.module.ContextModule
import net.appitiza.task.injection.module.NetworkModule
import net.appitiza.task.ui.post.PostPresenter

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

    open fun onViewDestroyed(){}

    private fun inject() {
        when (this) {
            is PostPresenter -> injector.inject(this)
        }
    }
}