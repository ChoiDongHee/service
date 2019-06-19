package com.kth.websocket;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

import com.kth.websocket.util.IOUtil;

import net.sf.json.JSONObject;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	SocketHandler socketHandler;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private Map<String, WebSocketSession> mSessionMap = null;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String home(Locale locale,@RequestParam Map<String,String> allRequestParams, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		

		String formattedDate = dateFormat.format(date);
		model.addAttribute("ssionMap", mSessionMap );
		model.addAttribute("serverTime", formattedDate );
		
		
		return "index";
	}
	
	@ResponseBody
	@RequestMapping(value = "/callMessage.do", method = RequestMethod.GET)
	public String callMessage(Locale locale,@RequestParam Map<String,String> allRequestParams, Model model) {
	
		JSONObject json = new JSONObject();
		if(allRequestParams.get("cmd") !=null && allRequestParams.get("code") !=null){
		
			String strCmd = allRequestParams.get("cmd");
			String stCode = allRequestParams.get("code");
			
			String strJason = "{\"cmd\":\""+strCmd+"\",\"code\":\""+stCode+"\"}";
			socketHandler.sendAllMessage(strJason);
				
			 json.put("result", "ok");
			 json.put("data", strJason);
		}else {
			 json.put("result", "input param");
		}

	    return json.toString();

	}
	
	
}
