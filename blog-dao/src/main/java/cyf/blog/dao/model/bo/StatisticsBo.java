package cyf.blog.dao.model.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 统计
 * @author Cheng Yufei
 * @create 2018-03-17 下午7:38
 **/
@Getter
@Setter
public class StatisticsBo implements Serializable{

    private long articles;
    private long comments;
    private long links;
    private long attachs;
}
