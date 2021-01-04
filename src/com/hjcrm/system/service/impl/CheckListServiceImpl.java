package com.hjcrm.system.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjcrm.publics.dao.IDataAccess;
import com.hjcrm.publics.util.PageBean;
import com.hjcrm.publics.util.UserContext;
import com.hjcrm.system.entity.ScanReportDetail;
import com.hjcrm.system.entity.ScanResultDetail;
import com.hjcrm.system.entity.ScanResultEnum;
import com.hjcrm.system.entity.SecurityCheckItem;
import com.hjcrm.system.entity.SecurityUsecase;
import com.hjcrm.system.entity.SecurityScanResult;
import com.hjcrm.system.entity.User;
import com.hjcrm.publics.util.UserContext;
import com.hjcrm.system.service.ICheckListService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CheckListServiceImpl implements ICheckListService {
	private int MAX_INT = Integer.MAX_VALUE - 1;
	
	@Autowired
	private IDataAccess<SecurityCheckItem> dao;

	@Override
	public List<SecurityCheckItem> queryCheckList(PageBean pageBean) {
		return dao.queryByStatment("querySecurityList", null, pageBean);
	}

	@Override
	public List<SecurityScanResult> queryCheckResultList(PageBean pageBean) {
		return dao.queryByStatment("querySecurityScanResult", null, pageBean);
	}
	
	@Override
	public SecurityCheckItem getSecurityCheckItem(Long id) {
		List<SecurityCheckItem> items = dao.queryByStatment("queryUsecaseById", id, null);
		if (items != null && items.size() > 0) {
			return items.get(0);
		}
		return null;
	}
	
	@Override
	public ArrayList<ScanResultDetail> insertScanResult(Long[] usecaseIds) {	
		ArrayList<ScanResultDetail> result = new ArrayList<ScanResultDetail>();
		Date date = new Date();       
		Timestamp currDate = new Timestamp(date.getTime());
		Random rand = new Random();	
		for (Long id : usecaseIds) {
			Integer idSeq = rand.nextInt(MAX_INT) + 1;
			ScanResultDetail obj = new ScanResultDetail(idSeq.longValue(), id, ScanResultEnum.SCANNING.getValue());
			User currUser = UserContext.getLoginUser();
			obj.setCreateBy(currUser.getUsername());
			obj.setCreateTime(currDate);
			obj.setUpdateBy(currUser.getUsername());
			obj.setUpdateTime(currDate);
			result.add(obj);
		}
		return (ArrayList<ScanResultDetail>)dao.insertByStatement("insertSecurityScanResult", result);
	}

	@Override
	public int updateScanResult(List<Long> ids, Integer status) {
		HashMap<String, Object> params = new HashMap();
		params.put("status", status);
		params.put("ids", ids);
		Date date = new Date();
		Timestamp currDate = new Timestamp(date.getTime());
		params.put("updateTime", currDate);
		return dao.updateByStatment("updateSecurityScanResult", params);
	}

	@Override
	public ScanReportDetail insertScanReport(User users, String reportPath) {
		ScanReportDetail reportDetail = new ScanReportDetail();
		Random rand = new Random();	
		Long idSeq = (long)rand.nextInt(MAX_INT) + 1;
		reportDetail.setId(idSeq);
		reportDetail.setUserId(users.getUserid());
		reportDetail.setCreateBy(users.getUsername());
		Date date = new Date();       
		Timestamp currDate = new Timestamp(date.getTime());
		reportDetail.setCreateTime(currDate);
		reportDetail.setReportPath(reportPath);
		return (ScanReportDetail)dao.insertByStatement("insertSecurityScanRport", reportDetail);
	}

}
