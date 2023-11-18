package Utils;

import java.io.FileInputStream;
import java.util.Properties;

public class Utils {
	
	//Declare variables
	public static Properties prop;
	
	/*
	 * Read and work with confir.properties
	 */
	
	public static Properties getConfigProperties() throws Exception{
		try {
			prop = new Properties();
			prop.load(new FileInputStream("config.properties"));
			
			return prop;
		}catch (Exception e) {
			throw new Exception("No se encuentra el archivo de configuración: config.properties");
		}
		
	}

}
