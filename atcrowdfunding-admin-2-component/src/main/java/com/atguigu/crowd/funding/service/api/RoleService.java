package com.atguigu.crowd.funding.service.api;

import java.util.List;

import com.atguigu.crowd.funding.entity.Role;
import com.github.pagehelper.PageInfo;

public interface RoleService {


	PageInfo<Role> queryForKeywordWithPage(Integer pageNum, Integer pageSize, String keyword);

	List<Role> getRoleListByIdList(List<Integer> roleIdList);

	void batchRemove(List<Integer> roleIdList);

	void saveRole(String roleName);

	void updateRole(Role role);

	List<Role> getAssignedRoleList(Integer adminId);

	List<Role> getUnAssignedRoleList(Integer adminId);

	void updateRelationship(Integer adminId, List<Integer> roleIdList);

	


}
