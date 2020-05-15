package com.jf.game.controller;

import com.jf.game.support.StreamGobbler;
import com.jf.game.utils.PropertiesUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * APK tool 手动打包
 */
public class ApktoolsController extends BaseController implements Initializable {
    @FXML
    private ComboBox choiceGame;
    private String filePath;
    @FXML
    private Button fileButton;
    @FXML
    private Button signButton;
    static  Logger logger = LoggerFactory.getLogger(ApktoolsController.class);
    /**
     * 选择文件
     *
     * @param event
     */
    @FXML
    public void chooseFile(ActionEvent event) {
        DirectoryChooser fileChooser = new DirectoryChooser();
        String basePath = PropertiesUtil.getValue("base.work.path");
        if(StringUtils.isNotBlank(basePath)){
            fileChooser.setInitialDirectory(new File(basePath));
        }
        fileChooser.setTitle("------------------请选择已经反编译的游戏包文件夹--------------------");
        Stage selectFile = new Stage();
        File file = fileChooser.showDialog(selectFile);
        if (file != null) {
            try {
                filePath = file.getPath();
                Optional.ofNullable(filePath).ifPresent(path->{
                    fileButton.setText(filePath);
                });
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
    public void startPackage(ActionEvent event) {
        try {
            String sourcefilePathStr = filePath;
            if (sourcefilePathStr == null || sourcefilePathStr.length() == 0) {
                super.alert("没选择游戏包，无法开始打包。");
                return;
            }
            logger.info(" apktools 打包文件 文件..................."+filePath);
            // E:\sdk_tools_202004\Pipiwan341_792_nosign.apk
            String apktoolPath = PropertiesUtil.getValue("sign.apktools.path");
            String apktoolName = PropertiesUtil.getValue("sign.apktools.jar.name");
            File dir = new File(apktoolPath);//此处是指定路径
            String[] cmd = new String[] { "cmd", "/c",
                    "java -jar "+apktoolName+" b -f "+sourcefilePathStr};
            logger.info(" 签名命令组装完成..............."+ Arrays.toString(cmd));
            final Process process = Runtime.getRuntime().exec(cmd,null,dir);
            // any error message?
            StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "ERROR", this);
            // any output?
            StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), "OUTPUT",this);
            // kick them off
            errorGobbler.start();
            outputGobbler.start();
            int rss = process.waitFor();  //等待执行完成
            if(rss==0){
                alert("手动打包 完成","系统提示");
            }else {
                alert("手动打包，请查看日志信息。","系统提示");
            }
        } catch (Throwable e) {
            logger.error(" 执行出错",e);
        }
    }
    /**
     * 初始化数据
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info(" ApktoolsController    手动打包   初始化完毕...................");
    }

}
