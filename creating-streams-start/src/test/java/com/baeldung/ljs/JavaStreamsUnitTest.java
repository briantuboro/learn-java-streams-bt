package com.baeldung.ljs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.baeldung.ljs.domain.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaStreamsUnitTest {

    private final Task task1 = new Task("T1", "John's house construction", "Construction of John's house in LA", LocalDate.of(2024, 1, 1));
    private final Task task2 = new Task("T2", "Thomas High School reparation", "Reparation of Thomas High School in London", LocalDate.of(2024, 8, 20));
    private final Task task3 = new Task("T3", "Flower Cafe construction", "Construction of Flower Cafe in Bucharest", LocalDate.of(2025, 6, 30));
    private final Task task4 = new Task("T4", "Lily's house construction", "Construction of Lily's house in NY", LocalDate.of(2028, 11, 15));
    private final Task task5 = new Task("T5", "Bee Steak House restoration", "Restoration of Bee Steak House in Constanta", LocalDate.of(2032, 9, 25));
    private final Task task6 = new Task("T6", "West Outer Ring street construction", "Construction of West Outer Ring street in Hamburg",
        LocalDate.of(2035, 5, 18));
    private final Task task7 = new Task("T7", "Green river bridge restoration", "Restoration of Green river bridge in Dublin", LocalDate.of(2029, 2, 22));
    private final Task task8 = new Task("T8", "Jane's Jacket factory reparation", "Reparation of Jane's Jacket factory", LocalDate.of(2028, 6, 10));
    private final Task task9 = new Task("T9", "Museum restoration", "Restoration of the historical museum in Paris", LocalDate.of(2027, 11, 5));

    private final Collection<Task> tasks = List.of(task1, task2, task3, task4, task5, task6, task7, task8, task9);

    private final List<Task> listOfTask = List.of(
            new Task("T1", "Task 1", "Task 1", LocalDate.now()),
            new Task("T2", "Task 2", "Task 2", LocalDate.now()),
            new Task("T3", "Task 3", "Task 3", LocalDate.now()),
            new Task("S1", "Task 4", "Task 4", LocalDate.now())
    );

    @Test
    void givenListOfTasks_whenJoiningCodesUsingImperative_thenCorrectStringIsReturned() {
        String combinedCodes = joinCodesImperatively(listOfTask);
        assertEquals("T1, T2, T3", combinedCodes);
    }

    String joinCodesImperatively(List<Task> tasks) {
        List<String> codes = new ArrayList<>();
        tasks.forEach(t -> {
            if (t.getCode().startsWith("T")) {
                codes.add(t.getCode());
            }
        });
        return String.join(", ", codes);
    }

    @Test
    void givenListOfTasks_whenJoiningCodesUsingStream_thenGetTheSameAsIterativeApproach() {
        assertEquals(joinCodesWithStream(listOfTask), joinCodesImperatively(listOfTask));
    }

    @Test
    void givenListOfTasks_whenJoiningCodesUsingStream_thenCorrectStringIsReturned() {
        String combinedCodes = joinCodesWithStream(listOfTask);
        assertEquals("T1, T2, T3", combinedCodes);
    }

    String joinCodesWithStream(List<Task> tasks) {
        return tasks.stream()
                .map(Task::getCode)
                .filter(code -> code.startsWith("T"))
                .collect(Collectors.joining(", "));
    }
}
