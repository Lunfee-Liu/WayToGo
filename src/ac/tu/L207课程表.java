package ac.tu;

import java.util.ArrayList;
import java.util.List;

public class L207课程表 {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> map = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            map.add(new ArrayList<>());
        }
        for (int[] prerequisite : prerequisites) {
            map.get(prerequisite[1]).add(prerequisite[0]);
        }

        int[] visited = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (hasCircle(map, i, visited)) {
                return false;
            }
        }

        return true;
    }

    private boolean hasCircle(List<List<Integer>> map, int i, int[] visited) {
        if (visited[i] == 1) {
            return true;
        }

        visited[i] = 1;
        for (int course : map.get(i)) {
            if (hasCircle(map, course, visited)) {
                return true;
            }
        }
        visited[i] = 0;
        return false;
    }

    private boolean hasCircle2(List<List<Integer>> map, int i, int[] visited) {
        if (visited[i] == 1) {
            return true;
        }

        if (visited[i] == 2) {
            return false;
        }

        visited[i] = 1;
        for (int course : map.get(i)) {
            if (hasCircle2(map, course, visited)) {
                return true;
            }
        }
        visited[i] = 2;
        return false;
    }
}
