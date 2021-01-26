package com.ehsanfallahi.loginapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ehsanfallahi.loginapp.R

import com.ehsanfallahi.loginapp.data.dataModel.Data
import com.ehsanfallahi.loginapp.databinding.ListUserLayoutBinding
import com.ehsanfallahi.loginapp.ui.user.userList.UserListFragmentDirections

class RecyclerViewAdapter(private val data:List<Data>):
    RecyclerView.Adapter<RecyclerViewAdapter.NewsViewHolder>() {
    private lateinit var binding:ListUserLayoutBinding
    var titleListUser=ArrayList<Data>()
    init {
        titleListUser= data as ArrayList<Data>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        binding=DataBindingUtil.inflate(layoutInflater, R.layout.list_user_layout,parent,false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        holder.bind(data[position])

        holder.itemView.setOnClickListener {
            val direction=UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(data[position])
            it.findNavController().navigate(direction)
        }
    }

    override fun getItemCount()=data.size

class NewsViewHolder(val binding:ListUserLayoutBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(data: Data){
        binding.tvNameList.text=data.firstName
        binding.tvEmailList.text=data.email
    }
    }

}
