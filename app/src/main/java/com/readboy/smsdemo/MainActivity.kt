package com.readboy.smsdemo

import android.os.Bundle
import com.readboy.smsdemo.util.CallLogUtils
import com.readboy.smsdemo.util.ContactUtil
import com.readboy.smsdemo.util.MessageUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_get_message.setOnClickListener {
            readMessage();
        }

        btn_get_contact.setOnClickListener {
            readContact()
        }
        btn_get_call_log.setOnClickListener {
            readCallLog()
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

    /**
     * 读取通话记录
     */
    private fun readCallLog() {
        val callLogInfos =
            CallLogUtils.getCallLogInfos(this, 0)
        tvContent.text = callLogInfos.toString()
    }

}
