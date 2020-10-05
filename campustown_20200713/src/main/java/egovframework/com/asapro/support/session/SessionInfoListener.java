package egovframework.com.asapro.support.session;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.com.utl.slm.EgovMultiLoginPreventor;

/**
 * 현제 접속자 통계를 위한 유틸
 * <Notice>
 * 	  session 정보를 이용
 * <Disclaimer>
 *		N/A
 *
 * @author yckim
 * @since 2019.06.04
 * @version 1.0
 * @see
 */

public class SessionInfoListener  implements HttpSessionListener  {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionInfoListener.class);
    
    static private int activeSessions;
    static private ConcurrentHashMap<String, HttpSession> sessionList = new ConcurrentHashMap<String, HttpSession>();
    
    public static int getActiveSessions() {
         return activeSessions;
     }

    public static ConcurrentHashMap<String, HttpSession> getsessionList() {
    	return sessionList;
    }
 
    @Override
     public void sessionCreated(HttpSessionEvent arg0) {
         activeSessions++;
         sessionList.put(arg0.getSession().getId(), arg0.getSession());
         //System.out.println("Created!! activeSessions : " + activeSessions);
         //System.out.println("현재 접속자 수 : " + sessionList.size());
     }
 
    @Override
     public void sessionDestroyed(HttpSessionEvent arg0) {
         activeSessions--;
         sessionList.remove(arg0.getSession().getId(), arg0.getSession());
         //System.out.println("Destoryed!! activeSessions : " + activeSessions);
         //System.out.println("현재 접속자 수 : " + sessionList.size());
     }

}
