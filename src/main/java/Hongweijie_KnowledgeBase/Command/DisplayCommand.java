package Hongweijie_KnowledgeBase.Command;

import Hongweijie_KnowledgeBase.Service.DisplayService;

import java.util.Scanner;

public class DisplayCommand implements  Command{
    private DisplayService displayService;
    public DisplayCommand(DisplayService displayService) {
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
        displayService.displayAllNotes();
    }

    @Override
    public String getCommandName() {
        return "查看笔记";
    }
}
