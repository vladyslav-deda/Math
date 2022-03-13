package com.example.mathwithfinik.multiply

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

class MultiplyFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    lateinit var binding: ExerciseFragmentBinding
    lateinit var viewModel: MultiplyViewModel
    var job = Job()
    var job2 = Job()
    val scope = CoroutineScope(Dispatchers.IO + job)
    val scope2 = CoroutineScope(Dispatchers.Main + job2)
    private var balance = 0

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
        viewModel = MultiplyViewModel(binding)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        balance = sharedPref.getInt(Constants.BALANCE, 0)
        binding.progressbar.max = 40
        binding.exercise.symbol.text = "*"
        scope.launch {
            val tickSeconds = 1
            for (second in 40 downTo tickSeconds) {
                activity?.runOnUiThread {
                    binding.apply {
                        progressbar.progress = second
                        tvSecondsLeft.text =
                            activity?.getString(R.string.left_time, second)
                                ?.let { String.format(it) }
                    }
                }
                delay(1000)
            }
            if (viewModel.score > 0) {
                balance += viewModel.score
                sharedPref.edit().putInt(Constants.BALANCE, balance).apply()
            }
            activity?.runOnUiThread {
                binding.progressbar.progress = 0
                activity?.getString(R.string.result, viewModel.score)
                    ?.let { String.format(it) }?.let { viewModel.showDialog(context, it) }
            }
        }
        GlobalScope.launch {
            viewModel.generateNewExercise()
        }
        viewModel.scoreLiveData.observe(viewLifecycleOwner) {
            activity?.runOnUiThread {
                binding.tvScore.text = "Твій рахунок: $it"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        job2.cancel()
    }
}