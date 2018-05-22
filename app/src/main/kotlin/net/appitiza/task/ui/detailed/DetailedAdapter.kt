package net.appitiza.task.ui.detailed

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import net.appitiza.task.R
import net.appitiza.task.databinding.ItemDetailedParentBinding
import net.appitiza.task.model.detailedmodel.CategoryArray
import net.appitiza.task.model.detailedmodel.ListCategory
import net.appitiza.task.model.detailedmodel.MenuArray


class DetailedAdapter(private val context: Context) : RecyclerView.Adapter<DetailedAdapter.DetailedViewHolder>() {


    private var detailedList: List<CategoryArray> = listOf()
    private var selectedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailedViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: ItemDetailedParentBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_detailed_parent, parent, false)
        return DetailedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailedAdapter.DetailedViewHolder, position: Int) {
        holder.bind(detailedList[position], context,selectedPosition,holder.adapterPosition)
        holder.itemView.setOnClickListener {
           selectedPosition = if(selectedPosition == holder.adapterPosition){
               -1
           } else{
               position
           }
            notifyDataSetChanged()
        }
    }


    override fun getItemCount(): Int {
        return detailedList.size
    }

    fun updateDetailed(detailedList: ListCategory) {
        this.detailedList = detailedList.categoryArray
        notifyDataSetChanged()
    }


    class DetailedViewHolder(private val binding: ItemDetailedParentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryArray, context: Context,selectedPosition :Int,position: Int) {
            binding.categoryArray = category
            binding.layoutManager = LinearLayoutManager(context)
            binding.dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            val childAdapter = ChildAdapter(context)
            binding.adapter = childAdapter
            if (selectedPosition == position) {
                childAdapter.updateDetailed(category.menuArray)
            }
            else{
                val menuArray: List<MenuArray> =  listOf()
                childAdapter.updateDetailed(menuArray)
            }
            binding.executePendingBindings()

        }
    }
}