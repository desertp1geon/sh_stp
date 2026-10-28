
package com.example.sh_stp.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sh_stp.model.MainActivityMotorcycleModel;

public class MainActivityViewModel extends ViewModel {

    private final MutableLiveData<MainActivityMotorcycleModel> viewState =
            new MutableLiveData<>();

    private final MainActivityMotorcycleModel uiModel =
            new MainActivityMotorcycleModel();

    public MainActivityViewModel() {
        viewState.setValue(uiModel);
    }

    public MutableLiveData<MainActivityMotorcycleModel> getViewState() {
        return viewState;
    }

    public MainActivityMotorcycleModel getUiModel() {
        return uiModel;
    }

    public void onNextButtonClicked(View v) {
        String brand = uiModel.getBrand().trim();
        String model = uiModel.getModel().trim();
        String year = uiModel.getYear().trim();
        String engineVolume = uiModel.getEngineVolume().trim();

        uiModel.setNavigateToDetails(false);
        uiModel.setShowError(false);

        if (brand.isEmpty()
                || model.isEmpty()
                || year.isEmpty()
                || engineVolume.isEmpty()) {

            uiModel.setShowError(true);

        } else {
            uiModel.setNavigateToDetails(true);
        }

        viewState.setValue(uiModel);
    }
}
