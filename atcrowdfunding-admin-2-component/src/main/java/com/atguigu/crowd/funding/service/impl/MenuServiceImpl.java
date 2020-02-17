package com.atguigu.crowd.funding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crowd.funding.entity.Menu;
import com.atguigu.crowd.funding.entity.MenuExample;
import com.atguigu.crowd.funding.mapper.MenuMapper;
import com.atguigu.crowd.funding.service.api.MenuService;

@Service
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	private MenuMapper menuMapper;

	public List<Menu> getAll() {
		
		return menuMapper.selectByExample(new MenuExample());
	}

	public void saveMenu(Menu menu) {
		menuMapper.insert(menu);
		
	}

	public Menu getMenuById(Integer menuId) {
		
		return menuMapper.selectByPrimaryKey(menuId);
	}

	public void updateMenu(Menu menu) {
		menuMapper.updateByPrimaryKey(menu);
		
	}

	public void removeMenu(Integer menuId) {
		menuMapper.deleteByPrimaryKey(menuId);
		
	}

}
