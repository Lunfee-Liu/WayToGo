package nov.nov.airline.strategy;

import nov.nov.airline.Port;

import java.util.HashSet;
import java.util.Objects;

public class DFSRouteReachChecker implements RouteReachStrategy {
    @Override
    public boolean canReach(Port start, String target) {
        if (Objects.isNull(start)) {
            return false;
        }

        return canReachDFS(start, new HashSet<>(), target);
    }

    private boolean canReachDFS(Port cur, HashSet<String> visited, String target) {
        if (cur.getPortCode().equals(target)) {
            return true;
        }
        if (visited.add(cur.getPortCode())) {
            return cur.getCanArrivePorts().stream().anyMatch(canReachPort -> canReachDFS(canReachPort, visited, target));
        }
        return false;
    }


}
