package cyf.blog.dao.mapper;

import cyf.blog.dao.model.Contents;
import cyf.blog.dao.model.ContentsExample;
import java.util.List;
import java.util.Map;

import cyf.blog.dao.model.bo.ArchiveBo;
import org.apache.ibatis.annotations.Param;

public interface ContentsMapper {
    long countByExample(ContentsExample example);

    int deleteByExample(ContentsExample example);

    int deleteByPrimaryKey(Integer cid);

    int insert(Contents record);

    int insertSelective(Contents record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<Contents> selectByExampleWithBLOBs(ContentsExample example);

    List<Contents> selectByExample(ContentsExample example);

    Contents selectByPrimaryKey(Integer cid);

    int updateByExampleSelective(@Param("record") Contents record, @Param("example") ContentsExample example);

    int updateByExampleWithBLOBs(@Param("record") Contents record, @Param("example") ContentsExample example);

    int updateByExample(@Param("record") Contents record, @Param("example") ContentsExample example);

    int updateByPrimaryKeySelective(Contents record);

    int updateByPrimaryKeyWithBLOBs(Contents record);

    int updateByPrimaryKey(Contents record);

    List<ArchiveBo> getArchiveBos();

    int updateMap(Map<String, Object> params);
}