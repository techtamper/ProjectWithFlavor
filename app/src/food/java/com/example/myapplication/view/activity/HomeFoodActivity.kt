package com.example.myapplication.view.activity

import androidx.databinding.ViewDataBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding

class HomeFoodActivity : MainActivity() {
    private lateinit var mBinding: ActivityMainBinding
    override fun initUpdate(binding: ViewDataBinding) {
        mBinding = binding as ActivityMainBinding
        initialUi()
    }

    private fun initialUi() {
        mBinding.appName.text = getString(R.string.app_name)
        mBinding.edtSearch.setText("Burger")
    }

}