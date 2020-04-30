package com.jf.game.subPackage;


/**
 * SDK 分包配置信息
 * @author sun
 * TODO
 * Jul 18, 2017
 */
public class SubConfig {
    /**
     * SDK 包存放的路劲  【固定前缀】
     */
	public static String SAVE_PATH="D:/data/sub/";
	
	public static String APP_FILE_PATH_ANDROID ="/META-INF/sdk_66096/sdkInfo.txt";
	
	public static String APP_FILE_PATH_IOS ="/sdkInfo.txt";
	
	
	public static String APP_CLOUD_PATH="/sdks/";
	
	// IOS 上传母包位置  = IOS_CLOUD_PARENT_PATH + 母包名称
	public static String IOS_CLOUD_PARENT_PATH="/sdks/iosparent/";
	
	public static String ZIP_FILE_PATH="/ZIP";
	
	public static String PLIST_PATH="D:/data/sub/plist/";

    /**
     * window linux 判断
     */
	static{
		if(System.getProperty("os.name").toLowerCase().contains("window")){
			// window 操作系统
		}else{
			SAVE_PATH="/usr/java/sub/";
			PLIST_PATH="/usr/java/sub/plist/";
		}
	}
    /**
     * android ios 手机文件保存位置区分
     * @param clientType
     * @return
     * @author sun
     * Jul 24, 2017
     */
	public static String getAppFilePath(int clientType){
		if(GameClientType.IOS_3.getId()==clientType){
			return APP_FILE_PATH_IOS;
		}
		return APP_FILE_PATH_ANDROID;
	}
	
}
