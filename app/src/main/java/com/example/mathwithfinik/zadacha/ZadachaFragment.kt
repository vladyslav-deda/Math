package com.example.mathwithfinik.zadacha

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mathwithfinik.R
import com.example.mathwithfinik.databinding.ZadachaFragmentBinding
import com.example.mathwithfinik.models.Zadacha
import kotlin.random.Random


class ZadachaFragment : Fragment() {

    private lateinit var viewModel: ZadachaViewModel
    private lateinit var file: String
    private lateinit var fileName: String
    private var level: String? = null
    lateinit var binding: ZadachaFragmentBinding
    lateinit var arrayListZadach: List<Zadacha>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ZadachaFragmentBinding.inflate(inflater, container, false)
        viewModel = ZadachaViewModel(binding)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        level = arguments?.getString("level")
        fileName = viewModel.getFileName(level)
        file = context?.let { context ->
            context.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
        }.toString()
        arrayListZadach = file.split("***").map {
            it.split("|")
        }.map {
            Zadacha(it[0], it[1].trim().toInt())
        }


        binding.apply {
            tvZadacha.text = arrayListZadach[Random.nextInt(0, 9)].text.trim()
            tvScore.text = activity?.getString(R.string.score, 0)
        }

        binding.buttonAnswer.setOnClickListener {
            viewModel.generateZadacha(context, arrayListZadach)
        }

        viewModel.scoreLiveData.observe(viewLifecycleOwner) {
            activity?.runOnUiThread {
                binding.tvScore.text = activity?.getString(R.string.score, it)
            }
        }

    }
}