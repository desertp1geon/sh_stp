
package com.example.sh_stp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sh_stp.activity1.MotorcycleRecyclerViewAdapter;
import com.example.sh_stp.basic.concurency.Fault;
import com.example.sh_stp.basic.concurency.TaskCallback;
import com.example.sh_stp.data.Motorcycle;
import com.example.sh_stp.data.MotorcycleRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private final MutableLiveData<List<Motorcycle>> motorcycles;
    private final MotorcycleRecyclerViewAdapter adapter;
    private final MotorcycleRepository repository;

    public MainActivityViewModel(
            @NonNull MotorcycleRepository repository
    ) {
        this.repository = repository;

        motorcycles = new MutableLiveData<>(new ArrayList<>());
        adapter = new MotorcycleRecyclerViewAdapter();

        loadMotorcycles();
    }

    public MutableLiveData<List<Motorcycle>> getMotorcycles() {
        return motorcycles;
    }

    public MotorcycleRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void loadMotorcycles() {
        repository.getAll(new TaskCallback<List<Motorcycle>>() {
            @Override
            public void onComplete(List<Motorcycle> result) {
                motorcycles.postValue(result);
            }

            @Override
            public void onError(Fault fault) {
                motorcycles.postValue(new ArrayList<>());
            }
        });
    }

    public void addMotorcycle(Motorcycle motorcycle) {
        repository.insert(motorcycle, new TaskCallback<Long>() {
            @Override
            public void onComplete(Long insertedId) {
                loadMotorcycles();
            }

            @Override
            public void onError(Fault fault) {
                // За потреби помилку можна передати через окремий LiveData.
            }
        });
    }
}
