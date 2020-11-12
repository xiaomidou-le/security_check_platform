package com.hjcrm.resource.service;

import java.util.List;

import com.hjcrm.publics.util.PageBean;
import com.hjcrm.resource.entity.Transferrecord;


/**
 * 转移记录接口
 * @author 
 * @date 2020-9-21 下午1:55:50
 */
public interface ITransferRecordService {
	
	/**
	 * 保存转移记录
	 * @param transferrecord
	 * @author  
	 * @date 2020-9-21 下午1:56:27
	 */
	public void saveTransferRecord(Transferrecord transferrecord);

}
