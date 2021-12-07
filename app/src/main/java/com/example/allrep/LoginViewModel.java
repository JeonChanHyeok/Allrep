package com.example.allrep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.allrep.userinfo.Userinfo;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<Userinfo> Login_id = new MutableLiveData<Userinfo>();

    public void logined(Userinfo user){
        Login_id.setValue(user);
    }
    public LiveData<Userinfo> getLoginedId(){
        return Login_id;
    }
}
