package net.appitiza.task.injection.component

import dagger.BindsInstance
import dagger.Component
import net.appitiza.task.base.BaseView
import net.appitiza.task.injection.module.ContextModule
import net.appitiza.task.injection.module.NetworkModule
import net.appitiza.task.ui.detailed.DetailedPresenter
import net.appitiza.task.ui.store.StorePresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class)])
interface PresenterInjector {

    fun inject(storePresenter: StorePresenter)
    fun inject(detailedPresenter: DetailedPresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector
        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder
        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }
}