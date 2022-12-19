package com.lee.foodiadmin.common

import android.widget.Toast

const val PAGE_ONE = "1"

class Utils {
    companion object{
        fun toastMessage(message : String) {
            Toast.makeText(FooDiAdminApplication.getInstance() , message , Toast.LENGTH_SHORT).show()
        }
    }
}