package com.example.sh_stp.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.sh_stp.activity1.MotorcycleRecyclerViewAdapter;
import com.example.sh_stp.basic.concurency.TaskCallback;
import com.example.sh_stp.data.Motorcycle;
import com.example.sh_stp.data.MotorcycleRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();

    private MotorcycleRepository repository;
    private MainActivityViewModel viewModel;

    @Before
    public void setUp() {
        repository = Mockito.mock(MotorcycleRepository.class);

        viewModel = new MainActivityViewModel(repository);
    }

    @Test
    public void viewModel_shouldInitializeCorrectly() {
        assertNotNull(viewModel);
        assertNotNull(viewModel.getMotorcycles());
        assertNotNull(viewModel.getIsLoading());
        assertNotNull(viewModel.getAdapter());

        verify(repository, times(1)).getAll(any());
    }

    @Test
    public void motorcycles_shouldInitiallyContainEmptyList() {
        assertNotNull(viewModel.getMotorcycles().getValue());
        assertTrue(viewModel.getMotorcycles().getValue().isEmpty());
    }

    @Test
    public void isLoading_shouldBeTrueWhileLoading() {
        assertEquals(Boolean.TRUE, viewModel.getIsLoading().getValue());
    }

    @Test
    public void loadMotorcycles_shouldUpdateLiveData() {
        List<Motorcycle> expectedMotorcycles = Arrays.asList(
                new Motorcycle(1, "Honda", "CBR600RR", 2020, 599),
                new Motorcycle(2, "Yamaha", "R6", 2021, 600)
        );

        ArgumentCaptor<TaskCallback<List<Motorcycle>>> captor =
                ArgumentCaptor.forClass(TaskCallback.class);

        verify(repository).getAll(captor.capture());

        captor.getValue().onComplete(expectedMotorcycles);

        assertEquals(
                expectedMotorcycles,
                viewModel.getMotorcycles().getValue()
        );

        assertEquals(Boolean.FALSE, viewModel.getIsLoading().getValue());
    }

    @Test
    public void loadMotorcycles_shouldClearListOnError() {
        ArgumentCaptor<TaskCallback<List<Motorcycle>>> captor =
                ArgumentCaptor.forClass(TaskCallback.class);

        verify(repository).getAll(captor.capture());

        captor.getValue().onError(
                new com.example.sh_stp.basic.concurency.Fault(
                        "Помилка завантаження"
                )
        );

        assertNotNull(viewModel.getMotorcycles().getValue());
        assertTrue(viewModel.getMotorcycles().getValue().isEmpty());
        assertEquals(Boolean.FALSE, viewModel.getIsLoading().getValue());
    }

    @Test
    public void adapter_shouldInitializeCorrectly() {
        MotorcycleRecyclerViewAdapter adapter = viewModel.getAdapter();

        assertNotNull(adapter);
        assertEquals(0, adapter.getItemCount());
    }

    @Test
    public void loadMotorcycles_shouldCallRepositoryAgain() {
        viewModel.loadMotorcycles();

        verify(repository, times(2)).getAll(any());
    }

    @Test
    public void addMotorcycle_shouldReloadListAfterSuccessfulInsert() {
        Motorcycle motorcycle = new Motorcycle(
                3,
                "Kawasaki",
                "Ninja 650",
                2022,
                649
        );

        ArgumentCaptor<TaskCallback<Boolean>> insertCaptor =
                ArgumentCaptor.forClass(TaskCallback.class);

        viewModel.addMotorcycle(motorcycle);

        verify(repository).insert(eq(motorcycle), insertCaptor.capture());

        insertCaptor.getValue().onComplete(true);

        verify(repository, times(2)).getAll(any());
    }
}