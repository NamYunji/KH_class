package com.kh.mybatis.common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtils {
	
	/**
	 * mybatis-config.xml 설정파일 기반으로 SqlSession객체를 생성반환
	 * build-path(target/classes)로 배포된 설정파일을 읽어들임
	 * 
	 * sqlsession을 상품으로 생각해보기.
	 * 상품을 찍어내려면 공장이 필요.
	 * 공장을 지으려면 공장짓는이로부터 공장을 만들고, 공장으로부터 sqlSession을 가져옴
	 */
	public static SqlSession getSqlSession() {
		SqlSession session = null;
		String resource = "/mybatis-config.xml";
		// 1. FactoryBuilder
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		
		// 2. factory생성 - 설정파일
		InputStream is = null;
		try {
			is = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SqlSessionFactory factory = builder.build(is);
		// 3. SqlSession - autoCommit:boolean
		session = factory.openSession(false);
		
		return session;
	}
}
