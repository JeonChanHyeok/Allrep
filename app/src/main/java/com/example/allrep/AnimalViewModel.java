package com.example.allrep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.allrep.userinfo.Animalinfo;
import com.example.allrep.userinfo.Feedinginfo;
import com.example.allrep.userinfo.Userinfo;

import java.util.ArrayList;
import java.util.List;

public class AnimalViewModel extends ViewModel {
    List<Animalinfo> animal_list = new ArrayList<Animalinfo>();
    private final MutableLiveData<List<Animalinfo>> animals = new MutableLiveData<List<Animalinfo>>();

    public void addAnimal(Animalinfo info){
        animal_list.add(info);
        animals.setValue(animal_list);
    }

    public LiveData<List<Animalinfo>> getAnimals(){
        return animals;
    }

    public List<Animalinfo> getAnimalList(){return animal_list;};

    public void resetAnimals(){
        animal_list.clear();
        animals.setValue(null);
    }
}
