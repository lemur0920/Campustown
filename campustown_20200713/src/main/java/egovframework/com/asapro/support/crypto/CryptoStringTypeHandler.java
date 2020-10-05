/**
 * 
 */
package egovframework.com.asapro.support.crypto;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.com.asapro.support.ApplicationContextProvider;

/**
 * 암호화 대상인 필드를 암호화/복호화 처리하는 Mybatis String TypeHandler.
 * <pre>
 * !!! 암호화 대상인 필드는 무작위가 아니라 몇몇 필드가 정해져 있으므로 
 * sql-map-config.xml 에서 타입핸들러를 적용하거나
 * context-sqlMap.xml 에서 sqlSession 의 typeHandlers 프로퍼티를 통해서 
 * 타입핸들러를 적용하면 모든 String 필드에 일괄 적용되어 버림.
 * 그런 오류를 막기 위해서 일부러 @MappedTypes, @MappedJdbcTypes 어노테이션을 이용해서
 * 필요한 정보를 설정해주고, 실제 쿼리 문에서는 
 * insert, update, delete인 경우는 inline parameterMap 을 이용하고 
 * select 인 경우는 resultMap 을 이용해서 해당필드에만 암/복호화를 적용시킬 수 있도록 함.
 * 1) prameterMap 엘리먼트는 Mybatis api에서 deprecated되었으니 사용하지 말고 inline parameterMap을 사용.
 * 2) sql-map-config.xml에서 TypeAlias를 설정 해놓고 굳이 동일한 resultMap을 쓰는 이유는
 * 타입핸들러가 resultType을 사용할때는 적용 되지 않기때문(버근인지, 원래 그런건지 알 수 없다-_-;;;)
 * -resultMap 에서 받을때 복호화 되어야 하는 필드는 직접 typeHandler를 지정해준다.
 * -무슨 말인지 모르겠으면 회원쪽 sql xml참고
 * </pre>
 * @author cwsong
 * 
 */
@MappedTypes(value={String.class})
@MappedJdbcTypes(value={JdbcType.VARCHAR, JdbcType.CLOB})
public class CryptoStringTypeHandler extends BaseTypeHandler<String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CryptoStringTypeHandler.class);
	
	//복호화
	@Override
	public String getResult(ResultSet resultSet, String columnLabel) throws SQLException {
		if( !GeneralCryptoWorker.USE_DB_ENCRYPTION ){
			return resultSet.getString(columnLabel);
		}
		LOGGER.trace("[ASAPRO] decrypt executed");
		CryptoWorker worker = ApplicationContextProvider.getApplicationContext().getBean(CryptoWorker.class);
		return worker.decrypt(resultSet.getString(columnLabel));
	}

	//복호화
	@Override
	public String getResult(ResultSet resultSet, int columnIndex) throws SQLException {
		if( !GeneralCryptoWorker.USE_DB_ENCRYPTION ){
			return resultSet.getString(columnIndex);
		}
		LOGGER.trace("[ASAPRO] decrypt executed");
		CryptoWorker worker = ApplicationContextProvider.getApplicationContext().getBean(CryptoWorker.class);
		return worker.decrypt(resultSet.getString(columnIndex));
	}

	//복호화
	@Override
	public String getResult(CallableStatement callableStatement, int parameterIndex) throws SQLException {
		if( !GeneralCryptoWorker.USE_DB_ENCRYPTION ){
			return callableStatement.getString(parameterIndex);
		}
		LOGGER.trace("[ASAPRO] decrypt executed");
		CryptoWorker worker = ApplicationContextProvider.getApplicationContext().getBean(CryptoWorker.class);
		return worker.decrypt(callableStatement.getString(parameterIndex));
	}

	//암호화
	@Override
	public void setParameter(PreparedStatement preparedStatement, int parameterIndex, String parameterStr, JdbcType jdbcType) throws SQLException {
		if( !GeneralCryptoWorker.USE_DB_ENCRYPTION ){
			preparedStatement.setString(parameterIndex, parameterStr);
		} else {
			LOGGER.trace("[ASAPRO] encrypt executed");
			CryptoWorker worker = ApplicationContextProvider.getApplicationContext().getBean(CryptoWorker.class);
			
			String parameter = worker.encrypt(parameterStr);
			preparedStatement.setString(parameterIndex, parameter);
		}
	}
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
		//현재로선 처리할게 없음. 그냥 PMD용 주석
	}

	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return null;
	}

	@Override
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return null;
	}

}
