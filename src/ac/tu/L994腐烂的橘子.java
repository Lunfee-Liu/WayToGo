package ac.tu;

import java.util.ArrayDeque;
import java.util.Deque;

public class L994腐烂的橘子 {
    private static final int[][] FOUR_DIRECTIONS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    public int orangesRotting(int[][] grid) {

        Deque<int[]> queue = new ArrayDeque<>();
        int rotOnes = 0;
        int freshOnes = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                    rotOnes++;
                } else if (grid[i][j] == 1) {
                    freshOnes++;
                }
            }
        }

        if (freshOnes == 0) {
            return 0;
        }
        if (rotOnes == 0) {
            return -1;
        }

        int time = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] rotIndex = queue.poll();
                for (int[] dir : FOUR_DIRECTIONS) {
                    if (enRot(grid, rotIndex[0] + dir[0], rotIndex[1] + dir[1])) {
                        freshOnes--;
                        queue.offer(new int[]{rotIndex[0] + dir[0], rotIndex[1] + dir[1]});
                    }
                }
            }
            time++;
        }

        if (freshOnes > 0) {
            return -1;
        }
        return time - 1;
    }

    private boolean enRot(int[][] grid, int i, int j) {
        if(i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
            return false;
        }
        if (grid[i][j] != 1) {
            return false;
        }
        grid[i][j] = 2;
        return true;
    }
}
