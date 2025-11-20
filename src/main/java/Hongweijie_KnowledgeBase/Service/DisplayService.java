package Hongweijie_KnowledgeBase.Service;

import Hongweijie_KnowledgeBase.Note;

import java.util.List;

public class DisplayService {
    private INoteService noteService;

    public DisplayService(INoteService noteService){
        this.noteService = noteService;

    }

    public INoteService getNoteService() {
        return noteService;
    }

    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }
    public void printFormat(Note note){
        System.out.println("----------------------------------");
        System.out.println("笔记详情: ");
        System.out.println("ID: " + note.getId());
        System.out.println("标题: " + note.getTitle());
        System.out.print("内容: " + note.getContent());
        System.out.print("标签: ");
        note.getTags().forEach(tag->System.out.print(tag+" "));
        System.out.println("创建时间: " + note.getUpdateTime());
        System.out.println("----------------------------------");
    }

    public void displayAllNotes(){
        List<Note> notes = noteService.getNotes();
        if(notes.isEmpty()){
            System.out.println("暂无笔记！");
            return;
        }
        for(Note note:notes){
            printFormat(note);
        }
    }

    public void displayNotesList(){
        List<Note> notes = noteService.getNotes();
        if(notes.isEmpty()){
            System.out.println("暂无笔记！");
            return;
        }
        System.out.println("笔记列表: ");
        System.out.println("总共"+ notes.size() + "篇笔记");
        for (Note note : notes) {
            System.out.println("ID: " + note.getId());
            System.out.println("标题: " + note.getTitle());
            System.out.println("创建时间: " + note.getCreateTime());
        }
    }

    public void displayOnlyNote(Long  id){

        List<Note> notes = noteService.getNotes();
        List<Note> newNotes = notes.stream().filter(note ->note.getId().equals(id)).toList();
        if(!newNotes.isEmpty()) {
            newNotes.forEach(this::printFormat);
        }
        else{
            System.out.println("未找到该笔记！");
        }
    }

    public void displaySearchResult(List<Note> notes){
        if(notes.isEmpty()){
            System.out.println("未找到匹配的笔记！");
            return;
        }
        for(Note note:notes){
            printFormat(note);
        }
    }
}
