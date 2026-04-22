package ac.huisu;

public class L79单词搜索 {
    private static final int[][] FOUR = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    public boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            boolean[][] used = new boolean[board.length][board[0].length];
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0) && dfs(board, i, j, word, 0, used)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs (char[][] board, int i, int j, String word, int index, boolean[][] used) {
        if (index == word.length()) {
            return true;
        }
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) {
            return false;
        }
        if (used[i][j]) {
            return false;
        }
        if (board[i][j] != word.charAt(index)) {
            return false;
        }

        used[i][j] = true;
        for (int[] dir : FOUR) {
            boolean match = dfs(board, i + dir[0], j + dir[1], word, index + 1, used);
            if (match) {
                return true;
            }
        }
        used[i][j] = false;
        return false;
    }
}
