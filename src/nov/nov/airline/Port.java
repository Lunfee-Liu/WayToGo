package nov.nov.airline;

import java.util.HashSet;
import java.util.Set;

public class Port {

    private final String portCode;
    private final Set<Port> canArrivePorts;

    public String getPortCode() {
        return portCode;
    }

    public Set<Port> getCanArrivePorts() {
        return canArrivePorts;
    }

    public Port(String portCode) {
        this.portCode = portCode;
        this.canArrivePorts = new HashSet<>();
    }

    public void addToRoute(Port arrPort) {
        canArrivePorts.add(arrPort);
    }
}
