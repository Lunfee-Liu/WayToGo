package ac.huisu;

import java.util.ArrayList;
import java.util.List;

public class L22括号生成 {
    private static char[] pair = new char[]{'(', ')'};

    public List<String> generateParenthesis(int n) {

        List<String> res = new ArrayList<>();
        dfs(res, 0, 0, new StringBuffer(), n);
        return res;
    }

    private void dfs(List<String> res, int leftNum, int rightNum, StringBuffer sb, int n) {
        if (leftNum > n || rightNum > n) {
            return;
        }
        if (sb.length() == n * 2) {
            res.add(new String(sb));
            return;
        }
        leftNum++;
        sb.append('(');
        dfs(res, leftNum, rightNum, sb, n);
        leftNum--;
        sb.deleteCharAt(sb.length() - 1);
        if (leftNum > rightNum) {
            rightNum++;
            sb.append(')');
            dfs(res, leftNum, rightNum, sb, n);
            sb.deleteCharAt(sb.length() - 1);
            rightNum--;
        }
    }
}
