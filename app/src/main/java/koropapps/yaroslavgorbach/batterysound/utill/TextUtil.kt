package koropapps.yaroslavgorbach.batterysound.utill

import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.filterIsEmpty(): Boolean {
    if (text.isNullOrEmpty()) {
        error = "It can't be empty"
        return false
    }
    return true
}
 fun TextInputEditText.filterLevel(): Boolean {
    if (text.toString().toInt()<0 || text.toString().toInt()>100) {
        error = "Can't be bigger less then 0 or bigger then 100"
        return false
    }
    return true
}