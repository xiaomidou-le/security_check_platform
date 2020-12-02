package com.hjcrm.system.entity;

public enum ScanResultEnum {
	SCANNING(0),FAILED(1),SUCCESS(2);
	private Integer value;
	private ScanResultEnum(Integer value) {
		this.value = value;
	}
	
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}	
}
