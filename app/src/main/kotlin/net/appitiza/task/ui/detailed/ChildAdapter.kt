package net.appitiza.task.ui.detailed

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import net.appitiza.task.R
import net.appitiza.task.databinding.ItemDetailedChildBinding
import net.appitiza.task.databinding.ItemDetailedParentBinding
import net.appitiza.task.model.DetailedModel.CategoryArray
import net.appitiza.task.model.DetailedModel.ListCategory
import net.appitiza.task.model.DetailedModel.MenuArray


class ChildAdapter(private val context: Context) : RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {


    private var menuList: List<MenuArray> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: ItemDetailedChildBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_detailed_child, parent, false)
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildAdapter.ChildViewHolder, position: Int) {
        holder.bind(menuList[position])
    }



    override fun getItemCount(): Int {
        return menuList.size
    }

    fun updateDetailed(menuList:  List<MenuArray>) {
        this.menuList = menuList
        notifyDataSetChanged()
    }


    class ChildViewHolder(private val binding: ItemDetailedChildBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(menu: MenuArray) {
            binding.menuArray = menu
            binding.executePendingBindings()
        }
    }
}