package com.kh.spring.common.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * String[] ---------> varchar2
 * 			setString
 * 			getString * 3 (resultSet처리를 위함)
 */
//어떤 타입을 위한 typeHandler인지 명시
@MappedTypes(String[].class) // java타입 - String 배열
@MappedJdbcTypes(JdbcType.VARCHAR) // jdbc타입 - varchar
public class StringArrayTypeHandler extends BaseTypeHandler<String[]> {

	/**
	 * db에 데이터를 추가할 때 사용
	 * 쿼리에 값세팅을 어떻게 해줄지에 대한 설정 
	 */
	/**
	 * String[] ---> varchar2
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType)
			throws SQLException {
		// String.join("구분자", String배열) - 배열을 지정한 구분자로 합쳐줌
		// String[] -> 요소별로 쪼개서 콤마로 연결시킨 문자열 리턴
		// i : 물음표 채워넣을 때의 인덱스
		ps.setString(i, String.join(", ", parameter));
	}

	/**
	 * 세개의 게터
	 * : resultSet을 받아와서 vo에 세팅할때 필요
	 */
	/**
	 * String[] <--- varchar2
	 */
	@Override
	public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// column name으로 resultSet을 조회해서
		String str = rs.getString(columnName);
		// null이 아니라면 콤마로 잘라줌
		return str != null ? str.split(", ") : null;
	}
	
	/**
	 * String[] <--- varchar2
	 */
	@Override
	public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// column index로 resultSet을 조회해서
		String str = rs.getString(columnIndex);
		// null이 아니라면 콤마로 잘라줌
		return str != null ? str.split(", ") : null;
	}
	
	/**
	 * String[] <--- varchar2
	 */
	@Override
	public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// column index로 resultSet을 조회해서
		String str = cs.getString(columnIndex);
		// null이 아니라면 콤마로 잘라줌
		return str != null ? str.split(", ") : null;
	}
}
