package com.chirq.study.jdbc.orm.datasource.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * <b>类名</b>：FKObjectUtils.java<br>
 * <p>
 * <b>标题</b>：对象操作utils
 * </p>
 * <p>
 * <p>
 * </p>
 * 
 * @author <font color='blue'>chirq</font>
 * @version 1.0
 * @date 2015-6-5 下午6:48:56
 */
public class FKObjectUtils {

    private FKObjectUtils() {
        super();
    }

    /**
     * 
     * <b>方法名</b>：parseMethodName<br>
     * <b>功能</b>：通过方法获取实体属性<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015年4月8日 下午12:11:54
     * @param method
     * @return
     */
    public static String parseMethodName(Method method) {
        String methodName = method.getName();
        boolean isBoo = Boolean.FALSE;
        String getRegexLow = "^get[a-z_].*$"; // get方法过滤参数
        String getRegexSub = "^get[A-Z].*$"; // get方法过滤参数
        Class<?>[] paramClass = method.getParameterTypes();
        if (paramClass.length > 0) { // set方法解析
            for (Class<?> cl : paramClass) {
                if (boolean.class.equals(cl.getName())) {
                    isBoo = Boolean.TRUE;
                    continue;
                }
            }
            if (!isBoo) {
                getRegexLow = "^set[a-z_].*$"; // set方法过滤参数
                getRegexSub = "^set[A-Z].*$"; // set方法过滤参数
            }
        } else { // get方法解析
            if (method.getReturnType() == boolean.class) {
                isBoo = Boolean.TRUE;
            }
        }

        String fieldName = null; // 通过方法解析出来的属性字段名称
        if (isBoo && methodName.startsWith("is")) {
            if (methodName.matches("^is[a-z_].*$")) {
                fieldName = methodName.substring(2);
            } else if (methodName.matches("^is[A-Z].*$")) {
                fieldName = methodName.substring(2, 3).toLowerCase() + methodName.substring(3);
            }
        } else {
            if (methodName.matches(getRegexLow)) {
                fieldName = methodName.substring(3);
            } else if (methodName.matches(getRegexSub)) {
                fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
            }
        }
        return fieldName;
    }

    /**
     * 
     * <b>方法名</b>：isExtends<br>
     * <b>功能</b>：判断clazz是否是superClass或其子类<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015年4月9日 下午3:50:17
     * @param clazz
     * @param superClass
     * @return
     */
    public static boolean isExtends(Class<?> clazz, Class<?> superClass) {
        if (clazz == superClass) {
            return true;
        }
        Class<?> _class = clazz.getSuperclass();
        while (_class != null) {
            if (_class == superClass) {
                return true;
            }
            _class = _class.getSuperclass();
        }
        return false;
    }

    /**
     * 
     * <b>方法名</b>：getAllField<br>
     * <b>功能</b>：获得类的所有属性<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015年4月9日 下午3:50:36
     * @param clazz
     * @return
     */
    public static List<Field> getAllField(Class<?> clazz) {
        List<Field> list = new ArrayList<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            list.add(field);
        }
        Class<?> _clazz = clazz.getSuperclass();
        while (_clazz != null) {
            list.addAll(getAllField(_clazz));
            _clazz = _clazz.getSuperclass();
        }
        return list;
    }

    /**
     * 
     * <b>方法名</b>：checkField<br>
     * <b>功能</b>：判断类的属性是否存在<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015-6-7 下午2:27:14
     * @param clazz
     * @param objField
     * @return
     */
    public static boolean checkField(Class<?> clazz, String objField) {
        if (clazz == null) {
            return false;
        }
        List<Field> list = new ArrayList<Field>();
        // 获取属性名称
        // Field[] fields = clazz.getDeclaredFields();
        list.addAll(Arrays.asList(clazz.getDeclaredFields()));
        // 获取父类属性名称
        Class<?> parent = clazz.getSuperclass();
        if (parent != null) {
            list.addAll(Arrays.asList(parent.getDeclaredFields()));
        }
        parent = parent.getSuperclass();
        if (parent != null) {
            list.addAll(Arrays.asList(parent.getDeclaredFields()));
        }
        // Field[] superFields = clazz.getSuperclass().getDeclaredFields();
        String fieldName = null;
        try {
            for (int count = 0, size = list.size(); count < size; count++) {
                fieldName = list.get(count).getName();
                if (fieldName.equals(objField)) {
                    return true;
                }
            }

            // // 判断当前类中是否存在该属性
            // for (int count = 0, size = fields.length; count < size; count++)
            // {
            // fieldName = fields[count].getName();
            // if (fieldName.equals(objField)) {
            // return true;
            // }
            // }
            // // 判断父类中是否存在该属性
            // for (int count = 0, size = superFields.length; count < size;
            // count++) {
            // fieldName = superFields[count].getName();
            // if (fieldName.equals(objField)) {
            // return true;
            // }
            // }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 
     * <b>方法名</b>：checkField<br>
     * <b>功能</b>：判断属性是否存在<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015-1-9 下午04:30:33
     * @param entity
     * @param objField
     * @return
     */
    public static boolean checkField(Object entity, String objField) {
        if (entity == null) {
            return false;
        }
        return checkField(entity.getClass(), objField);
        //
        // // 获取属性名称
        // Field[] fields = entity.getClass().getDeclaredFields();
        // // 获取父类属性名称
        // Field[] superFields =
        // entity.getClass().getSuperclass().getDeclaredFields();
        // String fieldName = null;
        // try {
        // // 判断当前类中是否存在该属性
        // for (int count = 0, size = fields.length; count < size; count++) {
        // fieldName = fields[count].getName();
        // if (fieldName.equals(objField)) {
        // return true;
        // }
        // }
        // // 判断父类中是否存在该属性
        // for (int count = 0, size = superFields.length; count < size; count++)
        // {
        // fieldName = superFields[count].getName();
        // if (fieldName.equals(objField)) {
        // return true;
        // }
        // }
        // } catch (IllegalArgumentException e) {
        // e.printStackTrace();
        // } catch (SecurityException e) {
        // e.printStackTrace();
        // }
        // return false;
    }

    /**
     * 
     * <b>方法名</b>：invokeGetterMethod<br>
     * <b>功能</b>：反射执行对象指定属性的get方法<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015-6-5 下午3:21:31
     * @param entityClass
     * @param fieldName
     * @return
     */
    public static Object invokeGetterMethod(Object entity, String fieldName) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, entity.getClass());
            return pd.getReadMethod().invoke(entity); // 调用get方法获取
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * <b>方法名</b>：invokeSetterMethod<br>
     * <b>功能</b>：反射执行对象指定属性的set方法<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2015-6-5 下午3:22:33
     * @param entityClass
     * @param fieldName
     * @param value
     */
    public static void invokeSetterMethod(Object entity, String fieldName, Object value) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, entity.getClass());
            pd.getWriteMethod().invoke(entity, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
    }

    /**
     * <b>方法名</b>：objectAsXml<br>
     * <b>功能</b>：将object转化为xml串<br>
     * 注意：依赖于getter和setter方法 注意：如果字符集转化过程中与系统默认的字符集不一致，会出现字符处理的错误。参见转化为字节的处理。
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2012-8-2 上午10:31:07
     * @param o
     * @return
     * @throws IOException
     */
    public static String objectAsXml(Object object) throws IOException {
        OutputStream os = new ByteArrayOutputStream(1024);
        // XMLEncoder xml = new XMLEncoder(os); // 默认UTF-8
        XMLEncoder xml = new XMLEncoder(os, "GBK", true, 0);
        xml.writeObject(object);
        xml.flush();
        String result = os.toString();
        xml.close();
        os.close();
        return result;
    }

    // Defaulting
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Returns a default value if the object passed is {@code null}.
     * </p>
     * 
     * <pre>
     * ObjectUtils.defaultIfNull(null, null)      = null
     * ObjectUtils.defaultIfNull(null, "")        = ""
     * ObjectUtils.defaultIfNull(null, "zz")      = "zz"
     * ObjectUtils.defaultIfNull("abc", *)        = "abc"
     * ObjectUtils.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     * 
     * @param <T>
     *            the type of the object
     * @param object
     *            the {@code Object} to test, may be {@code null}
     * @param defaultValue
     *            the default value to return, may be {@code null}
     * @return {@code object} if it is not {@code null}, defaultValue otherwise
     */
    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return object != null ? object : defaultValue;
    }

    // Identity ToString
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Gets the toString that would be produced by {@code Object} if a class did
     * not override toString itself. {@code null} will return {@code null}.
     * </p>
     * 
     * <pre>
     * ObjectUtils.identityToString(null)         = null
     * ObjectUtils.identityToString("")           = "java.lang.String@1e23"
     * ObjectUtils.identityToString(Boolean.TRUE) = "java.lang.Boolean@7fa"
     * </pre>
     * 
     * @param object
     *            the object to create a toString for, may be {@code null}
     * @return the default toString text, or {@code null} if {@code null} passed
     *         in
     */
    public static String identityToString(final Object object) {
        if (object == null) {
            return null;
        }
        final StringBuilder builder = new StringBuilder();
        identityToString(builder, object);
        return builder.toString();
    }

    /**
     * <p>
     * Appends the toString that would be produced by {@code Object} if a class
     * did not override toString itself. {@code null} will throw a
     * NullPointerException for either of the two parameters.
     * </p>
     * 
     * <pre>
     * ObjectUtils.identityToString(appendable, "")            = appendable.append("java.lang.String@1e23"
     * ObjectUtils.identityToString(appendable, Boolean.TRUE)  = appendable.append("java.lang.Boolean@7fa"
     * ObjectUtils.identityToString(appendable, Boolean.TRUE)  = appendable.append("java.lang.Boolean@7fa")
     * </pre>
     * 
     * @param appendable
     *            the appendable to append to
     * @param object
     *            the object to create a toString for
     * @throws IOException
     *             if an I/O error occurs
     * @since 3.2
     */
    public static void identityToString(final Appendable appendable, final Object object) throws IOException {
        if (object == null) {
            throw new NullPointerException("Cannot get the toString of a null identity");
        }
        appendable.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
    }

    /**
     * <p>
     * Appends the toString that would be produced by {@code Object} if a class
     * did not override toString itself. {@code null} will throw a
     * NullPointerException for either of the two parameters.
     * </p>
     * 
     * <pre>
     * ObjectUtils.identityToString(buf, "")            = buf.append("java.lang.String@1e23"
     * ObjectUtils.identityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa"
     * ObjectUtils.identityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa")
     * </pre>
     * 
     * @param buffer
     *            the buffer to append to
     * @param object
     *            the object to create a toString for
     * @since 2.4
     */
    public static void identityToString(final StringBuffer buffer, final Object object) {
        if (object == null) {
            throw new NullPointerException("Cannot get the toString of a null identity");
        }
        buffer.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
    }

    /**
     * <p>
     * Appends the toString that would be produced by {@code Object} if a class
     * did not override toString itself. {@code null} will throw a
     * NullPointerException for either of the two parameters.
     * </p>
     * 
     * <pre>
     * ObjectUtils.identityToString(builder, "")            = builder.append("java.lang.String@1e23"
     * ObjectUtils.identityToString(builder, Boolean.TRUE)  = builder.append("java.lang.Boolean@7fa"
     * ObjectUtils.identityToString(builder, Boolean.TRUE)  = builder.append("java.lang.Boolean@7fa")
     * </pre>
     * 
     * @param builder
     *            the builder to append to
     * @param object
     *            the object to create a toString for
     * @since 3.2
     */
    public static void identityToString(final StringBuilder builder, final Object object) {
        if (object == null) {
            throw new NullPointerException("Cannot get the toString of a null identity");
        }
        builder.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
    }

}
