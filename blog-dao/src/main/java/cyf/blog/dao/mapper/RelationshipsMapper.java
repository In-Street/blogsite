package cyf.blog.dao.mapper;

import cyf.blog.dao.model.RelationshipsExample;
import cyf.blog.dao.model.RelationshipsKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RelationshipsMapper {
    long countByExample(RelationshipsExample example);

    int deleteByExample(RelationshipsExample example);

    int deleteByPrimaryKey(RelationshipsKey key);

    int insert(RelationshipsKey record);

    int insertSelective(RelationshipsKey record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<RelationshipsKey> selectByExample(RelationshipsExample example);

    int updateByExampleSelective(@Param("record") RelationshipsKey record, @Param("example") RelationshipsExample example);

    int updateByExample(@Param("record") RelationshipsKey record, @Param("example") RelationshipsExample example);
}