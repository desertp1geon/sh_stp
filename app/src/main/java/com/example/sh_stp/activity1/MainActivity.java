
package com.example.sh_stp.activity1;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sh_stp.R;
import com.example.sh_stp.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
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

        // Створюємо вертикальний список
        recyclerView.setLayoutManager(
                new LinearLayoutManager(MainActivity.this)
        );

        // Ініціалізуємо ViewModel
        viewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getApplication())
        ).get(MainActivityViewModel.class);

        // Підключаємо адаптер
        adapter = viewModel.getAdapter();
        recyclerView.setAdapter(adapter);

        // Слухаємо зміни списку мотоциклів
        viewModel.getMotorcycles().observe(this, motorcycles -> {
            adapter.setMotorcycles(motorcycles);

            if (motorcycles == null || motorcycles.isEmpty()) {
                Toast.makeText(
                        MainActivity.this,
                        "Список мотоциклів порожній",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
