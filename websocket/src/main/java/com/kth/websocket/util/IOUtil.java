package com.kth.websocket.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
 
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kth.websocket.SocketHandler;
 
public class IOUtil {

	public static Map<String, String> splitQuery(URI uri) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String query = uri.getQuery();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}
	
	public static SocketHandler getMessage () throws Exception {
		 
		 //현재 요청중인 thread local의 HttpServletRequest 객체 가져오기
		 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		 
		 //HttpSession 객체 가져오기
		 HttpSession session = request.getSession();
		 
		 //ServletContext 객체 가져오기
		 ServletContext conext = session.getServletContext();
		 
		 //Spring Context 가져오기
		 WebApplicationContext wContext = WebApplicationContextUtils.getWebApplicationContext(conext);
		 
		 //스프링 빈 가져오기 & casting
		 SocketHandler sBean = (SocketHandler)wContext.getBean("myHandler");
		 
		 return sBean;
	}
	
}
