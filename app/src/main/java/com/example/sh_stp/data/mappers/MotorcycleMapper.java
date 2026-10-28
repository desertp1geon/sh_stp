
package com.example.sh_stp.data.mappers;

import com.example.sh_stp.data.Motorcycle;
import com.example.sh_stp.data.room.MotorcycleEntity;

import java.util.ArrayList;
import java.util.List;

public final class MotorcycleMapper {

    private MotorcycleMapper() {
    }

    // DTO (Room Entity) -> DomainModel
    public static Motorcycle toDomain(MotorcycleEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Motorcycle(
                entity.getId(),
                entity.getBrand(),
                entity.getModel(),
                entity.getYear(),
                entity.getEngineVolume()
        );
    }

    // DomainModel -> DTO (Room Entity)
    public static MotorcycleEntity toEntity(Motorcycle motorcycle) {
        if (motorcycle == null) {
            return null;
        }

        return new MotorcycleEntity(
                motorcycle.getId(),
                motorcycle.getBrand(),
                motorcycle.getModel(),
                motorcycle.getYear(),
                motorcycle.getEngineVolume()
        );
    }

    // List<DTO> -> List<DomainModel>
    public static List<Motorcycle> toDomainList(
            List<MotorcycleEntity> entities
    ) {
        List<Motorcycle> motorcycles = new ArrayList<>();

        if (entities == null) {
            return motorcycles;
        }

        for (MotorcycleEntity entity : entities) {
            Motorcycle motorcycle = toDomain(entity);

            if (motorcycle != null) {
                motorcycles.add(motorcycle);
            }
        }

        return motorcycles;
    }
}
