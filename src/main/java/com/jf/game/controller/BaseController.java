package com.jf.game.controller;

import com.jf.game.config.ApplicationContext;
import com.jf.game.support.AlertUtils;
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
public class BaseController extends AlertUtils {
    /**
     * 返回首页
     * @User: jspp@qq.com
     * @Date: 2020/4/30 16:03
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
