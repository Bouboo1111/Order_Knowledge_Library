package Hongweijie_KnowledgeBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SearchService {
    private NoteService noteService;
    private List<String> keyWords;
    public SearchService(NoteService noteService){
        this.noteService = noteService;
        keyWords = new ArrayList<>();
        Collections.addAll(keyWords,"搜索","查找","查询","search");
    }

    public NoteService getNoteService() {
        return noteService;
    }

    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    public List<Note> intelligentSearch(String keyword){
        if(keyword.trim().isEmpty()||keyword == null){
            return new ArrayList<>();
        }
        String newKeyWord =extractKeyWords(keyword);
        return noteService.getNotes().stream().filter(note->note.getTitle().toLowerCase().contains(newKeyWord.toLowerCase())
                ||note.getContent().toLowerCase().contains(newKeyWord.toLowerCase())
                ||note.getTags().stream().anyMatch(tag->tag.toLowerCase().contains(newKeyWord.toLowerCase())))
                .collect(Collectors.toList());

    }

    public String extractKeyWords(String command){
        for (String keyword : keyWords){
            if(command.startsWith(keyword)){
                return command.substring(keyword.length());
            }
        }
        return command;
    }
}
