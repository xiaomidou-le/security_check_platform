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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hjcrm.publics.constants.JumpViewConstants;
import com.hjcrm.publics.constants.ReturnConstants;
import com.hjcrm.publics.constants.StateConstants;
import com.hjcrm.publics.util.BaseController;
import com.hjcrm.publics.util.UserContext;
import com.hjcrm.publics.websocket.entity.WebSocketNeedBean;
import com.hjcrm.resource.entity.Dealrecord;
import com.hjcrm.resource.entity.Resource;
import com.hjcrm.resource.entity.Student;
import com.hjcrm.resource.entity.Transferrecord;
import com.hjcrm.resource.entity.Visitrecord;
import com.hjcrm.resource.service.IResourceService;
import com.hjcrm.resource.service.IStudentService;
import com.hjcrm.resource.service.ITaskService;
import com.hjcrm.resource.service.ITransferRecordService;
import com.hjcrm.resource.util.DownLoadExcelUtil;
import com.hjcrm.resource.util.ExcelExportUtil;
import com.hjcrm.resource.util.ExcelReaderUtil;
import com.hjcrm.system.entity.User;
import com.hjcrm.system.service.IUserService;

/**
 * 资源管理
 * @author 
 * @date 2020-9-31 下午1:46:09
 */
@Controller
public class ResourceController extends BaseController{
	
	
	@Autowired
	private IResourceService resourceService;
	@Autowired
	private IStudentService studentService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ITransferRecordService	transferrecordService;
	
	
	/**
	 * 跳转资源管理(运营部)
	 * @param model
	 * @return
	 * @author  
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/resource/resourcesMang.do",method = RequestMethod.GET)
	public String index(Model model){
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/resource/resourcesMang.do");
			if (isopen) {
				return JumpViewConstants.RESOURCE_YUNYING;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 跳转资源管理(销售部)
	 * @param model
	 * @return
	 * @author  
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/sales/resourcesMang.do",method = RequestMethod.GET) 
	public String salesIndex(Model model){
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/sales/resourcesMang.do");
			if (isopen) {
				return JumpViewConstants.RESOURCE_SALES;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 跳转电话量查询界面(主管)
	 * @param model
	 * @return
	 * @author 
	 * @date 2017年1月5日15:10:12
	 */
	@RequestMapping(value = "/sales/resourcesTelMang.do",method = RequestMethod.GET) 
	public String salesTelIndex(Model model){
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/sales/resourcesTelMang.do");
			if (isopen) {
				return JumpViewConstants.RESOURCE_TELSALES;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 跳转公司资源管理
	 * @param model
	 * @return
	 * @author  
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/sales/companySalesIndex.do",method = RequestMethod.GET)
	public String companySalesIndex(Model model){
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/sales/companySalesIndex.do");
			if (isopen) {
				return JumpViewConstants.RESOURCE_COMPANYSALES;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 增加资源(运营部、销售部)
	 * @param request
	 * @param userid
	 * @param pageSize
	 * @param currentPage
	 * @param deptid 部门id
	 * @return
	 * @author  
	 * @date 2020-9-31 下午2:41:06
	 */
//	@CacheEvict(value = "baseCache",allEntries = true)
	@RequestMapping(value = "/resource/addResource.do",method = RequestMethod.POST)
	public @ResponseBody String addResource(HttpServletRequest request,Visitrecord visitrecord,String userid,Resource resource,String deptid,String nextVisitTimes){
		if (userid != null && resource != null && deptid != null) {
			if (resource.getResourceId() != null && userid != null && visitrecord.getVisitRecord()!=null && !"".equals(visitrecord.getVisitRecord())) {
				List<Resource> listresource = resourceService.queryResourceByresourceId(resource.getResourceId());
				if (listresource != null && listresource.size() > 0) {
					listresource.get(0).setState(StateConstants.state2);//资源已处理
					resourceService.saveOrUpdate(listresource.get(0));
					
					visitrecord.setUserid(Long.parseLong(userid));
					visitrecord.setResourceId(resource.getResourceId());
					resourceService.saveOrUpdate(visitrecord);
				}
			}
			String phone = resource.getPhone();
			Long resourceId = resource.getResourceId();
			if ((phone != null && !"".equals(phone)) || (resource.getTel() != null && !"".equals(resource.getTel()))) {
				List<Resource> list =  resourceService.queryResourceByresourceId(resourceId);
				if (list != null && list.size() > 0 && resourceId != null) {
					Integer isstudent = list.get(0).getIsStudent();
					if (isstudent != null && isstudent == 1) {
						return ReturnConstants.NO_EDIT;//已成交之后的学员，不能进行资源修改
					}
				}
				List<Resource> listphone =  resourceService.queryResourceByPhone(phone);
				if (listphone != null && listphone.size() > 0 && resourceId == null) {
					if(deptid!=null && Long.parseLong(deptid)==StateConstants.DEPT_YUNYING){
						return ReturnConstants.USER_YES_EXIST;
					}
					for (int i = 0; i < listphone.size(); i++) {
						System.out.println(UserContext.getLoginUser().getUserid());
						Long userphoneid = listphone.get(i).getUserid();
						Long userphonebelongid = listphone.get(i).getBelongid();
						if (userphoneid == UserContext.getLoginUser().getUserid() || (userphonebelongid !=null && userphonebelongid == UserContext.getLoginUser().getUserid()) ) {
							if (listphone.get(i).getIsStudent() != null && listphone.get(i).getIsStudent() == 0) {
 								return ReturnConstants.USER_YES_EXIST;
							} 
						}
					}
				}
				if (nextVisitTimes != null && !"".equals(nextVisitTimes.trim())) {
					resource.setNextVisitTime(Timestamp.valueOf(nextVisitTimes));
				}
				if (Long.valueOf(deptid) == StateConstants.DEPT_YUNYING.longValue()|| Long.valueOf(deptid) == StateConstants.DEPT_ZONGBU.longValue()) {//运营部
					if (resourceId == null) {
						resource.setState(StateConstants.state0);//未分配
					}
					resource.setCreaterName(UserContext.getLoginUser().getUsername());
					resource.setUserid(UserContext.getLoginUser().getUserid());
					resourceService.saveOrUpdate(resource);
					return ReturnConstants.SUCCESS;
				}else if(Long.valueOf(deptid) == StateConstants.DEPT_XIAOSHOU.longValue()||Long.valueOf(deptid) == StateConstants.DEPT_AC.longValue()
						||Long.valueOf(deptid) == StateConstants.DEPT_FAC.longValue()||Long.valueOf(deptid) == StateConstants.DEPT_CHAOJIZD.longValue()
						||Long.valueOf(deptid) == StateConstants.DEPT_WUDIZD.longValue()||Long.valueOf(deptid) == StateConstants.DEPT_LEITINGZD.longValue()
						||Long.valueOf(deptid) == StateConstants.DEPT_TONGLUZD.longValue()||Long.valueOf(deptid) == StateConstants.DEPT_PHONEZD.longValue()
						||Long.valueOf(deptid) == StateConstants.DEPT_XXRZD.longValue()||Long.valueOf(deptid) == StateConstants.DEPT_HJJZD.longValue()
						||Long.valueOf(deptid) == StateConstants.DEPT_CLZD.longValue()||Long.valueOf(deptid) == StateConstants.DEPT_GSZY.longValue()){//DEPT_GSZY：公司资源管理
					if (resourceId == null) {
						User createuser = userService.queryByIdentity(Long.valueOf(userid));
						resource.setState(StateConstants.state1);//已分配
						resource.setAssignTime(new Timestamp(System.currentTimeMillis()));//销售自建的，分配时间显示创建时间
						resource.setCreaterName(createuser.getUsername());
						resource.setBelongid(UserContext.getLoginUser().getUserid());//归属者为自己
						if(UserContext.getLoginUser().getUserid()==107){//王老师自建的资源渠道为11(公司资源)
							resource.setSource(11);
						}
						resource.setYunYingResourceLevel(resource.getResourceLevel());
					}else{
						//=======start likang 2016-12-23 15:22:24 销售的资源等级降低时，运营显示的资源等级不变
						if(resource.getResourceLevel()!=null&&!"".equals(resource.getResourceLevel())){
							List<Resource> resourceList = resourceService.queryResourceByresourceId(resourceId);
							if(resourceList.get(0).getYunYingResourceLevel()==null){
								resource.setYunYingResourceLevel(resource.getResourceLevel());
							}else{
								if((resourceList.get(0).getYunYingResourceLevel()).compareTo(resource.getResourceLevel())>0){
									resource.setYunYingResourceLevel(resource.getResourceLevel());
								}
							}
						}
						//=======end
					}
					resourceService.saveOrUpdate(resource);
					return ReturnConstants.SUCCESS;
				}
				return ReturnConstants.USER_NO_POWER;//当前用户没有操作权限
			}
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	/**
	 * 删除资源(运营部)
	 * @param request
	 * @param userid
	 * @param resourceId
	 * @param studentstate
	 * @return
	 * @author  
	 * @date 2020-9-31 下午3:33:58
	 */
//	@CacheEvict(value = "baseCache")
	@RequestMapping(value = "/resource/deleteResources.do",method = RequestMethod.POST)
	public @ResponseBody String deleteResources(HttpServletRequest request,Long userid, String resourceId,Long studentstate){
		if (userid != null && resourceId != null && !"".equals(resourceId)) {
			if (studentstate != null) {
				if (studentstate.longValue() != 0) {
					return ReturnConstants.RESOURCE_DELETE;//已成交资源不能删除
				}
			}
			if (UserContext.getLoginUser() != null) {//运营的人员可以删除任何资源
				resourceService.deleteResourceById(resourceId);
				return ReturnConstants.SUCCESS;
			}else{
				return ReturnConstants.lOGINUSER_NO_CREATERUSER;
			}
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	/**
	 * 查询电话量(销售主管)
	 * @param request
	 * @param userid
	 * @param roleid 角色ID
	 * @param deptid 部门ID
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @author  likang
	 * @date 2017年1月5日15:14:26
	 */
	@RequestMapping(value = "/resource/queryResourceTel.do",method = RequestMethod.GET)
	public @ResponseBody String queryResourceTel(HttpServletRequest request,String timesearch,String userid,String roleid,String deptid,Integer pageSize, Integer currentPage){
		if (userid != null && UserContext.getLoginUser() != null && deptid != null) {
			List<Resource> list = resourceService.queryResourceTel(timesearch,deptid,userid, roleid, processPageBean(pageSize, currentPage));
		
			return jsonToPage(list);
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	/**
	 * 资源分配
	 * @param request
	 * @param resourceId
	 * @param belongid
	 * @return
	 * @author  
	 * @date 2020-09-4 下午3:40:47
	 */
//	@CacheEvict(value = "baseCache")
	@RequestMapping(value = "/resource/assigntoResource.do",method = RequestMethod.POST)
	public @ResponseBody String assigntoResource(HttpServletRequest request,String resourceIds,String states,Long belongid){
		if (resourceIds != null && belongid != null) {
			if (states != null && !"".equals(states.trim())) {
				String[] state = states.split(",");
				for (int i = 0; i < state.length; i++) {
					if (!"0".equals(state[i])) {
						return ReturnConstants.RESOURCE_STUDENT_NO_FENPEI;//资源中存在已分配的数据，不能进行分配，请重新确认
					}
				}
			}
			User user = UserContext.getLoginUser();
			if (user != null) {
				// 资源IDS，归属人id，已分配，分配时间
				resourceService.updateResourceBelongid(resourceIds, belongid,StateConstants.state1,new Timestamp(System.currentTimeMillis()));
				
				/*****************************消息推送**************极光推送************************/
				String[] resourceIds1 = resourceIds.split(",");
				sendmessage(WebSocketNeedBean.OBJ_TYPE_LIVE, String.valueOf(belongid),null, resourceIds, null, "运营部有分配给您的资源:"+resourceIds1.length+"条，请去【资源管理】查看");
				
				return ReturnConstants.SUCCESS;
			}
			return ReturnConstants.USER_NO_LOGIN;
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	/**
	 * 查询所有的创建者(资源)
	 * @param request
	 * @return
	 * @author  
	 * @date 2020-09-10 下午2:50:21
	 */
	@Cacheable(value = "baseCache", key="'queryAllCreatePerson'")
	@RequestMapping(value = "/resource/queryAllCreatePerson.do",method = RequestMethod.GET)
	public @ResponseBody String queryAllCreatePerson(HttpServletRequest request){
		List<User> list = resourceService.queryAllCreatePerson();
		return json(list);
	}
	
	/**
	 * 查询当前人员的所有下属员工
	 * @param request
	 * @param deptid
	 * @param roleid
	 * @return
	 * @author  
	 * @date 2020-09-15 上午10:55:13
	 */
	@Cacheable(value = "baseCache", key="#userid+'querydeptSubuser'")
	@RequestMapping(value = "/resource/querydeptSubuser.do",method = RequestMethod.GET)
	public @ResponseBody String querydeptSubuser(HttpServletRequest request,Long deptid,Long roleid,Long userid){
		if (userid != null && deptid != null && roleid != null) {
			List<User> list = resourceService.querydeptSubuser(deptid, roleid, userid);
			return json(list);
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	/**
	 * 根据手机号查询资源
	 * @param request
	 * @param phone
	 * @return
	 * @author  
	 * @date 2020-09-4 下午5:24:36
	 */
//	@Cacheable(value = "baseCache", key="#phone")
	@RequestMapping(value = "/resource/queryResourceByPhone.do",method = RequestMethod.GET)
	public @ResponseBody String queryResourceByPhone(HttpServletRequest request,String phone){
		if (phone != null && !"".equals(phone)) {
			List<Resource> list =  resourceService.queryResourceByPhone(phone);
			return json(list);
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	
	/**
	 * 根据条件进行查询资源--筛选导出
	 * @param request
	 * @param resource
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @author  
	 * @date 2020-09-4 下午5:30:46
	 */
	@RequestMapping(value = "/resource/queryResourceBySceen.do",method = RequestMethod.GET)
	public @ResponseBody String queryResourceBySceen(Resource resource,String userid,String roleid,String deptid,Integer pageSize,Integer currentPage){
		if (resource != null) {
			if (UserContext.getLoginUser() != null) {
				userid = String.valueOf(UserContext.getLoginUser().getUserid());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			List<Resource> list = resourceService.queryResourceBySceen(deptid,userid, roleid,resource, null,processPageBean(pageSize, currentPage));
			if(deptid!=null && Long.parseLong(deptid)==StateConstants.DEPT_YUNYING){
				List<Resource> listtoday = resourceService.queryResourceTodayCount(userid,deptid,roleid,resource);//查询今日数量
				map.put("listtoday", listtoday);
				map.put("list", list);
				return json(map);
			}else{
				map.put("list", list);
				return json(list);
			}
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	
	/**
	 * 根据条件进行查询资源--筛选
	 * @param request
	 * @param resource
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @author  
	 * @date 2020-09-4 下午5:30:46
	 */
//	@Cacheable(value = "baseCache", key="#resource + '#userid'")
	@RequestMapping(value = "/resource/queryExportResourceBySceen.do",method = RequestMethod.GET)
	public @ResponseBody String queryExportResourceBySceen(HttpServletRequest request,Resource resource,String userid,String roleid,String deptid,Integer pageSize,Integer currentPage){
		if (resource != null) {
			if (UserContext.getLoginUser() != null) {
				userid = String.valueOf(UserContext.getLoginUser().getUserid());
			}
			List<Resource> list = resourceService.queryResourceBySceen(deptid,userid, roleid,resource, null,null);
			return jsonToPage(list);
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	/**
	 * 查询重复的资源(手机号)
	 * @param request
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @author  
	 * @date 2020-09-4 下午3:58:53
	 */
	@RequestMapping(value = "/resource/queryRepeatResource.do",method = RequestMethod.GET)
	public @ResponseBody String queryRepeatResource(HttpServletRequest request,Integer pageSize, Integer currentPage){
		List<Resource> list = resourceService.queryRepeatResource(null,"false",null);//查询重复的手机号列表
		if (list != null && list.size() > 0) {
			String phones = "";
			for (int i = 0; i < list.size(); i++) {
				if (i == list.size() - 1) {
					phones = phones + list.get(i).getPhone();
				}else{
					if(!"".equals(list.get(i).getPhone()))//有时查询到的第一个手机号是空字符串，此处需要过滤掉   ng
					phones = phones + list.get(i).getPhone() + ",";
				}
			}
			List<Resource> listend = resourceService.queryRepeatResource(phones,"true",processPageBean(pageSize, currentPage));//查询所有重复手机号的详细信息
			return jsonToPage(listend);
		}
		return null;
	}
	
	/**
	 * 查询重复的公司资源(手机号)
	 * @param request
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @author 
	 * @date 2020-9-13 20:24:43
	 */
	@RequestMapping(value = "/resource/queryRepeatCompanyResource.do",method = RequestMethod.GET)
	public @ResponseBody String queryRepeatCompanyResourcetrue(HttpServletRequest request,Integer pageSize, Integer currentPage){
		List<Resource> list = resourceService.queryRepeatCompanyResource(null,"false",null);//查询重复的手机号列表
		if (list != null && list.size() > 0) {
			String phones = "";
			for (int i = 0; i < list.size(); i++) {
				if (i == list.size() - 1) {
					phones = phones + list.get(i).getPhone();
				}else{
					phones = phones + list.get(i).getPhone() + ",";
				}
			}
			List<Resource> listend = resourceService.queryRepeatCompanyResource(phones,"true",processPageBean(pageSize, currentPage));//查询所有重复手机号的详细信息
			return jsonToPage(listend);
		}
		return null;
	}
	
	/**
	 * 获取导出头部信息
	 * @return
	 * @author  
	 * @date 2020-09-7 上午9:55:24
	 */
	public static String[] getHeaders(){
		String[] header = {"创建时间","资源状态","创建人","归属者","渠道","省市地区","资源姓名","手机","意向课程","运营备注","第一次回访时间","资源等级","最近回访记录","咨询次数","座机","微信","QQ"} ;
		return header ;
	}
	
	
	/**
	 * 批量导出资源
	 * @param request
	 * @param file
	 * @return
	 * @author  
	 * @date 2020-09-1 上午9:26:11
	 */
	@RequestMapping(value = "/resource/excelExport.do",method = RequestMethod.POST)
	public @ResponseBody String excelExport(HttpServletRequest request,HttpServletResponse response,Resource resource,String resourceIds,String deptid){
		String[] header = getHeaders();//获取头部标题
		//获取要导出的数据
		List<Resource> list = resourceService.queryResourceBySceen(deptid,null, null,resource,resourceIds,null);
		//写入到excel
		String separator = File.separator;
//		String dir = request.getRealPath(separator + "upload") + separator + "资源信息.xls";
		String dir = request.getSession().getServletContext().getRealPath(separator + "upload")+ separator + "资源信息.xls";
		try {
			OutputStream out = new FileOutputStream(dir);
			ExcelExportUtil.exportExcel("资源信息", header, list, out);
			//下载到本地
			out.close();//********一定要关闭
			DownLoadExcelUtil.downLoadFile(dir, response, request, "资源信息.xls", "xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ReturnConstants.SUCCESS;
	}
	
	
	/**
	 * 增加资源回访记录
	 * @param request
	 * @param resourceId
	 * @param userid
	 * @return
	 * @author  
	 * @date 2020-09-8 上午9:25:48
	 */
	@RequestMapping(value = "/resource/addResourceVisitrecord.do",method = RequestMethod.POST)
	public @ResponseBody String addResourceVisitrecord(HttpServletRequest request,Long resourceId,Long userid,Visitrecord visitrecord){
		if (resourceId != null && userid != null) {
			userid = UserContext.getLoginUser().getUserid();
			List<Resource> listresource = resourceService.queryResourceByresourceId(resourceId);
			if (listresource != null && listresource.size() > 0) {
				listresource.get(0).setState(StateConstants.state2);//资源已处理
				resourceService.saveOrUpdate(listresource.get(0));
				
				visitrecord.setUserid(userid);
				visitrecord.setResourceId(resourceId);
				resourceService.saveOrUpdate(visitrecord);
				return ReturnConstants.SUCCESS;
			}
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	/**
	 * 增加成交记录
	 * 当资源状态由：已处理-->已成交，则由资源转为学员
	 * @param request
	 * @param userid
	 * @param resourceId
	 * @return
	 * @author  
	 * @date 2020-09-8 上午10:06:04
	 */
	@RequestMapping(value="/resource/addDealrecord.do",method = RequestMethod.POST)
	public @ResponseBody String addDealrecord(HttpServletRequest request,String userid,Long resourceId,Dealrecord dealrecord,String subjects,Student student){
		List<Resource> listresource = resourceService.queryResourceByresourceId(resourceId);
		if (resourceId != null && student.getIsOnlineBuy()!=1) {
			if (listresource != null && listresource.size() > 0) {
				Integer isstudent = listresource.get(0).getIsStudent();
				//1：修改资源是否为学员，学员状态为已成交
				if (isstudent != 1) {//是否为学员  0资源 1学员    
					listresource.get(0).setIsStudent(1);
					listresource.get(0).setState(StateConstants.state2);//资源已处理
					listresource.get(0).setStudentstate(StateConstants.studentstate2);
					resourceService.saveOrUpdate(listresource.get(0));
				}
				
				if (subjects != null && !"".equals(subjects.trim())) {
					String[] subject = subjects.split(",");
					for (int i = 0; i < subject.length; i++) {
						//2：增加学员的成交记录（弹框填写成交信息和学员信息）
						Dealrecord dealrecordafter = new Dealrecord();
						dealrecordafter.setCourseid(dealrecord.getCourseid());
						dealrecordafter.setResourceId(resourceId);
						dealrecordafter.setSubjectid(new Long(subject[i]));
						resourceService.saveOrUpdate(dealrecordafter);
					}
				}
				//3：增加一条学员信息，学员状态为已成交
				if (isstudent != 1) {//是否为学员  0资源 1学员
					listresource.get(0).setCourseid(dealrecord.getCourseid().intValue());
					student = resourceToStudent(listresource.get(0),student);
					//student.setIsOnlineBuy(student.getIsOnlineBuy()); //设置是否在线购买   ng
					studentService.saveOrUpdate(student); 
				}
				return ReturnConstants.SUCCESS;//已成功成交学员，详细信息，请到【学员管理】查看：姓名：13512341234
			}
		}else{
			List<Student> liststudent = studentService.queryOnLineStudents(listresource.get(0).getPhone(),null, null);
			if (liststudent != null && liststudent.size() > 0) {
				
				outterLoop: for (Student onlionStudent : liststudent) {                
					if(onlionStudent.getCourseid()==Integer.parseInt(String.valueOf(dealrecord.getCourseid()))){
						if(subjects!=null&&subjects.split(",").length==onlionStudent.getSubjectids().split(",").length){
							for (String s : subjects.split(",")) {
								if(onlionStudent.getSubjectids().contains(s)){
									continue;
								}else{
									continue outterLoop;
								}
							}
						}else{
							return ReturnConstants.OLION_NULL;
						}
						onlionStudent.setBelongid(listresource.get(0).getBelongid());
						//onlionStudent.setResourceId(listresource.get(0).getResourceId());
						if(onlionStudent.getStudentName()==null)
						onlionStudent.setStudentName(listresource.get(0).getResourceName());
						if(onlionStudent.getAddress()==null)
						onlionStudent.setAddress(listresource.get(0).getAddress());
						onlionStudent.setSource(listresource.get(0).getSource());
						if(onlionStudent.getEmail()==null)
						onlionStudent.setEmail(listresource.get(0).getEmail());
						onlionStudent.setTel(listresource.get(0).getTel());
						onlionStudent.setPreferinfo(student.getPreferinfo());
						onlionStudent.setIshavenetedu(student.getIshavenetedu());//是否有网络培训费
						onlionStudent.setNetedumoney(student.getNetedumoney());//网络培训费金额
						studentService.saveOrUpdate(onlionStudent);
						List<Visitrecord> listRecords = resourceService.queryVisitrecordsByresourceId(listresource.get(0).getResourceId(), null);
						for (Visitrecord v : listRecords) {
							v.setStudentId(onlionStudent.getStudentId());
							resourceService.saveOrUpdate(v);
						}
					}
				}
				if (listresource.get(0).getIsStudent() != 1) {//是否为学员  0资源 1学员    
					listresource.get(0).setIsStudent(1);
					listresource.get(0).setState(StateConstants.state2);//资源已处理
					listresource.get(0).setStudentstate(StateConstants.studentstate1);
					resourceService.saveOrUpdate(listresource.get(0));
				}
			}else{
				return ReturnConstants.OLION_NULL;
			}
			return ReturnConstants.SUCCESS;
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	/**
	 * 资源-推-学员
	 * @param resource
	 * @param student
	 * @return
	 * @author  
	 * @date 2020-09-15 上午11:15:30
	 */
	public Student resourceToStudent(Resource resource,Student student){
		if (resource != null) {
			student.setResourceId(resource.getResourceId());
			student.setUserid(resource.getUserid());
			student.setBelongid(resource.getBelongid());
			student.setStudentName(resource.getResourceName());
			student.setPhone(resource.getPhone());
			student.setAddress(resource.getAddress());
			student.setSex(resource.getSex());
			student.setSource(resource.getSource());
			student.setCourseid(resource.getCourseid());
			student.setResourceLevel(resource.getResourceLevel());
			student.setStudentstate(StateConstants.studentstate1);
			student.setIdCard(resource.getIdCard());
			student.setEmail(resource.getEmail());
			return student;
		}
		return null;
	}
	
	/**
	 * 标注或者取消资源星级客户
	 * @param request
	 * @param resourceIds
	 * @param resourceColor
	 * @return
	 * @author  
	 * @date 2020-9-6 下午12:26:22
	 */
	@RequestMapping(value = "/resource/resourceColor.do",method = RequestMethod.GET)
	public @ResponseBody String resourceColor(HttpServletRequest request,String resourceIds,String resourceColor,String resourceLevels){
		if (resourceIds != null && !"".equals(resourceIds.trim()) && resourceLevels != null && !"".equals(resourceLevels.trim())) {
			String[] resourceLevel = resourceLevels.split(",");
			if (resourceLevel != null && resourceLevel.length > 0) {
				for (int i = 0; i < resourceLevel.length; i++) {
					if (!"A".equals(resourceLevel[i])) {
						return ReturnConstants.NO_EDIT;//存在非A级客户，不能标注为星级客户
					}
				}
				resourceService.resourceColorSign(resourceIds, resourceColor);
				return ReturnConstants.SUCCESS;
			}
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	/**
	 * 标注或者取消资源星级客户
	 * @param request
	 * @param resourceIds
	 * @param resourceColor
	 * @return
	 * @author  
	 * @date 2020-9-6 下午12:26:22
	 */
//	@CacheEvict(value = "baseCache")
	@RequestMapping(value = "/resource/weixinResource.do",method = RequestMethod.GET)
	public @ResponseBody String weixinResource(HttpServletRequest request,String resourceIds,Integer isWeixin){
		if (resourceIds != null && !"".equals(resourceIds.trim())) {
				resourceService.updateResourceWeixin(resourceIds,isWeixin);
				return ReturnConstants.SUCCESS;
		}
		return ReturnConstants.PARAM_NULL;
	}
	
	
	/**
	 * 跳转转移记录界面--运营部
	 * @param model
	 * @return
	 * @author  
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/operate/transferRecord.do",method = RequestMethod.GET)
	public String transferRecord(Model model){
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/operate/transferRecord.do");
			if (isopen) {
				return JumpViewConstants.TRANSFER_RECORD_YUNYING;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	/**
	 * 跳转转移记录界面--销售部
	 * @param model
	 * @return
	 * @author  
	 * @date 2020-9-27 下午3:06:37
	 */
	@RequestMapping(value = "/sales/transferRecord.do",method = RequestMethod.GET)
	public String salesTransferRecord(Model model){
		User user = UserContext.getLoginUser();
		if (user != null) {
			Long roleid = user.getRoleid();
			boolean isopen = isSholdOpenMenu(roleid,"/sales/transferRecord.do");
			if (isopen) {
				return JumpViewConstants.TRANSFER_RECORD_SALES;
			}else{
				return ReturnConstants.USER_NO_OPEN;//用户无权限操作并打开，如果需要此权限，请联系运营部
			}
		}
		return ReturnConstants.USER_NO_LOGIN;//用户未登录，请刷新
	}
	
	
	/**
	 * 查询所有运营部人员 + 销售部主管人员
	 * @param request
	 * @return
	 * @author  
	 * @date 2020-9-27 下午2:05:11
	 */
	@RequestMapping(value = "/resource/queryAllDirectors.do",method = RequestMethod.GET)
	public @ResponseBody String queryAllDirectors(HttpServletRequest request){
		List<User> list = userService.queryAllDirectors();
		return jsonToPage(list);
	}
	
	/**
	 * 转移记录导出头部
	 * @return
	 * @author  
	 * @date 2020-9-27 下午3:09:45
	 */
	public static String[] gettfHeaders(){
		String[] header = {"转移时间","操作人","手机","座机","转移前归属者","转移后归属者","销售界面资源状态","转移前资源等级","转移后资源等级"} ;
		return header ;
	}
}
