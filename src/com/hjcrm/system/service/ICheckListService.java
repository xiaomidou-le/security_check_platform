package com.hjcrm.system.service;

import java.util.List;

import com.hjcrm.publics.util.PageBean;
import com.hjcrm.system.entity.SecurityCheckList;

public interface ICheckListService {
	/**
	 * 查询待检测的安全检查项清单
	 * @param 
	 * @return
	 * @author wangjing
	 * @date 2020-9-10 8:59:33
	 */
	//private SecurityCheckListEntity checkListEntity;
	public List<SecurityCheckList> queryCheckList(PageBean pageBean);
}
