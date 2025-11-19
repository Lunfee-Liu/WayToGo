package nov.airline;

import nov.airline.strategy.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> airlineRoutes = new ArrayList<>();
        airlineRoutes.add("AMS-FRA");
        airlineRoutes.add("FRA-AMS");
        airlineRoutes.add("HKG-FRA");
        airlineRoutes.add("AMS-LGG");
        airlineRoutes.add("PVG-AMS");
        airlineRoutes.add("PVG-MST");

        AirlineRouteMap routeMap = new AirlineRouteMap();
        routeMap.createMap(airlineRoutes);
        Port start = routeMap.getOrThrow("PVG");
        Port dest = routeMap.getOrThrow("HKG");

        RouteReachStrategy routeReachChecker = new BFSRouteReachChecker();
        System.out.println(routeReachChecker.canReach(start, dest.getPortCode()));

        VisitStrategy dfsVisitor = new BFSVisitor();
        dfsVisitor.visit(start, port -> System.out.println(port.getPortCode()));
    }
}
