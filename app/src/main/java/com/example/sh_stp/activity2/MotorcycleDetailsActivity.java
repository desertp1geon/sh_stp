
package com.example.sh_stp.activity2;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.sh_stp.R;
import com.example.sh_stp.activity1.MainActivity;
import com.example.sh_stp.data.Motorcycle;
import com.example.sh_stp.databinding.ActivityMotorcycleDetailsBinding;
import com.example.sh_stp.viewmodel.MotorcycleDetailsActivityViewModel;

public class MotorcycleDetailsActivity extends AppCompatActivity {

    private ActivityMotorcycleDetailsBinding binding;
    private MotorcycleDetailsActivityViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_motorcycle_details
        );

        viewModel = new ViewModelProvider(this)
                .get(MotorcycleDetailsActivityViewModel.class);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        Motorcycle motorcycle = getMotorcycleFromIntent();

        if (motorcycle == null) {
            Toast.makeText(
                    this,
                    "Не вдалося отримати дані про мотоцикл",
                    Toast.LENGTH_LONG
            ).show();

            finish();
            return;
        }

        viewModel.setMotorcycle(motorcycle);

        viewModel.getViewState().observe(this, state ->
                binding.invalidateAll()
        );
    }

    private Motorcycle getMotorcycleFromIntent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return getIntent().getSerializableExtra(
                    MainActivity.EXTRA_MOTORCYCLE,
                    Motorcycle.class
            );
        }

        Object extra = getIntent().getSerializableExtra(
                MainActivity.EXTRA_MOTORCYCLE
        );

        if (extra instanceof Motorcycle) {
            return (Motorcycle) extra;
        }

        return null;
    }
}
