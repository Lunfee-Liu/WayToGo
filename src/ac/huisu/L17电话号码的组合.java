package ac.huisu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class L17电话号码的组合 {

    public static Map<Character, char[]> map = new HashMap<>();
    static {
        map.put('2', new char[]{'a','b','c'});
        map.put('3', new char[]{'d','e','f'});
        map.put('4', new char[]{'g','h','i'});
        map.put('5', new char[]{'j','k','l'});
        map.put('6', new char[]{'m','n','o'});
        map.put('7', new char[]{'p','q','r','s'});
        map.put('8', new char[]{'t','u','v'});
        map.put('9', new char[]{'w','x','y','z'});
    }
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        char[] chars = digits.toCharArray();
        dfs(res, chars, 0, new StringBuffer());
        return res;
    }

    private void dfs(List<String> res, char[] chars, int index, StringBuffer sb) {
        if (sb.length() == chars.length) {
            res.add(new String(sb));
            return;
        }
        char[] alf = map.get(chars[index]);
        for (int i = 0; i < alf.length; i++) {
            sb.append(alf[i]);
            dfs(res, chars, index + 1, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
