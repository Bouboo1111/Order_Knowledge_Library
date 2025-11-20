package Hongweijie_KnowledgeBase.Command;

import Hongweijie_KnowledgeBase.Service.DisplayService;
import Hongweijie_KnowledgeBase.Service.INoteService;
import Hongweijie_KnowledgeBase.Service.SearchService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandFactory {
    private final Map<String, Command> commandMap = new HashMap<>();

    public CommandFactory(INoteService noteService, DisplayService displayService, SearchService searchService){
        initializeCommands(noteService, displayService, searchService);
    }

    private void initializeCommands(INoteService noteService, DisplayService displayService, SearchService searchService){
        commandMap.put("add", new AddCommand(noteService));
        commandMap.put("list", new DisplayListCommand(displayService));
        commandMap.put("search", new SearchCommand(noteService, searchService, displayService));
        commandMap.put("delete", new DeleteCommand(noteService));
        commandMap.put("update", new UpdateCommand(noteService));
        commandMap.put("display", new DisplayCommand(displayService));
        commandMap.put("help", new HelpCommand());
        commandMap.put("quit", new QuitCommand());
        addOtherCommand();
    }

    public void addOtherCommand(){
        commandMap.put("添加", commandMap.get("add"));
        commandMap.put("新增", commandMap.get("add"));
        commandMap.put("创建", commandMap.get("add"));
        commandMap.put("搜索", commandMap.get("search"));
        commandMap.put("查找", commandMap.get("search"));
        commandMap.put("查询", commandMap.get("search"));
        commandMap.put("删除", commandMap.get("delete"));
        commandMap.put("删掉", commandMap.get("delete"));
        commandMap.put("去除", commandMap.get("delete"));
        commandMap.put("展示", commandMap.get("display"));
        commandMap.put("显示", commandMap.get("display"));
        commandMap.put("查看", commandMap.get("display"));
        commandMap.put("修改", commandMap.get("update"));
        commandMap.put("更改", commandMap.get("update"));
        commandMap.put("更新", commandMap.get("update"));
        commandMap.put("帮助", commandMap.get("help"));
        commandMap.put("退出", commandMap.get("quit"));
    }
    public Command getCommand(String commandType){
        return commandMap.getOrDefault(commandType,commandMap.get("help"));
    }

    public String recognizeType(String command){
        Set<String> keys = commandMap.keySet();
        return keys.stream().filter(command::contains).findFirst().orElse(null);
    }
}
