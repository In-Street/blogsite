package cyf.blog.base.enums.db;

import lombok.Getter;

/**
 * @author Cheng Yufei
 * @create 2018-04-17 14:23
 **/
@Getter
public enum AttachType {

    image(1,"image"),
    file(2,"file")

    ;

    private Integer code;
    private String name;

    AttachType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public AttachType get(Integer code) {
        if (null == code) {
            return null;
        }
        AttachType[] values = AttachType.values();
        for (AttachType value : values) {
            if (code.equals(value.code)) {
                return value.get(code);
            }
        }
        return null;
    }
}
