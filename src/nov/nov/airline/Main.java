package nov.nov.airline;

import nov.nov.airline.strategy.*;

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
        airlineRoutes.add("PVG-HKG");
        airlineRoutes.add("MST-AMS");

        AirlineRouteMap routeMap = new AirlineRouteMap();
        routeMap.createMap(airlineRoutes);
        Port start = routeMap.getOrThrow("PVG");
        Port dest = routeMap.getOrThrow("FRA");

        RouteReachStrategy routeReachChecker = new BFSRouteReachChecker();
        System.out.println(routeReachChecker.canReach(start, dest.getPortCode()));

        VisitStrategy dfsVisitor = new BFSVisitor();
        dfsVisitor.visit(start, port -> System.out.println(port.getPortCode()));

        DFSPathSearcher pathSearcher = new DFSPathSearcher();
        List<List<String>> paths = pathSearcher.search(start, dest.getPortCode());
        for (List<String> path : paths) {
            System.out.println(String.join("-", path));
        }
    }
}
