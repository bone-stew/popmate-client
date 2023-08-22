package com.example.popmate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmate.model.data.local.TestModel

class TestViewModel: ViewModel() {

    val visitorCnt: MutableLiveData<String> = MutableLiveData("0")
    private var testModel: TestModel = TestModel()

    fun delCount() {
        testModel.del()
        visitorCnt.value = testModel.quantity.toString()
    }

    fun addCount() {
        testModel.add()
        visitorCnt.value = testModel.quantity.toString()
    }


}
