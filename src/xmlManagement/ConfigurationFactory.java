package xmlManagement;

public class ConfigurationFactory {

	public Configuration createConfiguration(String configName) {
		Class<Configuration> myConfig;
		try {
			myConfig = (Class<Configuration>) Class.forName("xmlManagement."+configName + "Configuration");
			return (Configuration) myConfig.newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
