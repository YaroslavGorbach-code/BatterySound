package koropapps.yaroslavgorbach.batterysound.screen.tasks

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.data.room.BatteryTask
import koropapps.yaroslavgorbach.batterysound.databinding.ItemTaskBinding
import koropapps.yaroslavgorbach.batterysound.util.getName

class TasksListAdapter(private val callback: Callback) :
    RecyclerView.Adapter<TasksListAdapter.Vh>() {

    interface Callback {
        fun onSwitchChecked(batteryTask: BatteryTask, isChecked: Boolean)
        fun onTask(batteryTask: BatteryTask)
    }

    private var data: List<BatteryTask> = ArrayList()

    init {
        setHasStableIds(true)
    }

    fun getList(): List<BatteryTask> {
        return data
    }

    fun setData(items: List<BatteryTask>) {
        data = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh =
        Vh(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false), callback)

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.bind(data[position])
    override fun getItemCount(): Int = data.size
    override fun getItemId(position: Int): Long {
        return data[position].id.toLong()
    }

    inner class Vh(
        private val binding: ItemTaskBinding,
        private val callback: Callback
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                callback.onTask(data[adapterPosition])
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: BatteryTask) {
            if (!item.text.isNullOrEmpty()) binding.text.text = item.text
            if (item.fileUri != null) binding.text.text = item.fileUri!!.getName(itemView.context)
            binding.level.text = item.batteryLevel.toString() + "%"
            binding.icBattery.setImageDrawable(when(item.batteryLevel){
                in 0..29-> ContextCompat.getDrawable(itemView.context, R.drawable.ic_battery_low)
                in 30..69-> ContextCompat.getDrawable(itemView.context, R.drawable.ic_battery_half)
                else -> ContextCompat.getDrawable(itemView.context, R.drawable.ic_battery_full)
            })
            if (item.isActive){
                binding.ovalActive.drawable.setTint(Color.GREEN)
                binding.textActive.setText(R.string.active)
                binding.textActive.setTextColor(Color.GRAY)
            }else{
                binding.ovalActive.drawable.setTint(Color.LTGRAY)
                binding.textActive.setText(R.string.not_active)
                binding.textActive.setTextColor(Color.LTGRAY)
            }
            // workaround because we can't change switch isChecked state with active listener
            binding.start.setOnCheckedChangeListener(null)
            binding.start.isChecked = item.isActive
            binding.start.setOnCheckedChangeListener { _, isChecked ->
                callback.onSwitchChecked(data[adapterPosition], isChecked)
            }
        }
    }
}