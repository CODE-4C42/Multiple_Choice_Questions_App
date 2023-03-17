package com.example.qcm_berrarat.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qcm_berrarat.Model.QuestionModel;
import com.example.qcm_berrarat.Repository.QuestionRepository;


public class QuestionVm extends ViewModel {
    QuestionRepository qRepo;
    public MutableLiveData<QuestionModel> returnData(int num) {
        qRepo = new QuestionRepository();
        MutableLiveData<QuestionModel> mld = new MutableLiveData<>();
        return qRepo.getQuestion(num);
    }




}
