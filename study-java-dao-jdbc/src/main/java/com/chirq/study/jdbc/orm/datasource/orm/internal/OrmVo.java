package com.chirq.study.jdbc.orm.datasource.orm.internal;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <b>类名</b>：OrmCacheVo.java<br>
 * <p>
 * <b>标题</b>：ORM关系映射缓存数据载体
 * </p>
 * 
 * @author <font color='blue'>chirq</font>
 * @version 1.0
 */
class OrmVo {
    /** 插入语句 */
    private String insertSql;

    /** 更新语句 */
    private String updateSql;

    /** 删除语句 */
    private String deleteSql;

    /** 查询语句 */
    private String selectSql;

    /** 主键属性 */
    private String priFieldKey;

    /** 主键字段 */
    private String priColumnKey;

    /** 表名称 */
    private String tableName;

    /** 实体属性段集合 */
    private List<String> fields;

    /** 实体类全名 包括包路径 */
    private String className;

    /** 实例的类加载器 */
    private ClassLoader cLoader;

    protected OrmVo() {
        fields = new ArrayList<String>();
    }

    protected ClassLoader getcLoader() {
        return cLoader;
    }

    protected void setcLoader(ClassLoader cLoader) {
        this.cLoader = cLoader;
    }

    protected String getInsertSql() {
        return insertSql;
    }

    protected void setInsertSql(String insertSql) {
        this.insertSql = insertSql;
    }

    protected String getUpdateSql() {
        return updateSql;
    }

    protected void setUpdateSql(String updateSql) {
        this.updateSql = updateSql;
    }

    protected String getDeleteSql() {
        return deleteSql;
    }

    protected void setDeleteSql(String deleteSql) {
        this.deleteSql = deleteSql;
    }

    protected String getSelectSql() {
        return selectSql;
    }

    protected void setSelectSql(String selectSql) {
        this.selectSql = selectSql;
    }

    protected String getPriFieldKey() {
        return priFieldKey;
    }

    protected void setPriFieldKey(String priFieldKey) {
        this.priFieldKey = priFieldKey;
    }

    protected String getPriColumnKey() {
        return priColumnKey;
    }

    protected void setPriColumnKey(String priColumnKey) {
        this.priColumnKey = priColumnKey;
    }

    protected String getTableName() {
        return tableName;
    }

    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

    protected List<String> getFields() {
        return fields;
    }

    protected void setFields(List<String> fields) {
        this.fields = fields;
    }

    protected String getClassName() {
        return className;
    }

    protected void setClassName(String className) {
        this.className = className;
    }

}
