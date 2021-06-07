package com.kh.spring.common.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(Boolean.class) // java쪽
@MappedJdbcTypes(JdbcType.CHAR) // db쪽
public class BooleanYnTypeHandler extends BaseTypeHandler<Boolean> {

	// set : true를 y, false를 n으로 db에 세팅할 때 사용
	/**
	 * boolean ---> YN
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType)
			throws SQLException {
		// 파라미터로 넘어온 값에 따라 'Y', 'N'으로 바꿔라
		ps.setString(i, parameter ? "Y" : "N");
	}

	// get : db에 있는 y,n을 가져와서 boolean형으로 바꿀 때 사용
	/**
	 * YN ---> boolean 
	 */
	@Override
	public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// 넘어온 columnName이 'Y'라면 true, 'N'이라면 false
		// return rs.getString(columnName).equals("Y"); // null값이 넘어오면 npe 직빵
		return "Y".equals(rs.getString(columnName));
	}

	/**
	 * YN ---> boolean 
	 */
	@Override
	public Boolean getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return "Y".equals(rs.getString(columnIndex));
	}

	/**
	 * YN ---> boolean 
	 */
	@Override
	public Boolean getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return "Y".equals(cs.getString(columnIndex));
	}

}
