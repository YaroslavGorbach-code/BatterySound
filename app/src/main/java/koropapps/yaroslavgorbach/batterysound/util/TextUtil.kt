package koropapps.yaroslavgorbach.batterysound.util

import com.google.android.material.textfield.TextInputEditText
import koropapps.yaroslavgorbach.batterysound.R

fun TextInputEditText.filterIsEmpty(): Boolean {
    if (text.isNullOrEmpty()) {
        error = context.getString(R.string.can_not_be_empty)
        return false
    }
    return true
}
 fun TextInputEditText.filterLevel(): Boolean {
    if (text.toString().toInt()<0 || text.toString().toInt()>100) {
        error = context.getString(R.string.filter_level)
        return false
    }
    return true
}