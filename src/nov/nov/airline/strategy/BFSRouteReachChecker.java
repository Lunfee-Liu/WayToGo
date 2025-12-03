package nov.nov.airline.strategy;

import nov.nov.airline.Port;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BFSRouteReachChecker implements RouteReachStrategy {
    @Override
    public boolean canReach(Port start, String target) {
        Queue<Port> queue = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        queue.add(start);
        visited.add(start.getPortCode());

        while (!queue.isEmpty()) {
            Port cur = queue.poll();
            if (cur.getPortCode().equals(target)) {
                return true;
            }

            for (Port canArrivePort : cur.getCanArrivePorts()) {
                if (visited.add(canArrivePort.getPortCode())) {
                    queue.add(canArrivePort);
                }
            }

        }
        return false;
    }
}
