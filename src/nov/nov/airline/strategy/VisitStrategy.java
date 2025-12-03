package nov.nov.airline.strategy;

import nov.nov.airline.Port;

import java.util.function.Consumer;

public interface VisitStrategy {
    void visit(Port start, Consumer<Port> action);
}
