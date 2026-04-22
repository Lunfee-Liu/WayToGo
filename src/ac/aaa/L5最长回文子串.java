package ac.aaa;

/**
 * 中心开花，
 */
public class L5最长回文子串 {
    public String longestPalindrome(String s) {
        if (s.length() <= 1) {
            return s;
        }
        String res = s.substring(0, 1);

        for (int i = 0; i < s.length(); i++) {
            String single = longest(s, i, i);
            if (single.length() > res.length()) {
                res = single;
            }

            if (i + 1 < s.length() && s.charAt(i) == s.charAt(i + 1)) {
                String dal = longest(s, i, i + 1);
                if (dal.length() > res.length()) {
                    res = dal;
                }
            }
        }
        return res;
    }

    private String longest (String s, int left, int right) {
        while (left >= 0 && right < s.length()) {
            if (s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
            } else {
                break;
            }
        }
        left++;
        right--;
        return s.substring(left, right + 1);
    }
}
