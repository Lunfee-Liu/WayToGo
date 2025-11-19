package nov.airline.strategy;

import nov.airline.Port;


public interface RouteReachStrategy {
    boolean canReach(Port start, String target);
}
