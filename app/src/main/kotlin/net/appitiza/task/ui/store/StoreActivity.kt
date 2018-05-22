package net.appitiza.task.ui.store

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_store.*
import net.appitiza.task.R
import net.appitiza.task.base.BaseActivity
import net.appitiza.task.databinding.ActivityStoreBinding
import net.appitiza.task.model.storemodel.StoreDetails

class StoreActivity : BaseActivity<StorePresenter>(), StoreView {


    private lateinit var binding: ActivityStoreBinding
    private lateinit var stores: StoreDetails
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_store)

        presenter.onStoreViewCreated("restaurantAreaInfo", 1, 21, 1, 366)

        ll_item.setOnClickListener { presenter.moveToDetailed(iv_item,this,this.stores ) }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun updateStore(stores: StoreDetails) {
        this.stores = stores
        binding.storeData = stores
        binding.executePendingBindings()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.progressVisibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressVisibility = View.GONE
    }

    override fun instantiatePresenter(): StorePresenter {
        return StorePresenter(this)
    }
}
