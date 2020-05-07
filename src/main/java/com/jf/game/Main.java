package com.jf.game;

import com.jf.game.config.ApplicationContext;
import com.jf.game.utils.PropertiesUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            primaryStage.initStyle(StageStyle.DECORATED);
            Parent root = FXMLLoader.load(getClass().getResource("/layout/subpackage.fxml"));
            primaryStage.setTitle(ApplicationContext.softName);

            Scene scene = new Scene(root, 470, 400);
            //scene.getStylesheets().add("bootstrapfx.css");
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/imgs/20180724111034.png")));
            primaryStage.setResizable(false);
            ApplicationContext.primaryStage = primaryStage;
            primaryStage.show();
            // 初始化 配置信息
            PropertiesUtil.load("global.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
