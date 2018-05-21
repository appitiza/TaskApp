package net.appitiza.task.ui.detailed

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import net.appitiza.task.R
import net.appitiza.task.databinding.ItemDetailedParentBinding
import net.appitiza.task.databinding.ItemPostBinding
import net.appitiza.task.model.DetailedModel.CategoryArray
import java.util.ArrayList
import net.appitiza.task.databinding.ItemStoreBinding
import net.appitiza.task.model.DetailedModel.ListCategory
import net.appitiza.task.model.Post
import net.appitiza.task.model.storeModel.RestaurantAreaInfo
import net.appitiza.task.ui.post.PostAdapter


class DetailedAdapter(private val context: Context) : RecyclerView.Adapter<DetailedAdapter.DetailedViewHolder>() {

    /**
     * The list of posts of the adapter
     */
    private var detailedList: List<CategoryArray> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailedViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: ItemDetailedParentBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_detailed_parent, parent, false)
        return DetailedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailedAdapter.DetailedViewHolder, position: Int) {
        holder?.bind(detailedList[position])
    }



    override fun getItemCount(): Int {
        return detailedList.size
    }


    /**
     * Updates the list of posts of the adapter
     * @param posts the new list of posts of the adapter
     */
    fun updateDetailed(detailedList: ListCategory) {
        this.detailedList = detailedList.categoryArray
        notifyDataSetChanged()
    }

    /**
     * The ViewHolder of the adapter
     * @property binding the DataBinging object for Post item
     */
    class DetailedViewHolder(private val binding: ItemDetailedParentBinding) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Binds a post into the view
         */
        fun bind(category: CategoryArray) {
            binding.categoryArray = category
            binding.executePendingBindings()
        }
    }
}