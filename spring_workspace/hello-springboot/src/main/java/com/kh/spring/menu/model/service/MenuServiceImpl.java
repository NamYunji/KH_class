package com.kh.spring.menu.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.menu.model.dao.MenuDao;
import com.kh.spring.menu.model.vo.Menu;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Override
	public List<Menu> selectMenulList() {
		return menuDao.selectMenuList();
	}

	@Override
	public List<Menu> selectMenuListByTypeAndTaste(Map<String, Object> param) {
		return menuDao.selectMenuListByTypeAndTaste(param);
	}

	@Override
	public int insertMenu(Menu menu) {
		// TODO Auto-generated method stub
		return 0;
	}
}