package com.hjcrm.system.entity;

import java.sql.Timestamp;

/**
 * @author wangjing1
 *
 */
public class ScanReportDetail {
	private Long id;
	private Long userId;
	private String reportPath;
	private String createBy;
	private Timestamp createTime;

	public ScanReportDetail() {
		super();
	}
	
	public ScanReportDetail(Long id, Long userId, String reportPath, String createBy, Timestamp createTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.reportPath = reportPath;
		this.createBy = createBy;
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getReportPath() {
		return reportPath;
	}
	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
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
	
}
