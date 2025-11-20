package Hongweijie_KnowledgeBase.Service;

import Hongweijie_KnowledgeBase.Note;

import java.util.*;

public class SearchService {
    private INoteService noteService;
    private List<String> keyWords;
    public SearchService(INoteService noteService){
        this.noteService = noteService;
        keyWords = new ArrayList<>();
        Collections.addAll(keyWords,"搜索","查找","查询","search");
    }

    public INoteService getNoteService() {
        return noteService;
    }

    public void setNoteService(INoteService noteService) {
        this.noteService = noteService;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    public String removeprifix(String command){
        String[] prifix = {"我要","我想","我想要","请帮我", "帮我", "请", "麻烦", "想要", "需要",
                "i want to", "i need to", "please", "can you", "could you"};
        for (String prefix : prifix){
            if(command.startsWith(prefix)){
                return command.substring(prefix.length());
            }
        }
        return command;//删除开头
    }

    public int calculateMatchScore(String keyword, Note note){
        int score = 0;
        if(keyword.equalsIgnoreCase(note.getTitle())){
            score = 100;
        }
        else if(note.getTitle().contains(keyword)){
            score = 90;
        }
        else if(keyword.contains(note.getTitle())){
            score = 80;
        }
        else if(note.getContent().contains( keyword)){
            score = 60;
        }
        else if(note.getTags().contains(keyword)){
            score = 40;
        }
        else if(isHaveCommonWord(note.getTitle(), keyword)){
            score = 20;
        }
        return score;
    }

    public boolean isHaveCommonWord(String title, String keyword){
        List<String>  words = new ArrayList<>(Arrays.stream(title.split("")).toList());//可变集合
        List<String> keywords = new ArrayList<>(Arrays.stream(keyword.split("")).toList());//可变集合
        boolean isHave =words.retainAll(keywords);//是否修改集合
        return isHave && !words.isEmpty();
    }

    public List<Note> intelligentSearch(String keyword){
        if(keyword == null||keyword.trim().isEmpty()){
            return new ArrayList<>();
        }
        keyword = removeprifix(keyword);//去除常见前缀
        String newKeyWord =extractKeyWords(keyword);//提取关键词
        if(newKeyWord == null||newKeyWord.trim().isEmpty()){
            return new ArrayList<>();
        }
        Map<Note,Integer> result = new HashMap<>();
        for(Note note : noteService.getNotes()) {
            int score = calculateMatchScore(newKeyWord, note);
            if(score > 0){
                result.put(note, score);
            }
        }
        return result.entrySet().stream().sorted((o1, o2) -> o2.getValue() - o1.getValue())
                        .map(Map.Entry::getKey).toList();
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
