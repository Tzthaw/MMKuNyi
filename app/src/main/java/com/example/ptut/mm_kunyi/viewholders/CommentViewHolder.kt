package com.example.ptut.mm_kunyi.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.example.ptut.mm_kunyi.viewholders.base.BaseViewHolder
import com.example.ptut.mm_kunyi.vos.CommentVO
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentViewHolder(itemView: View) : BaseViewHolder<CommentVO>(itemView) {
    private lateinit var commentVO: CommentVO
    override fun setData(data: CommentVO) {
        commentVO = data
        itemView.commentName.text = commentVO.commentUserName
        Glide.with(itemView.context)
                .load(commentVO.commentUserPhoto)
                .into(itemView.commentUserImage)
        itemView.commentDetail.text = commentVO.commentDetail
        itemView.commentDate.text = commentVO.commentDate
    }

    override fun onClick(v: View?) {

    }
}