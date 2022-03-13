package com.example.mathwithfinik.plus_minus_screen

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mathwithfinik.Constants
import com.example.mathwithfinik.R
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import kotlinx.coroutines.*
import javax.xml.datatype.DatatypeConstants.SECONDS

class PlusMinusFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: ExerciseFragmentBinding
    private lateinit var viewModel: PlusMinusViewModel
    var job = Job()
    var job2 = Job()
    val scope = CoroutineScope(Dispatchers.Main + job)
    val scope2 = CoroutineScope(Dispatchers.Main + job2)
    private var balance = 0
    var result: Char? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPref = context.getSharedPreferences(
            Constants.SHARED_PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("requestKey") { _, bundle ->
            result = bundle.getChar("bundleKey")
        }
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
            viewModel.generateNewExercise(result)
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
/**
 * private fun runCountDown() {
timerJob = viewModelScope.launch {
val tickSeconds = 1
for (second in 10 downTo tickSeconds) {
mCountDownValue.value = second
delay(SECONDS.toMillis(1))
}
dismissEvent.call()
}
}
 */