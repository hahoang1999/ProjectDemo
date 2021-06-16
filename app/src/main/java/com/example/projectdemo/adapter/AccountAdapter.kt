package com.example.projectdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdemo.R
import com.example.projectdemo.models.AccountModel
import kotlinx.android.synthetic.main.item_account.view.*

class AccountAdapter : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {
    private var mListAccount = arrayListOf<AccountModel>()

    inner class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        return AccountViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_account, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val itemData = mListAccount[position]
        holder.itemView.apply {
            tvEmail.text = itemData.email
            tvId.text = itemData.userId.toString()
            tvUserName.text = itemData.username
        }
    }

    override fun getItemCount() = mListAccount.size

    fun setData(list: ArrayList<AccountModel>) {
        mListAccount = list
        notifyDataSetChanged()
    }
}
