package com.example.mathwithfinik.plus_minus_screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mathwithfinik.Constants
import com.example.mathwithfinik.R
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import kotlinx.coroutines.*

class PlusMinusFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: ExerciseFragmentBinding
    private lateinit var viewModel: PlusMinusViewModel
    private var job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private var balance = 0
    private var result: String? = ""


    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPref = context.getSharedPreferences(
            Constants.SHARED_PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExerciseFragmentBinding.inflate(inflater, container, false)
        viewModel = PlusMinusViewModel(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        balance = sharedPref.getInt(Constants.BALANCE, 0)
        binding.progressbar.max = 40
        result = arguments?.getString("level_plus_minus")
        scope.launch {
            val tickSeconds = 0
            for (second in 40 downTo tickSeconds) {
                binding.apply {
                    progressbar.progress = second
                    tvSecondsLeft.text =
                        activity?.getString(R.string.left_time, second)
                            ?.let { String.format(it) }
                }

                delay(1000)
            }
            if (viewModel.score > 0) {
                balance += viewModel.score
                sharedPref.edit().putInt(Constants.BALANCE, balance).apply()
            }
            binding.progressbar.progress = 0
            activity?.getString(R.string.result, viewModel.score)
                ?.let { String.format(it) }?.let { viewModel.showDialog(context, it) }

        }

        viewModel.generateNewExercise(result)

        viewModel.scoreLiveData.observe(viewLifecycleOwner) {
            activity?.runOnUiThread {
                binding.tvScore.text = "Твій рахунок: $it"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}