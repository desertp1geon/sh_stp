package com.example.sh_stp.activity1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;

import com.example.sh_stp.R;
import com.example.sh_stp.basic.mvvm.BaseActivity;
import com.example.sh_stp.data.Motorcycle;
import com.example.sh_stp.data.MotorcycleRepository;
import com.example.sh_stp.viewmodel.MainActivityViewModel;

import org.koin.java.KoinJavaComponent;

public class MainActivity extends BaseActivity {
    public static final String EXTRA_MOTORCYCLE =
            "com.example.sh_stp.EXTRA_MOTORCYCLE";
    private RecyclerView recyclerView;
    private MotorcycleRecyclerViewAdapter adapter;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewMotorcycles);
        Button buttonAdd = findViewById(R.id.buttonAddMotorcycle);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends ViewModel> T create(
                    @NonNull Class<T> modelClass) {

                if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
                    MotorcycleRepository repository =
                            KoinJavaComponent.get(MotorcycleRepository.class);

                    return (T) new MainActivityViewModel(repository);
                }

                throw new IllegalArgumentException("Unknown ViewModel");
            }
        };

        viewModel = new ViewModelProvider(this, factory)
                .get(MainActivityViewModel.class);

        adapter = viewModel.getAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getMotorcycles().observe(this, motorcycles -> {
            adapter.setMotorcycles(motorcycles);
        });

        buttonAdd.setOnClickListener(v -> showAddMotorcycleDialog());
    }

    private void showAddMotorcycleDialog() {
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_add_motorcycle, null);

        EditText brandInput = dialogView.findViewById(R.id.editTextBrand);
        EditText modelInput = dialogView.findViewById(R.id.editTextModel);
        EditText yearInput = dialogView.findViewById(R.id.editTextYear);
        EditText volumeInput = dialogView.findViewById(R.id.editTextEngineVolume);

        new AlertDialog.Builder(this)
                .setTitle("Додавання мотоцикла")
                .setView(dialogView)
                .setNegativeButton("Скасувати", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Зберегти", (dialog, which) -> {
                    String brand = brandInput.getText().toString().trim();
                    String model = modelInput.getText().toString().trim();
                    String yearText = yearInput.getText().toString().trim();
                    String volumeText = volumeInput.getText().toString().trim();

                    if (brand.isEmpty() || model.isEmpty()
                            || yearText.isEmpty() || volumeText.isEmpty()) {
                        Toast.makeText(this,
                                "Заповніть усі поля",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        int year = Integer.parseInt(yearText);
                        double volume = Double.parseDouble(
                                volumeText.replace(',', '.')
                        );

                        if (year < 1885 || year > 2100 || volume <= 0) {
                            Toast.makeText(this,
                                    "Перевірте рік та об'єм двигуна",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Motorcycle motorcycle = new Motorcycle(
                                brand, model, year, volume
                        );

                        viewModel.addMotorcycle(motorcycle);

                        Toast.makeText(this,
                                "Мотоцикл додано",
                                Toast.LENGTH_SHORT).show();

                    } catch (NumberFormatException e) {
                        Toast.makeText(this,
                                "Некоректні числові значення",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
}