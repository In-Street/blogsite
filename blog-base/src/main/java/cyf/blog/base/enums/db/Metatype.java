package cyf.blog.base.enums.db;

import lombok.Getter;

/**
 * 目标类型
 *
 * @author Cheng Yufei
 * @create 2018-03-11 下午6:11
 **/
@Getter
public enum Metatype {

    link(1, "链接"),
    category(2, "分类"),

    ;

    private Integer code;
    private String name;

    Metatype(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Metatype get(Integer code) {
        if (null == code) {
            return null;
        }
        Metatype[] values = Metatype.values();
        for (Metatype value : values) {
            if (code.equals(value.code)) {
                return value.get(code);
            }
        }
        return null;
    }
}
