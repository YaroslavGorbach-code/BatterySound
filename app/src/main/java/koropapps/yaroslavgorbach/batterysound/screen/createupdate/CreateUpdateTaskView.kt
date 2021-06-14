package koropapps.yaroslavgorbach.batterysound.screen.createupdate

import android.view.View
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.data.room.BatteryTask
import koropapps.yaroslavgorbach.batterysound.databinding.DialogCreateUpdateTaskBinding
import koropapps.yaroslavgorbach.batterysound.util.filterIsEmpty
import koropapps.yaroslavgorbach.batterysound.util.filterLevel
import koropapps.yaroslavgorbach.batterysound.util.getName

class CreateUpdateTaskView(
    private val binding: DialogCreateUpdateTaskBinding,
    private val callback: Callback
) {
    private var task: BatteryTask? = null

    interface Callback {
        fun onPickFile()
        fun onAdd(level: Int, text: String?)
        fun onUpdate(batteryTask: BatteryTask)
    }

    init {
        binding.radioText.setOnClickListener {
            binding.showPickText()
        }
        binding.radioUri.setOnClickListener {
            binding.showPickUri()
        }
        binding.pickFile.setOnClickListener {
            callback.onPickFile()
        }

        binding.addUpdate.setOnClickListener {
            // if task not null it means update
            if (binding.level.filterIsEmpty() && binding.level.filterLevel()) {
                if (task == null) {
                    callback.onAdd(
                        binding.level.text.toString().toInt(),
                        if (binding.radioText.isChecked) binding.text.text.toString() else null
                    )
                } else {
                    task?.let {
                        it.batteryLevel = binding.level.text.toString().toInt()
                        it.text = binding.text.text.toString()
                        callback.onUpdate(it)
                    }
                }
            }
        }
    }

    fun setFileName(s: String) {
        binding.fileName.text = s
    }

    fun getRoot() = binding.root

    fun setTask(task: BatteryTask) {
        binding.addUpdate.text = binding.root.context.getString(R.string.update)
        binding.level.setText(task.batteryLevel.toString())
        binding.fileName.text = task.fileUri?.getName(binding.root.context)
            ?: binding.root.context.getString(R.string.file_name)

        task.text?.let {
            binding.radioText.isChecked = true
            binding.text.setText(it)
            binding.showPickText()
        }

        task.fileUri?.let {
            binding.radioUri.isChecked = true
            binding.showPickUri()
        }
        this.task = task
    }

    private fun DialogCreateUpdateTaskBinding.showPickUri() {
        textInputLayout.visibility = View.GONE
        pickFileLayout.visibility = View.VISIBLE
    }

    private fun DialogCreateUpdateTaskBinding.showPickText() {
        textInputLayout.visibility = View.VISIBLE
        pickFileLayout.visibility = View.GONE
    }

}