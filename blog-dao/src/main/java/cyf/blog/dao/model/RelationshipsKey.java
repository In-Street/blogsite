package cyf.blog.dao.model;

import java.io.Serializable;

/**
 * @author 
 */
public class RelationshipsKey implements Serializable {
    private Integer cid;

    private Integer mid;

    private static final long serialVersionUID = 1L;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }
}