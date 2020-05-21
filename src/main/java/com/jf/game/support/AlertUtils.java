package com.jf.game.support;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 * ouyangjie
 * 2020/5/18
 * 16:17
 */
public class AlertUtils {


    public static Alert alert(String msg,String title){
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle(title);
        _alert.setHeaderText(null);
        _alert.setContentText(msg);
        _alert.show();
        return _alert;
    }
    /**
     * @User: jspp@qq.com
     * @Date: 2020/5/18 16:33
     * @Desc   确认框提示信息
     * @Param
     */
    public static Alert confirm(String msg){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("系统提示");
        alert.setHeaderText("签名结果");
        alert.setContentText(msg);
        alert.initStyle(StageStyle.UTILITY);
        return alert;
    }

    public static void alert(String msg){
        alert(msg,"系统提示");
    }

    public static void errorAlert(String msg,Exception e){
        alert(msg+" "+e.getMessage(),"系统提示");
    }


}
