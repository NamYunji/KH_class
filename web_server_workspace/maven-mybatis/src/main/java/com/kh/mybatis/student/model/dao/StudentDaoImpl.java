package com.kh.mybatis.student.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.student.model.vo.Student;

public class StudentDaoImpl implements StudentDao {

	@Override
	// 인자로 vo 전달
	public int insertStudent(SqlSession session, Student student) {
		// insert(String statement, Object parameter)
		// statement : "namespace.queryTagId" 쿼리를 찾는 key값
		// mybatis-config에서 mapper파일을 등록해놨기 때문에, 
		// mapper파일에 가서 student라는 namespace에서 insertStudent라는 태그의 아이디값을 찾음
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

	@Override
	public int updateStudent(SqlSession session, Student student) {
		return session.update("student.updateStudent", student);
	}

	@Override
	public int deleteStudent(SqlSession session, int no) {
		return session.delete("student.deleteStudent", no);
	}
	/*
	 * 조회된 행이 없는 경우, 빈 ArrayList객체가 리턴된다.
	 */
	@Override
	public List<Student> selectStudentList(SqlSession session) {
		return session.selectList("student.selectStudentList");
	}

	@Override
	public List<Map<String, Object>> selectStudentMapList(SqlSession session) {
		return session.selectList("student.selectStudentMapList");
	}
}
