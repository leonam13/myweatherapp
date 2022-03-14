package com.example.leoweatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.leoweatherapp.databinding.FragmentErrorBinding
import com.example.leoweatherapp.exceptions.NotNetworkException

class ErrorFragment : Fragment() {

    private var _binding: FragmentErrorBinding? = null
    private val binding: FragmentErrorBinding
        get() = _binding!!

    private val args: ErrorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentErrorBinding.inflate(inflater).let {
            _binding = it
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            when (args.errorType?.cause) {
                is NotNetworkException ->
                    errorMessageTv.text = getString(R.string.error_fragment_not_network_exception)
            }
            errorMessageBtn.setOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}