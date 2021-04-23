package com.kh.test.model;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;

public class TestService {
	private TestDao testDao = new TestDao();

	public List<Test> selectList() {
		Connection conn = getConnection();
		List<Test> list = testDao.selectList(conn);
		close(conn);
		return list;
	}
}
