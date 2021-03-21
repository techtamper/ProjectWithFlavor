package com.example.myapplication.view.activity

import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.global.apiutils.ApiResponse
import com.example.myapplication.model.bean.SearchRespo
import com.example.myapplication.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.global.GlobalUtility
import com.example.myapplication.model.bean.SearchReq
import com.example.myapplication.view.adapter.SearchAdapter
import com.example.myapplication.view.base.BaseActivity
import com.example.myapplication.view.base.ScrollListener
import com.google.android.material.snackbar.Snackbar
import kotlin.collections.ArrayList

abstract class MainActivity : BaseActivity() {
    private lateinit var adapter: SearchAdapter
    private var mPageCount: Int = 1
    private lateinit var mBinding: ActivityMainBinding
    private val mViewModel: SearchViewModel by viewModel()
    private var dataList: ArrayList<SearchRespo.Photos.Photo>? = ArrayList()
    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initUI(binding: ViewDataBinding) {
        mBinding = binding as ActivityMainBinding

        mBinding.linearNoDataFound.visibility = View.VISIBLE
        init()
        initUpdate(mBinding)
        setAdapter()
        clickListener()

    }

    abstract fun initUpdate(binding: ViewDataBinding)

    private fun init() {
        mBinding.tool.toolbarTitle.text =
            GlobalUtility.capitalizeString(BuildConfig.FLAVOR_product) + " " +
                    GlobalUtility.capitalizeString(BuildConfig.FLAVOR_server) + " " +
                    GlobalUtility.capitalizeString(BuildConfig.BUILD_TYPE)
        mBinding.tool.toolbarSubtitle.text =
            BuildConfig.APPLICATION_ID + " v${BuildConfig.VERSION_CODE} - ${BuildConfig.VERSION_NAME}"
        mBinding.edtSearch.setText("Tesla")
    }

    private fun clickListener() {
        mBinding.imgSearch.setOnClickListener {
            mPageCount = 1
            callSearchImgApi(true)
        }
        callSearchImgApi(true)
    }

    private fun setAdapter() {
        adapter = SearchAdapter(dataList)
        mBinding.rv.layoutManager = GridLayoutManager(this, 3)
        mBinding.rv.addOnScrollListener(object :
            ScrollListener(mBinding.rv.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                mPageCount++
                callSearchImgApi(false)
            }
        })
        mBinding.rv.adapter = adapter
    }

    private fun callSearchImgApi(isRefresh: Boolean) {
        if (!isNetworkAvailable()) {
            Snackbar.make(mBinding.pd, "No Internet Connection", Snackbar.LENGTH_LONG).show()
            return
        }
        if (isRefresh) {
            dataList?.clear()
            adapter.notifyAdapter(dataList!!)
        }
        mBinding.pd.visibility = View.VISIBLE
        mBinding.linearNoDataFound.visibility = View.GONE
        val searchReq = SearchReq().apply {
            text = mBinding.edtSearch.text.toString()
            page = mPageCount
        }
        mViewModel.getSearchPicData().observe(this, channelObserver)
        mViewModel.newsChannelApi(searchReq)
    }

    private val channelObserver: Observer<ApiResponse<SearchRespo>> by lazy {
        Observer { response: ApiResponse<SearchRespo> -> handleLoginResponse(response) }
    }

    private fun handleLoginResponse(response: ApiResponse<SearchRespo>) {
        when (response.status) {
            ApiResponse.Status.LOADING -> {
                mBinding.pd.visibility = View.VISIBLE
            }
            ApiResponse.Status.ERROR -> {
                mBinding.pd.visibility = View.GONE
                mBinding.linearNoDataFound.visibility = View.VISIBLE
                mBinding.txtNoData.text = getString(R.string.something_went_wrong)
            }
            ApiResponse.Status.SUCCESS -> {
                mBinding.pd.visibility = View.GONE
//                mBinding.linearNoDataFound.visibility = View.GONE
                if (response.data?.stat.equals("fail")) {
                    Snackbar.make(mBinding.pd, "${response.data?.message}", Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    if (response.data!!.photos?.photo?.size == 0) {
                        Snackbar.make(
                            mBinding.pd,
                            "No data found",
                            Snackbar.LENGTH_LONG
                        ).show()
                        return
                    }
                    if (dataList == null || dataList?.size == 0) dataList =
                        response.data.photos?.photo
                    else response.data.photos?.let { dataList?.addAll(it.photo) }
                    adapter.notifyAdapter(dataList!!)
                }
            }
        }
    }
}