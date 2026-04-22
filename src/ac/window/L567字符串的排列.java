package ac.window;

import java.util.Arrays;

public class L567字符串的排列 {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        int[] h1 = new int[26];
        int[] h2 = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            h1[s1.charAt(i) - 'a']++;
            h2[s2.charAt(i) - 'a']++;

        }
        if (Arrays.equals(h1, h2)) {
            return true;
        }

        int left = 0;
        int right = s1.length();

        while (right < s2.length()) {
            h2[s2.charAt(left) - 'a']--;
            h2[s2.charAt(right) - 'a']++;

            if (Arrays.equals(h1, h2)) {
                return true;
            }
            left++;
            right++;
        }

        return false;
    }
}
