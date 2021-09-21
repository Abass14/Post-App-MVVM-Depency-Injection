package com.example.week_ten_task.util

import androidx.fragment.app.Fragment

object UiFunctions {
    fun closeFragment(fragment: Fragment) : Boolean{
        if (fragment.isAdded){
            fragment.parentFragmentManager.beginTransaction().remove(fragment).commit()
        }
        return true
    }
}