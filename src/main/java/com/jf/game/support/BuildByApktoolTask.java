package com.jf.game.support;

import com.jf.game.controller.MixController;
import com.jf.game.pojo.Game;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;

import java.io.File;

/** 
 * @User: jspp@qq.com
 * @Date: 2020/5/18 17:03
 * @Desc  apktool 打包
 * @Param 
 */
public class BuildByApktoolTask extends Task<Integer> {


    private String sourcefilePathStr;
    private MixController face;

    @Override
    protected Integer call() throws Exception {
        ApktoolsUtils.buildAppByApktool(sourcefilePathStr,face);
        return 1;
    }

    public BuildByApktoolTask(String sourcefilePathStr, MixController face) {
            this.sourcefilePathStr = sourcefilePathStr;
            this.face = face;
    }
}
