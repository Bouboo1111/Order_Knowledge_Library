package Hongweijie_KnowledgeBase.Command;

import Hongweijie_KnowledgeBase.Service.DisplayService;
import Hongweijie_KnowledgeBase.Service.INoteService;
import Hongweijie_KnowledgeBase.Service.SearchService;
import java.util.Scanner;

public class CommandHandler {
    private final CommandFactory commandFactory;
    private Runnable appExit;
    public CommandHandler(INoteService noteService, DisplayService displayService, SearchService searchService){
        commandFactory = new CommandFactory(noteService, displayService, searchService);
    }

    public CommandFactory getCommandFactory() {
        return commandFactory;
    }

    public void handleCommand(String input){
        String commandType = commandFactory.recognizeType(input);
        Command command= commandFactory.getCommand(commandType);
        command.execute(input, new Scanner(System.in));
    }

}
