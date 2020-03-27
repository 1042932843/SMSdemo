package com.readboy.smsdemo.entry

class MessageBean {
    var name: String? = null
    var phoneNumber: String? = null
    var smsBody: String? = null
    var date: String? = null
    var type: String? = null

    override fun toString(): String {
        return "MessageBean{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", smsBody='" + smsBody + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                '}'
    }
}