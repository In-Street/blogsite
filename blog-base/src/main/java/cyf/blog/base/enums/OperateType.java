package cyf.blog.base.enums;

import lombok.Getter;

/**
 * 操作类型
 * @author Cheng Yufei
 * @create 2018-03-24 15:30
 **/
@Getter
public enum OperateType {

    login(1, "登录"),

    ;

    private Integer code;
    private String name;

    OperateType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static OperateType get(Integer code) {
        if (null == code) {
            return null;
        }
        OperateType[] values = OperateType.values();
        for (OperateType value : values) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
