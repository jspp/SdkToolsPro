package com.jf.game.controller;

import com.jf.game.subPackage.FileUtil;
import com.jf.game.support.StreamGobbler;
import com.jf.game.utils.DateUtil;
import com.jf.game.utils.PropertiesUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class SignApkController extends BaseController implements Initializable {
    @FXML
    private ComboBox choiceGame;
    private String filePath;
    @FXML
    private Button signButton;
    static  Logger logger = LoggerFactory.getLogger(SignApkController.class);
    /**
     * 选择文件
     *
     * @param event
     */
    @FXML
    public void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("请选择APK文件，老铁别看错了，目前分包只支持 Android");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.apk"));
        Stage selectFile = new Stage();
        File file = fileChooser.showOpenDialog(selectFile);
        if (file != null) {
            try {
                filePath = file.getPath();
            } catch (Exception e) {
                logger.error("选择文件出错了",e);
            }
        }
    }

    /**
     * 跳转到融合功能
     *
     * @param event
     */
    @FXML
    public void backToIndex(ActionEvent event) {
        backToIndexPage();
    }
    /**
     * 开始签名
     * @param event
     */
    @FXML
    public void startSign(ActionEvent event) {
        try {
            String sourcefilePathStr = filePath;
            if (sourcefilePathStr == null || sourcefilePathStr.length() == 0) {
                super.alert("没选择游戏包，赐不了你签名。");
                return;
            }
            if (!sourcefilePathStr.endsWith("apk")) {
                super.alert("不是APK文件，赐不了你签名。");
                return;
            }
            logger.info(" startSign 开始签名文件..................."+filePath);
            // E:\sdk_tools_202004\Pipiwan341_792_nosign.apk
            String sourceFileName = "";
            String sourceFilePath="";// 源文件存储路径 默认带斜杠
            String newSignedFileName = "";
            String newFileAlignName = "";
            String newFileFullPath_sign = "";
            String newFileFullPath_align = "";
            String baseConfigPath = PropertiesUtil.getValue("sign.jre.path");
            logger.info(" start baseConfigPath={}...................",baseConfigPath);
            if(sourcefilePathStr.contains("\\")){
                sourceFilePath = sourcefilePathStr.substring(0,sourcefilePathStr.lastIndexOf("\\")+1);
                sourceFileName = sourcefilePathStr.substring(sourcefilePathStr.lastIndexOf("\\")+1,sourcefilePathStr.length());
            }
            logger.info("解析文件01........sourceFileName={}=====........",sourceFileName);
            newSignedFileName = sourceFileName.substring(0,sourceFileName.lastIndexOf("."))+"signed_"+ DateUtil.getCurrentDateTime("MMddHHmmss") +".apk";
            newFileAlignName = sourceFileName.substring(0,sourceFileName.lastIndexOf("."))+"_ssa_"+ DateUtil.getCurrentDateTime("ddHHmmss") +".apk";
            newFileFullPath_sign = sourceFilePath+newSignedFileName;
            newFileFullPath_align = sourceFilePath+newFileAlignName;
            logger.info("解析文件路径完毕02..................."+sourceFileName+" "+sourceFilePath);
            File dir = new File(baseConfigPath+"bin");//此处是指定路径
            String[] cmd = new String[] { "cmd", "/c",
                    "jarsigner.exe -verbose " +
                    "-keystore " + baseConfigPath+"/GameLeveling.keystore " +
                    "-storepass 3yx.com " +
                    "-keypass 3yx.com " +
                    "-signedjar "+newFileFullPath_sign+" " +
                            sourcefilePathStr+" " +
                    "gameleveling"+
                    " & zipalign.exe -f -v 4 " +
                            newFileFullPath_sign+" " +
                            newFileFullPath_align};
            logger.info(" 签名命令组装完成..............."+ Arrays.toString(cmd));
            Process process = Runtime.getRuntime().exec(cmd,null,dir);
            // any error message?
            StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "ERROR",this);
            // any output?
            StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), "OUTPUT",this);
            // kick them off
            errorGobbler.start();
            outputGobbler.start();
            int rss = process.waitFor();  //等待执行完成
            if(rss==0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("系统提示");
                alert.setHeaderText("签名结果");
                alert.setContentText(" 【成功】签名成功了，是否直接打开对应文件夹. ");
                alert.initStyle(StageStyle.UTILITY);
                Optional result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    logger.info(" 准备删除 签名初始文件 (未对齐) ");
                    FileUtil.deleteFile(newFileFullPath_sign);
                    File newApkPath = new File(newFileFullPath_align);
                    Desktop.getDesktop().open(newApkPath.getParentFile());
                } else {
                    logger.info(" 签名完毕，不需要打开文件夹 ");
                }
            }else {
                super.alert("签名操作失败，请查看日志信息。");
            }

        } catch (Throwable e) {
            logger.error(" 签名执行出错",e);
        }
    }
    /**
     * 初始化数据
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化上次 选择APK 的路径 是否还存在
        logger.info(" SignApkController 初始化完毕...................");
    }

}
