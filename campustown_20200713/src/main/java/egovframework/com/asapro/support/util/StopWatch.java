/**
 * 
 */
package egovframework.com.asapro.support.util;
/**
 * 
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 퍼포먼스 체크 유틸 - 라인과 라인 간의 연산시간을 계산해서 출력해주는 유틸 - 병목구간 측정시 사용한다.
 * <pre>
 * -사용법-
 * 체크시작지점에 egovframework.com.asapro.support.util.StopWatch.start(new Exception());
 * 중간체크지점에 egovframework.com.asapro.support.util.StopWatch.check(new Exception());
 * 중간체크지점에 egovframework.com.asapro.support.util.StopWatch.check(new Exception());
 * 중간체크지점에 egovframework.com.asapro.support.util.StopWatch.check(new Exception());
 * 체크종료지점에 egovframework.com.asapro.support.util.StopWatch.end(new Exception());
 * 패키지 포함해서 호출하는게 지울때 편함
 * -주의점-
 * static 변수를 사용해서 처리하기 때문에 여러 요청이 동시에 진행되면 필요한 값을 구할 수 없음.
 * <br/>가능하면 단독 요청을 처리하고 결과를 출력할 수 있는 환경에서 사용할것. 
 * 
 * - 2014.10.07 yckim 소스 업데이트
 * </pre>
 * @author yckim
 * @since 2012. 8. 3.
 */
public class StopWatch {

	private static boolean isRunning = false;
	public static List<Map<String, String>> checkTimeList = null;
	
	public synchronized static void start(Throwable throwable) {
		isRunning = true;
		check(throwable);
	}
	
	public synchronized static void end(Throwable throwable) {
		check(throwable);
		print();
		isRunning = false;
	}
	
	/**
	 * 체크할 지점에 StopWatch.check(new Exception());
	 * 
	 * @param throwable
	 */
	public synchronized static void check(Throwable throwable) {
		if( isRunning ){
			if (checkTimeList == null) {
				checkTimeList = new ArrayList<Map<String, String>>();
			}
			long checkTime = System.currentTimeMillis();
			Map<String, String> point = new HashMap<String, String>();
			point.put("where", getClassName(throwable.getStackTrace()[0].getClassName()) + "." + throwable.getStackTrace()[0].getMethodName() + "(line:" + throwable.getStackTrace()[0].getLineNumber()	+ ")");
			point.put("time", new Long(checkTime).toString());
			checkTimeList.add(point);
		} else {
			System.out.println("StopWatch : StopWatch was not started.");
		}
	}
	
	/**
	 * 모든 체크가 끝난 시점에서 콘솔에 프린트한다.
	 */
	private static void print() {
		int total = checkTimeList.size();
		Map<String, String> head = null;
		Map<String, String> tail = null;
		String headWhere = null;
		String tailWhere = null;
		long headTime = 0L;
		long tailTime = 0L;
		long period = 0L;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		for (int i = 0; i < total; i++) {
			if (i == 0) {
				head = checkTimeList.get(i);
				headWhere = head.get("where");
				headTime = Long.parseLong(head.get("time"));
				System.out.println("========================== STOPWATCH STARTED 	AT : [" + headWhere + "] : " + sdf.format(new Date(headTime)) + " ==========================");
			}
			if (i >= 0 && i < total - 1) {
				head = checkTimeList.get(i);
				tail = checkTimeList.get(i + 1);
				headWhere = head.get("where");
				tailWhere = tail.get("where");
				headTime = Long.parseLong(head.get("time"));
				tailTime = Long.parseLong(tail.get("time"));
				period = tailTime - headTime;
				System.out.println("FROM [" + headWhere + "]		TO [" + tailWhere + "]		TOOK [" + period + "] MILLISECONDS");
			} else {
				head = checkTimeList.get(i);
				headWhere = head.get("where");
				headTime = Long.parseLong(head.get("time"));
				System.out.println("========================== STOPWATCH FINISHED 	AT : [" + headWhere + "] : " + sdf.format(new Date(headTime)) + " ==========================");
			}
		}
		checkTimeList = null;
	}

	private static String getClassName(String fullClassName) {
		String[] data = fullClassName.split("\\.");
		return data[data.length - 1];
	}
}
