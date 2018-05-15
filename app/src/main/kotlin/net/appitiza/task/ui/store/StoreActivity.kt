package net.appitiza.task.ui.store

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import net.appitiza.task.R
import net.appitiza.task.base.BaseActivity
import net.appitiza.task.databinding.ActivityPostBinding
import net.appitiza.task.databinding.ActivityStoreBinding
import net.appitiza.task.model.Post
import net.appitiza.task.model.RestaurantAreaInfo
import net.appitiza.task.model.StoreDetails
import net.appitiza.task.ui.post.PostAdapter
import net.appitiza.task.ui.post.PostPresenter
import net.appitiza.task.ui.post.PostView

class StoreActivity : BaseActivity<StorePresenter>(), StoreView {


    private lateinit var binding: ActivityStoreBinding
    private val storeAdapter = StoreAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_store)
       // binding.adapter = storeAdapter
       // binding.layoutManager = LinearLayoutManager(this)
       // binding.dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)

        presenter.onStoreViewCreated("restaurantAreaInfo",1,21,1,366)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun updateStore(store: StoreDetails) {
        //storeAdapter.updateStore(store!!.restaurantAreaInfo)
        binding.storeData = store
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
