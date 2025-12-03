package nov.nov.airline.strategy;

import nov.nov.airline.Port;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class BFSVisitor implements VisitStrategy {
    @Override
    public void visit(Port start, Consumer<Port> action) {
        HashSet<String> visited = new HashSet<>();
        Queue<Port> queue = new LinkedList<>();
        queue.add(start);

        visited.add(start.getPortCode());
        while (!queue.isEmpty()) {
            Port cur = queue.poll();
            action.accept(cur);
            for (Port canArrivePort : cur.getCanArrivePorts()) {
                if (visited.add(canArrivePort.getPortCode())) {
                    queue.add(canArrivePort);
                }
            }
        }
    }
}
