package com.kh.mybatis.student.model.service;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.common.MybatisUtils;
import com.kh.mybatis.student.model.dao.StudentDao;
import com.kh.mybatis.student.model.dao.StudentDaoImpl;
import com.kh.mybatis.student.model.vo.Student;
import static com.kh.mybatis.common.MybatisUtils.getSqlSession;

public class StudentServiceImpl implements StudentService {

	private StudentDao studentDao = new StudentDaoImpl();

	@Override
	public int insertStudent(Student student) {
		// 1. SqlSession 생성 (Connection이 아님)
		SqlSession session = getSqlSession();
		int result = 0;
		try {
			// 2. dao 업무위임
			result = studentDao.insertStudent(session, student);
			// 3. transaction 처리 : SqlSession에 대해서 commit|rollback
			session.commit();
		} catch(Exception e) {
			session.rollback();
			throw e;
		} finally {
			// 4. SqlSession 자원반납
			session.close();
		}
		return result;
	}
}
