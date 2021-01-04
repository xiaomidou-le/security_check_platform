package com.hjcrm.system.service.impl;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjcrm.system.service.ICheckListService;
import com.hjcrm.system.service.IStartSecurityCheckService;
import com.hjcrm.system.util.SecurityScanThread;
@Service
@Transactional(rollbackFor = Exception.class)
public class StartSecurityCheckServiceImp implements IStartSecurityCheckService{
	@Autowired
	private ICheckListService iCheckListService;
	@Override
	public void startSecurityCheck(Long resultId, Long usecaseNo, String reportPath) {
		SecurityScanThread securityScanThread = new SecurityScanThread(resultId, usecaseNo, iCheckListService, reportPath);
		securityScanThread.start();
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
