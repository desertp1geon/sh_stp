package com.example.sh_stp;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sh_stp.activity1.MainActivity;
import com.example.sh_stp.model.Motorcycle;

public class MotorcycleDetailsActivity extends AppCompatActivity {

    private TextView textViewBrand;
    private TextView textViewModel;
    private TextView textViewYear;
    private TextView textViewEngineVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motorcycle_details);

        textViewBrand = findViewById(R.id.textViewBrand);
        textViewModel = findViewById(R.id.textViewModel);
        textViewYear = findViewById(R.id.textViewYear);
        textViewEngineVolume =
                findViewById(R.id.textViewEngineVolume);

        Motorcycle motorcycle;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            motorcycle = getIntent().getSerializableExtra(
                    MainActivity.EXTRA_MOTORCYCLE,
                    Motorcycle.class
            );

        } else {

            Object extra = getIntent().getSerializableExtra(
                    MainActivity.EXTRA_MOTORCYCLE
            );

            if (extra instanceof Motorcycle) {
                motorcycle = (Motorcycle) extra;
            } else {
                motorcycle = null;
            }
        }

        if (motorcycle == null) {

            Toast.makeText(
                    this,
                    "Не вдалося отримати дані про мотоцикл",
                    Toast.LENGTH_LONG
            ).show();

            finish();
            return;
        }

        textViewBrand.setText(
                "Марка: " + motorcycle.getBrand()
        );

        textViewModel.setText(
                "Модель: " + motorcycle.getModel()
        );

        textViewYear.setText(
                "Рік випуску: " + motorcycle.getYear()
        );

        textViewEngineVolume.setText(
                "Об'єм двигуна: "
                        + motorcycle.getEngineVolume()
                        + " см³"
        );
    }
}