package httpserver.server;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RequestHandler implements HttpHandler {

	String requestURL;
	URI requestURI;
	private ReflecShit reflectShit;

	@Override
	public void handle(HttpExchange exc) {
		requestURI = exc.getRequestURI();
		requestURL = requestURI.getPath();

		System.out.println("request indbound to path: " + requestURL + ", method: " + exc.getRequestMethod());
		System.out.println(requestURL);

		if (requestURL.equals("/")) {
			File index = new File("web/index.html");
			OutputStream out = exc.getResponseBody();
			int fileSize = Long.valueOf(index.length()).intValue();

			try {
				byte[] file = Files.readAllBytes(index.toPath());
				String html = new String(file, "UTF-8");
				exc.sendResponseHeaders(200, fileSize);
				out.write(html.getBytes());
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("FAIL during file service!");
			}
		} else {
			reflectShit = new ReflecShit(requestURL, exc);
			reflectShit.findHandler();
		}
	}

}
