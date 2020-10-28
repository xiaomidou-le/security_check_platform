package com.hjcrm.publics.jpush.pool;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hjcrm.publics.websocket.controller.TaskWebSocketManager;
import com.hjcrm.publics.websocket.entity.WebSocketNeedBean;


/**
 *  推送消息到客户端时  先使用当前的线程池把 数据推送到websocket服务端  然后websocket服务端再使用线程池把消息推送到客户端
 * @author 
 * @date 2020-9-17 上午11:06:41
 */
public class TaskPushInfoThreadPool {
	private static ExecutorService taskthreadPool = null;
	
	static{
		taskthreadPool = Executors.newFixedThreadPool(10);
	}
	
	/**
	 * 执行消息推送
	 * @param paramMap 组装消息参数
	 * @author  
	 * @date 2020-9-17 上午11:06:55
	 */
	public static void executePushInfoTask(final Map<String,Object> paramMap){
		taskthreadPool.execute(new Runnable(){
			public void run() {
				Class clazz = WebSocketNeedBean.class;
				WebSocketNeedBean needBean = new WebSocketNeedBean();
				Set<Entry<String,Object>> entrySet = paramMap.entrySet();
				Iterator<Entry<String, Object>> iterator = entrySet.iterator();
				while(iterator.hasNext()){
					Entry<String, Object> entry = iterator.next();
					String key = entry.getKey();
					Object value = entry.getValue();
					try {
						Field declaredField = clazz.getDeclaredField(key);
						if(declaredField != null){
							declaredField.setAccessible(true);
							declaredField.set(needBean, value);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				TaskWebSocketManager.pushInfoToClient(needBean);
			}
		});
	}	
}
