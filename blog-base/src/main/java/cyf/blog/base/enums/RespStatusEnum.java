package cyf.blog.base.enums;

import lombok.Getter;

/**
 * @since 1.0
 */
@Getter
public enum RespStatusEnum {

    //1000一下的状态码为通用状态码
    OK(0,"success"),
    ERROR(1,"系统错误"),
    PARAM_FAIL(2,"参数错误"),
    PARAM_FAIL_NOT_EMOJI(3,"参数不支持emoji表情"),

    //1000以上状态码按业务划分
    COLUMN_NOT_EXIST(1000, "栏目不存在"),
    TAG_NOT_EXIST(1001, "标签不存在"),
    USER_NOT_LOGIN(6000, "未登录"),

    ;




    private int status;
    private String desc;

    RespStatusEnum(int status, String desc){
        this.status = status;
        this.desc = desc;
    }
    
	public static RespStatusEnum get(Integer status) {
		if (status == null) {
			return null;
		}
		for (RespStatusEnum respStatusEnum : RespStatusEnum.values()) {
			if (respStatusEnum.getStatus() == status) {
				return respStatusEnum;
			}
		}
		return null;
	}
}
