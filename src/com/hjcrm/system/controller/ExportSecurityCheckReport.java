package com.hjcrm.system.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjcrm.publics.constants.ReturnConstants;
import com.hjcrm.publics.util.BaseController;
import com.hjcrm.publics.util.UserContext;
import com.hjcrm.system.entity.ScanResultDetail;
import com.hjcrm.system.entity.User;
import com.hjcrm.system.service.ExportSecurityCheckReportService;

@Controller
public class ExportSecurityCheckReport extends HttpServlet {
	@Autowired
	private ExportSecurityCheckReportService exportSecurityService;

	
	@RequestMapping(value = "/checkList/securityCheckExport.do", method = RequestMethod.GET)
//	public @ResponseBody String startSecurityCheck(HttpServletRequest request, HttpServletResponse response) {
//		String BASE_PATH = "/Users/wangjing1/work/auto_check/inspec_test/www/";
//		User currUser = UserContext.getLoginUser();
//		String reportPath = BASE_PATH + currUser.getUsername() + "_" + currUser.getUserid();
//
//		String exportFile = exportSecurityService.exportReport(reportPath, currUser.getUsername());
//		
//		// 通过文件路径得到一个输入流
//		String path = this.getServletContext().getRealPath(exportFile);
//		FileInputStream fis = new FileInputStream(path);
//		// 创建字节输出流,这里我们使用Servlet的输出流
//		ServletOutputStream sos = response.getOutputStream();
//		// 得到要下载文件的文件名
//		String filename = path.substring(path.lastIndexOf("\\") + 1);
//		System.out.println(filename);
//		// 告诉客户端下载文件
//		response.setHeader("content-disposition", "attachment; filename=" + filename);
//		response.setHeader("content-type", "image/jpeg"); // 内容类型是 jpg的图片
//		// 执行输出操作
//		int len = 1;
//		byte[] b = new byte[1024]; // 创建一个字节数组
//		while ((len = fis.read(b)) != -1) {
//			sos.write(b, 0, len);
//		}
//		sos.close();
//		fis.close();
//
//	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String BASE_PATH = "/Users/wangjing1/work/auto_check/inspec_test/www/";
		User currUser = UserContext.getLoginUser();
		String reportPath = BASE_PATH + currUser.getUsername() + "_" + currUser.getUserid();
		String path = exportSecurityService.exportReport(reportPath, currUser.getUsername());
		
		//通过文件路径得到一个输入流
		//String path = this.getServletContext().getRealPath(exportFile);
		//File path = new File("exportFile");
		FileInputStream fis = new FileInputStream(path);
		//创建字节输出流,这里我们使用Servlet的输出流
		ServletOutputStream sos = response.getOutputStream();
		//得到要下载文件的文件名
		String filename = path.substring(path.lastIndexOf("\\") + 1);
		System.out.println(filename);
		//告诉客户端下载文件
		response.setHeader("Content-Disposition" ,"attachment; filename=" + filename);
		//response.setHeader("Content-Type", "application/zip"); 
		
		response.setContentType("application/zip");
		//response.setContentLength(arg0);
		//执行输出操作
		int len = 1;
		byte[] b = new byte[1024]; //创建一个字节数组
		while( (len = fis.read(b)) != -1) {
			sos.write(b, 0, len);
		}
		//sos.close();
		fis.close();
	}
 
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
