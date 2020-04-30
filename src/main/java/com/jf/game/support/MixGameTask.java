package com.jf.game.support;

import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;
import com.jf.game.controller.MixController;
import com.jf.game.pojo.Game;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 加载游戏数据
 * sun
 * 2018/9/11
 * 18:02
 */
public class MixGameTask extends Task<Integer> {


    private String contextPath;
    private String targetName;
    private String sourceName;

    private ComboBox choiceGame;

    private MixController face;

    @Override
    protected Integer call() throws Exception {
        try {
            File dir = new File(contextPath);//此处是指定路径
            String[] cmd = new String[] { "cmd", "/c", "ShakaApktool.bat b -f -o "+targetName+" "+sourceName };// cmd[2]是要执行的dos命令
            face.showLog("开始执行打包命令2: "+cmd[2]);
            Process process = Runtime.getRuntime().exec(cmd,null,dir);
            // any error message?
            StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "ERROR",face);
            // any output?
            StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), "OUTPUT",face);
            // kick them off
            errorGobbler.start();
            outputGobbler.start();
            process.waitFor();  //等待执行完成
            Game game = (Game) choiceGame.getValue();
            face.showLog("打包目录执行结果: "+(process.exitValue()==0?game.getGameName() + " 融合 【成功】  ":"-------失败--------"));
            process.getOutputStream().close();  // 不要忘记了一定要关
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public MixGameTask(String sourceName, String targetName, String contextPath, ComboBox choiceGame, MixController face) {
            this.sourceName = sourceName;
            this.targetName = targetName;
            this.contextPath = contextPath;
            this.choiceGame = choiceGame;
            this.face = face;
    }

    static class StreamGobbler extends Thread {
        InputStream is;
        String type;
        MixController face;
        StreamGobbler(InputStream is, String type,MixController face) {
            this.is = is;
            this.type = type;
            this.face = face;
        }
        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is,"GBK");
                System.out.println(1);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null){
                    System.out.println(type + ">" + line);
                    face.showLog(type + ">" + line);
                }
            } catch (Exception ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
