package com.hjcrm.system.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hjcrm.system.entity.ScanResultEnum;
import com.hjcrm.system.service.ICheckListService;
public class SecurityScanThread extends Thread{
	private Long resultId;
	private Long usecaseId;
	private ICheckListService iCheckListService;
	
	public SecurityScanThread(Long resultId, Long usecaseId, ICheckListService iCheckListService) {
		super();
		this.resultId = resultId;
		this.usecaseId = usecaseId;
		this.iCheckListService = iCheckListService;
	}
	
	public Long getResultId() {
		return resultId;
	}
	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}
	public Long getUsecaseId() {
		return usecaseId;
	}
	public void setUsecaseId(Long usecaseId) {
		this.usecaseId = usecaseId;
	}

	private void printStream(Process process) {
		// 以下为调试代码
		InputStream fis = process.getInputStream();
		// 用一个读输出流类去读
		InputStreamReader isr = new InputStreamReader(fis);
		// 用缓冲器读行
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		// 直到读完为止
		try {
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			String shell = "/bin/sh /Users/wangjing1/work/work_path_auto_check/inspec_check.sh";
			Process process = Runtime.getRuntime().exec(shell);
			List<Long> ids = new ArrayList<Long>();
			ids.add(resultId);
			int exitValue = process.waitFor();
			if (0 != exitValue) {
				System.out.println("==================call shell failed. error code is :" + exitValue);
				iCheckListService.updateScanResult(ids, ScanResultEnum.FAILED.getValue());
				return;
			}
			iCheckListService.updateScanResult(ids, ScanResultEnum.SUCCESS.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
	            // nothing
            }
        }
    }
}
