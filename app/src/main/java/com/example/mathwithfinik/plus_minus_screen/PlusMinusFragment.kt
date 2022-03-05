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
import androidx.navigation.findNavController
import com.example.mathwithfinik.Constants
import com.example.mathwithfinik.R
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlusMinusFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: ExerciseFragmentBinding
    private lateinit var viewModel: PlusMinusViewModel
    var job = Job()
    var counter = 40
    val scope = CoroutineScope(Dispatchers.Main + job)
    private var balance = 0
    var level : Char? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPref = context.getSharedPreferences(
            Constants.SHARED_PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(Constants.LEVEL_KEY) { _, bundle ->
            // Any type can be passed via to the bundle
            level = bundle.getChar(Constants.LEVEL)
            // Do something with the result...
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExerciseFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        object : CountDownTimer(40000, 1000) {
            override fun onTick(p0: Long) {
                activity?.runOnUiThread {
                    binding.apply {
                        progressbar.max = 40
                        progressbar.progress = counter
                        tvSecondsLeft.text =
                            activity?.getString(R.string.left_time, counter)?.let { String.format(it) }
                    }
                }
                counter--
            }

            override fun onFinish() {
                if (viewModel.score > 0) {
                    balance += viewModel.score
                    sharedPref.edit().putInt(Constants.BALANCE, balance).apply()
                }
                activity?.runOnUiThread {
                    binding.progressbar.progress = 0
                    activity?.getString(R.string.result, viewModel.score)
                        ?.let { String.format(it) }?.let { showDialog(it) }
                }
            }
        }.start()

        scope.launch {
            viewModel.generateNewExercise(binding, savedInstanceState?.getChar(Constants.LEVEL, Constants.MEDIUM_CHAR))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[PlusMinusViewModel::class.java]
        // TODO: Use the ViewModel
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