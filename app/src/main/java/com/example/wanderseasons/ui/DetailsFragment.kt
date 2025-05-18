package com.example.wanderseasons.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.wanderseasons.databinding.FragmentDetailsBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val json = args.entityJson
        val mapType = object : TypeToken<Map<String, Any>>() {}.type
        val entity: Map<String, Any> = Gson().fromJson(json, mapType)

        // Pick a title and subtitle to feature
        val keys = entity.keys.toList()
        val title = entity["name"] ?: entity["itemName"] ?: entity[keys.first()]
        val subtitle = entity["architect"] ?: entity["designer"] ?: entity[keys.getOrNull(1)]

        binding.property1TextView.text = title.toString().replaceFirstChar { it.uppercase() }
        binding.property2TextView.text = subtitle?.toString()?.replaceFirstChar { it.uppercase() } ?: ""

        // Format the rest of the key-value pairs as description
        val formatted = entity.entries
            .filterNot { it.key == "name" || it.key == "itemName" || it.key == "architect" || it.key == "designer" }
            .joinToString("\n\n") { (key, value) ->
                val label = key.replaceFirstChar { it.uppercase() }
                val cleanValue = value.toString().removeSuffix(".0")
                "$label:\n$cleanValue"
            }

        binding.descriptionTextView.text = formatted

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
