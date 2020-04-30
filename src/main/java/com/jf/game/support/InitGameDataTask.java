package com.jf.game.support;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;
import com.jf.game.config.ApplicationContext;
import com.jf.game.pojo.Game;

/**
 * 加载游戏数据
 * sun
 * 2018/9/11
 * 18:02
 */
public class InitGameDataTask extends Task<Integer> {

    private ComboBox choiceGame;

    private String myProjectType;

    @Override
    protected Integer call() throws Exception {
        System.out.println(" ----------获取数据 fb---------- ");
        ObservableList<Game> list = FXCollections.observableArrayList(ApplicationContext.getGameList(myProjectType));
        choiceGame.getItems().remove(0,choiceGame.getItems().size()); // clear();//
        System.out.println(" ---------- 清理数据 ---------- ");
        choiceGame.getItems().addAll(list);
        System.out.println(" ---------- 添加数据 ---------- ");
        return 1;
    }

    public InitGameDataTask(ComboBox choiceGame,String myProjectType) {
            this.choiceGame = choiceGame;
            this.myProjectType = myProjectType;
    }
}
