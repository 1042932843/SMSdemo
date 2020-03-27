package com.readboy.smsdemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.readboy.smsdemo.util.ContactUtil
import com.readboy.smsdemo.util.MessageUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    private val callLogRequestCode = 1
    private val contactRequestCode = 2
    private val smsRequestCode = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_get_message.setOnClickListener {
            readMessage();
        }

        btn_get_contact.setOnClickListener {
            tvContent.text = ""
            readContact()
        }

    }
    /**
     * 读取短信
     */
    private fun readMessage() {
        val smsInPhone = MessageUtil.getSmsInPhone(0)
        tvContent.text = smsInPhone.toString()
    }
    /**
     * 读取本机通讯录信息
     *
     * @throws Throwable
     */
    private fun readContact() {
        try {
            val allContact =
                ContactUtil.getAllContact(this)
            tvContent.text = allContact.toString()
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
      if (requestCode == contactRequestCode) {
            readContact()
        } else if (requestCode == smsRequestCode) {
            readMessage()
        }
    }

}
