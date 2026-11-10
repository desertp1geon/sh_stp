
package com.example.sh_stp.data;

import android.os.Handler;
import android.os.Looper;

import com.example.sh_stp.basic.concurency.Fault;
import com.example.sh_stp.basic.concurency.TaskCallback;
import com.example.sh_stp.data.mappers.MotorcycleMapper;
import com.example.sh_stp.data.room.AppDatabase;
import com.example.sh_stp.data.room.MotorcycleDAO;
import com.example.sh_stp.data.room.MotorcycleEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MotorcycleDatabase implements MotorcycleRepository {

    private final AppDatabase appDatabase;
    private final MotorcycleDAO motorcycleDAO;
    private final ExecutorService executorService;
    private final Handler mainHandler;

    public MotorcycleDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        this.motorcycleDAO = appDatabase.motorcycleDAO();

        this.executorService = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void getAll(TaskCallback<List<Motorcycle>> callback) {
        executorService.execute(() -> {
            try {
                List<MotorcycleEntity> entities = motorcycleDAO.getAll();

                List<Motorcycle> motorcycles =
                        MotorcycleMapper.toDomainList(entities);

                mainHandler.post(() -> callback.onComplete(motorcycles));

            } catch (Exception e) {
                Fault fault = new Fault(
                        "Не вдалося отримати список мотоциклів",
                        e
                );

                mainHandler.post(() -> callback.onError(fault));
            }
        });
    }

    @Override
    public void insert(Motorcycle motorcycle, TaskCallback<Boolean> callback) {
        executorService.execute(() -> {
            try {
                if (motorcycle == null) {
                    throw new IllegalArgumentException(
                            "Мотоцикл не може бути null"
                    );
                }

                MotorcycleEntity entity = MotorcycleMapper.toEntity(motorcycle);

                if (entity == null) {
                    throw new IllegalArgumentException(
                            "Не вдалося перетворити мотоцикл"
                    );
                }

                motorcycleDAO.insert(entity);

                mainHandler.post(() -> callback.onComplete(true));

            } catch (Exception e) {
                Fault fault = new Fault(
                        "Не вдалося зберегти мотоцикл",
                        e
                );

                mainHandler.post(() -> callback.onError(fault));
            }
        });
    }
}
