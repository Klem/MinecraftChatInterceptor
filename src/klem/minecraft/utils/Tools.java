/**
 * Klem's Minecraft Chat Interceptor
 * 			Developed for
 * Maurin Entertainement Industries
 * v1.1.5
 */
package klem.minecraft.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import klem.minecraft.ChatInterceptor;
import klem.minecraft.resources.Resources;

/**
 * Class regroupant des méthode utiliaire appelées ça et la
 * @author e402685
 *
 */
public class Tools {

	/**
	 * prend un fichier en entrée et renvoie son centenu sous forme de chaine
	 * @param fileName
	 * @return
	 */
	public static String getFileContentAsString(String fileName) {
		System.out.println("Opening file : "+fileName);
		StringBuilder builder = new StringBuilder();
		
		String line;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(Resources.class.getResourceAsStream(fileName), "UTF-8"));
			while((line = br.readLine()) != null) {
			builder.append(line+"\n");	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String getContentAsString(URL url) {

		StringBuilder output = new StringBuilder();
		try {
			// open the stream and put it into BufferedReader
			URLConnection conn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String inputLine;

			while ((inputLine = br.readLine()) != null) {
				output.append(inputLine);
			}

			br.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output.toString();
	}
	
	 public static Properties load(String propsName) throws Exception {
	        Properties props = new Properties();
	        URL url = ClassLoader.getSystemResource(propsName);
	        props.load(url.openStream());
	        return props;
	    }

}
