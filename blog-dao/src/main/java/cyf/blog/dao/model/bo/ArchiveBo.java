package cyf.blog.dao.model.bo;

import cyf.blog.dao.model.Contents;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 归档传输对象
 *
 * @author Cheng Yufei
 * @create 2018-03-11 下午4:03
 **/
@Getter
@Setter
public class ArchiveBo implements Serializable {

    private String date;
    private Integer count;
    private List<Contents> articles;

}
