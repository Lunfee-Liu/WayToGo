package nov.airline.strategy;

import nov.airline.Port;

import java.util.function.Consumer;

public interface VisitStrategy {
    void visit(Port start, Consumer<Port> action);
}
