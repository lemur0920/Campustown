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

import egovframework.com.asapro.content_statis.service.ContentStatisService;
import egovframework.com.asapro.statistics.service.StatisticsService;
import egovframework.com.asapro.support.session.SessionInfoListener;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.cmm.service.EgovProperties;

/**
 * 
 * @author yckim
 * @since 2020. 4. 23.
 */
@Component
public class ContentStatisticsTask {
	private static final Logger LOGGER = LoggerFactory.getLogger(ContentStatisticsTask.class);
	private static final String SERVER_IP = EgovProperties.getProperty("schedule.cron.server.address");	//was 1
	private static final String DEV_SERVER_IP = EgovProperties.getProperty("schedule.cron.devserver.address");	//개발서버
	
	@Autowired
	private ContentStatisService contentStatisService;
	
	//@PostConstruct
	public void before(){
		LOGGER.info("[asapro] ContentStatisticsTask : before run task...");
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
	 * 콘텐츠별 (게시판, 아카이브등) 월별, 주별 통계데이터를 배치한다.
	 */
	@Scheduled(cron="0 30 0 * * *")
	public void contentStatisticsDailyBatch(){
		LOGGER.info("[asapro] ContentStatisticsTask contentStatisticsDailyBatch - ");
		
		String serverIp = AsaproUtils.getServerAddress();
		if(!serverIp.equals(SERVER_IP) && !serverIp.equals(DEV_SERVER_IP) ){
			LOGGER.info("[asapro] Content Statistics Batch - This Server is not scheduler server!!");
        	//배치를 was1에서만 돌 수있게 한다. 
        	return;
        }
		
		contentStatisService.csDailyBatch();
	}
	
	
	
}
