package Hongweijie_KnowledgeBase;

import java.util.Set;

/**
 * ID容器类，用于保存可重用的ID集合
 */
public class IdContainer {
    private Set<Long> reusableIds;
    private String description;

    public IdContainer() {
        this.description = "可重用ID集合";
    }

    public IdContainer(Set<Long> reusableIds) {
        this();
        this.reusableIds = reusableIds;
    }

    public Set<Long> getReusableIds() {
        return reusableIds;
    }

    public void setReusableIds(Set<Long> reusableIds) {
        this.reusableIds = reusableIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}