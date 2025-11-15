package Hongweijie_KnowledgeBase;

import java.util.*;
import java.util.stream.Collectors;

public class CommandHandler {
    private NoteService noteService;
    private DisplayService displayService;
    private SearchService searchService;
    private Map<String,String> keywords;
    private Runnable appExit;
    public CommandHandler(NoteService noteService, DisplayService displayService, SearchService searchService) {
        this.noteService = noteService;
        this.displayService = displayService;
        this.searchService = searchService;
        keywords = new HashMap<>();
        addKeyWord();
    }
    Scanner scanner = new Scanner(System.in);
    public NoteService getNoteService() {
        return noteService;
    }

    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }

    public DisplayService getDisplayService() {
        return displayService;
    }

    public void setDisplayService(DisplayService displayService) {
        this.displayService = displayService;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public Map<String, String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Map<String, String> keywords) {
        this.keywords = keywords;
    }

    public void setAppExit(Runnable appExit) {
        this.appExit = appExit;
    }
    public void addKeyWord(){
        //添加
        keywords.put("添加","add");
        keywords.put("新增","add");
        keywords.put("创建","add");
        keywords.put("add","add");
        // 搜索
        keywords.put("搜索","search");
        keywords.put("查找","search");
        keywords.put("查询","search");
        keywords.put("search","search");
        ///删除
        keywords.put("删除","delete");
        keywords.put("删掉","delete");
        keywords.put("去除","delete");
        keywords.put("delete","delete");
        //展示
        keywords.put("展示","display");
        keywords.put("显示","display");
        keywords.put("查看","display");
        keywords.put("display","display");
        //列表
        keywords.put("列表","list");
        keywords.put("全部","list");
        keywords.put("所有","list");
        keywords.put("list","list");
        //帮助
        keywords.put("帮助","help");
        keywords.put("使用","help");
        keywords.put("操作","help");
        keywords.put("帮忙","help");
        keywords.put("help","help");
        //退出
        keywords.put("退出","quit");
        keywords.put("quit","quit");
    }
    //命令处理
    public Boolean handleCommand(String command) {

        if(command == null||command.trim().isEmpty()){
            System.out.println("无效的命令！");
            return false;
        }
        String input = command.trim();//去除空格
        String commandType = recogniseCommandType(command);
        executeCommand(commandType,input);
        return true;
    }

    public boolean integerCommand(Long id){
        if(id == null){
            System.out.println("无效的ID！");
            return false;
        }
        displayService.displayOnlyNote(id);
        return true;
    }

    public void appExit(){
        if(appExit != null){
            appExit.run();
        }
    }

    //识别命令类型
    public String recogniseCommandType(String command){
        for (String keyword : keywords.keySet()){
            if(command.contains(keyword)){
                return keywords.get(keyword);
            }
        }
        return "help";
    }
    //执行命令
    public void executeCommand(String commandType,String input){
        switch (commandType){
            case "add":
                System.out.println("好的，正在为您加载添加笔记功能...");
                handleAddService(input);
                break;
            case "search":
                System.out.println("好的，正在为您加载搜索笔记功能...");
                handleSearchService(input);
                break;
            case "delete":
                System.out.println("好的，正在为您加载删除笔记功能...");
                handleDeleteService(input);
                break;
            case "display":
                System.out.println("好的，正在为您加载查看笔记功能...");
                handleDisplayService();
                break;
            case "list":
                System.out.println("好的，正在为您加载查看笔记列表功能...");
                handleListService();
                break;
            case "help":
                System.out.println("好的，正在为您加载帮助功能...");
                handleHelpService();
                break;
            case "quit":
                appExit();
                break;
            default:
                System.out.println("无效的命令！");
                break;
        }
    }

    public void handleAddService(String input){
        if(input.contains("|")){
            String[] parts = input.split("\\|");// 正则表达式
            String title = parts[0].trim();
            String content = parts[1].trim();
            List<String> tags = new ArrayList<>();
            noteService.addNotes(new Note(title,content,tags));
            System.out.println("快速添加成功！");
        }
        else{
            addNoteInteractive();
        }
    }
    public void addNoteInteractive(){
        System.out.println("请输入标题：");
        String title = scanner.nextLine();
        System.out.println("请输入内容：");
        StringBuilder content = new StringBuilder();
        while(true){
            String line = scanner.nextLine();
            if(line.trim().isEmpty()){
                break;
            }
            content.append(line).append("\n");// 添加换行符
        }
        String contentString = content.toString();
        contentString = contentString.substring(0, contentString.length()-1);

        List<String> tags = new ArrayList<>();
        System.out.println("是否要输入标签？");
        String answer = scanner.nextLine();
        if(answer.equalsIgnoreCase("是")) {
            System.out.println("请输入标签（多个标签用空格隔开）：");
            String tagInput = scanner.nextLine();
            String[] tagArray = tagInput.split(" ");
            Collections.addAll(tags, tagArray);
        }
        noteService.addNotes(new Note(title, contentString, tags));

    }

    public void handleSearchService(String input){
        List <Note> searchResult = searchService.intelligentSearch(input);
        if(searchResult.isEmpty()){
            System.out.println("未找到匹配的笔记！");
            return;
        }
        else {
            System.out.println("搜索结果: ");
            System.out.println("找到"+searchResult.size()+"个相关的笔记。");
            System.out.println("是否需要查看完整笔记？");
            String answer = scanner.nextLine();
            if(answer.equalsIgnoreCase("是")){
                displayService.displaySearchResult(searchResult);
            }
        }
    }

    public void handleDeleteService(String input){
        List<Note> notes =noteService.getNotes().stream().filter(note -> input.contains(note.getTitle())).toList();
        if(notes.isEmpty()){
            System.out.println("请输入要删除的笔记的标题：");
            String title = scanner.nextLine();
            noteService.deleteNotes(title);
        }
        else {
            noteService.deleteNotes(input);
        }
    }

    public void handleDisplayService(){
        displayService.displayAllNotes();
    }

    public void handleListService(){
        displayService.displayNotesList();
    }

    public void handleHelpService(){
        System.out.println("""
        使用帮助：
        
        添加笔记：
          快速添加：输入：标题 | 内容
          正常添加：输入关键词：添加、新增、创建或add
          
        搜索笔记：
          输入笔记关键词即可
        
        查看笔记：
          输入关键词：查看、展示、显示或display
          直接输入数字ID：比如1、2、3...
          
        删除笔记：
          输入关键词：删除、删掉、去除或delete
          
        帮助: 
          输入关键词：帮助、使用、操作、帮忙或help
          
        退出： 
          退出程序或quit
        """);//文本块
    }
}
