package net.appitiza.task.ui.startup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_start_up.*
import kotlinx.android.synthetic.main.activity_store.*
import net.appitiza.task.R
import net.appitiza.task.base.BaseActivity
import net.appitiza.task.ui.store.StorePresenter
import net.appitiza.task.ui.store.StoreView

class StartUpActivity :BaseActivity<StartUpPresenter>(), StartUpView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_up)
        btn_startup_store.setOnClickListener { presenter.moveToStore(btn_startup_store,this) }
        btn_startup_map.setOnClickListener { presenter.moveToMap(btn_startup_map,this) }
        btn_startup_track.setOnClickListener { presenter.moveToSetLoction(btn_startup_track,this) }
    }
    override fun instantiatePresenter(): StartUpPresenter {
        return StartUpPresenter(this)
    }

    override fun showError(error: String) {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}
