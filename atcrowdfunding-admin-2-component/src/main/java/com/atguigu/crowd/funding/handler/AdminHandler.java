package com.atguigu.crowd.funding.handler;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.service.api.AdminService;
import com.atguigu.crowd.funding.util.CrowdFundingConstant;
import com.github.pagehelper.PageInfo;

@Controller
public class AdminHandler {

	@Autowired
	private AdminService adminService;

	@RequestMapping("/admin/to/login/page")
	public String toLoginPage() {
		return "admin-login";
	}

	@RequestMapping("/admin/to/main/page")
	public String toMainPage() {
		return "admin-main";
	}

	@RequestMapping("/admin/to/add/page")
	public String toAddPage() {
		return "admin-add";
	}

	// 退出登录
	@RequestMapping("/admin/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/index.html";
	}

	// 管理员登录
	@RequestMapping("/admin/do/login")
	public String doLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd,
			Model model, HttpSession session) {

		// 调用adminService的login方法执行登录业务逻辑，返回查询到的Admin对象
		Admin admin = adminService.login(loginAcct, userPswd);

		// 判断admin是否为null
		if (admin == null) {

			model.addAttribute(CrowdFundingConstant.ATTR_NAME_MESSAGE, CrowdFundingConstant.MESSAGE_LOGIN_FAILED);

			return "admin-login";
		}

		session.setAttribute(CrowdFundingConstant.ATTR_NAME_LOGIN_ADMIN, admin);

		return "redirect:/admin/to/main/page.html";
		// return "admin-main";
	}

	@RequestMapping("/admin/get/all")
	public String getAll(Model model) {
		List<Admin> list = adminService.getAll();
		model.addAttribute("list", list);
		return "admin-target";
	}

	@RequestMapping("/admin/query/for/search")
	public String queryForSearch(

			// 如果页面上没有提供对应的请求参数，那么可以使用defaultValue指定默认值
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "keyword", defaultValue = "") String keyword, Model model) {

		PageInfo<Admin> pageInfo = adminService.queryForKeywordSearch(pageNum, pageSize, keyword);
		model.addAttribute(CrowdFundingConstant.ATTR_NAME_PAGE_INFO, pageInfo);
		return "admin-page";
	}

	/**
	 * 批量删除
	 * 
	 * @ResponseBody：将当前Handler方法的返回值作为响应体返回，不经过视图解析器
	 * @return
	 */
	@ResponseBody
	@RequestMapping("admin/batch/remove")
	private ResultEntity<String> batchRemove(@RequestBody List<Integer> adminIdList) {
		try {
			adminService.batchRemove(adminIdList);
			return ResultEntity.successWithoutData();
		} catch (Exception e) {
			return ResultEntity.failed(null, e.getMessage());
		}
	}

	/**
	 * 新增
	 */
	// 使用Admin实体类对象封装表单提交的请求参数，具体每一个请求参数会通过对应的setXxx()方法注入实体类
	@RequestMapping("/admin/save")
	public String saveAdmin(Admin admin) {

		try {
			adminService.saveAdmin(admin);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof DuplicateKeyException) {
				throw new RuntimeException(CrowdFundingConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}

		return "redirect:/admin/query/for/search.html?pageNum=" + Integer.MAX_VALUE;
	}

	/**
	 * 更新用户
	 */
	@RequestMapping("/admin/to/edit/page")
	public String toEditPage(@RequestParam("adminId") Integer adminId, Model model) {
		Admin admin = adminService.getAdminById(adminId);
		model.addAttribute("admin", admin);
		return "admin-edit";
	}

	/**
	 * 执行更新
	 */
	@RequestMapping("/admin/update")
	public String updateAdmin(Admin admin, @RequestParam("pageNum") String pageNum) {
		
		try {
			adminService.updateAdmin(admin);
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof DuplicateKeyException) {
				throw new RuntimeException(CrowdFundingConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}
		
		return "redirect:/admin/query/for/search.html?pageNum="+pageNum;
	}

}
