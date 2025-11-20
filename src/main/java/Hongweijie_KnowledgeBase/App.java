package Hongweijie_KnowledgeBase;

import Hongweijie_KnowledgeBase.Command.Command;
import Hongweijie_KnowledgeBase.Command.CommandHandler;
import Hongweijie_KnowledgeBase.Command.QuitCommand;
import Hongweijie_KnowledgeBase.Proxy.NoteServiceProxy;
import Hongweijie_KnowledgeBase.Service.*;

import java.lang.reflect.Proxy;
import java.util.Scanner;

@SuppressWarnings("all")
public class App {
    private INoteService noteService;
    private NoteService realNoteService;
    private SearchService searchService;
    private DisplayService displayService;
    private CommandHandler commandHandler;
    private FileService fileService;
    private boolean running = true;
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }
    public void start() {
        try {
            initialize();
            loadData(noteService);
            run();
        }catch (NoteException e){
            e.printStackTrace();
        }finally {
            try {
                saveData();
            } catch (NoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //初始化各个服务
    public void initialize(){
        System.out.println("正在初始化APP...");
        fileService = new FileService();
        realNoteService = new NoteService(fileService);
        NoteServiceProxy proxy = new NoteServiceProxy(realNoteService);
        noteService = (INoteService) Proxy.newProxyInstance(NoteService.class.getClassLoader(), new Class[]{INoteService.class}, proxy);
        searchService = new SearchService(noteService);
        displayService = new DisplayService(noteService);
        commandHandler = new CommandHandler(noteService, displayService, searchService);
        try {
            int time = Config.getIntProperty("app.initialize.time");
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("初始化完成！");
        System.out.println("欢迎使用知识库系统！");
    }
    //加载数据
    public void loadData(INoteService noteService) throws NoteException {
        System.out.println("正在加载数据...");
        noteService.loadNotes();
        try {
            int time = Config.getIntProperty("data.load.time");
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("数据加载完成！");
    }

    public void run(){
        System.out.println("很高兴为您服务！");
        while(running){
            QuitCommand quitCommand =(QuitCommand) commandHandler.getCommandFactory().getCommand("quit");
            quitCommand.setAppExit(()->{
                running = false;
            });
            realNoteService.setDataChangeListener(()->{
                System.out.println("检测到笔记有修改！");
            });
            commandHandler.handleCommand(scanner.nextLine());
        }
        System.out.println("感谢您的使用！");

    }

    public void saveData() throws NoteException{
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
