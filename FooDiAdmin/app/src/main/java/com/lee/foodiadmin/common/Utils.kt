package com.lee.foodiadmin.common

import android.widget.Toast

const val PAGE_ONE = "1"
const val EXTRA_SELECTED_FOOD = "selected_food"
const val NOT_AVAILABLE = "N/A"

const val UPDATE_DATA = "com.lee.foodiadmin.common.UPDATE_DATA"

class Utils {
    companion object{
        fun toastMessage(message : String) {
            Toast.makeText(FooDiAdminApplication.getInstance() , message , Toast.LENGTH_SHORT).show()
        }
    }
}