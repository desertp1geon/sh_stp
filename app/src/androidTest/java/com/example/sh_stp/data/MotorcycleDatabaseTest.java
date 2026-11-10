
package com.example.sh_stp.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.content.Context;

import com.example.sh_stp.basic.concurency.Fault;
import com.example.sh_stp.basic.concurency.TaskCallback;
import com.example.sh_stp.data.room.AppDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@RunWith(AndroidJUnit4.class)
public class MotorcycleDatabaseTest {

    private AppDatabase appDatabase;
    private MotorcycleDatabase repository;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();

        appDatabase = Room.inMemoryDatabaseBuilder(
                        context,
                        AppDatabase.class
                )
                .allowMainThreadQueries()
                .build();

        repository = new MotorcycleDatabase(appDatabase);
    }

    @After
    public void tearDown() {
        if (appDatabase != null) {
            appDatabase.close();
        }
    }

    @Test
    public void repository_shouldInitializeCorrectly() {
        assertNotNull(appDatabase);
        assertNotNull(repository);
    }

    @Test
    public void insert_shouldSaveMotorcycleSuccessfully() throws Exception {
        Motorcycle motorcycle = mock(Motorcycle.class);

        when(motorcycle.getId()).thenReturn(1);
        when(motorcycle.getBrand()).thenReturn("Honda");
        when(motorcycle.getModel()).thenReturn("CBR600RR");
        when(motorcycle.getYear()).thenReturn(2020);
        when(motorcycle.getEngineVolume()).thenReturn(0.6);

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Boolean> result = new AtomicReference<>();
        AtomicReference<Fault> error = new AtomicReference<>();

        repository.insert(motorcycle, new TaskCallback<Boolean>() {
            @Override
            public void onComplete(Boolean value) {
                result.set(value);
                latch.countDown();
            }

            @Override
            public void onError(Fault fault) {
                error.set(fault);
                latch.countDown();
            }
        });

        assertTrue("Callback was not invoked",
                latch.await(5, TimeUnit.SECONDS));

        assertTrue(Boolean.TRUE.equals(result.get()));
        assertEquals(null, error.get());
    }

    @Test
    public void getAll_shouldReturnSavedMotorcycles() throws Exception {
        insertAndWait(new Motorcycle(
                1,
                "Yamaha",
                "MT-07",
                2022,
                0.689
        ));

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<List<Motorcycle>> result = new AtomicReference<>();
        AtomicReference<Fault> error = new AtomicReference<>();

        repository.getAll(new TaskCallback<List<Motorcycle>>() {
            @Override
            public void onComplete(List<Motorcycle> motorcycles) {
                result.set(motorcycles);
                latch.countDown();
            }

            @Override
            public void onError(Fault fault) {
                error.set(fault);
                latch.countDown();
            }
        });

        assertTrue("Callback was not invoked",
                latch.await(5, TimeUnit.SECONDS));

        assertNotNull(result.get());
        assertEquals(null, error.get());
        assertEquals(1, result.get().size());

        Motorcycle motorcycle = result.get().get(0);

        assertEquals("Yamaha", motorcycle.getBrand());
        assertEquals("MT-07", motorcycle.getModel());
        assertEquals(2022, motorcycle.getYear());
        assertEquals(0.689, motorcycle.getEngineVolume(), 0.001);
    }

    @Test
    public void insert_shouldReturnError_whenMotorcycleIsNull()
            throws Exception {

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Boolean> result = new AtomicReference<>();
        AtomicReference<Fault> error = new AtomicReference<>();

        repository.insert(null, new TaskCallback<Boolean>() {
            @Override
            public void onComplete(Boolean value) {
                result.set(value);
                latch.countDown();
            }

            @Override
            public void onError(Fault fault) {
                error.set(fault);
                latch.countDown();
            }
        });

        assertTrue("Callback was not invoked",
                latch.await(5, TimeUnit.SECONDS));

        assertEquals(null, result.get());
        assertNotNull(error.get());
        assertNotNull(error.get().getCause());
        assertTrue(
                error.get().getCause() instanceof IllegalArgumentException
        );
    }

    private void insertAndWait(Motorcycle motorcycle) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Fault> error = new AtomicReference<>();

        repository.insert(motorcycle, new TaskCallback<Boolean>() {
            @Override
            public void onComplete(Boolean result) {
                latch.countDown();
            }

            @Override
            public void onError(Fault fault) {
                error.set(fault);
                latch.countDown();
            }
        });

        assertTrue("Insert callback was not invoked",
                latch.await(5, TimeUnit.SECONDS));

        assertEquals(null, error.get());
    }
}
