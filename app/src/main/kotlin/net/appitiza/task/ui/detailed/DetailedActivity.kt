package net.appitiza.task.ui.detailed

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast
import net.appitiza.task.R
import net.appitiza.task.base.BaseActivity
import net.appitiza.task.databinding.ActivityDetailedBinding
import net.appitiza.task.model.DetailedModel.CategoryArray
import net.appitiza.task.model.DetailedModel.ListCategory
import net.appitiza.task.model.storeModel.StoreDetails

class DetailedActivity : BaseActivity<DetailedPresenter>(), DetailedView {


    private lateinit var binding: ActivityDetailedBinding
    private val detailedAdapter = DetailedAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detailed)
        setActionbar()
        binding.adapter = detailedAdapter
        binding.layoutManager = LinearLayoutManager(this)
        binding.dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        presenter.onDetailedViewCreated("menuCategories", 366, 1, 1, 21)
        presenter.onViewCreated()
    }
    private fun setActionbar() {

        val toolbar: Toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {

            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun updateDetailed(detailedList: ListCategory) {
      detailedAdapter.updateDetailed(detailedList)
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

    override fun instantiatePresenter(): DetailedPresenter {
        return DetailedPresenter(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }
}
