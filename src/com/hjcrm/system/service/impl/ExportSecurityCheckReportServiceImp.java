package com.hjcrm.system.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Service;

import com.hjcrm.system.service.ExportSecurityCheckReportService;
@Service
public class ExportSecurityCheckReportServiceImp implements ExportSecurityCheckReportService{

	@Override
	public String exportReport(String filePath, String filePre) {
		String path = "/tmp/";
		
		// 压缩包名
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        String fileFullName  = path + filePre + dateString + ".zip";
        File zipFile = new File(fileFullName);
        File sourceFile = new File(filePath);  
        BufferedInputStream bis = null;
        ZipOutputStream zos = null;
        try {
        	  File[] sourceFiles = sourceFile.listFiles(); 
              if ((sourceFiles == null) || (sourceFiles.length < 1)) {
              	return null;
              }
              FileOutputStream fos = new FileOutputStream(zipFile);
              zos = new ZipOutputStream(fos);
              byte[] bufs = new byte[1024*10];  
              for(int i=0;i<sourceFiles.length;i++){  
                  //创建ZIP实体，并添加进压缩包  
                  ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());  
                  zos.putNextEntry(zipEntry);  
                  //读取待压缩的文件并写进压缩包里  
                  FileInputStream fis = new FileInputStream(sourceFiles[i]);  
                  bis = new BufferedInputStream(fis, 1024*10);  
                  int read = 0;  
                  while((read=bis.read(bufs, 0, 1024*10)) != -1){  
                      zos.write(bufs,0,read);  
                  }  
              } 
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        } catch (IOException e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        } finally{  
            //关闭流  
            try {  
                if(null != bis) bis.close();  
                if(null != zos) zos.close(); 

            } catch (IOException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            }  
        } 
        System.out.print(fileFullName);
        return fileFullName;
	}

}
