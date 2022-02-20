package com.example.mathwithfinik.multiply

import android.animation.ObjectAnimator
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mathwithfinik.Constants
import com.example.mathwithfinik.R
import com.example.mathwithfinik.databinding.ExerciseBinding
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MultiplyFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    lateinit var binding: ExerciseFragmentBinding
    private var viewModel = MultiplyViewModel()
    private var progress = 40
    val job = Job()
    val scope = CoroutineScope(Dispatchers.Main + job)
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        balance = sharedPref.getInt(Constants.BALANCE, 0)
        binding.progressbar.progress = progress
        object : CountDownTimer(40000, 1000) {
            override fun onTick(p0: Long) {
                progress--
                binding.progressbar.max = 40
                val i = progress * 100 / (30000 / 1000)
                binding.progressbar.progress = progress
                binding.tvSecondsLeft.text =
                    activity?.getString(R.string.left_time, progress)?.let { String.format(it) }
            }

            override fun onFinish() {
                binding.progressbar.progress = 0
                if (viewModel.score > 0) {
                    balance += viewModel.score
                    sharedPref.edit().putInt(Constants.BALANCE, balance).apply()
                }
                activity?.getString(R.string.result, viewModel.score)
                    ?.let { String.format(it) }?.let { showDialog(it) }
            }

        }.start()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MultiplyViewModel::class.java]
        scope.launch {
            viewModel.generateNewExercise(binding)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun showDialog(text: String) {
        val dialog = context?.let { Dialog(it) }
        dialog?.let { dialog ->
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_layout)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.findViewById<TextView>(R.id.tv_main_text).text = text
            dialog.findViewById<Button>(R.id.speach_dialog_ok_button).setOnClickListener {
                dialog.dismiss()
                binding.root.findNavController()
                    .navigate(R.id.action_multiplyFragment_to_mainScreenFragment)
            }
            dialog.show()
        }
    }
}