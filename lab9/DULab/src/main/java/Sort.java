public class Sort {
    public void merge(int arr[], int left, int middle, int right) {
        int low = middle - left + 1;
        int high = right - middle;

        int[] L = new int[low];
        int[] R = new int[high];

        for (int i = 0; i < low; i++)
            L[i] = arr[left + i];
        for (int j = 0; j < high; j++)
            R[j] = arr[middle + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < low && j < high) {
            if (L[i] <= R[j]) arr[k++] = L[i++];
            else arr[k++] = R[j++];
        }
        while (i < low) arr[k++] = L[i++];
        while (j < high) arr[k++] = R[j++];
    }

    public void mergeSort(int arr[], int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(arr, left, middle);
            mergeSort(arr, middle + 1, right);
            merge(arr, left, middle, right);
        }
    }
}
