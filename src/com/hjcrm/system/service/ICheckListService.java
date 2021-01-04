package com.hjcrm.system.service;

import java.util.ArrayList;
import java.util.List;

import com.hjcrm.publics.util.PageBean;
import com.hjcrm.system.entity.ScanReportDetail;
import com.hjcrm.system.entity.ScanResultDetail;
import com.hjcrm.system.entity.SecurityCheckItem;
import com.hjcrm.system.entity.SecurityScanResult;
import com.hjcrm.system.entity.User;

public interface ICheckListService {
	/**
	 * 查询待检测的安全检查项清单
	 * @param 
	 * @return
	 * @author wangjing
	 * @date 2020-9-10 8:59:33
	 */
	//private SecurityCheckListEntity checkListEntity;
	public List<SecurityCheckItem> queryCheckList(PageBean pageBean);
	
	/**
	 * 查询扫描结果
	 * @param 
	 * @return
	 * @author wangjing
	 * @date 2020-11-24 16:37:32
	 */

	public List<SecurityScanResult> queryCheckResultList(PageBean pageBean);

	/**
	 * 插入一条扫描数据
	 * @param 
	 * @return
	 * @author wangjing
	 * @date 2020-11-25 10:35:30
	 */
	public ArrayList<ScanResultDetail> insertScanResult(Long[] usecaseIds);
	
	/**
	 * 更新扫描数据的状态
	 * @param 
	 * @return
	 * @author wangjing
	 * @date 2020-11-25 18:40:00
	 */
	public int updateScanResult(List<Long> ids, Integer status);
	
	public SecurityCheckItem getSecurityCheckItem(Long id);
	
	/**
	 * 插入扫描报告信息
	 * @param 
	 * @return
	 * @author wangjing
	 * @date 2020-11-25 10:35:30
	 */
	public ScanReportDetail insertScanReport(User users, String reportPath);
	
}
