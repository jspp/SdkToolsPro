package com.jf.game.config;

import com.alibaba.fastjson.JSON;
import javafx.stage.Stage;
import com.jf.game.pojo.Game;
import com.jf.game.utils.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  基础配置信息
 * sun
 * 2018/8/4
 * 16:48
 */
public class ApplicationContext {
    // 场景切换
    public static Stage primaryStage=null;
    //默认的项目为 egame
    public static String defaultType = "egame";
    // 项目对应的URL
    private static Map<String,String> urls = new HashMap<>();
    static {
        urls.put("egame","http://egameapi.66096.com/api/game/games");
        urls.put("jfgame","http://api.yiigames.com/api/game/games");
    }
    // 项目对应的 二期渠道ID （测试专用）
    private static Map<String,String> agentIds = new HashMap<>();
    static {
        agentIds.put("egame","805");
        agentIds.put("jfgame","654");
    }

    static Map<String,List<Game>> dataCache = new HashMap<>();

    public static String softName = "SDK分包签名估计.v2020.04.28";

    /**
     * 获取游戏列表
     * @return
     */
    public static List<Game> getGameList(String myProjectType){
        List<Game> gameList = new ArrayList<>();
        try {
            if(dataCache!=null && dataCache.containsKey(myProjectType)){
                gameList = dataCache.get(myProjectType);
            }
            if(gameList!=null && gameList.size()==0){
                String dataUrl = getDataUrls(myProjectType);
                System.out.println("  获取数据的值  "+dataUrl);
                String games = HttpUtils.getGETString(dataUrl);
                System.out.println("  初始化游戏数据完成 "+games);
                gameList = JSON.parseArray(games, Game.class);
                dataCache.put(myProjectType,gameList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameList;
    }

    public static List<Game> getGameList(){
        return getGameList(defaultType);
    }

    public static  String getAgentId(String projectType){
           return agentIds.get(projectType);
    }

    public static  String getAgentId(){
           return getAgentId(defaultType);
    }

    public static  String getDataUrls(String projectType){
        return urls.get(projectType);
    }

}
