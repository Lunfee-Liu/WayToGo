package nov.nov.airline.strategy;

import nov.nov.airline.Port;


public interface RouteReachStrategy {
    boolean canReach(Port start, String target);
}
