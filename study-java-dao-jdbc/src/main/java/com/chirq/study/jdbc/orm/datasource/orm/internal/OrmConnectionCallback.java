package com.chirq.study.jdbc.orm.datasource.orm.internal;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chirq.study.jdbc.orm.datasource.orm.ConnectionCallback;
import com.chirq.study.jdbc.orm.datasource.util.SqlHelper;

/**
 * 
 * <b>类名</b>：OrmConnectionCallback.java<br>
 * <p>
 * <b>标题</b>：获取connection链接，ORM操作类
 * </p>
 * <p>
 * <b>描述</b>：
 * </p>
 * <p>
 * <b>版权声明</b>：Copyright (c) 2015
 * </p>
 * <p>
 * <b>公司</b>：4K梦想
 * </p>
 * 
 * @author <font color='blue'>chirq</font>
 * @version 1.0
 * @date 2015年4月9日 上午11:09:51
 */
public final class OrmConnectionCallback<T> implements ConnectionCallback<T> {

    private Logger logger = LoggerFactory.getLogger(OrmConnectionCallback.class);

    private String sqlType; // 查询类型

    private OrmVo ormVo; // orm对象

    private Object[] args; // 查询参数

    private StringBuffer sql; // 查询sql

    protected OrmConnectionCallback(String sqlType, OrmVo ormVo, Object... params) {
        sql = new StringBuffer();
        this.sqlType = sqlType;
        this.ormVo = ormVo;
        this.args = params;
        this.buildSql();
    }

    /**
     * 重写父类方法 执行查询
     */
    @Override
    public T doInConnection(Connection con) throws SQLException {
        PreparedStatement stmt = null;
        try {
            logger.info("执行sql：{}", sql);
            stmt = con.prepareStatement(sql.toString());
            this.buildStatement(stmt); // 绑定参数
            stmt.execute();
            ResultSet resultSet = stmt.getResultSet();
            if (resultSet != null) {
                return this.manageSearchResultSet(resultSet);
            }
            logger.info("受影响记录数：{}", stmt.getUpdateCount());
            return null;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * 
     * <b>方法名</b>：buildSql<br>
     * <b>功能</b>：组装sql<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015年4月9日 下午8:55:32
     */
    private void buildSql() {
        if (SqlHelper.SELECT_ENTITY.equals(this.sqlType)) { // 查询实体
            sql.append(ormVo.getSelectSql());
            sql.append(SqlHelper.WHERE).append(ormVo.getPriColumnKey()).append(SqlHelper.EQ).append(SqlHelper.PARAMETER_PLACEHOLDER);
        } else if (SqlHelper.SELECT_ALL.equals((this.sqlType))) { // 查询所有
            sql.append(ormVo.getSelectSql());
        } else if (SqlHelper.SELECT_PAGE.equals((this.sqlType))) { // 分页查询
            sql.append(ormVo.getSelectSql());
        } else if (SqlHelper.SAVE.equals((this.sqlType))) { // 保存
            sql.append(ormVo.getInsertSql());
            // this.checkPrimaryValue(this.args[0], ormVo.getPriFieldKey()); //
            // 验证主键是否有值，没有值给予默认值
        } else if (SqlHelper.SAVE_OR_UPDATE.equals((this.sqlType))) { // 保存或更新
            // 验证主键是否有值 有值则更新，没有值则保存
            if (this.checkPrimaryValue(this.args[0], ormVo.getPriFieldKey())) {
                this.sqlType = SqlHelper.UPDATE;
            } else {
                this.sqlType = SqlHelper.SAVE;
            }
            this.buildSql();
        } else if (SqlHelper.BATCH_SAVE.equals((this.sqlType))) { // 批量保存
            sql.append(ormVo.getInsertSql());
        } else if (SqlHelper.UPDATE.equals((this.sqlType))) { // 更新
            sql.append(ormVo.getUpdateSql());
            sql.append(SqlHelper.WHERE).append(ormVo.getPriColumnKey()).append(SqlHelper.EQ).append(SqlHelper.PARAMETER_PLACEHOLDER);
        } else if (SqlHelper.BATCH_UPDATE.equals((this.sqlType))) { // 批量更新
            sql.append(ormVo.getUpdateSql());
        } else if (SqlHelper.DELETE.equals((this.sqlType))) { // 删除
            sql.append(ormVo.getDeleteSql());
            sql.append(SqlHelper.WHERE).append(ormVo.getPriColumnKey());
            if (this.args.length == 1) {
                sql.append(SqlHelper.EQ).append(SqlHelper.PARAMETER_PLACEHOLDER);
            } else {
                sql.append(SqlHelper.IN).append(SqlHelper.LEFT_BRACE);
                for (int count = 0, size = args.length; count < size; count++) {
                    sql.append(SqlHelper.PARAMETER_PLACEHOLDER).append(SqlHelper.COMMA);
                }
                sql.deleteCharAt(sql.lastIndexOf(SqlHelper.COMMA));
                sql.append(SqlHelper.RIGHT_BRACE);
            }
        } else if (SqlHelper.UPDATE_FIELD.equals((this.sqlType))) { // 更新实体单个字段值
            this.builderUpdateField();
        } else {
            throw new RuntimeException(ormVo.getClassName() + "实体，未找到指定查询类型的SQL" + this.sqlType);
        }
    }

    /**
     * 
     * <b>方法名</b>：buildStatement<br>
     * <b>功能</b>：组装参数<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015年4月9日 下午8:55:43
     * @param pstmt
     * @throws SQLException
     */
    private void buildStatement(PreparedStatement pstmt) throws SQLException {
        if (SqlHelper.SELECT_ENTITY.equals(this.sqlType)) { // 查询实体
            pstmt.setObject(1, args[0]);

        } else if (SqlHelper.SELECT_ALL.equals((this.sqlType))) { // 查询所有

        } else if (SqlHelper.SELECT_PAGE.equals((this.sqlType))) { // 分页查询

        } else if (SqlHelper.SAVE.equals((this.sqlType))) { // 保存
            this.setStatementValue(ormVo.getFields(), this.args[0], pstmt);
        } else if (SqlHelper.BATCH_SAVE.equals((this.sqlType))) { // 批量保存

        } else if (SqlHelper.UPDATE.equals((this.sqlType))) { // 更新
            // 将主键放到参数最后面
            // ormVo.getFields().remove(ormVo.getFields().indexOf(ormVo.getPriFieldKey()));
            // ormVo.getFields().add(ormVo.getPriFieldKey()); // 放到list最后
            this.setStatementValue(ormVo.getFields(), this.args[0], pstmt);
        } else if (SqlHelper.BATCH_UPDATE.equals((this.sqlType))) { // 批量更新

        } else if (SqlHelper.DELETE.equals((this.sqlType))) { // 删除
            for (int count = 0, size = args.length; count < size; count++) {
                pstmt.setObject(count + 1, args[count]);
            }
        } else if (SqlHelper.UPDATE_FIELD.equals((this.sqlType))) { // 更新实体单个字段值
            pstmt.setObject(1, args[2]); // value
            Object[] ids = (Object[]) this.args[3]; // 主键集合
            for (int count = 0, size = ids.length; count < size; count++) {
                pstmt.setObject(count + 2, ids[count]); // 主键id
            }
        }
    }

    /**
     * 
     * <b>方法名</b>：manamanageSearchResultSetgeResultSet<br>
     * <b>功能</b>：处理查询结果集<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015年4月9日 下午8:56:14
     * @param resultSet
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    private T manageSearchResultSet(ResultSet resultSet) throws SQLException {
        List<Object> list = new ArrayList<Object>(); // 装载结果集
        Object newObj = null;
        try {
            Class<?> clazz = ormVo.getcLoader().loadClass(ormVo.getClassName()); // 反射实体
            while (resultSet.next()) {
                newObj = clazz.newInstance(); // 创建实体
                this.setFieldValue(ormVo.getFields(), newObj, resultSet);
                list.add(newObj);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        logger.info("查询结果 {} 行", list.size());
        if (SqlHelper.SELECT_ENTITY.equals(this.sqlType)) { // 查询实体
            return list.isEmpty() ? null : (T) list.get(0);
        }
        return (T) list;
    }

    /**
     * 
     * <b>方法名</b>：setFieldValue<br>
     * <b>功能</b>：将sql查询结果保存到对象中<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015年4月10日 下午5:02:47
     * @param list
     * @param entity
     * @param resultSet
     * @throws SQLException
     */
    public void setFieldValue(List<String> list, Object entity, ResultSet resultSet) throws SQLException, NumberFormatException {
        Class<?> objClass = entity.getClass();
        PropertyDescriptor pd = null;
        Method setter = null;
        int resCount = 1; // 占位符开始序号
        try {
            Class<?> typeClass = null;
            Object value = null;
            for (int count = 0, size = list.size(); count < size; count++) {
                // value = resultSet.getObject(resCount++); // sql查询结果
                value = resultSet.getObject(entity.getClass().getDeclaredField(list.get(count)).getAnnotation(Column.class).name());
                if (value == null) {
                    continue;
                }
                pd = new PropertyDescriptor(list.get(count), objClass);
                // System.out.println(pd.getPropertyType().getName());
                setter = pd.getWriteMethod(); // 获取set方法
                // System.out.println(setter.getName());
                typeClass = pd.getPropertyType(); // 方法参数类型class
                if (typeClass == String.class) { // String
                    if (value instanceof String) {
                        setter.invoke(entity, (String) value);
                    } else {
                        setter.invoke(entity, String.valueOf(value));
                    }
                } else if (typeClass == Integer.class || typeClass == int.class) { // int
                    if (value instanceof Integer) {
                        setter.invoke(entity, (Integer) value);
                    } else {
                        setter.invoke(entity, Integer.parseInt(String.valueOf(value)));
                    }
                } else if (typeClass == Long.class || typeClass == long.class) { // long
                    if (value instanceof Long) {
                        setter.invoke(entity, (Long) value);
                    } else {
                        setter.invoke(entity, Long.parseLong(String.valueOf(value)));
                    }
                } else if (typeClass == Float.class || typeClass == float.class) { // float
                    if (value instanceof Float) {
                        setter.invoke(entity, (Float) value);
                    } else {
                        setter.invoke(entity, Float.parseFloat(String.valueOf(value)));
                    }
                } else if (typeClass == Double.class || typeClass == double.class) { // double
                    if (value instanceof Double) {
                        setter.invoke(entity, (Double) value);
                    } else {
                        setter.invoke(entity, Double.parseDouble(String.valueOf(value)));
                    }
                } else if (typeClass == java.util.Date.class) {
                    setter.invoke(entity, new java.util.Date(((java.sql.Timestamp) value).getTime()));
                } else if (typeClass == java.sql.Timestamp.class) {
                    setter.invoke(entity, (java.sql.Timestamp) value);
                } else if (typeClass == java.sql.Date.class) {
                    setter.invoke(entity, (Double) value);
                } else if (typeClass == java.sql.Time.class) {
                    setter.invoke(entity, (java.sql.Time) value);
                } else if (typeClass == java.sql.Blob.class) { // Blob
                    value = resultSet.getBlob(resCount - 1); // 重新获取blob值
                    if (value != null) {
                        setter.invoke(entity, (java.sql.Blob) value);
                    }
                } else {
                    setter.invoke(entity, value);
                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * <b>方法名</b>：setStatementValue<br>
     * <b>功能</b>：设置PreparedStatement参数值<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015年4月10日 下午5:00:18
     * @param list
     * @param entity
     * @param pstmt
     * @throws SQLException
     */
    public void setStatementValue(List<String> list, Object entity, PreparedStatement pstmt) throws SQLException, NumberFormatException {
        Class<?> objClass = entity.getClass();
        PropertyDescriptor pd = null;
        Method getter = null;
        Object value = null;
        int psCount = 1; // 占位符开始序号
        try {
            Class<?> typeClass = null;
            for (int count = 0, size = list.size(); count < size; count++) {
                if (SqlHelper.SAVE.equals((this.sqlType))) { // 保存
                    if (list.get(count).equals("id")) {
                        continue;
                    }
                }
                pd = new PropertyDescriptor(list.get(count), objClass);
                getter = pd.getReadMethod(); // 获取get方法
                typeClass = pd.getPropertyType(); // 获取方法返回值类型
                value = getter.invoke(entity); // 调用get方法获取
                if (value == null) {
                    pstmt.setObject(psCount++, value);
                    continue;
                }
                if (typeClass == String.class) {
                    pstmt.setString(psCount++, (String) value);
                } else if (typeClass == java.util.Date.class) {
                    pstmt.setTimestamp(psCount++, new java.sql.Timestamp(((java.util.Date) value).getTime()));
                } else if (typeClass == java.sql.Date.class) {
                    pstmt.setDate(psCount++, (java.sql.Date) value);
                } else if (typeClass == java.sql.Time.class) {
                    pstmt.setTime(psCount++, (java.sql.Time) value);
                } else if (typeClass == Integer.class || typeClass == int.class) {
                    pstmt.setInt(psCount++, (Integer) value);
                } else if (typeClass == Long.class || typeClass == long.class) {
                    pstmt.setLong(psCount++, (Long) value);
                } else if (typeClass == Float.class || typeClass == float.class) {
                    pstmt.setFloat(psCount++, (Float) value);
                } else if (typeClass == Double.class || typeClass == double.class) {
                    pstmt.setDouble(psCount++, (Double) value);
                } else {
                    pstmt.setObject(psCount++, value);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * <b>方法名</b>：checkPrimaryValue<br>
     * <b>功能</b>：保存对象时，判断对象的主键是否有值，没有值默认添加32位主键<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015年4月21日 下午4:01:47
     * @param entity
     * @param priField
     * @return true: 有主键值 ； false：没有主键值
     */
    private boolean checkPrimaryValue(Object entity, String priField) {
        boolean isCheck = Boolean.TRUE;
        try {
            PropertyDescriptor pd = new PropertyDescriptor(priField, entity.getClass());
            Object value = pd.getReadMethod().invoke(entity); // 调用get方法获取
            if (value == null) {
                // pd.getWriteMethod().invoke(entity, FKStringUtils.UUID32());
                isCheck = false;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return isCheck;
    }

    /**
     * 
     * <b>方法名</b>：builderUpdateField<br>
     * <b>功能</b>：构建 更新实体单个字段值 sql<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015年4月23日 下午1:01:42
     */
    private void builderUpdateField() {
        sql.append("UPDATE ").append(ormVo.getTableName()).append(" SET ");
        try {
            // PropertyDescriptor pd = new PropertyDescriptor((String)
            // this.args[1], (Class<?>) this.args[0]);
            // Column column = pd.getReadMethod().getAnnotation(Column.class);
            Column column = ((Class<?>) this.args[0]).getDeclaredField((String) this.args[1]).getAnnotation(Column.class);
            if (column == null) {
                throw new NullPointerException(this.args[0] + "实体，属性: " + this.args[1] + " 没有 Column 注解");
            } else {
                sql.append(column.name()).append(SqlHelper.EQ).append(SqlHelper.PARAMETER_PLACEHOLDER);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sql.append(SqlHelper.WHERE).append(ormVo.getPriColumnKey());
        // 处理需要更新的实体主键id
        Object[] ids = (Object[]) this.args[3];
        if (ids.length == 1) {
            sql.append(SqlHelper.EQ).append(SqlHelper.PARAMETER_PLACEHOLDER);
        } else {
            sql.append(SqlHelper.IN).append(SqlHelper.LEFT_BRACE);
            for (int count = 0, size = ids.length; count < size; count++) {
                sql.append(SqlHelper.PARAMETER_PLACEHOLDER).append(SqlHelper.COMMA);
            }
            sql.deleteCharAt(sql.lastIndexOf(SqlHelper.COMMA));
            sql.append(SqlHelper.RIGHT_BRACE);
        }

    }
}
