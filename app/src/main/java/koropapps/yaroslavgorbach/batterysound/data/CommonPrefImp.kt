package koropapps.yaroslavgorbach.batterysound.data

import android.content.Context
import androidx.core.content.edit

class CommonPrefImp(context: Context) : CommonPref{
    private val prefs = context.getSharedPreferences("batterysound.prefs", Context.MODE_PRIVATE)

    override fun setDoNotDisturbStart(h: Int, m: Int) {
        prefs.edit {
            putInt("startH", h)
            putInt("startM", m)
        }
    }

    override fun setDoNotDisturbEnd(h: Int, m: Int) {
        prefs.edit {
            putInt("endH", h)
            putInt("endM", m)
        }
    }

    override fun getDoNotDisturbStartH(): Int {
        return prefs.getInt("startH", 22)
    }

    override fun getDoNotDisturbStartM(): Int {
        return prefs.getInt("startM", 0)
    }

    override fun getDoNotDisturbEndH(): Int {
        return prefs.getInt("endH", 9)
    }

    override fun getDoNotDisturbEndM(): Int {
        return prefs.getInt("endM", 0)
    }
}