package cyf.blog.base.enums;

import lombok.Getter;

/**
 * 操作对象
 * @author Cheng Yufei
 * @create 2018-03-24 15:30
 **/
@Getter
public enum OperateObject {

    system(1, "系统"),
    setting(2,"系统设置"),

    ;

    private Integer code;
    private String name;

    OperateObject(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static OperateObject get(Integer code) {
        if (null == code) {
            return null;
        }
        OperateObject[] values = OperateObject.values();
        for (OperateObject value : values) {
            if (code.equals(value.code)) {
                return value;
            }
        }
        return null;
    }
}
