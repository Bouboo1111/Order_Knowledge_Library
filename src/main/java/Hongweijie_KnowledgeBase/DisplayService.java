package Hongweijie_KnowledgeBase;

import java.util.List;

public class DisplayService {
    private NoteService noteService;

    public DisplayService(NoteService noteService){
        this.noteService = noteService;

    }

    public NoteService getNoteService() {
        return noteService;
    }

    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }
    public void printFormat(Long  id, String title, String content, List<String> tags){
        System.out.println("----------------------------------");
        System.out.println("笔记详情: ");
        System.out.println("ID: " + noteService.getId());
        System.out.println("标题: " + title);
        System.out.print("内容: " + content);
        System.out.print("标签: ");
        tags.forEach(tag->System.out.print(tag+" "));
        System.out.println("创建时间: " + noteService.getId());
        System.out.println("----------------------------------");
    }

    public void displayAllNotes(){
        List<Note> notes = noteService.getNotes();
        if(notes.isEmpty()){
            System.out.println("暂无笔记！");
        }
        for(Note note:notes){
            printFormat(note.getId(),note.getTitle(),note.getContent(),note.getTags());
        }
    }

    public void displayNotesList(){
        List<Note> notes = noteService.getNotes();
        if(notes.isEmpty()){
            System.out.println("暂无笔记！");
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
        List<Note> newNotes = notes.stream().filter(note ->note.getId().equals( id)).toList();
        if(!newNotes.isEmpty()) {
            newNotes.forEach(note -> printFormat(note.getId(),note.getTitle(),note.getContent(),note.getTags()));
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
            printFormat(note.getId(),note.getTitle(),note.getContent(),note.getTags());
        }
    }
}
