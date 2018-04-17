package cyf.blog.base.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 通用返回对象
 * 
 * @author Cheng Yufei
 * @create 2018-03-18 上午11:16
 */
@Getter
@Setter
@ToString
public class Response<T> implements Serializable {

	private static final long serialVersionUID = -6553522891216707934L;

	HttpStatus status;
	/**
	 * 错误信息
	 */
	private String msg;
	/**
	 * 请求是否成功
	 */
	private boolean success;
	/**
	 * 服务器响应数据
	 */
	private T payload;

	public Response(HttpStatus status) {
		this.status = status;
	}
	public Response(boolean success,T payload) {
		this.status = status;
		this.payload = payload;
	}

	public Response(boolean success,String msg) {
		this.success = success;
		this.msg = msg;
	}
	public Response(boolean success) {
		this.success = success;
	}

	public static Response ok() {
		return new Response(true);
	}

	public static <T> Response ok(T payload) {
		return new Response(true, payload);
	}

	public static Response fail(String msg) {
		return new Response(false,msg);
	}
	public static Response fail() {
		return new Response(false);
	}
}
