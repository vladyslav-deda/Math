package com.example.presentation.math_task.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.holder.model.Task
import com.example.domain.shop.ShopRepository
import com.example.domain.shop.model.ShopItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MathTaskViewModel @Inject constructor(
    private val shopRepository: ShopRepository
) : ViewModel() {

    private val _currentScore = MutableLiveData(0)
    val currentScore: LiveData<Int> = _currentScore

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val listOfTasks = ArrayList<Task>()

    fun setIsLoading(isLoading: Boolean) = _isLoading.postValue(isLoading)

    fun addTask(task: Task) = listOfTasks.add(task)

    fun getListOfTasks() = listOfTasks


    fun getSelectedItem(): ShopItem = shopRepository.getSelectedItem()

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
}