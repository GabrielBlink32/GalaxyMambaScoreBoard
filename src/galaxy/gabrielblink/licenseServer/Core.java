package galaxy.gabrielblink.licenseServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.Bukkit;


public class Core {
	private static String toBinary(String s){
		byte[] bytes = s.getBytes();
		  StringBuilder binary = new StringBuilder();
		  for (byte b : bytes)
		  {
		     int val = b;
		     for (int i = 0; i < 8; i++)
		     {
		        binary.append((val & 128) == 0 ? 0 : 1);
		        val <<= 1;
		     }
		  }
		  return binary.toString();
	}
	public static Response check(String pluginname, String license,String ipadress,int port){
		String rand = toBinary(UUID.randomUUID().toString());
		try{
			URL url = new URL("http://galaxyproject.dx.am/clientarea/verificar.php?plugin="+pluginname+"&license="+license+"&ip="+ipadress+"&port="+port);
			Scanner s = new Scanner(url.openStream());
			if(s.hasNext()){
				String response = s.next();
				if(response.equalsIgnoreCase("LICENSE_VALID")){
					return Response.LICENSE_VALID;
				}else if(response.equalsIgnoreCase("INVALID_PLUGIN_NAME")){
					return Response.INVALID_PLUGIN_NAME;
				}else if(response.equalsIgnoreCase("IP_ADRESS_INVALID")){
					return Response.IP_ADRESS_INVALID;
				}else if(response.equalsIgnoreCase("LICENSE_INVALID")){
					return Response.LICENSE_INVALID;
				}else if(response.equalsIgnoreCase("INVALID_PORT")){
					return Response.INVALID_PORT;
				}
				s.close();
			}else{
				s.close();
			}
		}catch(IOException exc){
			return Response.WEBSITE_ERROR;
		}
		return Response.WEBSITE_ERROR;
		
	}
}
