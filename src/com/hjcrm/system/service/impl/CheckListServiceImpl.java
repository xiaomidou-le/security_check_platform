package com.hjcrm.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjcrm.publics.dao.IDataAccess;
import com.hjcrm.publics.util.PageBean;
import com.hjcrm.system.entity.SecurityCheckList;
import com.hjcrm.system.service.ICheckListService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CheckListServiceImpl implements ICheckListService{
	@Autowired
	private IDataAccess<SecurityCheckList> dao;

//	public List<User> queryUserList(User user, PageBean pageBean) {
//		Map<String , Object> param = new HashMap<String, Object>();
//		if (user != null) {
//			param.put("phone", user.getPhone());
//			param.put("email", user.getEmail());
//		}
//		return userDao.queryByStatment("queryUserList", user, pageBean);
//	}
	
	@Override
	public List<SecurityCheckList> queryCheckList(PageBean pageBean) {
		// TODO Auto-generated method stub
		return dao.queryByStatment("querySecurityList", null, pageBean);
	}


}
