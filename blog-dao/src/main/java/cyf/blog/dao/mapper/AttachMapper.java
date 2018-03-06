package cyf.blog.dao.mapper;

import cyf.blog.dao.model.Attach;
import cyf.blog.dao.model.AttachExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttachMapper {
    long countByExample(AttachExample example);

    int deleteByExample(AttachExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Attach record);

    int insertSelective(Attach record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<Attach> selectByExample(AttachExample example);

    Attach selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Attach record, @Param("example") AttachExample example);

    int updateByExample(@Param("record") Attach record, @Param("example") AttachExample example);

    int updateByPrimaryKeySelective(Attach record);

    int updateByPrimaryKey(Attach record);
}