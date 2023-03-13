package com.example.mathwithfinik.zadacha

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.mathwithfinik.Constants
import com.example.mathwithfinik.R
import com.example.mathwithfinik.databinding.ZadachaFragmentBinding
import com.example.mathwithfinik.models.Zadacha
import com.example.mathwithfinik.room_db.ShopRepository
import kotlinx.android.synthetic.main.exercise_fragment.view.*
import kotlin.random.Random

class ZadachaViewModel(val binding: ZadachaFragmentBinding) : ViewModel() {

    var score = 0
    val scoreLiveData = MutableLiveData<Int>()
    val anim by lazy {
        AnimationUtils.loadAnimation(binding.root.context, R.anim.scale_alpha_notification)
    }

    fun increaseScore() = scoreLiveData.postValue(++score)

    fun decreaseScore() {
        if (score > 0) scoreLiveData.postValue(--score)
    }

    fun getRandom() = Random.nextInt(0, 9)

    @SuppressLint("UseCompatLoadingForDrawables")
    fun generateZadacha(context: Context?, arrayListZadach: List<Zadacha>, isFirstly: Boolean = false) {
        var intAnswerFromET = 0
        if (binding.etAnswer.text.toString().isNotEmpty()) {
            intAnswerFromET = binding.etAnswer.text.toString().toInt()
        }
        var random = getRandom()
        val answer = arrayListZadach[random].answer
        binding.etAnswer.text.clear()
        if (intAnswerFromET == answer) {
            binding.notification.apply {
                visibility = View.VISIBLE
                background = context?.getDrawable(R.drawable.back_for_item)
            }
            binding.notification.tv_notification.apply {
                visibility = View.VISIBLE
                text = "Молодець"
                this.startAnimation(anim)
                Glide
                    .with(this)
                    .load(context?.let { ShopRepository(it).getSelected().icon })
                    .into(binding.imageNotification)
            }

            context?.let { ShopRepository(it).getSelected().icon }
                ?.let { binding.imageNotification.setImageResource(it) }
            increaseScore()
            binding.tvZadacha.text = arrayListZadach[random].text.trim()

        } else {
            binding.notification.apply {
                visibility = View.VISIBLE
                background = context?.getDrawable(R.drawable.back_red)
            }
            decreaseScore()
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
    }

    fun getFileName(level: String?): String = when (level) {
        Constants.EASY_CHAR -> FREE_ZADACHA_EASY
        Constants.MEDIUM_CHAR -> FREE_ZADACHA_MEDIUM
        Constants.HARD_CHAR -> FREE_ZADACHA_HARD
        else -> FREE_ZADACHA_MEDIUM

    }

    fun showDialog(context: Context?, text: String) {
        val dialog = context?.let { Dialog(it) }
        dialog?.let { dlg ->
            dlg.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setCancelable(false)
                setContentView(R.layout.dialog_first_speach_layout)
                findViewById<AppCompatImageView>(R.id.first_speach_image_finik).setImageResource(
                    ShopRepository(context).getSelected().icon
                )
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                findViewById<TextView>(R.id.tv_main_text).text = text
                findViewById<Button>(R.id.speach_dialog_ok_button).setOnClickListener {
                    dismiss()
                    binding.root.findNavController()
                        .navigate(R.id.action_zadachaFragment_to_mainScreenFragment)
                }
                show()
            }
        }
    }

    companion object {
        const val FREE_ZADACHA_EASY = "free_zadachi_easy.txt"
        const val FREE_ZADACHA_MEDIUM = "free_zadachi_medium.txt"
        const val FREE_ZADACHA_HARD = "free_zadachi_hard.txt"
    }
}