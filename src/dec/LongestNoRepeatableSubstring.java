package dec;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/**
 * slide window
 */
public class LongestNoRepeatableSubstring {
    public static void main(String[] args) {
        String input = "abcdbcsbdsafsdfppkekmafkoerg";
        int result = getLongestNoRepeatableSubString(input);
        System.out.println(result);
    }

    private static int getLongestNoRepeatableSubString(String input) {
        if (Objects.isNull(input)) {
            throw new IllegalArgumentException();
        }
        if (input.length() <= 1) {
            return input.length();
        }
        // The map is the key
        HashMap<Character, Integer> characterIndexMap = new HashMap<>();
        int max = 1;
        characterIndexMap.put(input.charAt(0), 0);
        int left = 0;
        for (int right = 1; right < input.length(); right++) {
            if (characterIndexMap.containsKey(input.charAt(right)) && characterIndexMap.get(input.charAt(right)) >= left) {
                left = characterIndexMap.get(input.charAt(right)) + 1;
            } else {
                max = Math.max(max, right - left + 1);
            }
            characterIndexMap.put(input.charAt(right), right);
        }
        return max;
    }

    private String getLongestNoRepeatableSubStringV2(String input) {
        if (Objects.isNull(input) || input.isEmpty()) {
            throw new IllegalArgumentException("input invalid");
        }

        if (input.length() == 1) {
           return input;
        }

        HashMap<Character, Integer> appearedCharIndexMap = new HashMap<>();
        appearedCharIndexMap.put(input.charAt(0), 0);
        int left = 0;
        int maxLeft = 0;
        int maxRight = 0;
        int maxLength = 1;

        for (int right = 1; right <= input.length(); right++) {
            char currentChar = input.charAt(right);

            if (!appearedCharIndexMap.containsKey(currentChar) || left > appearedCharIndexMap.get(currentChar)) {
                if (right - left + 1 > maxLength) {
                   maxRight = right;
                   maxLeft = left;
                   maxLength = right - left + 1;
                }
            } else {
                left = appearedCharIndexMap.get(currentChar) + 1;
            }
            appearedCharIndexMap.put(currentChar, right);
        }
        return input.substring(maxLeft, maxRight + 1);
    }
}
