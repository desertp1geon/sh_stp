
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
import java.lang.Boolean;
public class MainActivityViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading;
    private final MutableLiveData<List<Motorcycle>> motorcycles;
    private final MotorcycleRecyclerViewAdapter adapter;
    private final MotorcycleRepository repository;

    public MainActivityViewModel(
            @NonNull MotorcycleRepository repository
    ) {
        isLoading = new MutableLiveData<>(false);
        this.repository = repository;

        motorcycles = new MutableLiveData<>(new ArrayList<>());
        adapter = new MotorcycleRecyclerViewAdapter();

        loadMotorcycles();
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<List<Motorcycle>> getMotorcycles() {
        return motorcycles;
    }

    public MotorcycleRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void loadMotorcycles() {
        isLoading.setValue(true);

        repository.getAll(new TaskCallback<List<Motorcycle>>() {
            @Override
            public void onComplete(List<Motorcycle> result) {
                motorcycles.postValue(result);
                isLoading.postValue(false);
            }

            @Override
            public void onError(Fault fault) {
                motorcycles.postValue(new ArrayList<>());
                isLoading.postValue(false);
            }
        });
    }

    public void addMotorcycle(Motorcycle motorcycle) {
        repository.insert(motorcycle, new TaskCallback<Boolean>() {

            @Override
            public void onComplete(Boolean result) {
                if (Boolean.TRUE.equals(result)) {
                    loadMotorcycles();
                }
            }

            @Override
            public void onError(Fault fault) {
                // тут обробляємо помилку, можна додати наприклад логування, але завданням не визначено дію в такому випадку
            }
        });
    }
}
