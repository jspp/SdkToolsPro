package com.jf.game.controller;

import com.jf.game.config.ApplicationContext;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

/**
 * 弹出信息等基础
 * sun
 * 2018/8/4
 * 16:44
 */
public class BaseController {


    public void alert(String msg,String title){
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle(title);
        _alert.setHeaderText(null);
        _alert.setContentText(msg);
        _alert.show();
    }

    public void alert(String msg){
        alert(msg,"系统提示");
    }

    public void errorAlert(String msg,Exception e){
        alert(msg+" "+e.getMessage(),"系统提示");
    }
    /**
     * 返回首页
     * @User: jspp@qq.com
     * @Date: 2020/4/30 16:03
     * @Desc
     * @Param
     */
    public void backToIndexPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/subpackage.fxml"));
            ApplicationContext.primaryStage.setScene(new Scene(root, 470, 400));
            ApplicationContext.primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/imgs/20180724111034.png")));
            ApplicationContext.primaryStage.setResizable(false);
            ApplicationContext.primaryStage.setTitle(ApplicationContext.softName);
            ApplicationContext.primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
