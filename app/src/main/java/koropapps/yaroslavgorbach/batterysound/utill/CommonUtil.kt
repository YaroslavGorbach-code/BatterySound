package koropapps.yaroslavgorbach.batterysound.utill

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

fun Uri.getName(context: Context): String {
    val returnCursor = context.contentResolver.query(this, null, null, null, null)
    val nameIndex = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor?.moveToFirst()
    val fileName = returnCursor?.getString(nameIndex!!)
    returnCursor?.close()
    return fileName.toString()
}