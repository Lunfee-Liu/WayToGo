package ac.hash;

import java.util.*;

public class L49字母异位词分组 {
    // 排序后作为key 就能分类了
    public static void main(String[] args) {
        String[] strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        for (List<String> item : groupAnagrams(strs)) {
            for (String s : item) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }

    public static List<List<String>> groupAnagrams(String[] strs) {

        Map<String, List<String>> map = new HashMap<>();
        for (String item : strs) {
            char[] chars = item.toCharArray();
            Arrays.sort(chars);
            String key = String.valueOf(chars);

            if (map.containsKey(key)) {
                map.get(key).add(item);
            } else {
                List<String> strings = new ArrayList<>();
                strings.add(item);
                map.put(key, strings);
            }
        }
        return new ArrayList<>(map.values());
    }
}
