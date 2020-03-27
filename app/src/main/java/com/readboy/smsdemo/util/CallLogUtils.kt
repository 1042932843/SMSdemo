package com.readboy.smsdemo.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.CallLog
import com.readboy.smsdemo.entry.CallLogInfoBean
import java.text.SimpleDateFormat
import java.util.*

object CallLogUtils {
    //    private static final Uri uri = CallLog.Calls.CONTENT_URI;
    private val uri = Uri.parse("content://call_log/calls")
    private val projection = arrayOf(
        CallLog.Calls.DATE,  // 日期
        CallLog.Calls.NUMBER,  // 号码
        CallLog.Calls.TYPE,  // 类型
        CallLog.Calls.CACHED_NAME,  // 名字
        CallLog.Calls._ID,  // id
        CallLog.Calls.DURATION //通话时长
    )

    @SuppressLint("SimpleDateFormat")
    fun getCallLogInfos(
        context: Context,
        lastPostTime: Long
    ): List<CallLogInfoBean> {
        var lastPostTime = lastPostTime
        val callLogInfos: MutableList<CallLogInfoBean> =
            ArrayList()
        val sfd = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        //获取ContentResolver
        val contentResolver = context.contentResolver
        if (lastPostTime == 0L) {
            lastPostTime = System.currentTimeMillis() - 90 * 24 * 60 * 60 * 1000L
        }
        val cursor = contentResolver.query(
            uri, projection, null, null,
            CallLog.Calls.DEFAULT_SORT_ORDER
        )
        if (cursor != null && cursor.count > 0) {
            var date: Date
            cursor.moveToFirst()
            for (i in 0 until cursor.count) {
                cursor.moveToPosition(i)
                if (lastPostTime > cursor.getLong(
                        cursor
                            .getColumnIndex(CallLog.Calls.DATE)
                    )
                ) {
                    continue
                }
                date = Date(
                    cursor.getLong(
                        cursor
                            .getColumnIndex(CallLog.Calls.DATE)
                    )
                )
                val number = cursor.getString(
                    cursor
                        .getColumnIndex(CallLog.Calls.NUMBER)
                )
                val type = cursor.getInt(
                    cursor
                        .getColumnIndex(CallLog.Calls.TYPE)
                )
                val cachedName = cursor.getString(
                    cursor
                        .getColumnIndex(CallLog.Calls.CACHED_NAME)
                )
                val id = cursor.getInt(
                    cursor
                        .getColumnIndex(CallLog.Calls._ID)
                )
                val duration = cursor.getLong(
                    cursor
                        .getColumnIndex(CallLog.Calls.DURATION)
                )
                val callLog = CallLogInfoBean()
                callLog.id = id
                callLog.number = number
                callLog.name = cachedName
                callLog.duration = duration
                if (null == cachedName || "" == cachedName) {
                    callLog.name = number
                }
                callLog.type = type
                callLog.date = sfd.format(date)
                callLogInfos.add(callLog)
            }
        }
        return callLogInfos
    }
}