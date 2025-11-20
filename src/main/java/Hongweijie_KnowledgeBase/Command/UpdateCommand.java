package Hongweijie_KnowledgeBase.Command;

import Hongweijie_KnowledgeBase.Note;
import Hongweijie_KnowledgeBase.Service.INoteService;

import java.util.List;
import java.util.Scanner;

public class UpdateCommand implements  Command{
    private INoteService noteService;

    public UpdateCommand(INoteService noteService){
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
        List<Note> notes =noteService.getNotes().stream().filter(note -> input.contains(note.getTitle())).toList();
        if(notes.isEmpty()){
            System.out.println("请输入要修改的笔记的标题：");
            String title = scanner.nextLine();
            noteService.updateNotes(title);
        }
        else {
            noteService.updateNotes(input);
        }
    }

    @Override
    public String getCommandName() {
        return "";
    }
}
