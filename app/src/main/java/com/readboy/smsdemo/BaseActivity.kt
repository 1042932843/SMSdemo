package com.readboy.smsdemo

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
    }
    /**
     * 必要权限检查
     */
    @SuppressLint("CheckResult", "AutoDispose")
    private fun checkPermission() {

        RxPermissions(this)
            .request( Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_CALL_LOG)
            .subscribe { granted ->
                if (!granted) {
                    finish()
                    Toast.makeText(this,"权限未能开启，无法使用",Toast.LENGTH_LONG).show()
                }else{

                }
            }
    }
}