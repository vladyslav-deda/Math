package com.example.mathwithfinik.zadacha

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mathwithfinik.R
import com.example.mathwithfinik.databinding.ZadachaFragmentBinding
import com.example.mathwithfinik.models.Zadacha
import com.example.mathwithfinik.room_db.ShopRepository
import kotlinx.android.synthetic.main.exercise_fragment.view.*
import kotlin.random.Random


class ZadachaFragment : Fragment() {

    private lateinit var viewModel: ZadachaViewModel
    private lateinit var file: String
    private lateinit var fileName: String
    private var level: String? = null
    lateinit var binding: ZadachaFragmentBinding
    lateinit var arrayListZadach: List<Zadacha>

    val anim by lazy {
        AnimationUtils.loadAnimation(binding.root.context, R.anim.scale_alpha_notification)
    }
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

            tvScore.text = activity?.getString(R.string.score, 0)
        }

        var random = viewModel.getRandom()
        var zadacha = arrayListZadach[random]
        binding.tvZadacha.text = zadacha.text
        binding.buttonAnswer.setOnClickListener {
            binding.notification.apply {
                visibility = View.VISIBLE
                background = context?.getDrawable(R.drawable.back_for_item)
            }

            if (binding.etAnswer.text.toString().toInt() == zadacha.answer) {
                viewModel.increaseScore()
                var newRandom = viewModel.getRandom()
                while (newRandom == random) newRandom = viewModel.getRandom()
                random = newRandom
                zadacha = arrayListZadach[random]
                binding.tvZadacha.text = zadacha.text


                binding.notification.tv_notification.apply {
                    visibility = View.VISIBLE
                    text = "Молодець"
                    this.startAnimation(anim)
                    Glide
                        .with(this)
                        .load(context?.let { ShopRepository(it).getSelected().icon })
                        .into(binding.imageNotification)
                }
            } else {
                viewModel.decreaseScore()
                binding.notification.tv_notification.apply {
                    visibility = View.VISIBLE
                    text = "Подумай краще"
                    this.startAnimation(anim)
                    Glide
                        .with(this)
                        .load(context?.let { ShopRepository(it).getSelected().icon })
                        .into(binding.imageNotification)
                }
            }
            binding.etAnswer.text.clear()
        }

        viewModel.scoreLiveData.observe(viewLifecycleOwner) {
            activity?.runOnUiThread {
                binding.tvScore.text = activity?.getString(R.string.score, it)
            }
        }
        viewModel.scoreLiveData.observe(viewLifecycleOwner) { score ->
            if (score == 10) {
                activity?.getString(R.string.result, viewModel.score)
                    ?.let { String.format(it) }?.let { viewModel.showDialog(context, it) }
            }
        }

    }
}