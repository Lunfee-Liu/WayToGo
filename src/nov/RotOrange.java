package nov;

import java.util.*;

class RotOrange {
    private static final int[][] FOUR_DIRECTIONS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    public int orangesRotting(int[][] grid) {
        int rotOnes = 0;
        int freshOnes = 0;
        Deque<int[]> queue = new LinkedList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    rotOnes++;
                    queue.offer(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    freshOnes++;
                }
            }
        }
        if (freshOnes == 0 && rotOnes == 0) {
            return -1;
        }
        if (rotOnes == 0) {
            return -1;
        }
        if (freshOnes == 0) {
            return 0;
        }

        int time = 0;
        while (!queue.isEmpty() && freshOnes > 0) {
            List<int[]> curLayer = new ArrayList<>();
            while (!queue.isEmpty()) {
                curLayer.add(queue.poll());
            }

            boolean roted = false;
            for (int[] rotOne : curLayer) {
                for (int[] direction : FOUR_DIRECTIONS) {
                    if (enRot(grid, rotOne[0] + direction[0], rotOne[1] + direction[1])) {
                        roted = true;
                        freshOnes--;
                        queue.offer(new int[]{rotOne[0] + direction[0], rotOne[1] + direction[1]});
                    }
                }

            }
            if (roted) {
                time++;
            } else {
                break;
            }
        }
        if (freshOnes > 0) {
            return -1;
        }
        return time;

    }

    private boolean enRot(int[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j>= grid[0].length || grid[i][j] != 1) {
            return false;
        }
        grid[i][j] = 2;
        return true;
    }

}