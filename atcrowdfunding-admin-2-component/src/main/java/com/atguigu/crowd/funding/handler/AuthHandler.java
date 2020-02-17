package com.atguigu.crowd.funding.handler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crowd.funding.entity.Auth;
import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.service.api.AuthService;

@Controller
public class AuthHandler {
	
	@Autowired
	private AuthService authService;
	
	@ResponseBody
	@RequestMapping("/assign/get/all/auth")
	public ResultEntity<List<Auth>> getAllAuth() {
		
		List<Auth> authList = authService.getAllAuth();
		
		return ResultEntity.successWithData(authList);
	}
	
	@ResponseBody
	@RequestMapping("/assign/get/assigned/auth/id/list")
	public ResultEntity<List<Integer>> getAssignedAuthIdList(@RequestParam("roleId") Integer roleId) {
		
		List<Integer> authIdList = authService.getAssignedAuthIdList(roleId);
		
		return ResultEntity.successWithData(authIdList);
	}
	
	@ResponseBody
	@RequestMapping("/assign/do/assign")
	public ResultEntity<String> doRoleAssignAuth(
			@RequestBody Map<String, List<Integer>> assignDataMap) {
		
		authService.updateRelationShipBetweenRoleAndAuth(assignDataMap);
		
		return ResultEntity.successWithoutData();
	}
}
