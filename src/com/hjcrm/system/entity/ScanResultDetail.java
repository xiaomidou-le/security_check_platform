package com.hjcrm.system.entity;

import java.sql.Timestamp;

public class ScanResultDetail{
	public ScanResultDetail(Long id, Long usecaseId, Integer result) {
		super();
		this.id = id;
		this.usecaseId = usecaseId;
		this.result = result;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -3003762393184234554L;
	private Long id;
	private Long usecaseId;
	private Integer result;
	private String createBy;
	private Timestamp createTime;
	private String updateBy;
	private Timestamp updateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUsecaseId() {
		return usecaseId;
	}
	public void setUsecaseId(Long usecaseId) {
		this.usecaseId = usecaseId;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
