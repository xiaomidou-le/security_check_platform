package com.hjcrm.resource.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjcrm.publics.dao.IDataAccess;
import com.hjcrm.publics.util.PageBean;
import com.hjcrm.publics.util.UserContext;
import com.hjcrm.resource.entity.Transferrecord;
import com.hjcrm.resource.service.ITransferRecordService;

/**
 * 转移记录
 * @author 
 * @date 2020-9-21 下午1:58:10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TransferRecordServiceImpl implements ITransferRecordService {
	
	@Autowired
	private IDataAccess<Transferrecord> transferrecordDao;

	/**
	 * 保存转移记录
	 */
	
	public void saveTransferRecord(Transferrecord transferrecord) {
		if (transferrecord != null) {
			transferrecord.setOperationId(UserContext.getLoginUser().getUserid());
			transferrecord.setOperationName(UserContext.getLoginUser().getUsername());
			transferrecordDao.insert(transferrecord);
		}
	}

}
