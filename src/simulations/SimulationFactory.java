package simulations;

import cellsociety_team09.UIView;


/**
 * Factory class to generate the concrete simulation class needed
 *
 * @author Brenna Milligan
 */

public class SimulationFactory {

    @SuppressWarnings("unchecked")
    public Simulation createSimulation (String simulationID) {
        Class<Simulation> clazz;
        try {
            clazz = (Class<Simulation>) Class.forName("simulations." + simulationID);
            return (Simulation) clazz.newInstance();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            UIView popup = new UIView();
            popup.displayInvalidFile();
        }
        return null;
    }

}
