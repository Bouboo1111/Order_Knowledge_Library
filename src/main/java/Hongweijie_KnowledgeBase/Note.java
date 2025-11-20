package Hongweijie_KnowledgeBase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Note {
    private Long id;
    private String title;
    private String content;
    private List<String> tags;// 标签
    private String createTime;
    private String updateTime;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public Note(){
        tags = new ArrayList<String>();
        createTime = LocalDateTime.now().format(formatter);
        updateTime = LocalDateTime.now().format(formatter);

    }

    public Note(String title,String content,List<String> tags){
        this();
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        updateTime = LocalDateTime.now().format(formatter);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        updateTime = LocalDateTime.now().format(formatter);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        updateTime = LocalDateTime.now().format(formatter);
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
        updateTime = LocalDateTime.now().format(formatter);
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
