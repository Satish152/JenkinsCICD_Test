package cicd.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
	public Properties prop;
	public PropertyReader(){
		String filePath=System.getProperty("user.dir")+"//Configurations//config.properties";
		prop=new Properties();
		try {
			prop.load(new FileInputStream(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getPropertyValue(String key){
		return prop.getProperty(key);
	}
}
