package simulations;

/**
 * Factory class to generate the concrete simulation class needed
 *
 * @author Brenna Milligan
 */

public class SimulationFactory {

    public Simulation createSimulation (String simulationID) {
        Class clazz;
        try {
            clazz = Class.forName("simulations."+simulationID);
            return (Simulation) clazz.newInstance();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
