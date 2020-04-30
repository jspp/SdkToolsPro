package com.jf.game.support;

import com.jf.game.controller.BaseController;
import com.jf.game.controller.MixController;
import com.jf.game.controller.SignApkController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 获取输出日志
 * ouyangjie
 * 2020/4/30
 * 16:19
 */
public class StreamGobbler extends Thread {
    Logger logger = LoggerFactory.getLogger(StreamGobbler.class);
    InputStream is;
    String type;
    BaseController face;

    public StreamGobbler(InputStream is, String type,BaseController face) {
        this.is = is;
        this.type = type;
        this.face = face;
    }
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is,"GBK");
            logger.info(" 开始打印CMD日志........ ");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null){
                logger.info(type + ">" + line);
                if(face instanceof  MixController){ // 融合
                    MixController mFace = (MixController)face;
                    mFace.showLog(type + ">" + line);
                }
            }
        } catch (Exception ioe) {
            logger.error(" 日志输出执行出错",ioe);
        }
    }
}