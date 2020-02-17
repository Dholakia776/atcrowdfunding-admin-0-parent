package com.atguigu.crowd.funding.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.service.api.RoleService;

@Controller
public class AssignHandler {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("assign/to/assign/role/page")
	public String toAssignRolePage(@RequestParam("adminId") Integer adminId, Model model) {
		
		// 1.查询已分配角色
		List<Role> assignedRoleList = roleService.getAssignedRoleList(adminId);
		
		// 2.查询未分配角色
		List<Role> unAssignedRoleList = roleService.getUnAssignedRoleList(adminId);
		
		// 3.存入模型
		model.addAttribute("assignedRoleList", assignedRoleList);
		model.addAttribute("unAssignedRoleList", unAssignedRoleList);
		
		return "assign-role";
	}
	
	@RequestMapping("/assign/role")
	public String doAssignRole(
			// roleIdList不一定每一次都能够提供，没有提供我们也接受
			@RequestParam(value="roleIdList", required=false) List<Integer> roleIdList, 
			@RequestParam("adminId") Integer adminId,
			@RequestParam("pageNum") String pageNum) {
		
		roleService.updateRelationship(adminId, roleIdList);
		
		return "redirect:/admin/query/for/search.html?pageNum="+pageNum;
	}
	
	
	

}
