package com.hjcrm.system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjcrm.publics.constants.JumpViewConstants;
import com.hjcrm.publics.constants.ReturnConstants;
import com.hjcrm.publics.util.BaseController;
import com.hjcrm.publics.util.UserContext;
import com.hjcrm.system.entity.ScanResultDetail;
import com.hjcrm.system.entity.SecurityCheckList;
import com.hjcrm.system.entity.SecurityScanResult;
import com.hjcrm.system.service.ICheckListService;
import com.hjcrm.system.service.IStartSecurityCheckService;

@Controller
public class SecurityCheckController extends BaseController{
	@Autowired
	private ICheckListService securityService;
	
	@Autowired
	private IStartSecurityCheckService startCheckService;
	
	@RequestMapping(value = "/securityCheck/securityCheckMgr.do",method = RequestMethod.GET)
	public String index(Model model){
		if (UserContext.getLoginUser() != null) {
			return JumpViewConstants.SECURITY_CHECK_MGR;
		}
		return ReturnConstants.USER_NO_LOGIN;
	}
	
	@RequestMapping(value = "/checkList/securityUsecaseList.do", method = RequestMethod.GET)
	public @ResponseBody String getCheckList(Integer pageSize, Integer currentPage){
		List<SecurityCheckList> checkList = securityService.queryCheckList(processPageBean(pageSize, currentPage));
		return jsonToPage(checkList);
	}
	
	@RequestMapping(value = "/checkList/securityCheckResultList.do", method = RequestMethod.GET)
	public @ResponseBody String getCheckResultList(Integer pageSize, Integer currentPage){
		List<SecurityScanResult> checkList = securityService.queryCheckResultList(processPageBean(pageSize, currentPage));
		return jsonToPage(checkList);
	}
	
	@RequestMapping(value = "/checkList/securityCheckStart.do", method = RequestMethod.POST)
	public @ResponseBody String startSecurityCheck(HttpServletRequest request, Long[] usecaseIds) {	
		ArrayList<ScanResultDetail> resultList = securityService.insertScanResult(usecaseIds);
		if (usecaseIds != null) {
			for (ScanResultDetail tmp : resultList) {
				startCheckService.startSecurityCheck(tmp.getId(), tmp.getUsecaseId());
			}	
			return ReturnConstants.SUCCESS;
		}
		return ReturnConstants.PARAM_NULL;	
	}
	
	//获取安全扫描结果的状态
	@RequestMapping(value = "/securityCheck/securityCheckResult.do",method = RequestMethod.GET)
	public String getSecurityScanResult(Model model){
		if (UserContext.getLoginUser() != null) {
			return JumpViewConstants.SECURITY_CHECK_RESULT;
		}
		return ReturnConstants.USER_NO_LOGIN;
	}
}