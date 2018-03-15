package cyf.blog.dao.mapper;

import cyf.blog.dao.model.Contents;
import cyf.blog.dao.model.ContentsExample;
import cyf.blog.dao.model.bo.ArchiveBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentsMapper {
    long countByExample(ContentsExample example);

    int deleteByExample(ContentsExample example);

    int deleteByPrimaryKey(Integer cid);

    int insert(Contents record);

    int insertSelective(Contents record);


    List<Contents> selectByExampleWithBLOBs(ContentsExample example);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    /**
        eg:
        fieldMap 名称固定
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("reply_num", "+1");
        fieldMap.put("comments_num", "+1");

        Map<String, Object> paramMap = new HashMap<>();
        params.put("fieldMap", fieldMap);
        params.put("id", id);
*/
    int multiplePlusMinusByPrimaryKey(java.util.Map<String, Object> paramMap);

    List<Contents> selectByExample(ContentsExample example);

    Contents selectByPrimaryKey(Integer cid);

    int updateByExampleSelective(@Param("record") Contents record, @Param("example") ContentsExample example);

    int updateByExampleWithBLOBs(@Param("record") Contents record, @Param("example") ContentsExample example);

    int updateByExample(@Param("record") Contents record, @Param("example") ContentsExample example);

    int updateByPrimaryKeySelective(Contents record);

    int updateByPrimaryKeyWithBLOBs(Contents record);

    int updateByPrimaryKey(Contents record);

    List<ArchiveBo> getArchiveBos();
}