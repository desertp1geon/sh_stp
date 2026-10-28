
package com.example.sh_stp.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MotorcycleDAO {

    @Query("SELECT * FROM motorcycles ORDER BY id ASC")
    List<MotorcycleEntity> getAll();

    @Insert
    long insert(MotorcycleEntity motorcycle);
}
