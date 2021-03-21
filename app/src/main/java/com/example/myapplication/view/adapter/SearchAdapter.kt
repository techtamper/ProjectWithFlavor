package com.example.myapplication.view.adapter

import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.RowSearchBinding
import com.example.myapplication.global.apiutils.ApiConstant
import com.example.myapplication.model.bean.SearchRespo
import com.example.myapplication.view.base.BaseAdapter

class SearchAdapter(private var dataList: ArrayList<SearchRespo.Photos.Photo>?) : BaseAdapter() {
    override fun getListSize(): Int? {
        if (dataList == null) return 0
        return dataList?.size
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.row_search
    }

    override fun onBindTo(rowBinding: ViewDataBinding, position: Int) {
        if (rowBinding is RowSearchBinding) {
            val source = dataList?.get(position)
            Glide.with(rowBinding.root.context)
                .load(ApiConstant.IMG_URL +"${source?.server}/${source?.id}_${source?.secret}.jpg").error(R.color.grey).placeholder(R.color.grey).into(rowBinding.img)
        }
    }

    fun notifyAdapter(newsBeanList: ArrayList<SearchRespo.Photos.Photo>) {
        dataList = newsBeanList
        notifyDataSetChanged()
    }
}
