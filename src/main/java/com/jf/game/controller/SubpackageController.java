package com.jf.game.controller;

import com.jf.game.config.ApplicationContext;
import com.jf.game.pojo.Game;
import com.jf.game.subPackage.ApkUtils;
import com.jf.game.support.AutoCompleteComboBoxListener;
import com.jf.game.support.InitGameDataTask;
import com.jf.game.support.ProgressFrom;
import com.jf.game.utils.DateUtil;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SubpackageController extends BaseController implements Initializable {
    static Logger logger = LoggerFactory.getLogger(SubpackageController.class);
    @FXML
    private ComboBox choiceGame;

    @FXML
    private ImageView img;


    private String filePath;

    @FXML
    private Button subPackageBtn;

    @FXML
    private RadioButton egame;  //小易

    @FXML
    private RadioButton jfgame;// 绝峰游戏

    //选择的项目类型
    private String selectProjectType;
    // 项目类型 单选组
    final ToggleGroup group = new ToggleGroup();
    // 临时保存选择的游戏的值
    static  Game teamp_chooseGame = null;
    /**
     * 选择文件
     *
     * @param event
     */
    @FXML
    public void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("请选择APK文件，老铁别看错了是APK，目前分包只支持 Android");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.apk"));
        Stage selectFile = new Stage();
        File file = fileChooser.showOpenDialog(selectFile);
        if (file != null) {
            try {
                filePath = file.getPath();
            } catch (Exception e) {
                logger.error(" 操作出错了 ",e);
            }
        }
    }

    /**
     * 跳转到融合功能
     *
     * @param event
     */
    @FXML
    public void toMix(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/mix.fxml"));
            ApplicationContext.primaryStage.close();
            ApplicationContext.primaryStage.setTitle(ApplicationContext.softName);
            ApplicationContext.primaryStage.setScene(new Scene(root, 895, 625));
            ApplicationContext.primaryStage.show();
        } catch (IOException e) {
            logger.error(" 操作出错了 ",e);
        }
    }

    /**
     * 去到一键签名页面
     * @param event
     */
    @FXML
    public void toSignApk(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/sign_apk.fxml"));
            ApplicationContext.primaryStage.setScene(new Scene(root, 460, 194));
            ApplicationContext.primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/imgs/20180724111034.png")));
            ApplicationContext.primaryStage.setResizable(false);
            ApplicationContext.primaryStage.setTitle("一键签名");
            ApplicationContext.primaryStage.show();
        } catch (IOException e) {
            logger.error(" 操作出错了 ",e);
        }
    }
    /**
     *  apk tools 打包
     * @param event
     */
    @FXML
    public void toApkTools(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/apktools_package.fxml"));
            ApplicationContext.primaryStage.setScene(new Scene(root, 460, 194));
            ApplicationContext.primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/imgs/20180724111034.png")));
            ApplicationContext.primaryStage.setResizable(false);
            ApplicationContext.primaryStage.setTitle("Apktools 打包");
            ApplicationContext.primaryStage.show();
        } catch (IOException e) {
            logger.error(" 操作出错了 ",e);
        }
    }
    /**
     * 分包
     *
     * @param event
     */
    @FXML
    public void subPackage(ActionEvent event) {
        try {
            String gameInfo = "";
            Game game = (Game) choiceGame.getValue();
            if (game != null) {
                gameInfo = game.getGameId();
            }
            String filePathStr = filePath;
            if (filePathStr == null || filePathStr.length() == 0) {
                super.alert("没选择母包，分不了包");
                return;
            }
            if (gameInfo == null || gameInfo.length() == 0) {
                super.alert("游戏信息为空，分不了包");
                return;
            }
            // 复制文件
            String parentPackageName = "";
            String subPackageName = "";
            String basepackagePath = "";
            parentPackageName = filePathStr.substring(filePathStr.lastIndexOf(File.separator) + 1, filePathStr.length());
            basepackagePath = filePathStr.substring(0, filePathStr.lastIndexOf(File.separator)) + File.separator;
            subPackageName = parentPackageName.substring(0, parentPackageName.indexOf(".")) + "_" + DateUtil.getCurrentDateTime("yyyyMMddHHmm") + ".apk";
            System.out.println("当前母包位置：" + filePathStr + "   parentPackageName=" + parentPackageName + "   basepackagePath=" + basepackagePath + "   subPackageName=" + subPackageName);
            //FileUtil.copyFile(filePathStr,basepackagePath+subPackageName,true);

            /*
            Button commit  = (Button) event.getSource();
            commit.setText("正在分包...");
            commit.setDisable(true);
            */

            // 新文件写入 分包信息
            ApkUtils apkUtils = new ApkUtils();
            apkUtils.addStrToApkByComment(filePathStr, basepackagePath + subPackageName, gameInfo.trim() + "_"+ApplicationContext.getAgentId()+"_v1");
            // 提示分包完毕
            super.alert("恭喜你分包完成。");
            // 打开文件夹
            String path = basepackagePath;
            Desktop.getDesktop().open(new File(basepackagePath));
        } catch (Exception e) {
            super.alert("出bug了"+e.getMessage());
            logger.error(" 操作出错了 ",e);
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
    /*    Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ObservableList<Game> list = FXCollections.observableArrayList(ApplicationContext.getGameList());
                choiceGame.getItems().addAll(list);
            }
        });*/
        InitGameDataTask initGameDataTask = new InitGameDataTask(choiceGame,ApplicationContext.defaultType);
        new Thread(initGameDataTask).start();

/*      Game initGame = new Game();
        initGame.setGameId("");
        initGame.setGameName("请选择游戏");
        choiceGame.setValue(initGame);*/
        choiceGame.converterProperty().set(new StringConverter<Game>() {
            @Override
            public String toString(Game object) {
                return object!=null?object.getGameName():"";
            }

            @Override
            public Game fromString(String string) {
                return null;
            }
        });

        egame.setToggleGroup(group);
        jfgame.setToggleGroup(group);
        // 选择项目事件
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> changed, Toggle oldVal, Toggle newVal)
            {
                RadioButton temp_rb=(RadioButton)newVal;
                System.out.println("当前选择的项目是："+temp_rb.getText() +"  " + temp_rb.getId());
                ApplicationContext.defaultType = temp_rb.getId();

                InitGameDataTask gameDataTask = new InitGameDataTask(choiceGame,temp_rb.getId());
                ProgressFrom progressFrom = new ProgressFrom(gameDataTask,ApplicationContext.primaryStage);
                progressFrom.activateProgressBar();
            }
        });

        choiceGame.setVisibleRowCount(20);
        // 选择为空是 默认选中上一次选中的值
        choiceGame.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{
            choiceGame.setVisibleRowCount(10); // 显示20行
            if(newVal != null && newVal !=oldVal ){
                teamp_chooseGame = (Game) newVal;
            }
            if(newVal==null && !choiceGame.isFocused()) {
                Platform.runLater(() -> choiceGame.setValue(teamp_chooseGame));
            }
        });
        // 搜索监听
        new AutoCompleteComboBoxListener(choiceGame);

       subPackageBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                ApplicationContext.primaryStage.getScene().setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
       /* final javafx.scene.image.Image image2 = new Image(Main.class.getResourceAsStream("./style/20180724111034.png"));
        img.setImage(image2);*/
    }

}
