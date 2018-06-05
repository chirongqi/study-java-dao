package com.chirq.study.jdbc.orm.datasource.util;

/**
 * 
 * <b>类名</b>：SqlHelper.java<br>
 * <p>
 * <b>标题</b>：sql语句拼接常量
 * </p>
 * <p>
 * <b>描述</b>：
 * </p>
 * 
 * @author <font color='blue'>chirq</font>
 * @version 1.0
 * @date 2015年4月9日 上午11:37:53
 */
public class SqlHelper {
    /** 空格符 */
    public static final char SPACE = ' ';

    /** 逗号分隔符 */
    public static final String COMMA = ", ";

    /** 参数占位符 */
    public static final String PARAMETER_PLACEHOLDER = "?";

    /** 左括号 */
    public static final String LEFT_BRACE = "(";

    /** 右括号 */
    public static final String RIGHT_BRACE = ")";

    /**
     * 操作符
     */
    /** 等于号 */
    public static final String EQ = " = ";

    /** 小于号 */
    public static final String LT = " < ";

    public static final String GT = " > ";

    public static final String NE = " <> ";

    public static final String LE = " <= ";

    public static final String GE = " >= ";

    public static final String LIKE = " LIKE ";

    public static final String IS_NULL = " IS NULL ";

    public static final String IS_NOT_NULL = " IS NOT NULL ";

    public static final String BETWEEN = " BETWEEN ";

    public static final String AND = " AND ";

    public static final String OR = " OR ";

    public static final String IN = " IN ";

    /** NOT_IN */
    public static final String NOT_IN = " NOT IN ";

    /** where */
    public static final String WHERE = " WHERE ";

    /** FROM */
    public static final String FROM = " FROM ";

    /**
     * 操作类型
     */
    /** 查询实体 */
    public static final String SELECT_ENTITY = "SELECT_ENTITY";

    /** 查询所有实体 */
    public static final String SELECT_ALL = "SELECT_ALL";

    /** 分页查询 */
    public static final String SELECT_PAGE = "SELECT_PAGE";

    /** 保存实体 */
    public static final String SAVE = "SAVE";

    /** 保存或实体 */
    public static final String SAVE_OR_UPDATE = "SAVE_OR_UPDATE";

    /** 批量实体 */
    public static final String BATCH_SAVE = "BATCH_SAVE";

    /** 更新实体 */
    public static final String UPDATE = "UPDATE";

    /** 批量更新实体 */
    public static final String BATCH_UPDATE = "BATCH_SAVE";

    /** 删除实体 */
    public static final String DELETE = "DELETE";

    /** 更新实体单个字段值 */
    public static final String UPDATE_FIELD = "UPDATE_FIELD";

    /**
     * 
     * <b>方法名</b>：spellMarkSql<br>
     * <b>功能</b>：将sql查询条件中单个条件拼装引号<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015年4月10日 下午4:47:08
     * @param str
     * @return
     */
    public static String spellMarkSql(String str) {
        if (FKStringUtils.isEmpty(str)) {
            return str;
        }
        String[] splits = str.split(",");
        StringBuffer strBf = new StringBuffer();
        strBf.append('(');
        boolean flag = Boolean.FALSE;
        for (int count = 0, size = splits.length; count < size; count++) {
            if (flag) {
                strBf.append(',');
            } else {
                flag = Boolean.TRUE;
            }
            if (FKStringUtils.isNotEmpty(splits[count])) {
                strBf.append('\'').append(splits[count]).append('\'');
            }
        }
        return strBf.append(')').toString();
    }

    /**
     * 
     * <b>方法名</b>：spellMarkSql<br>
     * <b>功能</b>：根据字符串数组将sql查询条件中单个条件拼装引号<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015年4月10日 下午4:47:08
     * @param str
     * @return
     */
    public static String spellMarkSql(String[] args) {
        if (args == null || args.length == 0) {
            return "";
        }
        StringBuffer strBf = new StringBuffer();
        strBf.append('(');
        boolean flag = Boolean.FALSE;
        for (int count = 0, size = args.length; count < size; count++) {
            if (flag) {
                strBf.append(',');
            } else {
                flag = Boolean.TRUE;
            }
            if (FKStringUtils.isNotEmpty(args[count])) {
                strBf.append('\'').append(args[count]).append('\'');
            }
        }
        return strBf.append(')').toString();
    }
}
