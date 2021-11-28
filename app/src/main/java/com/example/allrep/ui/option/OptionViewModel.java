package com.example.allrep.ui.option;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OptionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OptionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("옵션");
    }

    public LiveData<String> getText() {
        return mText;
    }
}