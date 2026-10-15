package com.baeldung.ljs;

import com.baeldung.ljs.domain.model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class JavaStreamsUnitTest {

    @Test
    void givenFunctionalInterfacesAsAnonymousClasses_whenExecutingThem_thenAllReturnCorrectResult() {
        Function<String, Integer> strLength = new Function<>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        };
        assertEquals(8, strLength.apply("Baeldung"));

        BiFunction<Integer, Integer, Double> divideToDouble = new BiFunction<>() {
            @Override
            public Double apply(Integer i1, Integer i2) {
                return i1.doubleValue() / i2.doubleValue();
            }
        };
        assertEquals(2.0, divideToDouble.apply(4, 2));

        Consumer<String> printString = new Consumer<>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        printString.accept("Hello Baeldung");

        Supplier<String> idGenerator = new Supplier<>() {
            @Override
            public String get() {
                return UUID.randomUUID().toString();
            }
        };
        assertTrue(idGenerator.get().length() > 0);

        Predicate<Integer> isEven = new Predicate<>() {
            @Override
            public boolean test(Integer i) {
                return i % 2 == 0;
            }
        };
        assertTrue(isEven.test(4));

        IntPredicate isValidInteger = new IntPredicate() {
            @Override
            public boolean test(int value) {
                return value > 0;
            }
        };
        assertTrue(isValidInteger.test(1));
    }

    @Test
    void givenFunctionalInterfacesAsLambdas_whenExecutingThem_thenAllReturnCorrectResult() {
        Supplier<String> idGenerator = () -> UUID.randomUUID()
                .toString();

        Function<String, Integer> strLength = s -> s.length();

        BiFunction<Integer, Integer, Double> divideToDouble = (s1, s2) -> s1.doubleValue() / s2.doubleValue();

        Consumer<String> printString = s -> System.out.println(s);

        Predicate<Integer> isEven = s -> s % 2 == 0;

        IntPredicate isValidPositiveInteger = s -> s > 0;

        // Supplier
        String newId = idGenerator.get();
        assertTrue(!newId.isEmpty());

        // Function
        Integer lengthOfString = strLength.apply("test");
        assertEquals(4, lengthOfString);

        // BiFunction
        Double result = divideToDouble.apply(5, 2);
        assertEquals(2.5, result);

        // Consumer
        printString.accept("Showing from consumer");

        // Predicate
        boolean is10Even = isEven.test(10);
        boolean is11Even = isEven.test(11);
        assertTrue(is10Even);
        assertFalse(is11Even);

        //IntPredicate
        assertTrue(isValidPositiveInteger.test(1));
        assertFalse(isValidPositiveInteger.test(-9));
    }

    @Test
    void givenTask_whenBuildingDueYearSummary_thenSummaryConstructed() {
        String summaryConnector = "is due on";

        // Uncommenting the next line would break the lambda below:
        // summaryConnector = "changed connector"; // Compile-time error: not effectively final

        Task t1 = new Task("T1", "Task Name", "Task Description", LocalDate.of(2050, 1, 1));

        Function<Task, String> dueYearSummary = task -> {
            final String name = task.getName();
            final int dueYear = task.getDueDate().getYear();
            return "%s %s %s".formatted(name, summaryConnector, dueYear);
        };

        String summaryValue = dueYearSummary.apply(t1);

        assertEquals("Task Name is due on 2050", summaryValue);
    }

    @Test
    void givenAList_whenUsingMethodReference_thenGetResult() {
        Function<Task, LocalDate> getDueDate = task -> task.getDueDate();
        Function<Task, LocalDate> getDueDateRef = Task::getDueDate;

        Task t1 = new Task("T2", "Task 2 Name", "Task 2 Description", LocalDate.of(2050, 1, 1));
        LocalDate dueDate = getDueDate.apply(t1);
        LocalDate dueDateRef = getDueDateRef.apply(t1);

        assertEquals(dueDate, dueDateRef);
    }

    private final List<Task> tasks = List.of(
            new Task("T1", "Task 1", "Task 1", LocalDate.now()),
            new Task("T2", "Task 2", "Task 2", LocalDate.now()),
            new Task("T3", "Task 3", "Task 3", LocalDate.now()),
            new Task("S1", "Task 4", "Task 4", LocalDate.now())
    );

    @Test
    void givenListOfTasks_whenJoiningCodesUsingStream_thenCorrectStringIsReturned() {
        String combinedCodes = joinCodesWithStream(tasks);

        assertEquals("T1, T2, T3", combinedCodes);
    }

    String joinCodesWithStream(List<Task> tasks) {
        return tasks.stream()
                .map(Task::getCode)
                .filter(code -> code.startsWith("T"))
                .collect(Collectors.joining(", "));
    }

}