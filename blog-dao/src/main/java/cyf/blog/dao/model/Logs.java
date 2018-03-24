package cyf.blog.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Logs implements Serializable {
    private Integer id;

    private Integer authorId;

    private String ip;

    private Date created;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 操作对象
     */
    private String operateObject;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperateObject() {
        return operateObject;
    }

    public void setOperateObject(String operateObject) {
        this.operateObject = operateObject;
    }
}