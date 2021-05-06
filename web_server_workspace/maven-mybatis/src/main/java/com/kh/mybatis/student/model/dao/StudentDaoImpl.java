package com.kh.mybatis.student.model.dao;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.student.model.vo.Student;

public class StudentDaoImpl implements StudentDao {

	@Override
	public int insertStudent(SqlSession session, Student student) {
		// statement : "namespace.queryTagId"
		return session.insert("student.insertStudent", student);
	}

}
