package nov;

public class IslandMaxArea {
    private static final int[][] FOUR_DIRECTIONS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    public int maxAreaOfIsland(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;

        int max = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 1) {
                    max = Math.max(max, dfs(grid, i, j, 0));
                }
            }
        }
        return max;
    }

    private int dfs(int[][] grid, int i, int j, int area) {
        int row = grid.length;
        int col = grid[0].length;
        if (i < 0 || i >= row || j < 0 || j >= col || grid[i][j] != 1) {
            return area;
        }
        area++;
        grid[i][j] = -1;
        for (int[] fourDirection : FOUR_DIRECTIONS) {
            area = dfs(grid, i + fourDirection[0], j + fourDirection[1], area);
        }
        return area;
    }
}
