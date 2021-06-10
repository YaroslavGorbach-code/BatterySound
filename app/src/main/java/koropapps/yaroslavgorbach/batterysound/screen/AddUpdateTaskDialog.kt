package koropapps.yaroslavgorbach.batterysound.screen

import android.Manifest
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.data.BatteryTask
import koropapps.yaroslavgorbach.batterysound.databinding.DialogAddUpdateTaskBinding
import koropapps.yaroslavgorbach.batterysound.utill.filterIsEmpty
import koropapps.yaroslavgorbach.batterysound.utill.filterLevel
import koropapps.yaroslavgorbach.batterysound.utill.getName

class AddUpdateTaskDialog : DialogFragment() {

    interface Host {
        fun onAdded(level: Int, text: String?, fileUri: Uri?)
        fun onUpdated(batteryTask: BatteryTask)
    }

    companion object {
        fun argsOf(task: BatteryTask) = bundleOf("task" to task)
        private val AddUpdateTaskDialog.task: BatteryTask? get() = arguments?.let { it["task"] as BatteryTask }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bind = DialogAddUpdateTaskBinding.inflate(LayoutInflater.from(context))
        var fileUri: Uri? = null
        val getUri = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            bind.fileName.text = uri?.getName(requireContext()) ?: "null"
            fileUri = uri
        }

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    getUri.launch("audio/*")
                }
            }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(bind.root)
            .setTitle(R.string.createTask)
            .create()

        // if task not null it means update
        task?.let {
            dialog.setTitle(R.string.updateTask)
            bind.add.text = getString(R.string.update)
            bind.level.setText(task!!.batteryLevel.toString())
            bind.text.setText(task!!.text.toString())
            bind.fileName.text = task!!.fileUri?.getName(requireContext()) ?: "File name"

            it.text?.let {
                bind.radioText.isChecked = true
                showPickText(bind)
            }
            it.fileUri?.let {
                bind.radioUri.isChecked = true
                showPickUri(bind)
            }

        }
        bind.radioText.setOnClickListener {
            showPickText(bind)
        }
        bind.radioUri.setOnClickListener {
            showPickUri(bind)
        }
        bind.pickFile.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        bind.add.setOnClickListener {
            // if task not null it means update
            if (bind.level.filterIsEmpty() && bind.level.filterLevel()) {
                if (task != null) {
                    task!!.batteryLevel = bind.level.text.toString().toInt()
                    task!!.text = bind.text.text.toString()
                    task!!.fileUri = fileUri
                    (parentFragment as Host).onUpdated(task!!)
                } else {
                    (parentFragment as Host).onAdded(
                        bind.level.text.toString().toInt(),
                        if (bind.radioText.isChecked) bind.text.text.toString() else null,
                        if (bind.radioUri.isChecked) fileUri else null,
                        )
                }
                dialog.dismiss()
            }
        }
        return dialog
    }

    private fun showPickUri(bind: DialogAddUpdateTaskBinding) {
        bind.textInputLayout.visibility = View.GONE
        bind.pickFileLayout.visibility = View.VISIBLE
    }

    private fun showPickText(bind: DialogAddUpdateTaskBinding) {
        bind.textInputLayout.visibility = View.VISIBLE
        bind.pickFileLayout.visibility = View.GONE
    }
}


