package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSState;

public class ComposedHeuristic implements Heuristic {

    private Heuristic first;
    private Heuristic second;

    public ComposedHeuristic(Heuristic firstHeuristic, Heuristic secondHeuristic) {
        this.first = firstHeuristic;
        this.second = secondHeuristic;
    }

    @Override
    public Integer getHValue(GPSState gpsState) {
        return first.getHValue(gpsState) + second.getHValue(gpsState);
    }

    @Override
    public Integer getCost() {
        return first.getCost() + second.getCost();
    }

    @Override
    public String getName() {
        return String.format("Composition of %s with %s", first.getName(), second.getName());
    }
}
