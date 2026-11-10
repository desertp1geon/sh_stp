
package com.example.sh_stp.data.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import com.example.sh_stp.data.Motorcycle;
import com.example.sh_stp.data.room.MotorcycleEntity;

public class MotorcycleMapperTest {

    @Test
    public void toDomain_shouldReturnNull_whenEntityIsNull() {
        Motorcycle result = MotorcycleMapper.toDomain(null);

        assertNull(result);
    }

    @Test
    public void toEntity_shouldReturnNull_whenMotorcycleIsNull() {
        MotorcycleEntity result = MotorcycleMapper.toEntity(null);

        assertNull(result);
    }

    @Test
    public void toDomainList_shouldReturnEmptyList_whenEntitiesAreNull() {
        List<Motorcycle> result = MotorcycleMapper.toDomainList(null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void toDomain_shouldMapAllFieldsCorrectly() {
        MotorcycleEntity entity = new MotorcycleEntity(
                1,
                "Honda",
                "CBR600RR",
                2020,
                0.6
        );

        Motorcycle result = MotorcycleMapper.toDomain(entity);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Honda", result.getBrand());
        assertEquals("CBR600RR", result.getModel());
        assertEquals(2020, result.getYear());
        assertEquals(0.6, result.getEngineVolume(), 0.001);
    }

    @Test
    public void toEntity_shouldMapAllFieldsCorrectly() {
        Motorcycle motorcycle = new Motorcycle(
                2,
                "Yamaha",
                "MT-07",
                2022,
                0.689
        );

        MotorcycleEntity result = MotorcycleMapper.toEntity(motorcycle);

        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals("Yamaha", result.getBrand());
        assertEquals("MT-07", result.getModel());
        assertEquals(2022, result.getYear());
        assertEquals(0.689, result.getEngineVolume(), 0.001);
    }

    @Test
    public void toDomainList_shouldMapAllEntitiesCorrectly() {
        List<MotorcycleEntity> entities = new ArrayList<>();

        entities.add(new MotorcycleEntity(
                1, "Honda", "CBR600RR", 2020, 0.6
        ));

        entities.add(new MotorcycleEntity(
                2, "Yamaha", "MT-07", 2022, 0.689
        ));

        List<Motorcycle> result = MotorcycleMapper.toDomainList(entities);

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("Honda", result.get(0).getBrand());
        assertEquals("CBR600RR", result.get(0).getModel());

        assertEquals("Yamaha", result.get(1).getBrand());
        assertEquals("MT-07", result.get(1).getModel());
    }

    @Test
    public void toDomainList_shouldSkipNullEntities() {
        List<MotorcycleEntity> entities = new ArrayList<>();

        entities.add(new MotorcycleEntity(
                1, "Honda", "CBR600RR", 2020, 0.6
        ));

        entities.add(null);

        List<Motorcycle> result = MotorcycleMapper.toDomainList(entities);

        assertEquals(1, result.size());
        assertEquals("Honda", result.get(0).getBrand());
    }
}
