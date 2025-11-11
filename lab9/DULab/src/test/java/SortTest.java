import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class SortTest {

    @Test
    @DisplayName("TC1 – Хоосон массив")
    void testEmptyArray() {
        Sort sort = new Sort();
        int[] arr = {};
        sort.mergeSort(arr, 0, arr.length - 1);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    @DisplayName("TC2 – Нэг элементтэй массив")
    void testSingleElementArray() {
        Sort sort = new Sort();
        int[] arr = {5};
        sort.mergeSort(arr, 0, arr.length - 1);
        assertArrayEquals(new int[]{5}, arr);
    }

    @Test
    @DisplayName("TC3 – Хоёр элементтэй массив")
    void testTwoElementsArray() {
        Sort sort = new Sort();
        int[] arr = {5, 2};
        sort.mergeSort(arr, 0, arr.length - 1);
        assertArrayEquals(new int[]{2, 5}, arr);
    }

    @Test
    @DisplayName("TC4 – Олон элементтэй массив")
    void testMultipleElementsArray() {
        Sort sort = new Sort();
        int[] arr = {9, 3, 1, 5, 13, 12};
        sort.mergeSort(arr, 0, arr.length - 1);
        assertArrayEquals(new int[]{1, 3, 5, 9, 12, 13}, arr);
    }
}
