package com.example.myapplication.global

/**
 * Created by Deepak Sharma on 01/03/21.
 */
class GlobalUtility {

    companion object {
        fun capitalizeString(str: String): String {
            var retStr = str
            try { // We can face index out of bound exception if the string is null
                retStr = str.substring(0, 1).toUpperCase() + str.substring(1)
            } catch (e: Exception) {
            }
            return retStr
        }

    }


}
