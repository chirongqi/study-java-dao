package com.chirq.study.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.chirq.study.mybatis.entity.User;

public interface UserMapper {

    User selectByPrimaryKey(Long id);

    User selectByName(String name);

    int deleteByPrimaryKey(Long id);

    int insert(User user);

    int insertSelective(User user);

    int updateByPrimaryKeySelective(User user);

    List<User> selectBySearch(User user);
    /**
     * 
     * <b>方法名</b>：selectByAge<br>
     * <b>功能</b>：下标绑定入参<br>
     * 
     * @author <font color='blue'>rongqi.chi</font>
     * @date 2018年5月25日 下午4:50:50
     * @param age
     * @return
     */
    @Select("select * from t_user where age = #{0}")
    @Results(value = { @Result(column = "user_dept", property = "userDepartment", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
    List<User> selectByAge(Integer age);

    /**
     * 
     * <b>方法名</b>：selectByAge2<br>
     * <b>功能</b>：参数名称与参数绑定同名称<br>
     * 
     * @author <font color='blue'>rongqi.chi</font>
     * @date 2018年5月25日 下午4:50:27
     * @param age
     * @return
     */
    @Select("select * from t_user where age = #{age}")
    @Results(value = { @Result(column = "user_dept", property = "userDepartment", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
    List<User> selectByAge2(Integer age);

    /**
     * 
     * <b>方法名</b>：selectByAge3<br>
     * <b>功能</b>：@Param标注入参<br>
     * 
     * @author <font color='blue'>rongqi.chi</font>
     * @date 2018年5月25日 下午4:49:38
     * @param userAge
     * @return
     */
    @Select("select * from t_user where age = #{age}")
    @Results(value = { @Result(column = "user_dept", property = "userDepartment", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
    List<User> selectByAge3(@Param("age") Integer userAge, int age);
}
