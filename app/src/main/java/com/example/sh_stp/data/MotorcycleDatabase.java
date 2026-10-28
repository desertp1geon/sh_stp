
package com.example.sh_stp.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import com.example.sh_stp.concurency.Fault;
import com.example.sh_stp.concurency.TaskCallback;
import com.example.sh_stp.data.mappers.MotorcycleMapper;
import com.example.sh_stp.data.room.AppDatabase;
import com.example.sh_stp.data.room.MotorcycleDAO;
import com.example.sh_stp.data.room.MotorcycleEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MotorcycleDatabase implements MotorcycleRepository {

    private static volatile MotorcycleDatabase instance;

    private final AppDatabase appDatabase;
    private final MotorcycleDAO motorcycleDAO;
    private final ExecutorService executorService;
    private final Handler mainHandler;

    private MotorcycleDatabase(Context context) {
        Context applicationContext = context.getApplicationContext();

        appDatabase = Room.databaseBuilder(
                applicationContext,
                AppDatabase.class,
                "motorcycles_database"
        ).build();

        motorcycleDAO = appDatabase.motorcycleDAO();

        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public static MotorcycleDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (MotorcycleDatabase.class) {
                if (instance == null) {
                    instance = new MotorcycleDatabase(context);
                }
            }
        }

        return instance;
    }

    @Override
    public void getAll(
            TaskCallback<List<Motorcycle>> callback
    ) {
        executorService.execute(() -> {
            try {
                List<MotorcycleEntity> entities =
                        motorcycleDAO.getAll();

                List<Motorcycle> motorcycles =
                        MotorcycleMapper.toDomainList(entities);

                mainHandler.post(
                        () -> callback.onComplete(motorcycles)
                );

            } catch (Exception e) {
                Fault fault = new Fault(
                        "Не вдалося отримати список мотоциклів",
                        e
                );

                mainHandler.post(
                        () -> callback.onError(fault)
                );
            }
        });
    }

    @Override
    public void insert(
            Motorcycle motorcycle,
            TaskCallback<Long> callback
    ) {
        executorService.execute(() -> {
            try {
                MotorcycleEntity entity =
                        MotorcycleMapper.toEntity(motorcycle);

                if (entity == null) {
                    throw new IllegalArgumentException(
                            "Мотоцикл не може бути null"
                    );
                }

                long insertedId = motorcycleDAO.insert(entity);

                mainHandler.post(
                        () -> callback.onComplete(insertedId)
                );

            } catch (Exception e) {
                Fault fault = new Fault(
                        "Не вдалося зберегти мотоцикл",
                        e
                );

                mainHandler.post(
                        () -> callback.onError(fault)
                );
            }
        });
    }
}
