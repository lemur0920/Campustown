/**
 * 
 */
package egovframework.com.asapro.support.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.com.asapro.support.ApplicationContextProvider;

/**
 * apache commons DbUtils 를 간편화 시킨 DB유틸
 * <pre>
 * - DbUtils 참고 : https://commons.apache.org/proper/commons-dbutils/examples.html
 * - jsp 에서 간편하게 사용할 용도로 만들었음.
 * - prepared statement 는 각 파라메터를 쿼리문에 ? 로 할당하면 됨
 * - bean 또는 bean list를 쿼리할 때 디비컬럼이 AA_BB 형태이면 자동으로 빈의 aaBb 에 입력됨
 * - 컬럼은 social_sec 인데 멤버변수명은 socialSecurityNumber 인 경우는 select social_sec# as socialSecurityNumber from person 처럼 쿼리하면 자동으로 populate됨.
 * - 사용예 1) int count = AsaproDBUtils.selectScalar("SELECT COUNT(*) FROM ASA_ADMIN_MEMBER");
 * - 사용예 2) List<Banner> bannerList = AsaproDBUtils.selectBeanList("SELECT * FROM ASA_BANNER WHERE BN_TYPE = ?", Banner.class, "popupzone"); 
 * - 사용예 3) List<Banner> bannerList = AsaproDBUtils.selectBeanList("SELECT * FROM ASA_BANNER WHERE BN_TYPE = ?", Banner.class, Object[]{"popupzone"}); 
 * </pre>
 * @author cwsong
 * @since 2015.07.08
 * @since 2018.08.06
 */
public class AsaproDBUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AsaproDBUtils.class);
	private static final QueryRunner QUERY_RUNNER = new QueryRunner();
	//컬럼이름에 _(underscore) 가 포함되어 있어도 무시하고 처리하는데 필요한 bean프로세서
	private static final BasicRowProcessor GENEROUS_BEAN_ROW_PROCESSOR = new BasicRowProcessor(new GenerousBeanProcessor());
	private static final String DATASOURCE_ID = "dataSource";
	
	/**
	 * 쿼리 수행 후 맵 리스트를 반환한다.
	 * <pre>
	 * - 파라메터를 prepared statement로 처리
	 * <pre>
	 * @param sql
	 * @return 맵 리스트
	 */
	public static List<Map<String, Object>> selectMapList( String sql, Object... params ){
		DataSource ds = (DataSource)ApplicationContextProvider.getApplicationContext().getBean(DATASOURCE_ID);
		Connection conn = null;  
		List<Map<String, Object>> result = null;
		try {
			conn = ds.getConnection();
			result = QUERY_RUNNER.query(conn, sql, new MapListHandler(new ImprovedMapRowProcessor()), params);
		} catch (SQLException e) {
			LOGGER.error("[SQLException] Try/Catch... : "+ e.getMessage());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return result;
	}
	
	/**
	 * 쿼리 수행 후 맵 리스트를 반환한다.
	 * @param sql
	 * @return 맵 리스트
	 */
	public static List<Map<String, Object>> selectMapList( String sql ){
		return selectMapList(sql, null);
	}
	
	/**
	 * 쿼리 수행 후 맵을 반환한다.
	 * @param sql
	 * @return 맵
	 */
	public static Map<String, Object> selectMap( String sql, Object... params ){
		DataSource ds = (DataSource)ApplicationContextProvider.getApplicationContext().getBean(DATASOURCE_ID);
		Connection conn = null;  
		Map<String, Object> result = null;
		try {
			conn = ds.getConnection();
			result = QUERY_RUNNER.query(conn, sql, new MapHandler(new ImprovedMapRowProcessor()), params);
		} catch (SQLException e) {
			LOGGER.error("[SQLException] Try/Catch... : "+ e.getMessage());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return result;
	}
	
	/**
	 * 쿼리 수행 후 맵을 반환한다.
	 * @param sql
	 * @return 맵
	 */
	public static Map<String, Object> selectMap( String sql ){
		return selectMap(sql, null);
	}
	
	/**
	 * bean 리스트를 반환한다.
	 * @param sql 쿼리
	 * @param params 파라메터
	 * @param type 반환타입
	 * @return bean 리스트
	 */
	public static <T> List<T> selectBeanList( String sql, Class<T> type, Object... params ){
		DataSource ds = (DataSource)ApplicationContextProvider.getApplicationContext().getBean(DATASOURCE_ID);
		Connection conn = null;  
		List<T> result = null;
		try {
			conn = ds.getConnection();
			result = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(type , GENEROUS_BEAN_ROW_PROCESSOR), params);
		} catch (SQLException e) {
			LOGGER.error("[SQLException] Try/Catch... : "+ e.getMessage());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return result;
	}
	
	/**
	 * bean 리스트를 반환한다.
	 * @param sql 쿼리
	 * @param params 파라메터
	 * @param type 반환타입
	 * @return bean 리스트
	 */
	public static <T> List<T> selectBeanList( String sql, Class<T> type ){
		return selectBeanList(sql, type, null);
	}
	
	/**
	 * bean을 반환한다.
	 * @param sql
	 * @param params
	 * @param type
	 * @return bean
	 */
	public static <T> T selectBean( String sql, Class<T> type, Object... params ){
		DataSource ds = (DataSource)ApplicationContextProvider.getApplicationContext().getBean(DATASOURCE_ID);
		Connection conn = null;  
		T result = null;
		try {
			conn = ds.getConnection();
			result = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(type , GENEROUS_BEAN_ROW_PROCESSOR), params);
		} catch (SQLException e) {
			LOGGER.error("[SQLException] Try/Catch... : "+ e.getMessage());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return result;
	}
	
	/**
	 * bean을 반환한다.
	 * @param sql
	 * @param type
	 * @return bean
	 */
	public static <T> T selectBean( String sql, Class<T> type ){
		return selectBean(sql, type, null);
	}
	
	/**
	 * 숫자결과를 조회하는 쿼리를 수행한다.
	 * @param sql
	 * @return
	 */
	public static int selectScalar( String sql, Object... params ){
		DataSource ds = (DataSource)ApplicationContextProvider.getApplicationContext().getBean(DATASOURCE_ID);
		Object result = null;
		Connection conn = null;  
		try {
			conn = ds.getConnection();
			result = QUERY_RUNNER.query(conn, sql, new ScalarHandler<Number>(), params);
		} catch (SQLException e) {
			LOGGER.error("[SQLException] Try/Catch... : "+ e.getMessage());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return getIntFromLongOrBigDecimal(result);
	}
	
	/**
	 * UPDATE, DELETE 쿼리를 수행한다.
	 * @param sql
	 * @return 수행 결과
	 */
	public static int execute( String sql, Object... params ){
		DataSource ds = (DataSource)ApplicationContextProvider.getApplicationContext().getBean(DATASOURCE_ID);
		int updated = 0;
		Connection conn = null;  
		try {
			conn = ds.getConnection();
			updated = QUERY_RUNNER.update(conn, sql, params);
		} catch (SQLException e) {
			LOGGER.error("[SQLException] Try/Catch... : "+ e.getMessage());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return updated;
	}
	
	/**
	 * mysql, oracle 에서 map 으로 받는 경우 number형 데이터의 자바타입이 다른것에 대한 처리
	 * @param object
	 * @return
	 */
	public static Integer getIntFromLongOrBigDecimal(Object object) {
		if( object == null ){
			LOGGER.info("[asapro] AsaproDBUtils - object is null. returns 0.");
			return 0;
		}
		//oracle
		if( "java.math.BigDecimal".equals(object.getClass().getName()) ){
			return ((BigDecimal)object).intValue();
		}
		//mysql
		else if( "java.lang.Long".equals(object.getClass().getName()) ){
			return ((Long)object).intValue();
		}
		//mysql
		else if( "java.lang.Integer".equals(object.getClass().getName()) ){
			return (Integer)object;
		}
		//mysql
		else if( "java.lang.Double".equals(object.getClass().getName()) ){
			return ((Double)object).intValue();
		}
		//아니면? 쓰지마
		else {
			LOGGER.warn("[asapro] AsaproDBUtils - casting object to int failed. - {}", object.getClass().getName());
			return 0;
		}
	}
	
	/**
	 * Object 를 String으로 캐스팅 - 없으면 defaultString 반환
	 * @param object
	 * @param defaultString
	 * @return
	 */
	public static String getString(Object object, String defaultString ){
		if( object == null ){
			return defaultString;
		} else {
			String temp = (String)object;
			if( StringUtils.isBlank(temp) ){
				return defaultString;
			} else {
				return temp.trim();
			}
		}
	}
	
	/**
	 * Object 를 String으로 캐스팅 없으면 null반환
	 * @param object
	 * @return
	 */
	public static String getString(Object object){
		return getString(object, null);
	}
}
