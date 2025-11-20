package Hongweijie_KnowledgeBase.Command;
import java.util.*;

import Hongweijie_KnowledgeBase.Note;
import Hongweijie_KnowledgeBase.Service.INoteService;

public class AddCommand implements Command{
    private INoteService noteService;

    public AddCommand(INoteService noteService) {
        this.noteService = noteService;
    }

    public INoteService getNoteService() {
        return noteService;
    }

    public void setNoteService(INoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public void execute(String input, Scanner scanner) {
        if(input.contains("|")){
            handleQuickAdd(input);
        }
        else{
            handleInteractiveAdd(scanner);
        }
    }

    public void handleQuickAdd(String input){
        String[] parts = input.split("\\|", 2);
        if(parts.length != 2){
            System.out.println("快速添加格式错误，请使用：标题 | 内容!");
            return;
        }
        String title = parts[0].trim();
        String content = parts[1].trim();
        List<String> tags = new ArrayList<>();
        noteService.addNotes(new Note(title, content, tags));
        System.out.println("✅ 笔记快速添加成功！");
    }

    public void handleInteractiveAdd(Scanner scanner){
        System.out.println("请输入笔记标题：");
        String title = scanner.nextLine();
        System.out.println("请输入笔记内容：");
        String contentString = getAllContent(scanner);
        List<String> tags = addTags(scanner);
        noteService.addNotes(new Note(title, contentString,tags));
        System.out.println("✅ 笔记添加成功！");
    }

    public String getAllContent(Scanner scanner){
        StringBuilder content = new StringBuilder();
        while(true) {
            String line =scanner.nextLine();//读取一行
            if(line.trim().isEmpty()){
                break;
            }
            content.append(line).append("\n");
        }
        content.deleteCharAt(content.length()-1);//删除最后一个换行符
        return content.toString();
    }

    public List<String> addTags(Scanner scanner){
        System.out.println("是否要输入标签？");
        String input = scanner.nextLine();
        if(input.equalsIgnoreCase("是")||input.equalsIgnoreCase("yes")){
            System.out.println("请输入标签（多个标签用空格间隔）：");
            String tag = scanner.nextLine();
            String[] tags = tag.split("\\s");// 使用空格分割标签
            return Arrays.asList(tags);
        }
        return new ArrayList<>();
    }
    @Override
    public String getCommandName() {
        return "添加新笔记";
    }
}
