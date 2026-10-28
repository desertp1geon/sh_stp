
package com.example.sh_stp.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {MotorcycleEntity.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MotorcycleDAO motorcycleDAO();
}
