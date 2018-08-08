package com.example.ptut.mm_kunyi.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.adapters.base.BaseRecyclerAdapter
import com.example.ptut.mm_kunyi.viewholders.CommentViewHolder
import com.example.ptut.mm_kunyi.viewholders.base.BaseViewHolder
import com.example.ptut.mm_kunyi.vos.CommentVO

class CommentAdapter(context: Context):BaseRecyclerAdapter<CommentViewHolder,CommentVO>(context){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CommentVO> {
        var view=mLayoutInflator.inflate(R.layout.comment_item,parent,false)
        return CommentViewHolder(view)
    }
}