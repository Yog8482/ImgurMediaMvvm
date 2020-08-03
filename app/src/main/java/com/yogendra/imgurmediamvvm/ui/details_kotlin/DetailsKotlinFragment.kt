package com.yogendra.imgurmediamvvm.ui.details_kotlin

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.yogendra.imgurmediamvvm.R
import com.yogendra.imgurmediamvvm.databinding.DetailsFragmentBinding
import com.yogendra.imgurmediamvvm.utils.custom.MultilineSnackbar

class DetailsKotlinFragment : Fragment() {


    private lateinit var binding: DetailsFragmentBinding
    private val args: DetailsKotlinFragmentArgs by navArgs()


    lateinit var viewModel: DetailsKotlinViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DetailsFragmentBinding.bind(
            inflater.inflate(
                R.layout.details_fragment,
                container,
                false
            )
        )
        viewModel = DetailsKotlinfragmentFactory(
            Application(),
            args.imageId
        ).create(DetailsKotlinViewModel::class.java)

        scribeUI()

        binding.submitButton.setOnClickListener {

            val comment: String = binding.editTextMultiLine.text.toString()
            clearFocus()
            binding.data?.local_comment = comment
            viewModel.updateData(binding.data!!)
        }


        return binding.root
    }


    fun scribeUI() {

        viewModel.mData.observe(viewLifecycleOwner) { result ->
            binding.data = result
        }

        viewModel.updateSuccess.observe(viewLifecycleOwner) { result ->

            val message: String

            if (result > 0) {
                message = resources.getString(R.string.update_success)
            } else {
                message = resources.getString(R.string.update_failed)
            }

            MultilineSnackbar(binding.root, message).show()

        }

    }


    fun clearFocus() {
        binding.editTextMultiLine.clearFocus()
        binding.submitButton.requestFocus()
    }

}