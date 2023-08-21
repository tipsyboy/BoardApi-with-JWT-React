package study.tipsyboy.boardApiProject.posts.domain;

import lombok.Getter;

@Getter
public enum Category {

    FREE("free", "자유게시판"),
    GAME("game", "게임게시판");

    private String key;
    private String value;

    Category(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Category getCategoryByKey(String key) {
        for (Category category : Category.values()) {
            if (category.getKey().equals(key)) {
                return category;
            }
        }
        return null;
    }
}
