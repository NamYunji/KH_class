package com.kh.mybatis.student.model.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.student.model.vo.Student;

public class StudentDaoImpl implements StudentDao {

	@Override
	// 인자로 vo 전달
	public int insertStudent(SqlSession session, Student student) {
		// statement : "namespace.queryTagId"
		return session.insert("student.insertStudent", student);
	}

	@Override
	// 인자로 map 전달
	public int insertStudentMap(SqlSession session, Map<String, Object> student) {
		return session.insert("student.insertStudentMap", student);
	}

	@Override
	public int selectStudentCount(SqlSession session) {
		// 단순 조회 -> 두번째 인자는 없음
		// 한 행을 리턴하는데 그 결과값이 int
		return session.selectOne("student.selectStudentCount");
	}
	
	@Override
	public Student selectOneStudent(SqlSession session, int no) {
		return session.selectOne("student.selectOneStudent", no);
	}

	/**
	 * Map<String, Object>
	 * - 컬럼명 : String
	 * - 컬럼값 : Object
	 */
	@Override
	public Map<String, Object> selectOneStudentMap(SqlSession session, int no) {
		return session.selectOne("student.selectOneStudentMap", no);
	}


}
