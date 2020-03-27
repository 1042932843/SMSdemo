package com.readboy.smsdemo.entry

class CallLogInfoBean {
    var id = 0
    var name // 名称
            : String? = null
    var number // 号码
            : String? = null
    var date // 日期
            : String? = null
    var type // 来电:1，拨出:2,未接:3
            = 0
    var count // 通话次数
            = 0
    var duration //通话时长
            : Long = 0

    override fun toString(): String {
        return "CallLogInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", date='" + date + '\'' +
                ", type=" + type +
                ", count=" + count +
                ", duration=" + duration +
                '}'
    }
}