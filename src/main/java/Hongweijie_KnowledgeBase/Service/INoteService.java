package Hongweijie_KnowledgeBase.Service;

import Hongweijie_KnowledgeBase.Note;
import Hongweijie_KnowledgeBase.NoteException;

import java.util.List;

public interface INoteService {
    void addNotes(Note note);
    void updateNotes(String title);
    boolean deleteNotes(String title);
    List<Note> getNotes();
    void saveNotes() throws NoteException;
    void loadNotes() throws NoteException;
}
