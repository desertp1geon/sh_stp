
package com.example.sh_stp.activity1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.databinding.DataBindingUtil;

import com.example.sh_stp.R;
import com.example.sh_stp.databinding.ActivityMainBinding;
import com.example.sh_stp.model.MainActivityMotorcycleModel;
import com.example.sh_stp.model.Motorcycle;
import com.example.sh_stp.viewmodel.MainActivityViewModel;
import com.example.sh_stp.activity2.MotorcycleDetailsActivity;

import java.util.Calendar;

public class MainActivity extends Activity {

    public static final String EXTRA_MOTORCYCLE =
            "com.example.sh_stp.EXTRA_MOTORCYCLE";

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
        );

        viewModel = new ViewModelProvider(this)
                .get(MainActivityViewModel.class);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.getViewState().observe(this, this::handleViewState);
    }

    private void handleViewState(MainActivityMotorcycleModel state) {
        if (state == null) {
            return;
        }

        if (state.isShowError()) {
            state.setShowError(false);
            viewModel.getViewState().setValue(state);

            Toast.makeText(
                    this,
                    "Заповніть усі поля!",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (state.isNavigateToDetails()) {
            state.setNavigateToDetails(false);
            viewModel.getViewState().setValue(state);

            openDetailsScreen(state);
        }
    }

    private void openDetailsScreen(MainActivityMotorcycleModel state) {
        try {
            String brand = state.getBrand().trim();
            String model = state.getModel().trim();
            int year = Integer.parseInt(state.getYear().trim());

            String volumeText = state.getEngineVolume()
                    .trim()
                    .replace(',', '.');

            double engineVolume = Double.parseDouble(volumeText);

            int currentYear = Calendar.getInstance()
                    .get(Calendar.YEAR);

            if (year < 1885 || year > currentYear) {
                Toast.makeText(
                        this,
                        "Введіть коректний рік випуску!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            if (!Double.isFinite(engineVolume) || engineVolume <= 0) {
                Toast.makeText(
                        this,
                        "Об'єм двигуна повинен бути більшим за нуль!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            Motorcycle motorcycle = new Motorcycle(
                    brand,
                    model,
                    year,
                    engineVolume
            );

            Intent intent = new Intent(
                    MainActivity.this,
                    MotorcycleDetailsActivity.class
            );

            intent.putExtra(EXTRA_MOTORCYCLE, motorcycle);
            startActivity(intent);

        } catch (NumberFormatException e) {
            Toast.makeText(
                    this,
                    "Перевірте рік і об'єм двигуна!",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
