package com.hjcrm.system.service.impl;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjcrm.system.service.IStartSecurityCheckService;
@Service
@Transactional(rollbackFor = Exception.class)
public class StartSecurityCheckServiceImp implements IStartSecurityCheckService{

	@Override
	public void startSecurityCheck(Integer usecaseNo) {
        try {      	
        	//String shell = "inspec exec /Users/wangjing1/work/auto_check/inspec_test/my_puppet --reporter html:/Users/wangjing1/work/auto_check/www/out3.html";
        	//String shell = "cat /Users/wangjing1/work/work_path_auto_check/inspec_check.sh";
        	String shell = "/bin/sh /Users/wangjing1/work/work_path_auto_check/inspec_check.sh";
  
            //String[] cmd = {"inspec","exec", "/Users/wangjing1/work/auto_check/inspec_test/my_puppet","--reporter", "html:/Users/wangjing1/work/auto_check/www/out3.html"};
            //String[] cmd = {"inspec","exec", "/Users/neng.cao/Downloads/inspec_test/my_puppet" ,"--reporter", "html:/Users/neng.cao/Documents/out3.html"};

        	Process process = Runtime.getRuntime().exec(shell);
        	
            // 以下为调试代码   
            InputStream fis=process.getInputStream();    
           //用一个读输出流类去读    
            InputStreamReader isr=new InputStreamReader(fis);    
           //用缓冲器读行    
            BufferedReader br=new BufferedReader(isr);    
            String line=null;    
           //直到读完为止    
           while((line=br.readLine())!=null)    
            {    
                System.out.println(line);    
            }
           
           // 以下为调试代码   
           InputStream fis1=process.getErrorStream();    
          //用一个读输出流类去读    
           InputStreamReader isr1=new InputStreamReader(fis1);    
          //用缓冲器读行    
           BufferedReader br1=new BufferedReader(isr1);    
           String line1=null;    
          //直到读完为止    
          while((line1=br1.readLine())!=null)    
           {    
               System.out.println(line1);    
           }
            int exitValue = process.waitFor();
            if (0 != exitValue) {
                System.out.println("=============================================call shell failed. error code is :" + exitValue);
                return ;
            }
            System.out.println("=============================================编译结束");
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
