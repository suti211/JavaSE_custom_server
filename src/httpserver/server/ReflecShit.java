package httpserver.server;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;

import com.sun.net.httpserver.HttpExchange;

import httpserver.annotation.HttpRequestHandler;
import httpserver.annotation.WebRoute;

public class ReflecShit {
	
	Class<?> handler;
	String path;
	HttpExchange exc;
	
	public ReflecShit(String path, HttpExchange exc) {
		this.path = path;
		this.exc = exc;
	}
	
	public void findHandler(){
		try{
//			handler = Class.forName("handlers.UserHandlers");
//			Object handlerInstance = handler.newInstance();
//			
//			Method[] methods = handler.getMethods();
//			
//			for(Method m : methods){
//				if(m.isAnnotationPresent(WebRoute.class)){
//					if(path.equals(m.getAnnotation(WebRoute.class).path())){
//						m.invoke(handlerInstance, exc);
//					}
//				}
//			}
			
			Reflections ref = new Reflections("httpserver.handlers");
			
			Set<Class<? extends Object>> classes = ref.getTypesAnnotatedWith(HttpRequestHandler.class);
			System.out.println(classes.size());
			
			boolean handlerFound = false;
			Method[] methods;
			for(Class<?> c : classes){
				methods = c.getMethods();
				for(Method m : methods){
					if(m.isAnnotationPresent(WebRoute.class)){
						if(m.getDeclaredAnnotation(WebRoute.class).path().equals(exc.getRequestURI().getPath())){
							handler = c;
							System.out.println(c.getName());
							Object handlerInstance = handler.newInstance();
							
							Method handlerMethod = handler.getMethod("handle", HttpExchange.class);
							
							handlerMethod.invoke(handlerInstance, exc);
							handlerFound = true;
						} 
					}
				}
			}
			
			if(!handlerFound){
				System.out.println("nem Handler found");
				OutputStream os = exc.getResponseBody();
				exc.sendResponseHeaders(404, 0);
				os.write("a kurva anyád".getBytes());
				os.close();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
