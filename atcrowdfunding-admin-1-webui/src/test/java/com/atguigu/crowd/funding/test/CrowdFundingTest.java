package com.atguigu.crowd.funding.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.mapper.RoleMapper;
import com.atguigu.crowd.funding.service.api.AdminService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml" })
public class CrowdFundingTest {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private AdminService adminService;

	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private RoleMapper roleMapper;

	@Test
	public void testConnection() throws SQLException {
		Connection connection = dataSource.getConnection();

		System.out.println(connection);
	}

	@Test
	public void testMybatis() {
		List<Admin> adminList = adminService.getAll();
		for (Admin admin : adminList) {
			System.err.println(admin);
		}
	}

	@Test
	public void testTx() {
		adminService.updataAdmin();
	}

	// 插入测试用的假数据
	@Test
	public void batchSaveAdmin() {
		for (int i = 0; i < 50; i++) {
			adminMapper
					.insert(new Admin(null, "loginAcct" + i, "1111111", "userName" + i, "email" + i + "@qq.com", null));
		}
	}

	// 插入测试用的假数据
	@Test
	public void testSaveAdmin() {
		for (int i = 0; i < 30; i++) {
			roleMapper.insert(new Role(null,"role"+i));
		}
	}

	@Test
	public void testAdminMapperSearch() {
		String keyword = "0";
		List<Admin> list = adminMapper.selectAdminListByKeyword(keyword);
		for (Admin admin : list) {
			System.err.println(admin);
		}
	}
}
