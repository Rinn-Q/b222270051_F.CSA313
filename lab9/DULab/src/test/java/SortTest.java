import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SortTest {

    @Test
    void testSortedArray() {
        Sort sort = new Sort();
        int[] arr = {1, 2, 3, 4, 5};
        sort.mergeSort(arr, 0, arr.length - 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testReverseArray() {
        Sort sort = new Sort();
        int[] arr = {9, 7, 5, 3, 1};
        sort.mergeSort(arr, 0, arr.length - 1);
        assertArrayEquals(new int[]{1, 3, 5, 7, 9}, arr);
    }

    @Test
    void testEmptyArray() {
        Sort sort = new Sort();
        int[] arr = {};
        sort.mergeSort(arr, 0, arr.length - 1);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void testSingleElement() {
        Sort sort = new Sort();
        int[] arr = {42};
        sort.mergeSort(arr, 0, arr.length - 1);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void testDuplicateValues() {
        Sort sort = new Sort();
        int[] arr = {4, 2, 4, 2, 4};
        sort.mergeSort(arr, 0, arr.length - 1);
        assertArrayEquals(new int[]{2, 2, 4, 4, 4}, arr);
    }
}
