package Hongweijie_KnowledgeBase.Command;

import Hongweijie_KnowledgeBase.Service.DisplayService;

import java.util.Scanner;

public class DisplayListCommand implements  Command{
    private DisplayService displayService;
    public DisplayListCommand(DisplayService displayService) {
        this.displayService = displayService;
    }

    public DisplayService getDisplayService() {
        return displayService;
    }

    public void setDisplayService(DisplayService displayService) {
        this.displayService = displayService;
    }

    @Override
    public void execute(String input, Scanner scanner) {
        displayService.displayNotesList();//显示笔记列表
    }

    @Override
    public String getCommandName() {
        return "查看笔记列表";
    }
}
