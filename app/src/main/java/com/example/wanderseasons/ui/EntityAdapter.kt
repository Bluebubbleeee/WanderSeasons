package com.example.wanderseasons.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wanderseasons.databinding.ItemEntityBinding

class EntityAdapter(
    private val onClick: (Map<String, Any>) -> Unit
) : RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {

    private val items = mutableListOf<Map<String, Any>>()

    fun submitList(data: List<Map<String, Any>>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val binding = ItemEntityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class EntityViewHolder(private val binding: ItemEntityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: Map<String, Any>) {
            val keys = entity.keys.toList()
            val firstKey = keys.getOrNull(0) ?: "Item"
            val secondKey = keys.getOrNull(1)
            val thirdKey = keys.getOrNull(2)

            binding.itemNameText.text = entity["title"]?.toString() ?: ""
            binding.designerText.text = entity["subtitle"]?.toString() ?: ""
            binding.materialText.text = "Tap for full details →"

//            ###############Basic working version#######################
//            binding.root.setOnClickListener {
//                val description = entity.entries.joinToString("\n") { "${it.key.uppercase()}: ${it.value}" }
//                onClick(
//                    mapOf(
//                        "title" to entity[firstKey].toString(),
//                        "subtitle" to secondKey?.let { "${entity[it]}" }.orEmpty(),
//                        "description" to description
//                    )
//                )
//            }

            // #############Bit better working version####################

            binding.root.setOnClickListener {
                val keys = entity.keys.toList()
                val descriptionIndex = keys.indexOf("description")

                val description = entity["description"]?.toString().orEmpty()

                val details = if (descriptionIndex != -1 && descriptionIndex < keys.lastIndex) {
                    keys.subList(descriptionIndex + 1, keys.size)
                        .map { key ->
                            val value = entity[key]?.toString().orEmpty()
                            "${key.uppercase()}: ${value.uppercase()}"
                        }
                        .joinToString("\n", prefix = "\n")
                } else {
                    ""
                }

                onClick(
                    mapOf(
                        "title" to entity[firstKey].toString(),
                        "essentials" to secondKey?.let { entity[it].toString() }.orEmpty(),
                        "description" to description,
                    )
                )
            }


        }
    }
}
