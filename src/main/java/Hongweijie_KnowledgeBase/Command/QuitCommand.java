package Hongweijie_KnowledgeBase.Command;

import java.util.Scanner;

public class QuitCommand implements Command{
    private Runnable appExit;
    public QuitCommand() {
    }

    @Override
    public void execute(String input, Scanner scanner) {
        System.out.println("感谢您的使用！");
        System.out.println("正在退出...");
        appExit();
    }

    public void setAppExit(Runnable appExit) {
        this.appExit = appExit;
    }

    public Runnable getAppExit() {
        return appExit;
    }

    public void appExit(){
        if(appExit != null){
            appExit.run();
        }
    }
    @Override
    public String getCommandName() {
        return "退出";
    }
}