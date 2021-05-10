package com.kh.mybatis.common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtils {
	
	/**
	 * mybatis계의 connection객체
	 * mybatis-config.xml 설정파일 기반으로 SqlSession객체를 생성반환
	 * build-path(target/classes)로 배포된 설정파일을 읽어들임
	 * 
	 * sqlsession을 상품으로 생각해보기
	 * 상품을 찍어내려면 공장이 필요
	 * 공장을 지으려면 공장짓는이(SqlSessionFactoryBuilder)로부터 공장(SqlSessionFactory)을 만들고, 
	 * 공장으로부터 sqlSession을 가져오는 구조
	 */
	// sqlSession을 리턴하는 메소드
	public static SqlSession getSqlSession() {
		SqlSession session = null;
		String resource = "/mybatis-config.xml";
		
		// 1. FactoryBuilder
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		
		// 2. factory생성 
		// 설정파일 읽어옴 -> 입력스트림 반환
		InputStream is = null;
		try {
			is = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 공장 만들기
		// builder야, mybatis-config.xml을 보고 공장을 지어줘
		SqlSessionFactory factory = builder.build(is);
		
		// 3. SqlSession 
		// 공장으로부터 sqlSession을 가져오기
		// openSession(false) - autoCommit:boolean
		session = factory.openSession(false);
		
		return session;
	}
}
