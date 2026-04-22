package ac.erfen;

public class L74搜索二维矩阵 {
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = searchRow(matrix, target);
        if (row >= 0) {
            return search(matrix[row], target);
        }
        return false;
    }

    private boolean search(int[] row, int target) {
        int left = 0;
        int right = row.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (row[mid] == target) {
                return true;
            }
            if (target > row[mid]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }

    private int searchRow(int[][] matrix, int target) {
        int up = 0;
        int down = matrix.length;

        while (up < down) {
            int mid = (down + up) / 2;
            if (target > matrix[mid][0]) {
                up = mid + 1;
            } else {
                down = mid;
            }
        }
        return up - 1;
    }



    public boolean searchMatrix2(int[][] matrix, int target) {
        int up = 0;
        int right = matrix[0].length - 1;

        while (up < matrix.length && right >= 0){
            if (target == matrix[up][right]) {
                return true;
            }
            if (target > matrix[up][right]) {
                up++;
            } else {
                right--;
            }
        }
        return false;
    }
}
