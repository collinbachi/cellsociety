package xmlManagement;


public class ConwayWriter extends SimWriter {

    public ConwayWriter () {
        super("XML/Conway.xml", "", "Conway's Game of Life", "Brenna Milligan", 25, 25, 2);

        super.writeFile();
    }

    @Override
    public void populateParameterMap () {

        // The Conway simulation is the only one that does not require specific parameters

    }

}
