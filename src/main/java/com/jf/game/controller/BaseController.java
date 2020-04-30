package com.jf.game.controller;

import javafx.scene.control.Alert;

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


}
