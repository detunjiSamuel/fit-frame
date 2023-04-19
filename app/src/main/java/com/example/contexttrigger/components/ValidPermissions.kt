package com.example.contexttrigger.components

import android.Manifest


val validPermissions = arrayOf(
    Manifest.permission.ACTIVITY_RECOGNITION,
)

class ValidPermissions {
    companion object {
        fun isValid(permission: String): Boolean {
            return validPermissions.contains(permission)
        }

        fun getAll(): Array<String> {
            return validPermissions
        }
    }
}