package com.example.mathwithfinik.zadacha

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mathwithfinik.Constants
import com.example.mathwithfinik.R
import com.example.mathwithfinik.databinding.ZadachaFragmentBinding
import com.example.mathwithfinik.models.Zadacha
import kotlinx.android.synthetic.main.exercise_fragment.view.*
import kotlin.random.Random

class ZadachaViewModel(val binding: ZadachaFragmentBinding) : ViewModel() {


    var score = 0
    val scoreLiveData = MutableLiveData<Int>()
    val anim by lazy {
        AnimationUtils.loadAnimation(binding.root.context, R.anim.scale_alpha_notification)
    }

    private fun increaseScore() = scoreLiveData.postValue(++score)

    private fun decreaseScore() {
        if (score > 0) scoreLiveData.postValue(--score)
    }

    private fun getRandom() = Random.nextInt(0, 9)

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
            binding.notification.background = context?.getDrawable(R.drawable.back_for_item)
            binding.notification.tv_notification.apply {
                visibility = View.VISIBLE
                text = "Молодець"
                this.startAnimation(anim)
            }
            increaseScore()
            binding.tvZadacha.text = arrayListZadach[random].text.trim()

        } else {
            binding.notification.background = context?.getDrawable(R.drawable.back_red)
            decreaseScore()
            binding.notification.tv_notification.apply {
                visibility = View.VISIBLE
                text = "Подумай краще"
                this.startAnimation(anim)
            }
        }
    }

    fun getFileName(level: String?): String = if (Constants.isPremium) {
        when (level) {
            Constants.EASY_CHAR -> PREMIUM_ZADACHA_EASY
            Constants.MEDIUM_CHAR -> PREMIUM_ZADACHA_MEDIUM
            Constants.HARD_CHAR -> PREMIUM_ZADACHA_HARD
            else -> PREMIUM_ZADACHA_MEDIUM
        }
    } else {
        when (level) {
            Constants.EASY_CHAR -> FREE_ZADACHA_EASY
            Constants.MEDIUM_CHAR -> FREE_ZADACHA_MEDIUM
            Constants.HARD_CHAR -> FREE_ZADACHA_HARD
            else -> FREE_ZADACHA_MEDIUM
        }
    }

    companion object {
        const val FREE_ZADACHA_EASY = "free_zadachi_easy.txt"
        const val FREE_ZADACHA_MEDIUM = "free_zadachi_easy.txt"
        const val FREE_ZADACHA_HARD = "free_zadachi_easy.txt"
        const val PREMIUM_ZADACHA_EASY = "free_zadachi_easy"
        const val PREMIUM_ZADACHA_MEDIUM = "free_zadachi_easy"
        const val PREMIUM_ZADACHA_HARD = "free_zadachi_easy"
    }
}