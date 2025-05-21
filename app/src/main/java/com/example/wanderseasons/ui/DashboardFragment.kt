package com.example.wanderseasons.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanderseasons.R
import com.example.wanderseasons.databinding.FragmentDashboardBinding
import com.example.wanderseasons.viewmodel.MainViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val args: DashboardFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val gson = Gson()

        val adapter = EntityAdapter { entityMap ->
            val json = gson.toJson(entityMap)
            val action = DashboardFragmentDirections
                .actionDashboardFragmentToDetailsFragment(entityJson = json)
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.fetchDashboard(args.keypass)

        viewModel.dashboardData.observe(viewLifecycleOwner) { rawEntities ->
            val formatted = rawEntities.mapNotNull { entityRaw ->
                val entity = entityRaw as? Map<*, *> ?: return@mapNotNull null

                val keyList = entity.keys.mapNotNull { it as? String }
                val title = keyList.getOrNull(0)?.let { entity[it].toString() } ?: "Untitled"
                val subtitle = keyList.getOrNull(1)?.let { "$it: ${entity[it]}" } ?: ""

                val actualDescription = entity["description"]?.toString()?.trim() ?: ""

                val additionalDetails = entity.entries
                    .filter { (key, _) ->
                        key?.toString()?.lowercase() != "description"
                    }
                    .joinToString("\n") { (key, value) ->
                        "${key.toString().replaceFirstChar { it.uppercase() }}: $value"
                    }

                val fullDescription = if (actualDescription.isNotEmpty()) {
                    "$actualDescription\n\n$additionalDetails"
                } else {
                    additionalDetails
                }

                mapOf(
                    "title" to title,
                    "subtitle" to subtitle,
                    "description" to fullDescription
                )
            }

            adapter.submitList(formatted)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        // Handle logoff button
        binding.logoffButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
