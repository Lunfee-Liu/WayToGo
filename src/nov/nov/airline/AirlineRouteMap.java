package nov.nov.airline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AirlineRouteMap {
    private final Map<String, Port> routeMap = new HashMap<>();
    public void createMap(List<String> airlineRoutes) {
        for (String airlineRoute : airlineRoutes) {
            String[] depAndArrPorts = airlineRoute.split("-");
            String depPortCode = depAndArrPorts[0];
            String arrPortCode = depAndArrPorts[1];
            Port depPort = getOrCreate(depPortCode);
            Port arrPort = getOrCreate(arrPortCode);

            depPort.addToRoute(arrPort);
        }
    }

    public Port getOrThrow(String portCode) {
        return Optional.ofNullable(routeMap.get(portCode)).orElseThrow(IllegalArgumentException::new);
    }

    private Port getOrCreate(String portCode) {
        return routeMap.computeIfAbsent(portCode, Port::new);
    }
}
