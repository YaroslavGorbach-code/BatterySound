package koropapps.yaroslavgorbach.batterysound.screen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.data.BatteryTask
import koropapps.yaroslavgorbach.batterysound.databinding.ItemTaskBinding

class TasksListAdapter(private val onTask: (BatteryTask) -> Unit) :
    RecyclerView.Adapter<TasksListAdapter.Vh>() {
    private var data: List<BatteryTask> = ArrayList()

    fun setData(items: List<BatteryTask>) {
        data = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh =
        Vh(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false), onTask)

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.bind(data[position])
    override fun getItemCount(): Int = data.size


    inner class Vh(
        private val binding: ItemTaskBinding,
        private val onTask: (BatteryTask) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onTask(data[adapterPosition])
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: BatteryTask) {
            binding.text.text = item.text
            binding.level.text = item.level.toString() + "%"

            if (item.isActive) {
                binding.icPlay.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.ic_stop
                    )
                )
            } else {
                binding.icPlay.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.ic_play
                    )
                )
            }
        }
    }
}