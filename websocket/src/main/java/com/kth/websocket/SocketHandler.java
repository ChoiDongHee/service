package com.kth.websocket;
 
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.kth.websocket.util.IOUtil;


/*
afterConnectionEstablished - WebSocket 연결이 열리고 사용이 준비될 때 호출
afterConnectionClosed - WebSocket 연결이 닫혔을 때 호출
handleMessage - 클라이언트로부터 메시지가 도착했을 때 호출
handleTransportError-  전송 에러 발생할 때 호출
supportsPartialMessages WebSocketHandler가 부분 메시지를 처리할 때 호출
*/
 
public class SocketHandler extends TextWebSocketHandler implements InitializingBean {
 
	private final Logger logger = LoggerFactory.getLogger(getClass());
	//private Set<WebSocketSession> sessionSet = new HashSet<WebSocketSession>();
 
	private Map<WebSocketSession, String> keyMap = (Map<WebSocketSession, String>) new HashMap<WebSocketSession, String>();
	private Set<WebSocketSession> sessionSet = new HashSet<WebSocketSession>();

	public SocketHandler (){
	     super();
	     this.logger.info("#############SocketHandler() create Start #############");   
	     
	     this.logger.info("#############SocketHandler() create End   #############"); 
	         
	       }
	 

/**
 * @see org.springframework.web.socket.handler.AbstractWebSocketHandler#afterConnectionClosed(org.springframework.web.socket.WebSocketSession,
 *      org.springframework.web.socket.CloseStatus)
 * 
 *      접속한 사용자의 웹 소켓 정보를 제거한다.
 */
   @Override
   public  void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
	   
        super.afterConnectionClosed(session, status);
		 /* 세션 삭제 */
        this.logger.info("#############afterConnectionClosed["+ status +"] Start #############");   
        try {
        	sessionSet.remove(session);
		} catch (Exception e) {
			// TODO: handle exception
			this.logger.info("#############afterConnectionClosed["+ status +"] Err #############");   
		}
        this.logger.info("#############afterConnectionClosed["+ status +"]  End  #############"); 
       }
 
/**
 * @see org.springframework.web.socket.handler.AbstractWebSocketHandler#afterConnectionEstablished(org.springframework.web.socket.WebSocketSession)
 * 
 *      접속한 사용자의 웹 소켓 정보를 담는다.
 * 
 *      ws = new WebSocket(url); url 접근에 성공했을 시 이 메소드를 호출함. 웹 소켓의 접근에 성공하고 웹
 *      소켓 연결을 열고 사용할 준비가 된 후에 호출됨.
 */
   @Override
    public  void afterConnectionEstablished(WebSocketSession session ) throws Exception {
	   
         super.afterConnectionEstablished(session);              
       
         logger.info("############# afterConnectionEstablished start #############");
         sessionSet.add(session);
         logger.info("############# afterConnectionEstablished ["+ sessionSet.size()+"] end #############");
		
 
   
   }
 
  
   @Override
   public synchronized void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
	   
	   super.handleMessage(session, message);
	   
       logger.info("############# handleMessage start #############");
       sendTextMessage(message);
       logger.info("############# handleMessage enddd #############");
	   
    }
 



   @Override
   public synchronized void handleTransportError(WebSocketSession session,Throwable exception) throws Exception {
       logger.info("############# handleTransportError start #############");
       
       logger.info("############# handleTransportError enddd #############");
       }
 
   @Override
   public synchronized boolean supportsPartialMessages() {

         logger.info("############# supportsPartialMessages start #############");
 
		 logger.info("############# supportsPartialMessages enddd #############");
		
		 return super.supportsPartialMessages();
   }
  
   public   void sendMessage(String message){
	   logger.info("############# supportsPartialMessages enddd #############");
       for (WebSocketSession session: this.sessionSet){
           if (session.isOpen()){
                  try{
                	  	this.logger.error("send message!", message);
                        session.sendMessage(new TextMessage(message));
                        
                  }catch (Exception ignored){
                        this.logger.error("fail to send message!", ignored);
                  }
           }
       }//END OF FOR 
       logger.info("############# supportsPartialMessages enddd #############");
   }		
   
   public   void sendAllMessage(String message){
	   logger.info("############# supportsPartialMessages enddd #############");

       for (WebSocketSession session: this.sessionSet){
           if (session.isOpen()){
                  try{
                	  	this.logger.error("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@send message!", message);
                        session.sendMessage(new TextMessage(message));
                        
                  }catch (Exception ignored){
                        this.logger.error("fail to send message!", ignored);
                  }
           }
       }//END OF FOR 
       logger.info("############# supportsPartialMessages enddd #############");
   }		
 
   public  void sendTextMessage(WebSocketMessage<?> message){
	   
       for (WebSocketSession session: this.sessionSet){
           if (session.isOpen()){
                  try{
                        session.sendMessage(message);
                        
                  }catch (Exception ignored){
                        this.logger.error("fail to send message!", ignored);
                  }
           }
       }//END OF FOR 
   }			

   @Override
   public  void afterPropertiesSet() throws Exception {
    	   		

   }
   	/**
   	 * @see org.springframework.web.socket.handler.AbstractWebSocketHandler#handleTextMessage(org.springframework.web.socket.WebSocketSession,
   	 *      org.springframework.web.socket.TextMessage)
   	 * 
   	 *      새로운 웹 소켓 메시지가 도착할 때 호출됨. ex ) ws.send("your message");
   	 */     
    @Override
	protected   void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// TODO Auto-generated method stub
		super.handleTextMessage(session, message);
		this.logger.info(">>> handleTextMessage!");
		
	}

	@Override
	protected  void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
		// TODO Auto-generated method stub
		super.handlePongMessage(session, message);
		this.logger.info(">>> handlePongMessage!");
	}
	
  	public  int sendMessageSession(String sendKey ,String message) {
  		int retVal= 0;
  		logger.info("############# sendMessageSession start #############");
  		logger.info("############# sendMessageSession  end  #############");
  
      		return retVal;
    	}
     
	public  void printSessionList() { 
		for( WebSocketSession keys : keyMap.keySet() ){
			this.logger.info( String.format(">> KEY     MAP SAID : %s, 값 : %s", keyMap.get(keys),keys) );
	}
	}
}