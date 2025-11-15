package Hongweijie_KnowledgeBase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Note {
    private Long id;
    private String title;
    private String content;
    private List<String> tags;// 标签
    private String createTime;
    private String updateTime;

    public Note(){
        tags = new ArrayList<String>();
        createTime = LocalDateTime.now().toString();
        updateTime = LocalDateTime.now().toString();

    }

    public Note(String title,String content,List<String> tags){
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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
