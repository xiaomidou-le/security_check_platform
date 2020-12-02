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
import com.hjcrm.system.entity.ScanResultDetail;
import com.hjcrm.system.entity.ScanResultEnum;
import com.hjcrm.system.entity.SecurityCheckList;
import com.hjcrm.system.entity.SecurityScanResult;
import com.hjcrm.system.service.ICheckListService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CheckListServiceImpl implements ICheckListService {
	private int MAX_INT = Integer.MAX_VALUE - 1;
	@Autowired
	private IDataAccess<SecurityCheckList> dao;

	@Override
	public List<SecurityCheckList> queryCheckList(PageBean pageBean) {
		return dao.queryByStatment("querySecurityList", null, pageBean);
	}

	@Override
	public List<SecurityScanResult> queryCheckResultList(PageBean pageBean) {
		return dao.queryByStatment("querySecurityScanResult", null, pageBean);
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
			obj.setCreateBy("");
			obj.setCreateTime(currDate);
			obj.setUpdateBy("");
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
		return dao.updateByStatment("updateSecurityScanResult", params);
	}
}
