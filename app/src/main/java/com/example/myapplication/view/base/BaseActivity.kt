package com.example.myapplication.view.base

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        val TAG = BaseActivity::class.java.simpleName
    }

    abstract fun getLayout(): Int

    abstract fun initUI(binding: ViewDataBinding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val layoutResId = getLayout()
        val binding: ViewDataBinding?
        if (layoutResId != 0) {
            try {
                binding = DataBindingUtil.setContentView(this, layoutResId)
                initUI(binding)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun isNetworkAvailable():Boolean{
        val connectivityManager=getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }
}