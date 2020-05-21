package com.jf.game.support;

import com.jf.game.controller.BaseController;
import com.jf.game.controller.MixController;
import com.jf.game.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;

/**
 * ouyangjie
 * 2020/5/18
 * 16:16
 */
public class ApktoolsUtils {

    static  Logger logger = LoggerFactory.getLogger(ApktoolsUtils.class);

    public static void buildAppByApktool(String sourcefilePathStr, BaseController baseContext){
        try {
            logger.info(" apktools 打包文件 文件..................."+sourcefilePathStr);
            // E:\sdk_tools_202004\Pipiwan341_792_nosign.apk
            String apktoolPath = PropertiesUtil.getValue("sign.apktools.path");
            String apktoolName = PropertiesUtil.getValue("sign.apktools.jar.name");
            File dir = new File(apktoolPath);//此处是指定路径
            String[] cmd = new String[] { "cmd", "/c", "java -jar "+apktoolName+" b -f "+sourcefilePathStr};
            logger.info(" 签名命令组装完成..............."+ Arrays.toString(cmd));
            final Process process = Runtime.getRuntime().exec(cmd,null,dir);
            // any error message?
            StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "ERROR", baseContext);
            // any output?
            StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), "OUTPUT",baseContext);
            // kick them off
            errorGobbler.start();
            outputGobbler.start();
            int rss = process.waitFor();  //等待执行完成
            if(baseContext instanceof MixController){ // 融合
                MixController mFace = (MixController)baseContext;
                if(rss==0){
                    mFace.showLog(" 打包成功。 ");
                }else {
                    mFace.showLog(" 手动打包，请查看日志信息。 ");
                }
            }
            process.getOutputStream().close();  // 不要忘记了一定要关
        } catch (Throwable e) {
            logger.error(" 打包失败了--------------------------- ",e);
        }
    }
}
