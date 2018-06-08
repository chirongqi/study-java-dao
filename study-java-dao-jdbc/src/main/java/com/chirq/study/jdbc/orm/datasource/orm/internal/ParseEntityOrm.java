package com.chirq.study.jdbc.orm.datasource.orm.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.chirq.study.jdbc.orm.datasource.util.FKObjectUtils;
import com.chirq.study.jdbc.orm.datasource.util.FKStringUtils;
import com.chirq.study.jdbc.orm.datasource.util.SqlHelper;
import com.chirq.study.jdbc.orm.test.entity.UserOrm;

/**
 * 
 * <b>类名</b>：ParseEntityOrm.java<br>
 * <p>
 * <b>标题</b>：解析实体对象成ORM
 * </p>
 * <p>
 * </p>
 * 
 * @author <font color='blue'>chirq</font>
 * @version 1.0
 */
final class ParseEntityOrm {

    private ParseEntityOrm() {
        // 私有化构造方法
    }

    /**
     * 
     * <b>方法名</b>：parseEntity<br>
     * <b>功能</b>：解析实体封装成ORM对象<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @param entity
     * @return
     */
    protected <T> OrmVo parseEntity(Class<T> clazz) {
        OrmVo ormVo = new OrmVo(); // orm映射实体
        ormVo.setTableName(this.getTableName(clazz));// 实体对应的表名称
        this.parsePrimaryKey(clazz, ormVo);
        ormVo.setClassName(clazz.getName());
        // 实体的属性与表字段的映射
        Map<String, String> fieldKeyColVal = this.parseMethodAnnotation(clazz);
        fieldKeyColVal.remove(ormVo.getPriFieldKey());
        fieldKeyColVal.put(ormVo.getPriFieldKey(), ormVo.getPriColumnKey());
        // System.out.println("fieldKeyColVal : " + fieldKeyColVal);

        this.createOrmVoSql(clazz, ormVo, fieldKeyColVal);
        return ormVo;
    }

    /**
     * 
     * <b>方法名</b>：createOrmVoSql<br>
     * <b>功能</b>：封装ORM对象的sql语句<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @param clazz
     * @param ormVo
     * @param fieldKeyColVal
     */
    protected <T> void createOrmVoSql(Class<T> clazz, OrmVo ormVo, Map<String, String> fieldKeyColVal) {
        List<String> fields = new ArrayList<String>();
        StringBuffer insterSql = new StringBuffer("INSERT INTO ").append(ormVo.getTableName()).append(SqlHelper.LEFT_BRACE);
        StringBuffer insertPerch = new StringBuffer(); // 占位符
        StringBuffer updateSql = new StringBuffer("UPDATE ").append(ormVo.getTableName()).append(" SET ");
        StringBuffer deleteSql = new StringBuffer("DELETE FROM ").append(ormVo.getTableName());
        StringBuffer selectSql = new StringBuffer("SELECT ");

        String cName = null; // 表字段名称
        boolean flag = Boolean.FALSE;
        boolean upFlag = Boolean.FALSE;
        for (String field : fieldKeyColVal.keySet()) {
            cName = fieldKeyColVal.get(field);
            if (flag) {
                insterSql.append(SqlHelper.COMMA);
                insertPerch.append(SqlHelper.COMMA);
                selectSql.append(SqlHelper.COMMA);
            } else {
                flag = Boolean.TRUE;
            }
            // 处理insert
            if (!cName.equals(ormVo.getPriColumnKey())) {
                insterSql.append(cName);
                insertPerch.append(SqlHelper.PARAMETER_PLACEHOLDER);
            }

            selectSql.append(cName);

            // 处理更新语句
            if (!cName.equals(ormVo.getPriColumnKey())) {
                if (upFlag) {
                    updateSql.append(SqlHelper.COMMA);
                } else {
                    upFlag = Boolean.TRUE;
                }
                updateSql.append(cName).append(SqlHelper.EQ).append(SqlHelper.PARAMETER_PLACEHOLDER);
            }
            fields.add(field);
        }

        if (insterSql.lastIndexOf(SqlHelper.COMMA) == (insterSql.length() - SqlHelper.COMMA.length())) {
            insterSql.deleteCharAt(insterSql.lastIndexOf(SqlHelper.COMMA));
        }
        if (insertPerch.lastIndexOf(SqlHelper.COMMA) == (insertPerch.length() - SqlHelper.COMMA.length())) {
            insertPerch.deleteCharAt(insertPerch.lastIndexOf(SqlHelper.COMMA));
        }
        insterSql.append(" ) values ( ").append(insertPerch).append(SqlHelper.RIGHT_BRACE);
        selectSql.append(SqlHelper.FROM).append(ormVo.getTableName());
        ormVo.setInsertSql(insterSql.toString());
        ormVo.setSelectSql(selectSql.toString());
        ormVo.setDeleteSql(deleteSql.toString());
        ormVo.setUpdateSql(updateSql.toString());
        ormVo.setFields(fields);
        // System.out.println("insterSql : " + insterSql);
        // System.out.println("selectSql : " + selectSql);
        // System.out.println("updateSql : " + updateSql);
        // System.out.println("deleteSql : " + deleteSql);
    }

    /**
     * 
     * <b>方法名</b>：getTableName<br>
     * <b>功能</b>：解析注解获取实体对应的表名称<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @param clazz
     * @return
     */
    protected <T> String getTableName(Class<T> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        if (table == null) {
            throw new NullPointerException(clazz.getClass().getName() + "实体，没有Table注解");
        }
        String tName = table.name();
        String scmema = table.schema();
        if (FKStringUtils.isNotEmpty(scmema)) {
            tName = scmema + "." + tName;
        }
        return tName.toUpperCase();
    }

    /**
     * 
     * <b>方法名</b>：getPrimaryKey<br>
     * <b>功能</b>：获取主键注解<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015年4月9日 下午4:21:32
     * @param clazz
     * @return
     */
    protected <T> void parsePrimaryKey(Class<T> clazz, OrmVo ormVo) {
        String cName = null; // 主键字段名称
        Column column = null; // Column注解
        boolean isFind = Boolean.FALSE;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getAnnotation(Id.class) != null) { // 找到ID注解
                if (isFind) {
                    throw new RuntimeException(clazz.getName() + "实体，不允许重复ID注解");
                } else {
                    isFind = Boolean.TRUE;
                }
                column = field.getAnnotation(Column.class);
                if (column != null) {
                    cName = column.name(); // 获取注解字段名称
                    ormVo.setPriFieldKey(field.getName());
                }
            }
        }
        if (!isFind) {
            for (Method method : clazz.getMethods()) {
                if (method.getAnnotation(Id.class) != null) { // 找到ID注解
                    if (isFind) {
                        throw new RuntimeException(clazz.getName() + "实体，不允许重复ID注解");
                    } else {
                        isFind = Boolean.TRUE;
                    }
                    column = method.getAnnotation(Column.class);
                    if (method.getAnnotation(Column.class) != null) {
                        cName = column.name(); // 获取注解字段名称
                        ormVo.setPriFieldKey(FKObjectUtils.parseMethodName(method));
                    }
                }
            }
        }
        if (cName == null) {
            throw new NullPointerException(clazz.getName() + "实体，没有ID注解");
        }
        ormVo.setPriColumnKey(cName);
    }

    /**
     * 
     * <b>方法名</b>：parseMethodAnnotation<br>
     * <b>功能</b>：解析方法注解<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @param clazz
     * @param fieldMap
     */
    protected <T> Map<String, String> parseMethodAnnotation(Class<T> clazz) {
        Map<String, String> fieldMap = new LinkedHashMap<String, String>();
        Column column = null; // Column注解
        String cName = null; // 注解中定义的字段名称
        Transient trans = null; // Transient注解
        List<String> tranList = new ArrayList<String>(2); // 存放有Transient注解的字段
        String fieldName = null; // 字段名称
        // for (Method method : clazz.getMethods()) {
        for (Field field : clazz.getDeclaredFields()) {
            column = field.getAnnotation(Column.class);
            trans = field.getAnnotation(Transient.class);
            if (column != null) {
                // fieldName = FKObjectUtils.parseMethodName(method);
                fieldName = field.getName();
                if (FKStringUtils.isEmpty(fieldName)) {
                    throw new NullPointerException(clazz.getName() + "实体，请符合javaBean get/set方法");
                }
                cName = column.name();
                // 注解字段为空，则默认实体字段与表字段名称相同
                if (FKStringUtils.isEmpty(cName)) {
                    cName = fieldName;
                }
                // 属性与表字段的映射配置
                fieldMap.put(fieldName, cName.toUpperCase());
            }

            if (trans != null) { // Transient注解不为空,则取消实体映射
                // fieldName = FKObjectUtils.parseMethodName(method);
                fieldName = field.getName();
                tranList.add(fieldName);
            }
        }

        for (String tranCol : tranList) {
            if (fieldMap.containsKey(tranCol)) {
                fieldMap.remove(tranCol);
            }
        }
        return fieldMap;
    }

    /**
     * 
     * <b>方法名</b>：getInstance<br>
     * <b>功能</b>：获取实例<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2014-7-7 上午10:10:15
     * @return
     */
    protected static ParseEntityOrm getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 静态内部类实现单例模式，实现延迟加载
     */
    private static class SingletonHolder {
        // 单例对象
        private static final ParseEntityOrm INSTANCE = new ParseEntityOrm();
    }

    public static void main(String[] args) {
        ParseEntityOrm parse = ParseEntityOrm.getInstance();
        UserOrm user = new UserOrm();
        user.setName("王尼玛");
        user.setAge(12);
        user.setAddress("ddddd");
        OrmVo vo = parse.parseEntity(UserOrm.class);
        System.out.println(vo);
    }

}
