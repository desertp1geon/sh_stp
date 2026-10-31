
package com.example.sh_stp.data;

import java.util.List;

import com.example.sh_stp.basic.concurency.TaskCallback;

public interface MotorcycleRepository {

    void getAll(
            TaskCallback<List<Motorcycle>> callback
    );

    void insert(
            Motorcycle motorcycle,
            TaskCallback<Long> callback
    );
}
