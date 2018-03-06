package cyf.blog.dao.mapper;

import cyf.blog.dao.model.Metas;
import cyf.blog.dao.model.MetasExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MetasMapper {
    long countByExample(MetasExample example);

    int deleteByExample(MetasExample example);

    int deleteByPrimaryKey(Integer mid);

    int insert(Metas record);

    int insertSelective(Metas record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<Metas> selectByExample(MetasExample example);

    Metas selectByPrimaryKey(Integer mid);

    int updateByExampleSelective(@Param("record") Metas record, @Param("example") MetasExample example);

    int updateByExample(@Param("record") Metas record, @Param("example") MetasExample example);

    int updateByPrimaryKeySelective(Metas record);

    int updateByPrimaryKey(Metas record);
}