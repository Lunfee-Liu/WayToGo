package ac.window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class L438找到字符串中所有字母异位词 {
    public static void main(String[] args) {
        for (Integer anagram : findAnagrams1("cbaebabacd", "abc")) {
            System.out.println(anagram);
        }
    }
    public static List<Integer> findAnagrams(String s, String p) {
        char[] chars = p.toCharArray();
        Arrays.sort(chars);
        String match = String.valueOf(chars);
        int left = 0;
        int right = p.length() - 1;

        List<Integer> result = new ArrayList<>();
        while (right < s.length()) {
            char[] chars1 = s.substring(left, right + 1).toCharArray();
            Arrays.sort(chars1);
            if(match.equals(String.valueOf(chars1))) {
                result.add(left);
            }
            left++;
            right++;
        }
        return result;
    }

    public static List<Integer> findAnagrams1(String s, String p) {
        if (p.length() > s.length()) {
            return Collections.emptyList();
        }
        int[] sTable = new int[26];
        int[] pTable = new int[26];
        for (int i = 0; i < p.length(); i++) {
            sTable[s.charAt(i) - 'a']++;
            pTable[p.charAt(i) - 'a']++;
        }
        List<Integer> result = new ArrayList<>();
        if (Arrays.equals(sTable, pTable)) {
            result.add(0);
        }
        int left = 0;
        int right = p.length();
        while (right < s.length()) {
            sTable[s.charAt(left) - 'a']--;
            sTable[s.charAt(right) - 'a']++;

            if (Arrays.equals(sTable, pTable)) {
                result.add(left + 1);
            }
            left++;
            right++;

        }
        return result;
    }
}
