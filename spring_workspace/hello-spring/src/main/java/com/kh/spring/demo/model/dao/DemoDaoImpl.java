package com.kh.spring.demo.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.demo.model.vo.Dev;

// @Repository : db를 액세스하는 bean
@Repository // db를 액세스(dao) + bean 등록 (@component)
public class DemoDaoImpl implements DemoDao {

	// mybatis에서는 서비스로부터 session객체를 전달받았었음
	// but 지금은 session객체를 전달받지 않았음
	// -> session객체를 의존주입받기
	// 사용하려면 pom.xml에 태그들을 등록해둬야 함
	@Autowired
	private SqlSessionTemplate session;
	
	@Override
	public int insertDev(Dev dev) {
		return session.insert("demo.insertDev", dev);
	}

	@Override
	public List<Dev> selectDevList() {
		return session.selectList("demo.selectDevList");
	}

	@Override
	public Dev selectOneDev(int no) {
		return session.selectOne("demo.selectOneDev", no);
	}

	@Override
	public int updateDev(Dev dev) {
		return session.update("demo.updateDev", dev);
	}

	@Override
	public int deleteDev(int no) {
		return session.delete("demo.deleteDev", no);
	}
}