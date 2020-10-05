/**
 * 
 */
package egovframework.com.asapro.support.scheduler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import egovframework.com.asapro.allowip.service.AllowIpService;
import egovframework.com.asapro.openapiinfo.service.OpenApiInfo;
import egovframework.com.asapro.openapiinfo.service.OpenApiInfoService;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.cmm.service.EgovProperties;

/**
 * 임시허용 IP  스케줄러
 * @author yckim
 * @since 2019. 5. 23.
 * 
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
@Component
public class AllowIpTempTask {
	private static final Logger LOGGER = LoggerFactory.getLogger(AllowIpTempTask.class);
	private static final String SERVER_IP = EgovProperties.getProperty("schedule.cron.server.address");	//was 2
	private static final String DEV_SERVER_IP = EgovProperties.getProperty("schedule.cron.devserver.address");	//개발서버
	
	@Autowired
	private AllowIpService allowIpService;
	
	//@PostConstruct
	public void before(){
		LOGGER.info("[asapro] AllowIpTempTask : before run task...");
	}

	/**
	 * 임시허용 IP 정보를 모두 삭제한다.
	 */
	@Scheduled(cron="0 0 3 * * *")
	public void deleteAllowIpTempAll() {
		String serverIp = AsaproUtils.getServerAddress();
		if(!serverIp.equals(SERVER_IP) && !serverIp.equals(DEV_SERVER_IP) ){
			LOGGER.info("[asapro] AllowIpTempTask - This Server is not scheduler server!!");
        	//배치를 was2에서만 돌 수있게 한다. 
        	return;
        }
		
		int deleted = allowIpService.deleteAllowIpTempAll();
		
		if(deleted > 0){
			LOGGER.info("[asapro] AllowIpTempTask deleted count {} : " , deleted);
		} else {
			LOGGER.info("[asapro] AllowIpTempTask - 삭제하지 못하였습니다.[데이터가 없거나 삭제 실패]");
		}
		
	}
	
	
}
