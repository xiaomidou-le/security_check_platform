package com.hjcrm.system.service;

import java.util.List;

import com.hjcrm.publics.util.PageBean;
import com.hjcrm.system.entity.Role;
import com.hjcrm.system.entity.Role_menu;

/**
 * 角色接口
 * @author 
 * @date 2020-9-18 下午4:07:29
 */
public interface IRoleService {
	
	/**
	 * 保存或者修改角色
	 * @param role
	 * @author  
	 * @date 2020-9-18 下午5:08:49
	 */
	public void saveOrUpdate(Role role);
	
	/**
	 * 保存角色-菜单对应关系
	 * @param roleMenu
	 * @author  
	 * @date 2020-9-20 下午1:40:50
	 */
	public void saveRoleMenu(Role_menu roleMenu);
	
	
	/**
	 * 删除角色
	 * @param ids
	 * @author  
	 * @date 2020-9-18 下午5:16:58
	 */
	public void delete(String ids);
	
	/**
	 * 根据部门ID，查询系统中所有的权限
	 * @param 
	 * @return
	 * @author  
	 * @date 2020-9-20 上午9:22:48
	 */
	public List<Role> queryRoleList();
	
	/**
	 * 修改角色对应的菜单，删除对应关系
	 * @param roleid
	 * @author  
	 * @date 2020-9-20 上午11:51:32
	 */
	public void deleteRoleMenu(Long roleid);
	
	/**
	 * 查询所有角色列表
	 * @param pageBean
	 * @return
	 * @author  
	 * @date 2020-9-21 下午1:45:04
	 */
	public List<Role> queryAllRole(PageBean pageBean);
	
	

}
