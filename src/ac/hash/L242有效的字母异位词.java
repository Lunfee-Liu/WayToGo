package ac.hash;

import java.util.HashMap;
import java.util.Map;

public class L242有效的字母异位词 {
    public static void main(String[] args) {

    }
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> map1 = new HashMap<>();
        Map<Character, Integer> map2 = new HashMap<>();
        char[] chars1 = s.toCharArray();
        char[] chars2 = t.toCharArray();

        for (int i = 0; i < chars1.length; i++) {
            if (map1.containsKey(chars1[i])) {
                map1.put(chars1[i], map1.get(chars1[i]) + 1);
            } else {
                map1.put(chars1[i], 1);
            }

            if (map2.containsKey(chars2[i])) {
                map2.put(chars2[i], map2.get(chars2[i]) + 1);
            } else {
                map2.put(chars2[i], 1);
            }
        }
        return map2.equals(map1);
    }

    public boolean isAnagram1(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] table = new int[26];
        for (char c : s.toCharArray()) {
            table[c - 'a']++;
        }
        for (char c : t.toCharArray()) {
            table[c - 'a']--;
            if (table[c - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }
}
