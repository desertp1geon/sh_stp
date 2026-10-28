
package com.example.sh_stp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sh_stp.data.Motorcycle;
import com.example.sh_stp.model.MotorcycleDetailsActivityMotorcycleModel;

public class MotorcycleDetailsActivityViewModel extends ViewModel {

    private final MutableLiveData<MotorcycleDetailsActivityMotorcycleModel>
            viewState = new MutableLiveData<>();

    private final MotorcycleDetailsActivityMotorcycleModel uiModel =
            new MotorcycleDetailsActivityMotorcycleModel();

    public MotorcycleDetailsActivityViewModel() {
        viewState.setValue(uiModel);
    }

    public MutableLiveData<MotorcycleDetailsActivityMotorcycleModel>
    getViewState() {
        return viewState;
    }

    public MotorcycleDetailsActivityMotorcycleModel getUiModel() {
        return uiModel;
    }

    public void setMotorcycle(Motorcycle motorcycle) {
        if (motorcycle == null) {
            return;
        }

        uiModel.fromMotorcycle(motorcycle);
        viewState.setValue(uiModel);
    }
}
