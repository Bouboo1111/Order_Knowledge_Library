package Hongweijie_KnowledgeBase;

import java.util.Scanner;

@SuppressWarnings("all")
public class App {
    private NoteService noteService;
    private SearchService searchService;
    private DisplayService displayService;
    private CommandHandler commandHandler;
    private boolean running = true;
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }
    public void start(){
        initialize();
        loadData(noteService);
        run();
        saveData();
    }
    //初始化各个服务
    public void initialize(){
        System.out.println("正在初始化APP...");
        noteService = new NoteService();
        searchService = new SearchService(noteService);
        displayService = new DisplayService(noteService);
        commandHandler = new CommandHandler(noteService, displayService, searchService);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("初始化完成！");
        System.out.println("欢迎使用知识库系统！");
    }
    //加载数据
    public void loadData(NoteService noteService){
        System.out.println("正在加载数据...");
        noteService.loadNotes();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("数据加载完成！");
    }

    public void run(){
        System.out.println("很高兴为您服务！");
        while(running){
            commandHandler.setAppExit(()->running = false);
            noteService.setDataChangeListener(()->{
                System.out.println("检测到笔记有修改！");
            });
            commandHandler.handleCommand(scanner.nextLine());
        }
        System.out.println("感谢您的使用！");

    }

    public void saveData(){
        System.out.println("正在保存数据...");
        noteService.saveNotes();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("数据保存完成！");
    }
}
