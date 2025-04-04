package com.example.uni.viewModel;


import android.app.*;

import androidx.annotation.NonNull;
import androidx.lifecycle.*;

import com.example.uni.entities.owner;

public class ownerVModel extends AndroidViewModel {
    private MutableLiveData<owner> ownerdata;
    public ownerVModel(@NonNull Application application) {
        super(application);
        ownerdata = new MutableLiveData<>();
    }

    public LiveData<owner> getuserdata() {
        return ownerdata;
    }

    public void setuserdata(owner ownerdata) {
        this.ownerdata.setValue(ownerdata);
    }
}
