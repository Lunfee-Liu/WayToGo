package nov.airline.strategy;

import nov.airline.Port;

import java.util.List;

public interface PathStrategy {
    List<List<String>> search(Port start, String target);
}
