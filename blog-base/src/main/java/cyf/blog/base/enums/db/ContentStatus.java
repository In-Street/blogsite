package cyf.blog.base.enums.db;

import lombok.Getter;

/**
 * @author Cheng Yufei
 * @create 2018-03-06 17:48
 **/
@Getter
public enum ContentStatus {

    publish(1,"已发布"),

    ;

    private Integer code;
    private String name;

    ContentStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public ContentStatus get(Integer code) {
        if (null == code) {
            return null;
        }
        ContentStatus[] values = ContentStatus.values();
        for (ContentStatus value : values) {
            if (code.equals(value.code)) {
                return value.get(code);
            }
        }
        return null;
    }
}
