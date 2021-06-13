package com.kh.spring.menu.model.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.kh.spring.menu.model.vo.MenuType;

@MappedTypes(MenuType.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MenuTypeHandler extends BaseTypeHandler<MenuType> {

	// 세터 (자바 KR -> DB kr)
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, MenuType parameter, JdbcType jdbcType)
			throws SQLException {
		// i번지의 데이터에 대해 parameter.getValue()
		// parameter로 넘어온 것의 데이터를 꺼내서 db에 사용
		ps.setString(i, parameter.getValue()); // kr
	}

	// 게터 (DB kr -> 자바 KR)
	@Override
	public MenuType getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// rSet에서 getString - db의 결과집합에서 데이터를 가져와서
		// enum클래스의 세터로 변환
		return MenuType.menuTypevalueOf(rs.getString(columnName)); // KR
	}

	@Override
	public MenuType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return MenuType.menuTypevalueOf(rs.getString(columnIndex)); // KR
	}

	@Override
	public MenuType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return MenuType.menuTypevalueOf(cs.getString(columnIndex)); // KR
	}
}
