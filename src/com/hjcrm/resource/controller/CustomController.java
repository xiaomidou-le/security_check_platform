package com.hjcrm.resource.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjcrm.publics.constants.JumpViewConstants;
import com.hjcrm.publics.constants.ReturnConstants;
import com.hjcrm.publics.constants.StateConstants;
import com.hjcrm.publics.util.BaseController;
import com.hjcrm.publics.util.UserContext;
import com.hjcrm.resource.entity.Dealrecord;
import com.hjcrm.resource.entity.Student;
import com.hjcrm.resource.service.IStudentService;
import com.hjcrm.resource.util.PublicExcelExportUtil;
import com.hjcrm.system.entity.Menu;
import com.hjcrm.system.entity.User;
import com.hjcrm.system.service.IMenuService;
import com.hjcrm.system.service.IUserService;

/**
 * 客服学员管理
 * @author 
 * @date 2020-09-7 下午3:34:41
 */
@Controller
public class CustomController extends BaseController{
	
	@Autowired
	private IStudentService studentService;

	@Autowired
	private IMenuService menuService;
	@Autowired
	private IUserService userService;
	
	/**
	 * 新增学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/addStudentMang.do", method = RequestMethod.GET)
	public String customindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/addStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_CUSTOM;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * AFP总表学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/AFPallStudentMang.do", method = RequestMethod.GET)
	public String AFPallindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/AFPallStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_AFPALL;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * CFP总表学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/CFPallStudentMang.do", method = RequestMethod.GET)
	public String CFPallindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/CFPallStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_CFPALL;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 基金总表学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/jijinallStudentMang.do", method = RequestMethod.GET)
	public String jijinallindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/jijinallStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_JIJINALL;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 银从总表学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/yincongallStudentMang.do", method = RequestMethod.GET)
	public String yincongallindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/yincongallStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_YINCONGALL;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}

	
	/**
	 * 中级总表学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/zhongjiallStudentMang.do", method = RequestMethod.GET)
	public String zhongjiallindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/zhongjiallStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_ZHONGJIALL;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}

	/**
	 * 证券总表学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/zhengquanallStudentMang.do", method = RequestMethod.GET)
	public String zhengquanallindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/zhengquanallStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_ZHENGQUANALL;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 期货总表学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/qihuoallStudentMang.do", method = RequestMethod.GET)
	public String qihuoallindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/qihuoallStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_QIHUOALL;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 会计从业总表学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/kjcyallStudentMang.do", method = RequestMethod.GET)
	public String kicyallindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/kjcyallStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_KJCYALL;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 初级会计总表学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/cjkjallStudentMang.do", method = RequestMethod.GET)
	public String cjkjallindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/cjkjallStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_CJKJALL;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	

	/**
	 * AFP回访学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/AFPStudentMang.do", method = RequestMethod.GET)
	public String AFPindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/AFPStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_AFP;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * CFP回访学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/CFPStudentMang.do", method = RequestMethod.GET)
	public String CFPindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/CFPStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_CFP;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 基金回访学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/jiJinStudentMang.do", method = RequestMethod.GET)
	public String jiJinindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/jiJinStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_JIJIN;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 银从回访学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/yinCongStudentMang.do", method = RequestMethod.GET)
	public String yinCongindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/yinCongStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_YINCONG;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 中级回访学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/zhongJiStudentMang.do", method = RequestMethod.GET)
	public String zhongJiindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/zhongJiStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_ZHONGJI;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 证券回访学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/zhengQuanStudentMang.do", method = RequestMethod.GET)
	public String zhengQuanindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/zhengQuanStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_ZHENGQUAN;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 期货回访学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/qiHuoStudentMang.do", method = RequestMethod.GET)
	public String qiHuoindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/qiHuoStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_QIHUO;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 会计从业回访学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/kjcyStudentMang.do", method = RequestMethod.GET)
	public String kjcyindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/kjcyStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_KJCY;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 初级会计回访学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/cjkjStudentMang.do", method = RequestMethod.GET)
	public String cjkjindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/cjkjStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_CJKJ;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 通过学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/passStudentMang.do", method = RequestMethod.GET)
	public String passindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/passStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_PASS;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 特殊学员管理(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/specialStudentMang.do", method = RequestMethod.GET)
	public String specialindex(Model model) {
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/custom/specialStudentMang.do");
			if (isopen) {
				return JumpViewConstants.STUDENT_SPECIAL;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 回访学员详情页(客服部)
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/hfDetails.do", method = RequestMethod.GET)
	public String hfDetails(Model model,Long studentId,Long deptid,String menuCode) {
		if (studentId != null) {
			List<Student> liststudent = studentService.queryhfStudents(null,String.valueOf(studentId), null, null, null);//查询学员信息-回访信息-成交记录信息
			if (liststudent != null && liststudent.size() > 0) {
				model.addAttribute("liststudent", liststudent);
				model.addAttribute("record", liststudent.get(0).getVisitrecord());//回访记录信息;
				model.addAttribute("dealrecord", liststudent.get(0).getDealrecord());//成交记录信息;
			}else{
				model.addAttribute("liststudent", null);
				model.addAttribute("record", null);
				model.addAttribute("dealrecord", null);
			}
		}
		if ("AFPhf".equals(menuCode)) {//AFP回访
			return JumpViewConstants.STUDENT_AFPHF;
		}else if("CFPhf".equals(menuCode)){//CFP回访
			return JumpViewConstants.STUDENT_CFPHF;
		}else if("jijinhf".equals(menuCode)||"yinconghf".equals(menuCode)||"zhongjihf".equals(menuCode)||"zhengquanhf".equals(menuCode)
				||"kuaijihf".equals(menuCode)||"chujihf".equals(menuCode)||"qihuohf".equals(menuCode)){//非AC回访
			return JumpViewConstants.STUDENT_FEI_AC;
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	
	/**
	 * 查询总表学员(客服)
	 * AFPtotal CFPtotal jijintotal yincongtotal zhongjitotal zhengquantotal qihuototal kuaijitotal chujitotal passtotal teshutotal
	 * AFPhf CFPhf jijinhf yinconghf zhongjihf zhengquanhf qihuohf kuaijihf chujihf 
	 * @param request
	 * @param userid 登陆用户ID
	 * @param deptid 用户部门ID
	 * @param roleid 角色ID
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @author  
	 * @date 2020-09-16 上午9:16:09
	 */
	@RequestMapping(value = "/custom/queryTotalStudents.do",method = RequestMethod.GET)
	public @ResponseBody String queryTotalStudents(HttpServletRequest request,String menuCode,String userid,String deptid,String roleid,Integer pageSize, Integer currentPage){
		if (userid != null && deptid != null && roleid != null && menuCode != null && !"".equals(menuCode.trim())) {
			List<Student> list = null;
			if ("AFPtotal".equals(menuCode)) {//AFP
				list = studentService.queryAFPTotalStudents(deptid, roleid, userid, processPageBean(pageSize, currentPage));
			}else if("CFPtotal".equals(menuCode)){//CFP
				list = studentService.queryCFPTotalStudents(deptid, roleid, userid, processPageBean(pageSize, currentPage));
			}else if("jijintotal".equals(menuCode)){//基金
				list = studentService.queryjijinTotalStudents(deptid, roleid, userid, processPageBean(pageSize, currentPage));
			}else if("yincongtotal".equals(menuCode)){//银从
				list = studentService.queryyincongTotalStudents(deptid, roleid, userid, processPageBean(pageSize, currentPage));
			}else if("chujitotal".equals(menuCode)){//初级
				list = studentService.querychujiTotalStudents(deptid, roleid, userid, processPageBean(pageSize, currentPage));
			}
			return jsonToPage(list);
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	/**
	 * 查询回访学员(客服)
	 * AFPtotal CFPtotal jijintotal yincongtotal zhongjitotal zhengquantotal qihuototal kuaijitotal chujitotal passtotal teshutotal
	 * AFPhf CFPhf jijinhf yinconghf zhongjihf zhengquanhf qihuohf kuaijihf chujihf 
	 * @param request
	 * @param menucode 菜单编码
	 * @param userid 登陆用户ID
	 * @param deptid 用户部门ID
	 * @param roleid 角色ID
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @author  
	 * @date 2020-09-16 上午9:16:09
	 */
	@RequestMapping(value = "/custom/queryhfStudents.do",method = RequestMethod.GET)
	public @ResponseBody String queryhfStudents(HttpServletRequest request,String menuCode,String userid,String deptid,String roleid,Integer pageSize, Integer currentPage){
		if (userid != null && deptid != null && roleid != null && menuCode != null && !"".equals(menuCode.trim())) {
			List<Student> list = null;
			if ("AFPhf".equals(menuCode)) {//AFP
				list = studentService.queryAFPhfStudents(deptid, roleid, userid, processPageBean(pageSize, currentPage));
			}else if("CFPhf".equals(menuCode)){//CFP
				list = studentService.queryCFPhfStudents(deptid, roleid, userid, processPageBean(pageSize, currentPage));
			}else if("jijinhf".equals(menuCode)){//基金
				list = studentService.queryjijinhfStudents(deptid, roleid, userid, processPageBean(pageSize, currentPage));
			}else if("yinconghf".equals(menuCode)){//银从
				list = studentService.queryyinconghfStudents(deptid, roleid, userid, processPageBean(pageSize, currentPage));
			}else if("kuaijihf".equals(menuCode)){//会计
				list = studentService.querykuaijihfStudents(deptid, roleid, userid, processPageBean(pageSize, currentPage));
			}else if("chujihf".equals(menuCode)){//初级
				list = studentService.querychujihfStudents(deptid, roleid, userid, processPageBean(pageSize, currentPage));
			}
			return jsonToPage(list);
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	
	/**
	 * 转入回访表(客服)
	 * @param request
	 * @param userid
	 * @param deptid
	 * @param roleid
	 * @param resourceIds
	 * @param studentIds
	 * @return
	 * @author  
	 * @date 2020-09-22 下午5:06:11
	 */
	@RequestMapping(value = "/custom/transferStudentsToCourse.do",method = RequestMethod.POST)
	public @ResponseBody String transferStudentsToCourse(HttpServletRequest request,Long userid,Long deptid,Long roleid,String resourceIds,String studentIds,String studentstates){
		if (studentIds != null && !"".equals(studentIds.trim()) && studentstates != null && !"".equals(studentstates.trim())) {
			String[] states = studentstates.split(",");
			String s = null;
			for (int i = 0; i < states.length; i++) {
				s= states[i];
				if (s != null && ("0".equals(s)||"1".equals(s)||"2".equals(s)||"3".equals(s)||"8".equals(s))) {
					return ReturnConstants.STUDENT_NO_COMMIT;//学员状态存在非已分配状态，不能转入回访表
				}
			}
			//修改资源状态为“已转入”
			if (resourceIds != null && !"".equals(resourceIds.trim())) {
				studentService.updateResourceStudentstate(resourceIds, StateConstants.studentstate5);
			}
			//修改学员状态为“已转入”
			if (studentIds != null && !"".equals(studentIds.trim())) {
				studentService.updateStudentstate(studentIds, StateConstants.studentstate5, null, null, null);
			}
			return ReturnConstants.SUCCESS;
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	/**
	 * 客服主管转移总表学员
	 * @param request
	 * @param resourceIds
	 * @param studentIds
	 * @param customerId
	 * @param studentstates
	 * @return
	 * @author  
	 * @date 2020-09-23 下午3:27:00
	 */
	@RequestMapping(value = "/custom/transCustomStudent.do",method = RequestMethod.POST)
	public @ResponseBody String transCustomStudent(HttpServletRequest request,String resourceIds,String studentIds,String customerId,String studentstates){
		if (studentIds != null && !"".equals(studentIds.trim()) && customerId != null && !"".equals(customerId.trim()) && studentstates != null && !"".equals(studentstates.trim())) {
			//star likang 2017-1-17 17:59:50 ==TODO 行政部需要所有权限去转移学员到对应的客服
			/*String[] states = studentstates.split(",");
			String s = null;
			for (int i = 0; i < states.length; i++) {
				s= states[i];
				if (s != null && !"4".equals(s)) {
					return ReturnConstants.STUDENT_NO_COMMIT;//学员状态存在非已分配状态，不能转移客服
				}
			}*/
			//end
			User user = userService.queryByIdentity(new Long(customerId));
			studentService.assignStudent(resourceIds, studentIds, customerId,user.getUsername(), StateConstants.studentstate4, new Timestamp(System.currentTimeMillis()));
			return ReturnConstants.SUCCESS;
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	public static String getTitle(Long deptid,String menuCode){
		String title = "";
		if (deptid != null &&  (deptid.longValue() == StateConstants.DEPT_AFPZLH.longValue() || deptid.longValue() == StateConstants.DEPT_AFPXC.longValue()) && "AFPtotal".equals(menuCode)) {//客服部-AFP总表学员-导出标题
			title = "AFP总表学员.xls";
		}else if(deptid != null &&  (deptid.longValue() == StateConstants.DEPT_AFPZLH.longValue() || deptid.longValue() == StateConstants.DEPT_AFPXC.longValue())  && "AFPhf".equals(menuCode)){//客服部-AFP回访-导出标题
			title = "AFP回访学员.xls";
		}else if(deptid != null &&  (deptid.longValue() == StateConstants.DEPT_CFPWYL.longValue()) && "CFPtotal".equals(menuCode)){//客服部-CFP总表学员-导出标题
			title = "CFP总表学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_CFPWYL.longValue() && "CFPhf".equals(menuCode)){//客服部-CFP回访-导出标题
			title = "CFP回访学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_FACLWH.longValue() && "jijintotal".equals(menuCode)){//客服部-基金总表学员-导出标题
			title = "基金总表学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_FACLWH.longValue() && "jijinhf".equals(menuCode)){//客服部-基金回访-导出标题
			title = "基金回访学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_FACLWH.longValue() && "yincongtotal".equals(menuCode)){//客服部-银从总表学员-导出标题
			title = "银从总表学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_FACLWH.longValue() && "yinconghf".equals(menuCode)){//客服部-银从回访-导出标题
			title = "银从回访学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_FACLWH.longValue() && "zhongjitotal".equals(menuCode)){//客服部-中级总表学员-导出标题
			title = "中级总表学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_FACLWH.longValue() && "zhongjihf".equals(menuCode)){//客服部-中级回访-导出标题
			title = "中级回访学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_FACLWH.longValue() && "zhengquantotal".equals(menuCode)){//客服部-证券总表学员-导出标题
			title = "证券总表学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_FACLWH.longValue() && "zhengquanhf".equals(menuCode)){//客服部-证券回访-导出标题
			title = "证券回访学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_FACLWH.longValue() && "qihuototal".equals(menuCode)){//客服部-期货总表学员-导出标题
			title = "期货总表学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_FACLWH.longValue() && "qihuohf".equals(menuCode)){//客服部-期货回访-导出标题
			title = "期货回访学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_FACLWH.longValue() && "kuaijitotal".equals(menuCode)){//客服部-会计从业总表学员-导出标题
			title = "会计从业总表学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_FACLWH.longValue() && "kuaijihf".equals(menuCode)){//客服部-会计从业回访-导出标题
			title = "会计从业回访学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_FACLWH.longValue() && "chujitotal".equals(menuCode)){//客服部-初级会计总表学员-导出标题
			title = "初级会计总表学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_FACLWH.longValue() && "chujihf".equals(menuCode)){//客服部-初级会计回访-导出标题
			title = "初级会计回访学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_KEFU.longValue() && "passtotal".equals(menuCode)){//客服部-通过学员-导出标题
			title = "通过学员.xls";
		}else if(deptid != null && deptid.longValue() == StateConstants.DEPT_KEFU.longValue() && "teshutotal".equals(menuCode)){//客服部-特殊情况学员-导出标题
			title = "特殊情况学员.xls";
		}
		return title;
	}
	

	
	/**
	 * 特殊情况学员
	 * @param request
	 * @param userid
	 * @param deptid
	 * @param roleid
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @author  
	 * @date 2020-09-16 上午9:44:14
	 */
	@RequestMapping(value = "/custom/querySpecialStudents.do",method = RequestMethod.GET)
	public @ResponseBody String querySpecialStudents(HttpServletRequest request,Long userid,Long deptid,Long roleid,Student student,Integer pageSize, Integer currentPage){
		if (userid != null && deptid != null && roleid != null) {
			List<Student> list = studentService.querySpecialStudents(student,deptid, roleid, userid, processPageBean(pageSize, currentPage));
			return jsonToPage(list);
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	/**
	 * 跳转 特殊情况学员详情页界面
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/speaialdetails.do", method = RequestMethod.GET)
	public String speaialdetails(Model model,Long userid,Long deptid,Long roleid,String studentId) {
		if (studentId != null) {
			List<Student> liststudent = studentService.queryhfStudents(null, String.valueOf(studentId), null, null, null);//查询学员信息-回访信息-成交记录信息
			if (liststudent != null && liststudent.size() > 0) {
				model.addAttribute("liststudent", liststudent);
				model.addAttribute("record", liststudent.get(0).getVisitrecord());//回访记录信息;
				model.addAttribute("dealrecord", liststudent.get(0).getDealrecord());//成交记录信息;
			}else{
				model.addAttribute("liststudent", null);
				model.addAttribute("record", null);
				model.addAttribute("dealrecord", null);
			}
		}
		return JumpViewConstants.STUDENT_SPECIAL_DETAILS;
	}
	
	/**
	 * 通过学员表
	 * @param request
	 * @param userid
	 * @param deptid
	 * @param roleid
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @author  
	 * @date 2020-09-16 上午9:44:14
	 */
	@RequestMapping(value = "/custom/customQueryPassStudents.do",method = RequestMethod.GET)
	public @ResponseBody String customQueryPassStudents(HttpServletRequest request,Long userid,Long deptid,Long roleid,Student student,Integer pageSize, Integer currentPage){
		if (userid != null && deptid != null && roleid != null) {
			List<Student> list = studentService.customQueryPassStudents(student,deptid, roleid, userid, processPageBean(pageSize, currentPage));
			return jsonToPage(list);
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	
	/**
	 * 跳转 通过学员详情页界面
	 * 
	 * @param model
	 * @return
	 * @author 
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/custom/passdetails.do", method = RequestMethod.GET)
	public String passdetails(Model model,Long userid,Long deptid,Long roleid,String studentId) {
		if (studentId != null) {
			List<Student> liststudent = studentService.queryhfStudents(null, String.valueOf(studentId), null, null, null);//查询学员信息-回访信息-成交记录信息
			if (liststudent != null && liststudent.size() > 0) {
				model.addAttribute("liststudent", liststudent);
				model.addAttribute("record", liststudent.get(0).getVisitrecord());//回访记录信息;
				model.addAttribute("dealrecord", liststudent.get(0).getDealrecord());//成交记录信息;
			}else{
				model.addAttribute("liststudent", null);
				model.addAttribute("record", null);
				model.addAttribute("dealrecord", null);
			}
		}
		return JumpViewConstants.STUDENT_PASS_DETAILS;
	}

	
	/**
	 * 批量修改颜色
	 * @param request
	 * @param studentIds
	 * @param studentColor
	 * @return
	 * @author  
	 * @date 2020-9-6 下午12:26:22
	 */
	@RequestMapping(value = "/custom/studentColorSign.do",method = RequestMethod.GET)
	public @ResponseBody String studentColorSign(HttpServletRequest request,String studentIds,String studentColor){
		if (studentIds != null && !"".equals(studentIds.trim())) {
			studentService.studentColorSign(studentIds, studentColor);
			return ReturnConstants.SUCCESS;
		}
		return ReturnConstants.PARAM_NULL;
	}
	
}
