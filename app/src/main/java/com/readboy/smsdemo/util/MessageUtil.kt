package com.readboy.smsdemo.util

import android.database.sqlite.SQLiteException
import android.net.Uri
import android.provider.ContactsContract
import com.readboy.smsdemo.APP
import com.readboy.smsdemo.entry.MessageBean
import java.text.SimpleDateFormat
import java.util.*

object MessageUtil {
    /**
     * 获取短信数据
     *
     * @param lastPostTime 最后一次提交到数据库的时间
     * @return
     */
    fun getSmsInPhone(lastPostTime: Long): List<MessageBean> { //TODO:是否加权限判断
        var lastPostTime = lastPostTime
        val messageList: MutableList<MessageBean> =
            ArrayList()
        val SMS_URI_ALL = "content://sms/"
        val SMS_URI_INBOX = "content://sms/inbox"
        val SMS_URI_SEND = "content://sms/sent"
        val SMS_URI_DRAFT = "content://sms/draft"
        try {
            val cr = APP.context?.contentResolver
            val projection = arrayOf(
                "_id", "address", "person",
                "body", "date", "type"
            )
            if (lastPostTime == 0L) {
                lastPostTime = System.currentTimeMillis() - 90 * 24 * 60 * 60 * 1000L
            }
            val where = (" date >  "
                    + lastPostTime)
            val uri = Uri.parse(SMS_URI_ALL)
            val cur = cr?.query(uri, projection, where, null, "date desc")
            if (cur != null && cur.moveToFirst()) {
                var name: String
                var phoneNumber: String?
                var smsbody: String?
                var date: String
                var type: String
                val phoneNumberColumn = cur.getColumnIndex("address")
                val smsbodyColumn = cur.getColumnIndex("body")
                val dateColumn = cur.getColumnIndex("date")
                val typeColumn = cur.getColumnIndex("type")
                do {
                    phoneNumber = cur.getString(phoneNumberColumn)
                    smsbody = cur.getString(smsbodyColumn)
                    val dateFormat = SimpleDateFormat(
                        "yyyy-MM-dd hh:mm:ss"
                    )
                    val d = Date(cur.getString(dateColumn).toLong())
                    date = dateFormat.format(d)
                    val typeId = cur.getInt(typeColumn)
                    type = if (typeId == 1) {
                        "1"
                    } else {
                        "2"
                    }
                    name = getPeopleNameFromPerson(phoneNumber)
                    val message = MessageBean()
                    message.name = name
                    message.type = type
                    message.phoneNumber = phoneNumber
                    message.smsBody = smsbody
                    message.date = date
                    messageList.add(message)
                    if (smsbody == null) smsbody = ""
                } while (cur.moveToNext())
            } else {
            }
        } catch (ex: SQLiteException) {
        }
        return messageList
    }

    // 通过address手机号关联Contacts联系人的显示名字
    private fun getPeopleNameFromPerson(address: String?): String {
        if (address == null || address === "") {
            return "( no address )\n"
        }
        var strPerson = "null"
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val uri_Person = Uri.withAppendedPath(
            ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI,
            address
        ) // address 手机号过滤
        val cursor =
            APP.context?.contentResolver?.query(uri_Person, projection, null, null, null)
        if (cursor!!.moveToFirst()) {
            val index_PeopleName =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val strPeopleName = cursor.getString(index_PeopleName)
            strPerson = strPeopleName
        }
        cursor.close()
        return strPerson
    }
}