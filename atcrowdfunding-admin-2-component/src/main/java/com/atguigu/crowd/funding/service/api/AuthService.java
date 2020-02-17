package com.atguigu.crowd.funding.service.api;

import java.util.List;
import java.util.Map;

import com.atguigu.crowd.funding.entity.Auth;

public interface AuthService {

	List<Auth> getAllAuth();

	List<Integer> getAssignedAuthIdList(Integer roleId);

	void updateRelationShipBetweenRoleAndAuth(Map<String, List<Integer>> assignDataMap);

}
