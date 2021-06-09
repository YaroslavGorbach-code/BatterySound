package koropapps.yaroslavgorbach.batterysound.screen

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import koropapps.yaroslavgorbach.batterysound.databinding.DialogAddTaskBinding
import koropapps.yaroslavgorbach.batterysound.utill.filterIsEmpty
import koropapps.yaroslavgorbach.batterysound.utill.filterLevel

class AddTaskDialog : DialogFragment() {

    interface Host {
        fun onAdd(level: Int, text: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bind = DialogAddTaskBinding.inflate(LayoutInflater.from(context))
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(bind.root)
            .setTitle("Add task")
            .create()
        bind.add.setOnClickListener {
            if (bind.level.filterIsEmpty() && bind.level.filterLevel()) {
                (parentFragment as Host).onAdd(
                    bind.level.text.toString().toInt(),
                    bind.text.text.toString()
                )
                dialog.dismiss()
            }
        }

        return dialog
    }
}


