import java.net.*;
import java.io.*;

public class Connector{
	private String url;


	public Connector(String url){
		this.url = url;
	}
	public String getContent() throws Exception{
		URL website = new URL(url);

		URLConnection connection = website.openConnection();

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		StringBuilder response = new StringBuilder();
		String inputLine;

		while((inputLine = in.readLine()) != null)
			response.append(inputLine);

		in.close();

		return response.toString();
	}
}