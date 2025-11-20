package Hongweijie_KnowledgeBase.Service;

import Hongweijie_KnowledgeBase.Note;
import Hongweijie_KnowledgeBase.NoteException;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class NoteService implements INoteService {
    private List<Note> notes;
    private FileService fileService;
    private Runnable dataChangeListener;
    private Set<Long> noteSet;
    private final Scanner scanner = new Scanner(System.in);
    public NoteService(FileService fileService) {
        notes = new ArrayList<>();
        this.fileService = fileService;
        noteSet = new HashSet<>();
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public FileService getFileService() {
        return fileService;
    }

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    public Runnable getDataChangeListener() {
        return dataChangeListener;
    }

    public void setDataChangeListener(Runnable listener) {
        dataChangeListener = listener;
    }


    public Set<Long> getNoteSet() {
        return noteSet;
    }

    public void setNoteSet(Set<Long> noteSet) {
        this.noteSet = noteSet;
    }

    // 笔记加载
    public void loadNotes() throws NoteException {
        notes = fileService.loadFromFile();
        noteSet = fileService.loadReusableIds();
        if(!notes.isEmpty()){
            System.out.println("笔记已加载成功！");
        }
    }
    
    // 添加笔记
    public void addNotes(Note note){
        long id;
        if(!noteSet.isEmpty()){
            Queue<Long> queue = new LinkedList<>(noteSet);
            id = queue.poll();
            noteSet.remove(id); // 从noteSet中移除已使用的ID
        } else {
            // 如果没有可重用的ID，则从1开始分配ID
            id = notes.stream().mapToLong(Note::getId).max().orElse(0L) + 1;
        }
        note.setId(id);
        notes.add(note);
        System.out.println("笔记已添加！");
        notes.sort(Comparator.comparingLong(Note::getId));
        changeNotes();// 调用监听器
    }

    public void updateNotes(String input){
        if(notes.isEmpty()){
            System.out.println("没有笔记可修改！");
            return;
        }
        Note note = notes.stream().filter(m ->input.contains(m.getTitle()) ).findFirst().orElse( null);
        if(note != null){
            System.out.println("请输入修改后的笔记标题：");
            note.setTitle(scanner.nextLine());
            System.out.println("请输入修改后的笔记内容：");
            StringBuilder content = new StringBuilder();
            while(true) {
                 String line =scanner.nextLine();
                 if(line.isEmpty()){
                     break;
                 }
                 content.append(line).append("\n");
            }
            content.deleteCharAt(content.length()-1);
            note.setContent(content.toString());
            System.out.println("请输入修改后的笔记标签：");
            note.setTags(Arrays.asList(scanner.nextLine().split(",")));
            System.out.println("笔记已修改！");
            changeNotes();
        }
    }
    // 删除笔记
    public boolean deleteNotes(String input){
        if(notes.isEmpty()){
            System.out.println("没有笔记可删除！");
            return false;
        }
        
        // 收集所有要删除的笔记
        List<Note> notesToRemove = notes.stream()
                .filter(n -> input.contains(n.getTitle()))
                .collect(ArrayList::new, (list, item) -> list.add(item), ArrayList::addAll);
        
        // 删除笔记
        boolean isRemove = notes.removeIf(n -> input.contains(n.getTitle()));
        
        if(isRemove){
            System.out.println("笔记已删除！");
            // 将所有被删除笔记的ID添加到noteSet中
            notesToRemove.forEach(note -> noteSet.add(note.getId()));
            changeNotes();
        }
        else{
            System.out.println("笔记删除失败！");
        }
        return isRemove;
    }
    // 保存笔记
    public void saveNotes() throws NoteException {
        fileService.saveToFile(notes, noteSet);
    }

    public void changeNotes(){
        if(dataChangeListener != null){// 判断监听器是否为空
            dataChangeListener.run();
        }
    }


}