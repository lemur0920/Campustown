/**
 * 
 */
package egovframework.com.asapro.support.jdbc;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MapHandler, MapListHandler로 쿼리결과를 받을때 문제가 생기는 부분에 대해 처리로직을 추가한 로우핸들러
 * <br/>
 * MapHandler, MapListHandler 에서 사용
 * <pre>
 * - Date 형 컬럼 받아올때 시간 누락되는 부분 보완
 * </pre>
 * 
 * @author yckim
 * 
 */
public class ImprovedMapRowProcessor extends BasicRowProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImprovedMapRowProcessor.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.dbutils.BasicRowProcessor#toMap(java.sql.ResultSet)
	 */
	@Override
	public Map<String, Object> toMap(ResultSet rs) throws SQLException {
		// return super.toMap(rs);
		@SuppressWarnings("unchecked")
		Map<String, Object> result = new CaseInsensitiveMap();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			for (int i = 1; i <= cols; i++) {
				String columnName = rsmd.getColumnLabel(i);
				if ((null == columnName) || (0 == columnName.length())) {
					columnName = rsmd.getColumnName(i);
				}
				// date 시간까지 포함하도록
				if (rs.getObject(i) != null && ("java.sql.Timestamp".equals(rsmd.getColumnClassName(i)) || rsmd.getColumnType(i) == 91 || "DATE".equals(rsmd.getColumnTypeName(i)))) {
					Timestamp timestamp = (Timestamp) rs.getTimestamp(i);
					result.put(columnName, new Date(timestamp.getTime()));
				} else if(rs.getObject(i) != null && ( "oracle.sql.CLOB".equals(rsmd.getColumnClassName(i))) ){
					Clob clob = (Clob) rs.getObject(i);
					long size = clob.length();
					result.put(columnName, clob.getSubString(1, (int)size));
				} else {
					result.put(columnName, rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			LOGGER.error("[SQLException] Try/Catch... : "+ e.getMessage());
		}
		return result;
	}

}

