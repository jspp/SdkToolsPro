package com.jf.game.controller;

import com.jf.game.support.StreamGobbler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
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
            String filePathStr = filePath;
            if (filePathStr == null || filePathStr.length() == 0) {
                super.alert("没选择游戏包，赐不了你签名。");
                return;
            }
            if (!filePathStr.endsWith("apk")) {
                super.alert("不是APK文件，赐不了你签名。");
                return;
            }
            logger.info(" startSign 开始签名文件..................."+filePath);
            // E:\sdk_tools_202004\Pipiwan341_792_nosign.apk
            String sourceFileName = "";
            String sourceFilePath="";// 源文件存储路径 默认带斜杠
            String newFileName = "";
            String baseConfigPath = getClass().getResource("/signJavaRunTime/").getPath();
            if(filePathStr.contains("\\")){
                sourceFilePath = filePathStr.substring(0,filePathStr.lastIndexOf("\\")+1);
                sourceFileName = filePathStr.substring(filePathStr.lastIndexOf("\\")-1,filePathStr.length());
            }
            logger.info("解析文件路径完毕..................."+sourceFileName+" "+sourceFilePath);
            File dir = new File(baseConfigPath+"/bin");//此处是指定路径
            String[] cmd = new String[] { "cmd", "/c",
                    "jarsigner.exe -verbose " +
                    "-keystore " + baseConfigPath+"/GameLeveling.keystore " +
                    "-storepass 3yx.com " +
                    "-keypass 3yx.com " +
                    "-signedjar "+sourceFilePath+newFileName+" " +
                    filePathStr+" " +
                    "gameleveling"};// cmd[2]是要执行的dos命令
            logger.info(" 签名命令组装完成..............."+cmd.toString());
            Process process = Runtime.getRuntime().exec(cmd,null,dir);
            // any error message?
            StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "ERROR",this);
            // any output?
            StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), "OUTPUT",this);
            // kick them off
            errorGobbler.start();
            outputGobbler.start();
            process.waitFor();  //等待执行完成
        } catch (Exception e) {
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
