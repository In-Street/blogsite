package cyf.blog.dao.mapper;

import cyf.blog.dao.model.Users;
import cyf.blog.dao.model.UsersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersMapper {
    long countByExample(UsersExample example);

    int deleteByExample(UsersExample example);

    int deleteByPrimaryKey(Integer uid);

    int insert(Users record);

    int insertSelective(Users record);

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

    List<Users> selectByExample(UsersExample example);

    Users selectByPrimaryKey(Integer uid);

    int updateByExampleSelective(@Param("record") Users record, @Param("example") UsersExample example);

    int updateByExample(@Param("record") Users record, @Param("example") UsersExample example);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);
}