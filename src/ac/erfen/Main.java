package ac.erfen;

public class Main {
    public static void main(String[] args) {
        int[][] ints = new int[][]{
                {1,3,5,7},
                {10,11,16,20},
                {23,30,34,60}};
        L74搜索二维矩阵 l74搜索二维矩阵 = new L74搜索二维矩阵();
        l74搜索二维矩阵.searchMatrix(ints, 3);
    }
}
