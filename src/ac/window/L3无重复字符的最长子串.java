package ac.window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 滑动窗口
 * 移动窗口右边界，用HashMap记录出现的字符和位置
 * 当遇到重复字符时，根据重复字符的位置更新窗口左边界
 */

public class L3无重复字符的最长子串 {
    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring2("tmmmzuuuuxxxt"));
    }

    // 原版
    public static int lengthOfLongestSubstring(String s) {
        if (s.length() <= 1) {
            return s.length();
        }
        int left = 0;
        int right = 1;
        Map<Character, Integer> chars = new HashMap<>();
        chars.put(s.charAt(0), 0);

        int maxLength = 1;
        while (right < s.length()) {
            // 没有重复字符 或 重复字符不在窗口内
            if (!chars.containsKey(s.charAt(right)) || chars.get(s.charAt(right)) < left) {
                maxLength = Math.max(maxLength, right - left  + 1);
            } else {
                left = chars.get(s.charAt(right)) + 1;
            }
            chars.put(s.charAt(right), right);
            right++;

        }

        return maxLength;
    }

    // 变种1：返回该子串

    /**
     * 更新长度时同时更新最新字符串
     */
    public static String lengthOfLongestSubstring1(String s) {
        if (s.length() <= 1) {
            return s;
        }
        int left = 0;
        int right = 1;
        Map<Character, Integer> chars = new HashMap<>();
        chars.put(s.charAt(0), 0);

        int maxLength = 1;
        String result = s.substring(0, 1);
        while (right < s.length()) {
            if (!chars.containsKey(s.charAt(right)) || chars.get(s.charAt(right)) < left) {
                maxLength = Math.max(maxLength, right - left  + 1);
                if (right - left + 1 > maxLength) {
                    maxLength = right - left + 1;
                    // 更新长度时同时更新最新字符串
                    result = s.substring(left, right + 1);
                }
            } else {
                left = chars.get(s.charAt(right)) + 1;
            }
            chars.put(s.charAt(right), right);
            right++;

        }

        return result;
    }

    // 允许每个字符最多重复1次

    /**
     * Map中记录字符出现的最后两个坐标
     */
    public static int lengthOfLongestSubstring2(String s) {
        if (s.length() <= 1) {
            return s.length();
        }
        int left = 0;
        int right = 1;
        Map<Character, List<Integer>> chars = new HashMap<>();
        List<Integer> index = new ArrayList<>();
        index.add(0);
        chars.put(s.charAt(0), index);

        int maxLength = 1;
        while (right < s.length()) {
            // 窗口内没有该值时，更新map
            if (!chars.containsKey(s.charAt(right))) {
                maxLength = Math.max(maxLength, right - left  + 1);
                ArrayList<Integer> index1 = new ArrayList<>();
                index1.add(right);
                chars.put(s.charAt(right), index1);
                // 窗口内有1个该值时，更新map
            } else if (chars.get(s.charAt(right)).size() == 1) {
                maxLength = Math.max(maxLength, right - left  + 1);
                chars.get(s.charAt(right)).add(right);
            }
            // 窗口内有1个该值时，更新map，更新left
            else {
                List<Integer> index3 = chars.get(s.charAt(right));
                int first = index3.get(0);
                index3.remove(0);
                index3.add(right);
                left = first + 1;
            }

            right++;

        }

        return maxLength;
    }

}
