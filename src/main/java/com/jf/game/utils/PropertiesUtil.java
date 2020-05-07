package com.jf.game.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
/**
 * 属性文件读取工具类
 */
public final class PropertiesUtil {
    private static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * 全局配置数据
     */
    private static  Properties GLOBAL_DATA;
    /**
     * 获取全局配置数据
     * @return 全局配置数据
     */
    public static Properties getGlobalData() {
        return GLOBAL_DATA;
    }
    /**
     * 加载属性文件
     */
    public static void load(String propsFileName) {
        try {
            GLOBAL_DATA = new Properties();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            GLOBAL_DATA.load(classLoader.getResourceAsStream(propsFileName));
            log.info(" 配置文件初始化完成。");
        } catch (Exception e) {
            log.error("加载属性文件【 " + propsFileName + "】失败！", e);
        }
    }
    /**
     * 获取全局的值
     * @param key
     * @return str value if null return ""
     * @author i3yx@qq.com
     */
    public static String getValue(String key){
        String rs = "";
        if(GLOBAL_DATA!=null && GLOBAL_DATA.containsKey(key)){
            rs = GLOBAL_DATA.getProperty(key, "");
        }
        return rs;
    }
}
