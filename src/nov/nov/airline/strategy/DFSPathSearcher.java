package nov.nov.airline.strategy;

import nov.nov.airline.Port;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DFSPathSearcher implements PathStrategy {
    @Override
    public List<List<String>> search(Port start, String target) {
        List<List<String>> result = new ArrayList<>();
        dfsSearcher(start, new ArrayList<>(), result, new HashSet<>(), target);
        return result;
    }

    private void dfsSearcher(Port cur, List<String> path, List<List<String>> result, Set<String> visited, String target) {

        visited.add(cur.getPortCode());
        path.add(cur.getPortCode());

        if (cur.getPortCode().equals(target)) {
            result.add(new ArrayList<>(path));
        } else {
            for (Port canArrivePort : cur.getCanArrivePorts()) {
                if (!visited.contains(canArrivePort.getPortCode())) {
                    dfsSearcher(canArrivePort, path, result, visited, target);
                }
            }
        }

        visited.remove(cur.getPortCode());
        path.remove(path.size() - 1);
    }
}
