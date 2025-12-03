package nov;

import java.util.LinkedList;
import java.util.Queue;

// todo 1 用visited + bitmap
class IslandNum {
    private static final int[][] FOUR_DIRECTIONS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    public int numIslands(char[][] grid) {
        int result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    result += 1;
                    bfs(grid, i, j);
                }
            }
        }
        return result;
    }


    private void bfs(char[][] grid, int i, int j) {

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});

        while (!queue.isEmpty()) {
            int[] index = queue.poll();
            int row = index[0];
            int col = index[1];
            if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length) {
                continue;
            }
            if (grid[row][col] != '1') {
                continue;
            }
            grid[index[0]][index[1]] = 'x';
            for (int[] direction : FOUR_DIRECTIONS) {
                queue.offer(new int[]{row + direction[0], col + direction[1]});
            }
        }
    }

    private void dfs(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length) {
            return;
        }
        if (grid[i][j] != '1') {
            return;
        }
        grid[i][j] = 'x';
        for (int[] direction : FOUR_DIRECTIONS) {
            dfs(grid, i + direction[0], j + direction[1]);
        }
    }
}