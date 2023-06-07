package com.example.presentation.add_task.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.firebase_tasks_db.usecase.AddTaskUseCase
import com.example.presentation.base.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {

    private val _requestState = MutableLiveData<RequestState>()
    val requestState: LiveData<RequestState> = _requestState

    fun addTask(
        textOfTask: String,
        answerOfTask: Int,
    ) {
        viewModelScope.launch {
            addTaskUseCase.invoke(
                textOfTask,
                answerOfTask,
                onSuccess = {
                    _requestState.postValue(RequestState.Successful())
                },
                onError = {
                    _requestState.postValue(RequestState.Error)
                })
        }
    }
}