package Hongweijie_KnowledgeBase.Command;

import Hongweijie_KnowledgeBase.Note;
import Hongweijie_KnowledgeBase.Service.DisplayService;
import Hongweijie_KnowledgeBase.Service.INoteService;
import Hongweijie_KnowledgeBase.Service.SearchService;

import java.util.List;
import java.util.Scanner;

public class SearchCommand implements  Command{
    private INoteService noteService;
    private SearchService searchService;
    private DisplayService displayService;

    public SearchCommand(INoteService noteService, SearchService searchService, DisplayService displayService) {
        this.noteService = noteService;
        this.searchService = searchService;
        this.displayService = displayService;
    }

    public INoteService getNoteService() {
        return noteService;
    }

    public void setNoteService(INoteService noteService) {
        this.noteService = noteService;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public DisplayService getDisplayService() {
        return displayService;
    }

    public void setDisplayService(DisplayService displayService) {
        this.displayService = displayService;
    }

    @Override
    public void execute(String input, Scanner scanner) {
        List<Note> notes = searchService.intelligentSearch(input);
        if(notes.isEmpty()){
            System.out.println("没有找到匹配的笔记");
            return;
        }
        else{
            System.out.println("共找到" + notes.size() + "个匹配的笔记：");
            if(isShowNoteDetail(scanner)){
                displayService.displaySearchResult(notes);
            }

        }
    }

    public boolean isShowNoteDetail(Scanner scanner){
        System.out.println("是否需要查看完整笔记？");
        String answer = scanner.nextLine();
        return answer.equalsIgnoreCase("是")||answer.equalsIgnoreCase("yes");
    }

    @Override
    public String getCommandName() {
        return "搜索笔记";
    }
}
