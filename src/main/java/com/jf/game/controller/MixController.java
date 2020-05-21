package com.jf.game.controller;

import com.jf.game.config.ApplicationContext;
import com.jf.game.config.ScreenDirection;
import com.jf.game.config.SdkVersion;
import com.jf.game.pojo.Game;
import com.jf.game.subPackage.FileUtil;
import com.jf.game.subPackage.XmlUtils;
import com.jf.game.support.*;
import com.jf.game.utils.DateUtil;
import com.jf.game.utils.PropertiesUtil;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class MixController extends BaseController implements Initializable {
    static Logger logger = LoggerFactory.getLogger(MixController.class);
    /**
     * 反编译的游戏文件夹
     */
    private String gameDirPath;
    /**
     * 需要覆盖的文件夹
     */
    private String replaceDirPath;
    @FXML
    private ComboBox choiceGame;
    @FXML
    private Button fileButton_replace;
    @FXML
    private Button fileButton_game;
    @FXML
    private TextArea logInfo; // 日志信息
    //换行
    static  String newLine = "    \n ";
    // 融合基础的目录
    public  static  String baseFilePath = "E:\\";
    // 项目类型 单选组
    final ToggleGroup group_project = new ToggleGroup();
    @FXML
    private RadioButton egame;  // 皮皮玩

    // 版本选择 单选组
    final ToggleGroup group_version = new ToggleGroup();
    @FXML
    private RadioButton v420;  // 新版本-带小号

    // 小号功能 屏幕方向 仅针对 新版有效
    @FXML
    private ChoiceBox<String>  screenSmallAccount;

    // 初始化 屏幕方向 仅针对 新版有效
    @FXML
    private ChoiceBox<String>  screenInitPage;


    // 是否替换内容 标志
    static  String sdkVersion = SdkVersion.v420;
    // 临时保存选择的游戏的值
    static  Game teamp_chooseGame = null;


    /**
     * 初始化
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String basePath = PropertiesUtil.getValue("mix.work.path");
        if(StringUtils.isNotBlank(basePath)){
            baseFilePath = basePath;
        }
        logInfo.appendText(" 融合游戏比较耗资源（有点卡）。请保证你的磁盘空间足够。"+newLine );
        logInfo.appendText(" 4.3.0 版本发布了 "+newLine+"1、修复浮点问题"+newLine+" 2、优化游戏包更新... " +newLine);
        logInfo.appendText(newLine );

        // 异步初始化数据
        InitGameDataTask initGameDataTask = new InitGameDataTask(choiceGame,ApplicationContext.defaultType);
        new Thread(initGameDataTask).start();

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
        // 选择为空是 默认选中上一次选中的值
        choiceGame.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{
            choiceGame.setVisibleRowCount(20);
            if(newVal != null && newVal !=oldVal ){
                teamp_chooseGame = (Game) newVal;
            }
            if(newVal==null && !choiceGame.isFocused()){
                Platform.runLater(() -> choiceGame.setValue(teamp_chooseGame));
            }
        });

        showLog(" 初始化游戏数据成功 ");
        // 初始化组件
        initComponent();
        // 按钮组
        initGroups();
        // 对应的事件
        initGroupEvent();
        // 只展示 10 行
        choiceGame.setVisibleRowCount(10);

        new AutoCompleteComboBoxListener(choiceGame);
    }
    /**
     * 开始融合
     * 2018 08 31 新增疑难游戏融合兼容 layout-11 layout-17兼容
     * 2018 08 31 新增配置文件自动备份
     *
     * 2020 04 新增浮窗优化，Android10 游戏包更新
     */
    @FXML
    public void startMix() {
        try {

            String filePathStr = replaceDirPath;
            if (filePathStr == null || filePathStr.length() == 0) {
                super.alert("没选择需要替换的【配置文件夹】。 ");
                return;
            }
            printAndShowLog("需要替换的文件：" + filePathStr + "   gameDirPath=" + gameDirPath);

            filePathStr = gameDirPath;
            if (filePathStr == null || filePathStr.length() == 0) {
                super.alert("没选择已经反编译的【游戏包文件夹】。 ");
                return;
            }
            if (choiceGame.getValue() == null ) {
                super.alert("请选择游戏信息。 ");
                return;
            }
            Game game = (Game) choiceGame.getValue();

            String appId = game.getGameId();
            String appKey = game.getAppKey();

            printAndShowLog("当前选择的游戏: "+game.getGameName());

            printAndShowLog("【覆盖或新增】 layout layout-land ");

            FileUtil.copyDirIfResourceExsit(replaceDirPath + "/layout", gameDirPath + "/res/layout", true);

            FileUtil.copyDirIfResourceExsit(replaceDirPath + "/layout-land", gameDirPath + "/res/layout-land", true);


            printAndShowLog("【覆盖或新增】 drawable ");
            FileUtil.copyDirIfResourceExsit(replaceDirPath + "/drawable", gameDirPath + "/res/drawable", true);

            printAndShowLog("【覆盖或新增】 anim ");
            FileUtil.copyDirIfResourceExsit(replaceDirPath + "/anim", gameDirPath + "/res/anim", true);

            printAndShowLog("【覆盖或新增】 assets ");
            FileUtil.copyDirIfResourceExsit(replaceDirPath + "/assets", gameDirPath + "/assets", true);

            printAndShowLog("【清空且复制】 class");
            FileUtil.deleteAllFilesOfDir(new File(gameDirPath + "/smali/com/juefeng/sdk/juefengsdk/"));
            FileUtil.copyDirIfResourceExsit(replaceDirPath + "/class", gameDirPath + "/smali/com/juefeng/sdk/juefengsdk/", true);

            /*printAndShowLog("【皮皮玩】 新增 support 包 ");
            if(ApplicationContext.defaultType.equals("egame")){
                  showLog("egame 准备新增  ");
                  System.out.println("egame 准备新增  ");
                  FileUtil.copyDirIfResourceExsitAndDestNoExsit(replaceDirPath + "/design", gameDirPath + "/smali/android/support/design", true);
            }*/
            //  color 文件调整
           /* if(FileUtil.isExsit(replaceDirPath + "/colors.xml")){
                modifyColorsXml();
            }*/
            //  是否替换 mainfest 文件内容
            modifyMainFestXml(appId, appKey);

            // xml 文件处理  存在不处理，否则复制
            if(!FileUtil.isExsit(gameDirPath + "/res/xml/file_paths.xml")){
                FileUtil.copyFile(replaceDirPath + "/xml/file_paths.xml", gameDirPath + "/res/xml/file_paths.xml", true);
            }
            /*// 处理游戏兼容问题 layout11
            if(FileUtil.isExsit(gameDirPath + "/res/layout-v11/sdk_dialog_bind_phone_tip.xml")){
                printAndShowLog("layout11 准备替换");
                FileUtil.copyFile(replaceDirPath + "/layout/sdk_dialog_bind_phone_tip.xml", gameDirPath + "/res/layout-v11/sdk_dialog_bind_phone_tip.xml", true);
            }
            // 处理游戏兼容问题 layout17
            if(FileUtil.isExsit(gameDirPath + "/res/layout-v17/sdk_activity_pay_layout.xml")){
                printAndShowLog("layout17  准备替换");
                FileUtil.copyFile(replaceDirPath + "/layout/sdk_activity_pay_layout.xml", gameDirPath + "/res/layout-v17/sdk_activity_pay_layout.xml", true);
            }*/
            // 游戏解压或的目录名称
            String gameDirName = gameDirPath;
            if(gameDirPath.contains("\\")){
                gameDirName = gameDirPath.substring(gameDirPath.lastIndexOf("\\")+1,gameDirPath.length());
            }
            // 游戏打包后的 APK 名称
            String gameApkName = gameDirName+ DateUtil.getCurrentDateTime("yyyyMMdd") +".apk";

            showLog("元文件夹名称： "+gameDirName+"   融合后的APK名称为："+gameApkName);

        } catch (Exception e) {
            logger.error("当前操作出错了",e);
            super.errorAlert("当前操作出bug了 ", e);
        }
    }

    /**
     * 开始打包
     */
    @FXML
    public void startPackage() {
        try {
            String filePathStr = replaceDirPath;
            if (filePathStr == null || filePathStr.length() == 0) {
                super.alert("没选择需要替换的【文件夹】。 囧 ");
                return;
            }
            printAndShowLog("  startPackage 需要替换的文件：" + filePathStr + "   gameDirPath=" + gameDirPath);

            filePathStr = gameDirPath;
            if (filePathStr == null || filePathStr.length() == 0) {
                super.alert("没选择已经反编译的【游戏包文件夹】。 囧 ");
                return;
            }
            if (choiceGame.getValue() == null ) {
                super.alert("请选择游戏信息。 囧 ");
                return;
            }
            Game game = (Game) choiceGame.getValue();
            printAndShowLog("当前选择的游戏: "+game.getGameName());

            // 游戏解压或的目录名称
            String gameDirName = gameDirPath;
            /*if(gameDirPath.contains("\\")){
                gameDirName = gameDirPath.substring(gameDirPath.lastIndexOf("\\")+1,gameDirPath.length());
            }*/
            // 游戏打包后的 APK 名称
            //String gameApkName = gameDirName+ DateUtil.getCurrentDateTime("yyyyMMdd")+ new Random().nextInt(100) +".apk";

            printAndShowLog("元文件夹名称： "+gameDirName);


            printAndShowLog ("文件替换成功 开始打包 ");

            BuildByApktoolTask gameDataTask = new BuildByApktoolTask(gameDirName,this);
            ProgressFrom progressFrom = new ProgressFrom(gameDataTask,ApplicationContext.primaryStage);
            progressFrom.activateProgressBar();

        } catch (Exception e) {
            logger.error("当前操作出错了",e);
            super.errorAlert(" 当前操作出bug了 ", e);
        }
    }
    /**
     * mainfest 文件替换
     * @User: jspp@qq.com
     * @Date: 2018/9/11 13:27
     * @Desc
     * @Param
     */
    void modifyMainFestXml(String appId, String appKey) throws IOException {
        printAndShowLog("准备开始替换  AndroidManifest 内 appId, appKey ");
        Element manifest = XmlUtils.getRootElement(gameDirPath + "/AndroidManifest.xml");
        // showLog("新增权限  <uses-permission  android:name='xy.game.READ' />");
        Node permission = manifest.selectSingleNode("//uses-permission[@android:name='xy.game.READ']");
        if(permission==null){
            printAndShowLog(" 不存在权限 即将添加  uses-permission ");
            manifest.addElement("uses-permission").addAttribute("android:name","xy.game.READ");
        }else {
            printAndShowLog(" permission 已经存在 ");
        }
        // 浮窗权限
        Node float_window_permission = manifest.selectSingleNode("//uses-permission[@android:name='android.permission.SYSTEM_ALERT_WINDOW']");
        if(float_window_permission==null){
            showLog(" 浮窗权限 不存在权限 即将添加  uses-permission ");
            manifest.addElement("uses-permission").addAttribute("android:name","android.permission.SYSTEM_ALERT_WINDOW");
        }else {
            printAndShowLog(" 浮窗权限 permission 已经存在 ");
        }
        // 如果是皮皮玩，且不存在对应的activity 需要新增
        if(ApplicationContext.defaultType.equals("egame")){
            Node activity = manifest.selectSingleNode("//activity[@android:name='com.juefeng.sdk.juefengsdk.ui.activity.MyPtbActivity']");
            if(activity==null){ // 新增
                showLog("新增  平台币 activity");
                System.out.println("  新增  平台币 MyPtbActivity  ");
                Element application = manifest.element("application");
                application.addElement("activity")
                        .addAttribute("android:name","com.juefeng.sdk.juefengsdk.ui.activity.MyPtbActivity")
                        .addAttribute("android:screenOrientation","portrait");
            }else{
                printAndShowLog(" 平台币 activity 已经存在 ");
            }
        }

        // 如果是皮皮玩，且不存在对应的activity 需要新增- 小号
        if(ApplicationContext.defaultType.equals("egame")){
            if(sdkVersion.equalsIgnoreCase(SdkVersion.v420)){  // 4.2 版本才支持小号
                Node activity = manifest.selectSingleNode("//activity[@android:name='com.juefeng.sdk.juefengsdk.ui.activity.SmallNumberActivity']");
                Element application = manifest.element("application");
                if(activity==null){ // 新增
                    showLog("新增  小号 SmallNumberActivity");
                    System.out.println("  新增  小号  SmallNumberActivity  ");
                    if(screenSmallAccount.getValue().equalsIgnoreCase(ScreenDirection.landscape)){
                        application.addElement("activity")
                                .addAttribute("android:name","com.juefeng.sdk.juefengsdk.ui.activity.SmallNumberActivity")
                                .addAttribute("android:theme","@android:style/Theme.Light.NoTitleBar.Fullscreen").addAttribute("android:screenOrientation","landscape");
                    }else{ //默认为竖向
                        application.addElement("activity")
                                .addAttribute("android:name","com.juefeng.sdk.juefengsdk.ui.activity.SmallNumberActivity")
                                .addAttribute("android:theme","@android:style/Theme.Light.NoTitleBar.Fullscreen").addAttribute("android:screenOrientation","portrait");
                    }
                }else{
                    printAndShowLog(" 小号  SmallNumberActivity 已经存在 ");
                }
                // 新增 游戏包更新文件权限 android.support.v4.content.FileProvider
                Node fileProvider = manifest.selectSingleNode("//provider[@android:name='android.support.v4.content.FileProvider']");
                if(fileProvider==null){
                    printAndShowLog("游戏包更新文件权限不存在，需要新增  v4.content.FileProvider");
                    String apkPackageName = "";
                    if(manifest.attributeValue("package")!=null){
                        apkPackageName = manifest.attributeValue("package");
                    }
                    Element provider =application.addElement("provider")
                            .addAttribute("android:name","android.support.v4.content.FileProvider")
                            .addAttribute("android:authorities",apkPackageName+".fileprovider")
                            .addAttribute("android:exported","false")
                            .addAttribute("android:grantUriPermissions","true");
                    // 添加 meta 属性
                    Element metaData = provider.addElement("meta-data")
                            .addAttribute("android:name","android.support.FILE_PROVIDER_PATHS")
                            .addAttribute("android:resource","@xml/file_paths");

                }else {
                    printAndShowLog("游戏包更新文件权限  已经存在  v4.content.FileProvider");
                }
            }
        }
        Node appIdNode = manifest.selectSingleNode("//meta-data[@android:name='JF_APPID']");
        Element   appIdEle =  (Element)appIdNode;
        appIdEle.attribute(1).setText(appId);

        Node appKeyNode = manifest.selectSingleNode("//meta-data[@android:name='JF_APPKEY']");
        Element   apk =  (Element)appKeyNode;
        apk.attribute(1).setText(appKey);

        // 是否修改初始化页面的 横竖屏方向
        if(screenInitPage.getValue().equalsIgnoreCase(ScreenDirection.portrait)){
            Node initActivityNode = manifest.selectSingleNode("//activity[@android:name='com.juefeng.sdk.juefengsdk.ui.activity.InitdataActivity']");
            Element   initActivityEle =  (Element)initActivityNode;
            initActivityEle.attribute("android:screenOrientation").setText(ScreenDirection.portrait); //强制设置为竖屏
            showLog("   InitdataActivity 强制设置为竖屏  ");
        }

        System.out.println(" 替换完毕  AndroidManifest " +appKeyNode.getStringValue());
        // 删除并生产xml
        FileUtil.deleteFile(gameDirPath + "/AndroidManifest.xml");
        XmlUtils.wirteStandLong(manifest.getDocument(),gameDirPath + "/AndroidManifest.xml");
    }

    /**
     * 打印日志 且展示 在 APP 界面
     * @User: jspp@qq.com
     * @Date: 2020/4/28 16:11
     */
    void printAndShowLog(String msg) {
        logger.error("=======>"+msg);
        showLog(msg);
    }

    /**
     * 替换 ColorsXml
     * @User: jspp@qq.com
     * @Date: 2018/9/11 13:25
     * @Desc
     * @Param
     */
    void modifyColorsXml() throws IOException {
        Element root_config = XmlUtils.getRootElement(replaceDirPath + "/colors.xml");
        System.out.println("root_config 初始化 "+root_config.asXML());

        Element root_game = XmlUtils.getRootElement(gameDirPath + "/res/values/colors.xml");
        System.out.println("root_game 初始化  "+root_game.asXML());
        List<Element> resources_game = root_game.elements();//所有一级子节点的list

        List<Element> resources_config = root_config.elements();//所有一级子节点的list
        if (resources_config != null && resources_config.size() > 0) { // add all sdk color val
            for (Element element_cfg : resources_config) {
                boolean contains = false;
                for (Element element_game : resources_game) {
                    if (element_game != null && element_game.attributeValue("name").equals(element_cfg.attributeValue("name"))) {
                        element_game.setText(element_cfg.getText()); // 替换色值
                        contains =true;
                        continue;
                    }
                }
                if(!contains){ //不包含 则【游戏配置文件中】添加节点
                    root_game.addElement("color")
                            .addText(element_cfg.getStringValue())
                            .addAttribute("name",element_cfg.attributeValue("name"));
                }
            }
        }
        System.out.println("root_game 最终效果"+root_game.asXML());
        // 删除并生产xml
        FileUtil.deleteFile(gameDirPath + "/res/values/colors.xml");
        XmlUtils.wirte(root_game.getDocument(),gameDirPath + "/res/values/colors.xml");
    }

/*    public   void buildAppByShaKatool(String sourceName,String targetName,String contextPath){
        BuildByShakaTask gameDataTask = new BuildByShakaTask(sourceName,targetName,contextPath,choiceGame,this);
        ProgressFrom progressFrom = new ProgressFrom(gameDataTask,ApplicationContext.primaryStage);
        progressFrom.activateProgressBar();
    }*/

    /**
     * 对应的事件
     * @User: jspp@qq.com
     * @Date: 2018/9/11 13:31
     * @Desc
     * @Param
     */
    private void initGroupEvent() {
        // 元素转换
        choiceGame.converterProperty().set(new StringConverter<Game>() {
            public String toString(Game object) {
                return object!=null?object.getGameName():"";
            }
            @Override
            public Game fromString(String string) {
                return null;
            }
        });
        Game initGame = new Game();
        choiceGame.setValue(initGame);
        // 选择【项目类型】事件
        group_project.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> changed, Toggle oldVal, Toggle newVal)
            {
                RadioButton temp_rb=(RadioButton)newVal;
                System.out.println("当前选择的项目是 ："+temp_rb.getText() +"  " + temp_rb.getId());
                ApplicationContext.defaultType = temp_rb.getId();

                InitGameDataTask gameDataTask = new InitGameDataTask(choiceGame,temp_rb.getId());
                ProgressFrom progressFrom = new ProgressFrom(gameDataTask,ApplicationContext.primaryStage);
                progressFrom.activateProgressBar();
            }
        });

        // 选择【SDK版本】事件
        group_version.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> changed, Toggle oldVal, Toggle newVal)
            {
                RadioButton temp_mf=(RadioButton)newVal;
                sdkVersion = temp_mf.getId().equals(SdkVersion.v400)?SdkVersion.v400:SdkVersion.v420;
                System.out.println("当前选择的    group_version ："+temp_mf.getText() +"  " + temp_mf.getId()+"  sdkVersion:"+sdkVersion);
            }
        });
    }

    /**
     * 初始化组件
     * @User: jspp@qq.com
     * @Date: 2018/9/11 13:30
     * @Desc
     * @Param
     */
    private void initComponent() {
        screenSmallAccount.setItems(FXCollections.observableArrayList("请选择屏幕方向","竖向","横向"));
        screenSmallAccount.setValue("请选择屏幕方向");
        screenInitPage.setItems(FXCollections.observableArrayList("请选择屏幕方向","竖向","横向"));
        screenInitPage.setValue("请选择屏幕方向");
    }

    /**
     * 单选按钮对应的组
     * @User: jspp@qq.com
     * @Date: 2018/9/11 13:30
     * @Desc
     * @Param
     */
    private void initGroups() {
        egame.setToggleGroup(group_project);

        v420.setToggleGroup(group_version);
    }

    /**
     * 选择文件
     * @param event
     */
    @FXML
    public void chooseFileGame(ActionEvent event) {
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setInitialDirectory(new File(baseFilePath));
        fileChooser.setTitle("------------------请选择已经反编译的游戏包文件夹--------------------");
        Stage selectFile = new Stage();
        File chosenDir = fileChooser.showDialog(selectFile);
        if (chosenDir != null) {
            try {
                gameDirPath = chosenDir.getPath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fileButton_game.setText(gameDirPath);
        showLog("选择游戏路径："+gameDirPath );
    }

    /**
     * 选择需要替换的文件
     *
     * @param event
     */
    @FXML
    public void chooseFileReplace(ActionEvent event) {
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setInitialDirectory(new File(baseFilePath));
        fileChooser.setTitle("------------------请选择需要替换的文件夹--------------------");
        Stage selectFile = new Stage();
        File chosenDir = fileChooser.showDialog(selectFile);
        if (chosenDir != null) {
            try {
                replaceDirPath = chosenDir.getPath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fileButton_replace.setText(replaceDirPath);
        showLog("选择的配置文件路劲:"+replaceDirPath );
    }

    /**
     * 开始融合
     */
    @FXML
    public void backMain() {
        backToIndexPage();
    }

    public void showLog(String info){
        logInfo.appendText(info + newLine );
    }

/*    public static void main(String[] args) {
        MixController mixController = new MixController();
        mixController.buildAppByShaKatool("","","D:/aaa_sdk/");
    }*/

}
