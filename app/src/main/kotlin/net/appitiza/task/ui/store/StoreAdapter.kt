package net.appitiza.task.ui.store

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import net.appitiza.task.R
import net.appitiza.task.databinding.ItemPostBinding
import net.appitiza.task.databinding.ItemStoreBinding
import net.appitiza.task.model.RestaurantAreaInfo

class StoreAdapter(private val context: Context) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    private var stores: List<RestaurantAreaInfo> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: ItemStoreBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_store, parent, false)
        return StoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder?.bind(stores[position])
    }


    override fun getItemCount(): Int {
        return stores.size
    }

    fun updateStore(stores: List<RestaurantAreaInfo>) {
        this.stores = stores
        notifyDataSetChanged()
    }

    class StoreViewHolder(private val binding: ItemStoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(store: RestaurantAreaInfo) {
            binding.store = store
            binding.executePendingBindings()
        }
    }
}