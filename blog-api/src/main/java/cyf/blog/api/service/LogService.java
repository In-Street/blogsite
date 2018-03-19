package cyf.blog.api.service;

import com.github.pagehelper.PageHelper;
import cyf.blog.dao.mapper.LogsMapper;
import cyf.blog.dao.model.Logs;
import cyf.blog.dao.model.LogsExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Cheng Yufei
 * @create 2018-03-17 下午6:56
 **/
@Service
@Slf4j
public class LogService {

    @Autowired
    private LogsMapper logsMapper;

    public List<Logs> getLogs(Integer pageIndex,Integer limit) {

        LogsExample logsExample = new LogsExample();
        logsExample.setOrderByClause("created desc,id desc");
        PageHelper.startPage(pageIndex, limit);
        List<Logs> logs = logsMapper.selectByExample(logsExample);
        return logs;

    }

}
