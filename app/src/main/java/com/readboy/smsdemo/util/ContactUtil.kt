package com.readboy.smsdemo.util

import android.content.Context
import android.provider.ContactsContract
import com.readboy.smsdemo.entry.ContactBean
import java.util.*

class ContactUtil {
    var strings = arrayOf("", "")

    companion object {
        /**
         * 获取通讯录中所有联系人的简单信息
         *
         * @throws Throwable
         */
        @Throws(Throwable::class)
        fun getAllContact(context: Context): List<ContactBean> {
            val list: MutableList<ContactBean> =
                ArrayList()
            val contactIdMap: MutableMap<String, String?> =
                HashMap()
            //获取联系人信息的Uri
            val uri = ContactsContract.Contacts.CONTENT_URI
            //获取ContentResolver
            val contentResolver = context.contentResolver
            //查询数据，返回Cursor
            val cursor =
                contentResolver.query(uri, null, null, null, null) ?: return list
            while (cursor.moveToNext()) {
                val sb = StringBuilder()
                //获取联系人的ID
                val contactId =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                if (contactIdMap.containsKey(contactId)) {
                    continue
                }
                contactIdMap[contactId] = contactId
                val contactBean = ContactBean()
                //获取联系人的姓名
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                contactBean.setName(name)
                //构造联系人信息
                sb.append("contactId=").append(contactId).append(",Name=").append(name)
                //查询电话类型的数据操作
                val phones = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null
                )
                contactBean.setPhoneNumber(null)
                while (phones!!.moveToNext()) {
                    val phoneNumber = phones.getString(
                        phones.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                    )
                    //添加Phone的信息
                    contactBean.setPhoneNumber(phoneNumber)
                }
                phones.close()
                //查询Email类型的数据操作
                val emails = contentResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,
                    null, null
                )
                contactBean.setEmail(null)
                while (emails!!.moveToNext()) {
                    val emailAddress = emails.getString(
                        emails.getColumnIndex(
                            ContactsContract.CommonDataKinds.Email.DATA
                        )
                    )
                    //添加Email的信息
                    contactBean.setEmail(emailAddress)
                }
                emails.close()
                list.add(contactBean)
            }
            cursor.close()
            return list
        }
    }
}