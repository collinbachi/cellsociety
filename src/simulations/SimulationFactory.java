package simulations;

import java.util.HashMap;

/**
 * Factory class to generate the concrete simulation class needed
 *
 * @author Brenna Milligan
 */

public class SimulationFactory {

    private static HashMap<String, Simulation> myRegisteredSimulations = new HashMap<>();

    public static void registerSimulation (String simulationID, Simulation simulation) {
        myRegisteredSimulations.put(simulationID, simulation);
    }

    public Simulation createSimulation (String simulationID, HashMap<String, Double> parameterMap) {
        return myRegisteredSimulations.get(simulationID).createSimulation(parameterMap);
    }

}
