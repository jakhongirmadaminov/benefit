package com.example.benefit.ui.auth.order_card

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by jahon on 06-Sep-20
 */
class OrderCardViewModel @ViewModelInject constructor() : ViewModel() {

    private val _currentStep = MutableLiveData(1)
    val currentStep: LiveData<Int> = _currentStep

    fun setCurrentStep(step: Int) {
        _currentStep.value = step
    }

    fun nextStep() {
        _currentStep.value = _currentStep.value!! + 1
    }

    fun previousStep() {
        _currentStep.value = _currentStep.value!! - 1
    }

}