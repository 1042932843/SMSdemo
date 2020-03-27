package com.readboy.smsdemo.entry

import android.text.TextUtils
import java.util.regex.Pattern

class ContactBean {
    private var name: String? = null
    private var phoneNumber: String? = null
    private var email: String? = null
    fun getName(): String? {
        return if (TextUtils.isEmpty(name)) {
            ""
        } else name
    }

    fun getPhoneNumber(): String? {
        return if (TextUtils.isEmpty(phoneNumber)) {
            ""
        } else phoneNumber
    }

    fun getEmail(): String? {
        return if (TextUtils.isEmpty(email)) {
            ""
        } else email
    }

    fun setEmail(email: String?) {
        if (TextUtils.isEmpty(email)) {
            this.email = ""
        } else {
            this.email = email
        }
    }

    fun setPhoneNumber(phoneNumber: String?) {
        var phoneNumber = phoneNumber
        if (TextUtils.isEmpty(phoneNumber)) {
            if (TextUtils.isEmpty(this.phoneNumber)) {
                this.phoneNumber = ""
            }
        } else {
            val p = Pattern.compile("\\s*|\t|\r|\n|")
            val m = p.matcher(phoneNumber)
            phoneNumber = m.replaceAll("")
            phoneNumber = phoneNumber.replace("\\-".toRegex(), "")
            if (TextUtils.isEmpty(this.phoneNumber)) {
                this.phoneNumber = phoneNumber
            } else {
                this.phoneNumber += ",$phoneNumber"
            }
        }
    }

    fun setName(name: String?) {
        if (TextUtils.isEmpty(name)) {
            this.name = ""
        } else {
            this.name = name
        }
    }

    override fun toString(): String {
        return "ContactBean{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}'
    }
}