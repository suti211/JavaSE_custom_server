package httpserver.handlers;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.sun.net.httpserver.HttpExchange;

import httpserver.annotation.HttpRequestHandler;
import httpserver.annotation.WebRoute;

@HttpRequestHandler
public class UserHandlers {

	@WebRoute(path = "/user")
	public void handle(HttpExchange ex) throws Exception {

		String requestMethod = "";
		InputStream is = ex.getRequestBody();
		String StringFromInputStream = IOUtils.toString(is, "UTF-8");
		System.out.println(StringFromInputStream);

		switch (ex.getRequestMethod()) {
		case ("GET"):
			//HANDLE GET REQUEST HERE
			requestMethod = "GET";
			break;

		case ("POST"):
			String[] requestData = StringFromInputStream.split("=");
			if (requestData[1].equalsIgnoreCase("DELETE")) {
				//HANDLE DELETE REQUEST HERE
				requestMethod = "DELETE";
				
			} else if (requestData[1].equalsIgnoreCase("PUT")) {
				//HANDLE PUT REQUEST HERE
				requestMethod = "PUT";
				
			} else if (requestData[1].equalsIgnoreCase("POST")) {
				//HANDLE POST REQUEST HERE
				requestMethod = "POST";
			}
			break;
		}

		String response = requestMethod + " Request forwarded to /user path! HANDLER: " + this.getClass().getName()
				+ " handle()";
		ex.sendResponseHeaders(200, response.length());

		OutputStream os = ex.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
