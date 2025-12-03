package nov.nov.airline.strategy;

import nov.nov.airline.Port;

import java.util.HashSet;
import java.util.function.Consumer;

public class DFSVisitor implements VisitStrategy {
    @Override
    public void visit(Port start, Consumer<Port> action) {
        dfsVisit(start, new HashSet<>(), action);
    }

    private void dfsVisit(Port cur, HashSet<String> visited, Consumer<Port> action) {
        action.accept(cur);
        // 这句看起来可以优化
        visited.add(cur.getPortCode());
        for (Port canArrivePort : cur.getCanArrivePorts()) {
            if (visited.add(canArrivePort.getPortCode())) {
                dfsVisit(canArrivePort, visited, action);
            }
        }
    }
}
