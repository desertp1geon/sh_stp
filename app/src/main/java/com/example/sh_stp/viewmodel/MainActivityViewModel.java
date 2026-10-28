
package com.example.sh_stp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sh_stp.activity1.MotorcycleRecyclerViewAdapter;
import com.example.sh_stp.concurency.Fault;
import com.example.sh_stp.concurency.TaskCallback;
import com.example.sh_stp.data.Motorcycle;
import com.example.sh_stp.data.MotorcycleDatabase;
import com.example.sh_stp.data.MotorcycleRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Motorcycle>> motorcycles;
    private MotorcycleRecyclerViewAdapter adapter;
    private MotorcycleRepository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        motorcycles = new MutableLiveData<>(new ArrayList<>());
        adapter = new MotorcycleRecyclerViewAdapter();
        repository = MotorcycleDatabase.getInstance(application);

        loadMotorcycles();
    }

    public MutableLiveData<List<Motorcycle>> getMotorcycles() {
        return motorcycles;
    }

    public MotorcycleRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(MotorcycleRecyclerViewAdapter adapter) {
        if (adapter != null) {
            this.adapter = adapter;
        }
    }

    public MotorcycleRepository getRepository() {
        return repository;
    }

    public void setRepository(MotorcycleRepository repository) {
        if (repository != null) {
            this.repository = repository;
        }
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
                // Помилку збереження можна обробити в окремому LiveData.
            }
        });
    }
}
