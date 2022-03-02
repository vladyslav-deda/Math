package com.example.mathwithfinik

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import kotlinx.coroutines.*

class DivideFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    lateinit var binding: ExerciseFragmentBinding
    private lateinit var viewModel: DivideViewModel
    var job = Job()
    val scope = CoroutineScope(Dispatchers.Main + job)

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

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var balance = sharedPref.getInt(Constants.BALANCE, 0)
        GlobalScope.launch(Dispatchers.Main) {
            val totalSeconds = 40
            val tickSeconds = 1
            for (second in totalSeconds downTo tickSeconds) {
                binding.apply {
                    progressbar.max = 40
                    progressbar.progress = second
                    tvSecondsLeft.text =
                        activity?.getString(R.string.left_time, second)?.let { String.format(it) }
                }
                delay(1000)
            }
            if (viewModel.score > 0) {
                balance += viewModel.score
                sharedPref.edit().putInt(Constants.BALANCE, balance).apply()
            }
            binding.progressbar.progress = 0
            activity?.getString(R.string.result, viewModel.score)
                ?.let { String.format(it) }?.let { showDialog(it) }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[DivideViewModel::class.java]
        viewModel.generateNewExercise(binding)
        }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun showDialog(text: String) {
        val dialog = context?.let { Dialog(it) }
        dialog?.let { dlg ->
            dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dlg.setCancelable(false)
            dlg.setContentView(R.layout.dialog_layout)
            dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dlg.findViewById<TextView>(R.id.tv_main_text).text = text
            dlg.findViewById<Button>(R.id.speach_dialog_ok_button).setOnClickListener {
                dlg.dismiss()
                binding.root.findNavController()
                    .navigate(R.id.action_divideFragment_to_mainScreenFragment)
            }
            dlg.show()
        }
    }
}