package ac.sort;

public class L归并排序 {
    private void mergeSort(int[] originArray, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        // 分治思想
        mergeSort(originArray, left, mid);
        mergeSort(originArray, mid + 1, right);
        // 合并排好序的两个数组
        merge(originArray, left, mid, right);
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        // 临时数组做
        int[] temp = new int[right - left + 1];
        int cur = 0;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[cur++] = arr[i++];
            } else {
                temp[cur++] = arr[j++];
            }
        }

        // 如果有剩余，直接放在数组后面
        while (i <= mid) {
            temp[cur++] = arr[i++];
        }
        while (j <= right) {
            temp[cur++] = arr[j++];
        }
        cur = 0;
        for (int k = left; k <= right; k++) {
            arr[k] = temp[cur++];
        }
    }
}
