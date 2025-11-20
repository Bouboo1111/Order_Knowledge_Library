package Hongweijie_KnowledgeBase.Command;

import Hongweijie_KnowledgeBase.Note;
import Hongweijie_KnowledgeBase.Service.INoteService;

import java.util.List;
import java.util.Scanner;

public class DeleteCommand implements  Command{
    private INoteService noteService;
    public DeleteCommand(INoteService noteService) {
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
            System.out.println("请输入要删除的笔记的标题：");
            String title = scanner.nextLine();
            noteService.deleteNotes(title);
        }
        else {
            noteService.deleteNotes(input);
        }
    }

    @Override
    public String getCommandName() {
        return "删除笔记";
    }
}
