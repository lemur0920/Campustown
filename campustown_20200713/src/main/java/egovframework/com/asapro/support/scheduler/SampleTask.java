/**
 * 
 */
package egovframework.com.asapro.support.scheduler;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 스케쥴러 등록시 SampleTask 카피해서 만들어 쓰도록... 어노테이션 주석풀고 사용
 * ref : https://blog.outsider.ne.kr/1066
 * @author yckim
 * @since 2019. 4. 25.
 */
@Component
public class SampleTask {
	
	//@PostConstruct
	public void before(){
		System.out.println("[asapro] SampleTask : before run task...");
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
	//@Scheduled(cron="*/10 * * * * *")
	public void task1(){
		System.out.println("[asapro] SampleTask : task1 - cron");
	}
	
	/**
	 * 이전에 실행된 Task의 종료 시간으로부터의 fixed-delay로 정의한 시간만큼 소비한 이후 Task 실행
	 */
	//@Scheduled(fixedDelay=1000)
	public void task2(){
		System.out.println("[asapro] SampleTask : task2 - fixedDelay 1000ms");
	}
	
	/**
	 * 이전에 실행된 Task의 시작 시간으로부터 fixed-rate로 정의한 시간만큼 소비한 이후 Task 실행
	 */
	//@Scheduled(fixedRate=1000 * 5)
	public void task3(){
		System.out.println("[asapro] SampleTask : task3 - fixedRate 5000ms");
	}
	
}
