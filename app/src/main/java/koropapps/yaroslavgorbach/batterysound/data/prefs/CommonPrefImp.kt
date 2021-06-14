package koropapps.yaroslavgorbach.batterysound.data.prefs

import android.content.Context
import androidx.core.content.edit
import koropapps.yaroslavgorbach.batterysound.data.prefs.CommonPref

class CommonPrefImp(context: Context) : CommonPref {
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
        return prefs.getInt("endH", 5)
    }

    override fun getDoNotDisturbEndM(): Int {
        return prefs.getInt("endM", 0)
    }
}