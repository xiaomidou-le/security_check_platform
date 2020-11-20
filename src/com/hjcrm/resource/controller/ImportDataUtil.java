package com.hjcrm.resource.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjcrm.publics.constants.ReturnConstants;
import com.hjcrm.publics.util.BaseController;
import com.hjcrm.resource.entity.Dealrecord;
import com.hjcrm.resource.entity.Student;
import com.hjcrm.resource.service.IStudentService;
import com.hjcrm.system.entity.User;
import com.hjcrm.system.service.IUserService;

/**
 * 数据导入工具类
 * @author 
 * @date 2020-9-29 下午5:16:30
 */
@Controller
public class ImportDataUtil extends BaseController{
	
	
	private static POIFSFileSystem fs;
	private static HSSFWorkbook wb;
	private static HSSFSheet sheet;
	private static HSSFRow row;

	@Autowired
	private IUserService userService;
	@Autowired
	private IStudentService studentService;

	/**
	 * 读取Excel表格表头的内容
	 * 
	 * @param is
	 * @return 表头内容的数组String类型
	 * @author 
	 * @date 2020-09-7 上午9:58:13
	 */
	public String[] readExcelTitle(InputStream is) {
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(0);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			title[i] = getCellFormatValue(row.getCell((short) i));
		}
		return title;
	}
	private static String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			switch (cell.getCellType()) {// 判断当前Cell的Type
			case HSSFCell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_FORMULA: {
				cell.getDateCellValue();
				boolean istrue = HSSFDateUtil.isCellDateFormatted(cell);
				if (istrue) {// 判断当前的cell是否为Date
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);
				} else {
					DecimalFormat df = new DecimalFormat("0");
					cellvalue = df.format(cell.getNumericCellValue());
				}
				break;
			}
			case HSSFCell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRIN
				cellvalue = cell.getRichStringCellValue().getString();// 取得当前的Cell字符串
				break;
			default:
				cellvalue = " ";// 默认的Cell值
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	public static Map<String,Object> readExcelContent(InputStream is) {
		Map<String, Object> maps = new HashMap<String, Object>();
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Map> list = new ArrayList<Map>();
		sheet = wb.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();// 总行数
		row = sheet.getRow(0);
		if (row != null) {
			String str = null;
			int colNum = row.getPhysicalNumberOfCells();//总列数
			int j = 0;
			for (int i = 1; i <= rowNum; i++) {// 正文内容应该从第二行开始,第一行为表头的标题
				Map<String, String> content = new HashMap<String, String>();
				row = sheet.getRow(i);
				while (j < colNum) {
					str = getCellFormatValue(row.getCell((short) j)).trim();
					if (j == 0) {
						content.put("studentName", str);//姓名
					}else if(j == 1){
						content.put("idCard", str);//身份证
					}else if(j == 2){
						content.put("phone", str);//电话
					}else if(j == 3){
						content.put("tel", str);//固定电话
					}else if(j == 4){
						content.put("email", str);//邮箱
					}else if(j == 5){
						content.put("company", str);//单位
					}else if(j == 6){
						content.put("companyAddr", str);//地址
					}else if(j == 7){
						content.put("position", str);//职务
					}else if(j == 8){
						content.put("school", str);//毕业院校
					}else if(j == 9){
						content.put("education", str);//学历
					}else if(j == 10){
						content.put("nation", str);//民族
					}else if(j == 11){
						content.put("belongName", str);//招生老师
					}else if(j == 12){
						content.put("dealprice", str);//成交金额
					}else if(j == 13){
						content.put("arrive_time", str);//到账日期
					}else if(j == 14){
						content.put("remitWay", str);//支付方式
					}else if(j == 15){
						content.put("courseid", str);//课程
					}else if(j == 16){
						content.put("jijin1", str);//基金1
					}else if(j == 17){
						content.put("jijin1ispass", str);//是否通过
					}else if(j == 18){
						content.put("jijin2", str);//基金2
					}else if(j == 19){
						content.put("jijin2ispass", str);//是否通过
					}else if(j == 20){
						content.put("kefuNote1", str);//客服备注
					}
					j++;
					str = null;
				}
				j=0;
				list.add(content);
			}
			if (list.size() > 0) {
				maps.put("listdata", list);
			}else{
				maps.put("listdata", null);
			}
		}
		return maps;
	}
	
	
}
