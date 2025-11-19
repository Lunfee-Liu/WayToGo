package nov.airline.strategy;

import nov.airline.Port;

public interface PathStrategy {
    void visit(Port start, String target);
}
