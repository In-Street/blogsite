package cyf.blog.base.enums.db;

import lombok.Getter;

@Getter
public enum ContentType {

    post(1,"post"),
    page(2,"page"),

    ;

    private Integer code;
    private String name;

    ContentType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public ContentType get(Integer code) {
        if (null == code) {
            return null;
        }
        ContentType[] values = ContentType.values();
        for (ContentType value : values) {
            if (code.equals(value.code)) {
                return value.get(code);
            }
        }
        return null;
    }
}
