package Hongweijie_KnowledgeBase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NoteService {
    private List<Note> notes;
    private FileService fileService;
    private Runnable dataChangeListener;
    private Long Id = 1L;
    public NoteService() {
        notes = new ArrayList<>();
        fileService = new FileService();
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

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
    // 笔记加载
    public void loadNotes() {
        notes = fileService.loadFromFile();
        if(!notes.isEmpty()){
            System.out.println("笔记已加载成功！");
        }
    }
    // 添加笔记
    public void addNotes(Note note){
        notes.add(note);
        System.out.println("笔记已添加！");
        note.setId(Id++);
        changeNotes();
    }
    // 删除笔记
    public Boolean deleteNotes(String  title){
        List<Note> newNotes =notes.stream().filter(note->!note.getTitle().equals(title)).collect(Collectors.toList());
        Boolean isRemove = notes.size() > newNotes.size();
        if(isRemove){
            notes = newNotes;
            System.out.println("笔记已删除！");
            changeNotes();
        }
        else{
            System.out.println("笔记删除失败！");
        }
        return isRemove;
    }
    // 保存笔记
    public void saveNotes(){
        fileService.saveToFile(notes);
    }

    public void changeNotes(){
        if(dataChangeListener != null){// 判断监听器是否为空
            dataChangeListener.run();
        }
    }


}
