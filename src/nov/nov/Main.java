package nov.nov;

import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        QuanPaiLie solution = new QuanPaiLie();
        List<List<Integer>> permute = solution.permute(new int[]{1, 2, 3});
        for (List<Integer> path : permute) {
            System.out.println(path.stream().map(String::valueOf).collect(Collectors.joining(", ")));
        }
    }
}
