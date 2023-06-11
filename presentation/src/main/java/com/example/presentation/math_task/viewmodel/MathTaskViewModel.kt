package com.example.presentation.math_task.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.firebase_tasks_db.model.Task
import com.example.domain.firebase_tasks_db.usecase.LoadTasksUseCase
import com.example.domain.firebase_users_db.usecase.UpdateMoneyBalanceUseCase
import com.example.domain.holder.SessionHolder
import com.example.presentation.R
import com.example.presentation.base.RequestState
import com.example.presentation.base.extension.getImageRes
import com.example.presentation.base.extension.getSelectedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MathTaskViewModel @Inject constructor(
    private val updateMoneyBalanceUseCase: UpdateMoneyBalanceUseCase,
    private val loadTasksUseCase: LoadTasksUseCase
) : ViewModel() {

    private val _currentScore = MutableLiveData(0)
    val currentScore: LiveData<Int> = _currentScore

    private val _requestState = MutableLiveData<RequestState>()
    val requestState: LiveData<RequestState> = _requestState

    private val listOfTasks = ArrayList<Task>()

    init {
        loadTasks()
    }

    fun addTask(task: Task) = listOfTasks.add(task)

    fun getListOfTasks() = listOfTasks

    fun increaseScore() {
        val newScore = _currentScore.value?.plus(1)
        _currentScore.postValue(newScore)
    }

    fun decreaseScore() {
        _currentScore.value?.let { currentScore ->
            if (currentScore > 0) {
                val newScore = currentScore - 1
                _currentScore.postValue(newScore)
            }
        }
    }

    fun updateMoneyBalance(
        newBalance: Int,
    ) {
        SessionHolder.currentUser?.userName?.let {
            viewModelScope.launch {
                updateMoneyBalanceUseCase.invoke(it, newBalance)
            }
        }
        SessionHolder.currentUser?.moneyBalance = newBalance
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _requestState.postValue(RequestState.Loading(true))
            loadTasksUseCase.invoke(
                onSuccess = {
                    _requestState.postValue(RequestState.Successful(it))
                },
                onError = {
                    _requestState.postValue(RequestState.Error)
                }
            )
            _requestState.postValue(RequestState.Loading(false))
        }
    }

    fun getSelectedItemIcon() =
        SessionHolder.currentUser?.shopItems?.getSelectedItem()?.getImageRes()
            ?: R.drawable.logo_cat
}