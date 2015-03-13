package cn.app118.framework.util;

import org.apache.log4j.Logger;

import cn.app118.constants.SystemConstant;
import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

public class JPushUtil {
	private static Logger log = Logger.getLogger(JPushUtil.class);//日志
	
	public static String pushMsg(){
		JPushClient jpushClient = new JPushClient(SystemConstant.JPUSH_MASTER_SECRET, SystemConstant.JPUSH_APPKEY, 3);
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = PushPayload.alertAll("JPush测试。");
        try {
            PushResult result = jpushClient.sendPush(payload);
            log.info("Got result - " + result);
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            log.error("Connection error, should retry later", e);
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            log.error("Should review the error, and fix the request", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
        }
		return "";
	}
	
	public static void main(String[] args) {
		JPushUtil.pushMsg();
	}
}
