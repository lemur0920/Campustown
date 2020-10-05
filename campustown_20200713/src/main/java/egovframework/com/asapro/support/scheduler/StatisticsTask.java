/**
 * 
 */
package egovframework.com.asapro.support.scheduler;

import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import egovframework.com.asapro.statistics.service.StatisticsService;
import egovframework.com.asapro.support.session.SessionInfoListener;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.cmm.service.EgovProperties;

/**
 * ref : https://blog.outsider.ne.kr/1066
 * @author yckim
 * @since 2019. 6. 14.
 */
@Component
public class StatisticsTask {
	private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsTask.class);
	private static final String SERVER_IP = EgovProperties.getProperty("schedule.cron.server.address");	//was 2
	private static final String DEV_SERVER_IP = EgovProperties.getProperty("schedule.cron.devserver.address");	//개발서버
	
	@Autowired
	private StatisticsService statisticsService;
	
	//@PostConstruct
	public void before(){
		LOGGER.info("[asapro] StatisticsSessionResetTask : before run task...");
	}
	
	/**
	 * Cron Expression을 이용하여 Task 실행 주기 정의.
	 * <pre>
	 * 0 0 ＊ ＊ ＊ ＊ : 매일 매시 시작 시점
	 * ＊/10 ＊ ＊ ＊ ＊ ＊ : 10초 간격
	 * 0 0 8-10 ＊ ＊ ＊ : 매일 8,9,10시
	 * 0 0/30 8-10 * * * : 매일 8:00, 8:30, 9:00, 9:30, 10:00
	 * 0 0 9-17 ＊ ＊ MON-FRI : 주중 9시부터 17시까지
	 * 0 0 0 25 12 ? : 매년 크리스마스 자정
	 * </pre>
	 */
	
	/**
	 * 접속통계를 위한 세션의 체크값 일괄 삭제 - 00시 1분에 접속 카운트 다시 시작위해.
	 */
	@Scheduled(cron="0 1 0 * * *")
	public void statisticsSessionReset(){
		LOGGER.info("[asapro] StatisticsTask statisticsSessionReset - session count : {}",SessionInfoListener.getActiveSessions());
		//System.out.println(SessionInfoListener.getActiveSessions());
		ConcurrentHashMap<String, HttpSession> sessionList = SessionInfoListener.getsessionList();
		if(sessionList != null && !sessionList.isEmpty() ){
			Enumeration<String> ids = sessionList.keys();
			while (ids.hasMoreElements()) {
				String id = (String) ids.nextElement();
				//System.out.println(sessionList.get(id).getId());

				@SuppressWarnings("rawtypes")
				Enumeration enums = sessionList.get(id).getAttributeNames();
				while( enums.hasMoreElements() ){
					String key = (String) enums.nextElement();
					if( key.startsWith("siteVisit_") ){
						//System.out.println("key: " + key);
						sessionList.get(id).removeAttribute(key);
					}
				}
				
			}
		}
		//System.out.println("[asapro] StatisticsTask : StatisticsSessionReset - cron");
	}
	
	/**
	 * 일접속 기본데이터를 일별, OS별, 브라우저별, 국가별로 통계데이터를 추출한다.
	 */
	@Scheduled(cron="0 0 4 * * *")
	public void statisticsDailyBatch(){
		LOGGER.info("[asapro] StatisticsTask statisticsDailyBatch - ");
		
		String serverIp = AsaproUtils.getServerAddress();
		if(!serverIp.equals(SERVER_IP) && !serverIp.equals(DEV_SERVER_IP) ){
			LOGGER.info("[asapro] OpenApiBatch - This Server is not scheduler server!!");
        	//배치를 was2에서만 돌 수있게 한다. 
        	return;
        }
		
		statisticsService.statisticsDailyBatch();
	}
	
	
	
}
