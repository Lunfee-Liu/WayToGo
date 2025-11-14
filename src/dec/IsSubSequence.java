package dec;

public class IsSubSequence {
    public static void main(String[] args) {
        String sub = "fdpfdsu";
        String sup = "fdpfdis";
        boolean yes = isSub(sub, sup);
        System.out.println(yes);
    }

    private static boolean isSub(String sub, String sup) {
        if (sub.isEmpty()) {
            return true;
        }
        if (sub.length() > sup.length()) {
            return false;
        }
        char[] subArray = sub.toCharArray();
        char[] supArray = sup.toCharArray();
        int index = 0;
        for (char c : supArray) {
            if (index >= subArray.length) {
                return true;
            }
            if (subArray[index] == c) {
                index++;
            }
        }
        return index >= subArray.length;
    }
}
