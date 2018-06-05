package com.chirq.study.jdbc.orm.datasource.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FKStringUtils {

    /**
     * 
     * <b>方法名</b>：subStringToInteger<br>
     * <b>功能</b>：将截取后的字符串转化为数字<br>
     * 
     * @author <font color='blue'>InfiniteKeys</font>
     * @date 2015年4月6日 下午5:18:11
     * @param src
     * @param start
     * @param to
     * @return
     */
    public static Integer subStringToInteger(String src, String start, String to) {
        return stringToInteger(subString(src, start, to));
    }

    /**
     * 
     * <b>方法名</b>：subString<br>
     * <b>功能</b>：例子: subString("abcd","a","c")="b"<br>
     * 
     * @author <font color='blue'>InfiniteKeys</font>
     * @date 2015年4月6日 下午5:17:16
     * @param src
     * @param start
     *            null while start from index=0
     * @param to
     *            to null while to index=src.length
     * @return
     */
    public static String subString(String src, String start, String to) {
        int indexFrom = start == null ? 0 : src.indexOf(start);
        int indexTo = to == null ? src.length() : src.indexOf(to);
        if (indexFrom < 0 || indexTo < 0 || indexFrom > indexTo) {
            return null;
        }

        if (null != start) {
            indexFrom += start.length();
        }

        return src.substring(indexFrom, indexTo);

    }

    /**
     * 
     * <b>方法名</b>：stringToInteger<br>
     * <b>功能</b>：将字符串 转换为数字,字符串为空直接返回NULL，字符串必须为数字格式，如"1233"=1233<br>
     * 
     * @author <font color='blue'>InfiniteKeys</font>
     * @date 2015年4月6日 下午5:16:03
     * @param in
     * @return
     */
    public static Integer stringToInteger(String in) {
        if (in == null) {
            return 0;
        }
        in = in.trim();
        if (in.length() == 0) {
            return 0;
        }

        try {
            return Integer.parseInt(in);
        } catch (NumberFormatException e) {
            // LOG.warn("stringToInteger fail,string=" + in, e);
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 
     * <b>方法名</b>：stringToDouble<br>
     * <b>功能</b>：字符串转DOUBLE<br>
     * 
     * @author <font color='blue'>InfiniteKeys</font>
     * @date 2015-9-7 上午11:14:22
     * @param in
     * @return
     */
    public static Double stringToDouble(String in) {
        if (in == null) {
            return 0.00;
        }
        in = in.trim();
        if (in.length() == 0) {
            return 0.00;
        }

        try {
            return Double.parseDouble(in);
        } catch (NumberFormatException e) {
            // LOG.warn("stringToInteger fail,string=" + in, e);
            e.printStackTrace();
            return 0.00;
        }
    }

    public static int lowerHashCode(String text) {
        if (text == null) {
            return 0;
        }
        int h = 0;
        for (int i = 0; i < text.length(); ++i) {
            char ch = text.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                ch = (char) (ch + 32);
            }

            h = 31 * h + ch;
        }
        return h;
    }

    /**
     * 从最后一个sub开始截取str，或者self为true将包含sub字符串，否则只截取最后一个sub以外的str。
     * <p>
     * 注意：若在str中查找不到sub则返回空字符串""
     * </p>
     * 
     * @param str
     * @param sub
     * @param self
     * @return str
     */
    public static String lastSubString(String str, String sub, boolean self) {
        int li = str.lastIndexOf(sub);
        if (li == -1) {
            return "";
        }
        li = self ? li : li + 1;
        return str.substring(li);
    }

    /**
     * 从最后一个sub开始截取str（包含sub字符串）。
     * 
     * @param str
     * @param sub
     * @return str
     */
    public static String lastSubString(String str, String sub) {
        return lastSubString(str, sub, true);
    }

    /**
     * Replaces all instances of oldString with newString in line with the added
     * feature that matches of newString in oldString ignore case.
     * 
     * @param line
     *            the String to search to perform replacements on
     * @param oldString
     *            the String that should be replaced by newString
     * @param newString
     *            the String that will replace all instances of oldString
     * @return a String will all instances of oldString replaced by newString
     */
    public static final String replaceIgnoreCase(String line, String oldString, String newString) {
        if (line == null) {
            return null;
        }
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }
        return line;
    }

    /**
     * Replaces all instances of oldString with newString in line. The count
     * Integer is updated with number of replaces.
     * 
     * @param line
     *            the String to search to perform replacements on
     * @param oldString
     *            the String that should be replaced by newString
     * @param newString
     *            the String that will replace all instances of oldString
     * @return a String will all instances of oldString replaced by newString
     */
    public static final String replace(String line, String oldString, String newString, int[] count) {
        if (line == null) {
            return null;
        }
        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            int counter = 0;
            counter++;
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = line.indexOf(oldString, i)) > 0) {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        }
        return line;
    }

    /**
     * This method takes a string which may contain HTML tags (ie, &lt;b&gt;,
     * &lt;table&gt;, etc) and converts the '&lt'' and '&gt;' characters to
     * their HTML escape sequences.
     * 
     * @param input
     *            the text to be converted.
     * @return the input string with the characters '&lt;' and '&gt;' replaced
     *         with their HTML escape sequences.
     */
    public static final String escapeHTMLTags(String input) {
        // Check if the string is null or zero length -- if so, return
        // what was sent in.
        if (input == null || input.length() == 0) {
            return input;
        }
        // Use a StringBuffer in lieu of String concatenation -- it is
        // much more efficient this way.
        StringBuffer buf = new StringBuffer(input.length());
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '<') {
                buf.append("&lt;");
            } else if (ch == '>') {
                buf.append("&gt;");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    /**
     * This method takes a string which may contain Quotation tags (ie, &quot;.
     * etc) and converts the '"' characters to their HTML escape sequences.
     * 
     * @param input
     *            the text to be converted.
     * @return the input string with the characters '"' replaced with their HTML
     *         escape sequences.
     */
    public static final String escapeQuotationTags(String input) {
        // Check if the string is null or zero length -- if so, return
        // what was sent in.
        if (input == null || input.length() == 0) {
            return input;
        }
        // Use a StringBuffer in lieu of String concatenation -- it is
        // much more efficient this way.
        StringBuffer buf = new StringBuffer(input.length());
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '"') {
                buf.append("&quot;");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    /**
     * Pseudo-random number generator object for use with randomString(). The
     * Random class is not considered to be cryptographically secure, so only
     * use these random Strings for low to medium security applications.
     */
    private static Random randGen = null;

    /**
     * Array of numbers and letters of mixed case. Numbers appear in the list
     * twice so that there is a more equal chance that a number will be picked.
     * We can use the array to get a random number or letter by picking a random
     * array index.
     */
    private static char[] numbersAndLetters = null;

    private static Object initLock = new Object();

    /**
     * Returns a random String of numbers and letters of the specified length.
     * The method uses the Random class that is built-in to Java which is
     * suitable for low to medium grade security uses. This means that the
     * output is only pseudo random, i.e., each number is mathematically
     * generated so is not truly random.
     * <p>
     * <p/>
     * For every character in the returned String, there is an equal chance that
     * it will be a letter or number. If a letter, there is an equal chance that
     * it will be lower or upper case.
     * <p>
     * <p/>
     * The specified length must be at least one. If not, the method will return
     * null.
     * 
     * @param length
     *            the desired length of the random String to return.
     * @return a random String of numbers and letters of the specified length.
     */
    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        }
        // Init of pseudo random number generator.
        if (randGen == null) {
            synchronized (initLock) {
                if (randGen == null) {
                    randGen = new Random();
                    // Also initialize the numbersAndLetters array
                    numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
                }
            }
        }
        // Create a char buffer to put random letters and numbers in.
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }

    /**
     * 把字符串转换编码
     * 
     * @param input
     * @param sourceEncoding
     * @param targetEncoding
     * @return
     */
    public static String changeEncoding(String input, String sourceEncoding, String targetEncoding) {
        if (input == null || input.equals("")) {
            return input;
        }

        try {
            byte[] bytes = input.getBytes(sourceEncoding);
            return new String(bytes, targetEncoding);
        } catch (Exception ex) {
        }
        return input;
    }

    /**
     * arrs数组里是否包含有obj结点
     * 
     * @param arrs
     * @param obj
     * @return
     */
    public static boolean contain(Object[] arrs, Object obj) {
        if (arrs == null || arrs.length == 0) {
            return false;
        }
        for (int i = 0; i < arrs.length; i++) {
            Object object = arrs[i];
            if (object.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 从origin中把del的数组内容都删掉。
     * 
     * @param origin
     * @param del
     * @return
     */
    public static String[] filter(String[] origin, String[] del) {
        List<String> list = new ArrayList<String>(origin.length - del.length);
        for (int i = 0; i < origin.length; i++) {
            String string = origin[i];
            boolean needDel = false;
            for (int j = 0; j < del.length; j++) {
                String string2 = del[j];
                if (string.equals(string2)) {
                    needDel = true;
                    break;
                }
            }
            if (!needDel) {
                list.add(string);
            }
        }
        String[] result = new String[list.size()];
        return list.toArray(result);
    }

    /**
     * 将字符串转成布尔型 1,yes,y,true=true ,否则为false
     * 
     * @param str
     * @return
     */
    public static boolean convertStringToBoolean(Object str) {
        if ("1".equals(str)) {
            return true;
        } else if ("yes".equals(str)) {
            return true;
        } else if ("y".equals(str)) {
            return true;
        } else if ("true".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 将字符串转成布尔型 1,yes,y,true=true ,否则为false
     * 
     * @param str
     * @return
     */
    public static boolean convertStringToBoolean(String str) {
        if ("1".equals(str)) {
            return true;
        } else if ("yes".equals(str)) {
            return true;
        } else if ("y".equals(str)) {
            return true;
        } else if ("true".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 返回obj在objArr中的位置
     * 
     * @param objArr
     * @param obj
     * @return
     */
    public static int getIndexInArr(Object[] objArr, Object obj) {
        if (objArr == null) {
            return -1;
        }
        for (int i = 0; i < objArr.length; i++) {
            Object object = objArr[i];
            if (object.equals(obj)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 
     * <b>方法名</b>：upperCaseFirstLetter<br>
     * <b>功能</b>：首字母大写<br>
     * 
     * @author <font color='blue'>InfiniteKeys</font>
     * @date 2015年4月6日 下午4:58:31
     * @param str
     * @return
     */
    public static String upperCaseFirstLetter(String str) {
        return str.replaceFirst(String.valueOf(str.charAt(0)), String.valueOf(str.charAt(0)).toUpperCase());
    }

    /**
     * 
     * <b>方法名</b>：changeDiffEncodingString<br>
     * <b>功能</b>：改变字符串编码以符合当前系统环境<br>
     * 
     * @author <font color='blue'>InfiniteKeys</font>
     * @date 2015年4月6日 下午5:12:47
     * @param str
     * @return
     */
    public static String changeDiffEncodingString(String str) {
        if (str == null) {
            return str;
        }
        String returnStr = "";
        try {
            returnStr = new String(str.getBytes("GBK"), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!returnStr.equals(str)) {
            try {
                returnStr = new String(str.getBytes("gb2312"), "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (!returnStr.equals(str)) {
            try {
                returnStr = new String(str.getBytes("iso8859-1"), "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (!returnStr.equals(str)) {
            try {
                returnStr = new String(str.getBytes("utf-8"), "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return returnStr;
    }

    /**
     * 把String 拼成 一个String[]。
     * 
     * @param str
     * @param split
     *            默认是","
     * @return
     */
    public static String[] regroupStringArr(String str, String split) {
        if (str == null) {
            return null;
        }
        if (split == null) {
            split = ",";
        }
        String[] returnStrArr;
        String[] strArr = str.split(split);
        boolean[] booArr = new boolean[strArr.length];// 判断每一项是否符合要求，符合记为true。
        int count = 0;// 符合要求的总数
        for (int i = 0; i < strArr.length; i++) {
            if (!strArr[i].trim().equals("")) {
                count++;
                booArr[i] = true;
            } else {
                booArr[i] = false;
            }
        }
        returnStrArr = new String[count];
        count = 0;
        for (int i = 0; i < booArr.length; i++) {
            if (booArr[i]) {
                returnStrArr[count++] = strArr[i];
            }
        }
        return returnStrArr;
    }

    /**
     * 把String[] 拼成 一个String。 如：regroupStringArr(new String[]{"a", "b", "c"},
     * "#", "%", ",") 返回 #a%,#b%,#c%
     * 
     * @param strArr
     * @param split
     * @return
     */
    public static String regroupStringArr(String[] strArr, String prefix, String subfix, String split) {
        if (strArr == null || strArr.length == 0) {
            return "";
        }
        if (prefix == null) {
            prefix = "";
        }
        if (subfix == null) {
            subfix = "";
        }
        if (split == null) {
            split = "";
        }
        StringBuffer returnValue = new StringBuffer("");
        for (int i = 0; i < strArr.length; i++) {
            returnValue.append(prefix).append(strArr[i]).append(subfix).append(split);
        }
        // 去掉最后一个split
        if (split.length() > 0)
            returnValue.deleteCharAt(returnValue.length() - split.length());
        return returnValue.toString();
    }

    /**
     * 把类似于 |fsdjl|fjskadlfj|fjskafj| 这样的id串拼成类似于 'fsdjl','fjskadlfj','fjskafj'
     * 这样的串返回(删除中间""空字符串) cutStringToArray(str, "|", "'", ",")
     * 
     * @param str
     * @param split
     * @param replacement
     * @param replacementSplit
     * @return
     */
    public static String regroupString(String str, String split, String replacement, String replacementSplit) {
        if (str == null) {
            return "";
        }
        String[] strArr = str.split(split);
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < strArr.length; i++) {
            if (!"".equals(strArr[i])) {
                sb.append(replacement + strArr[i] + replacement + replacementSplit);
            }
        }
        // 去掉最后一个replacementSplit
        if (sb.length() > replacementSplit.length()) {
            sb.deleteCharAt(sb.length() - replacementSplit.length());
        }
        return sb.toString();
    }

    /**
     * 
     * <b>方法名</b>：isHoliday<br>
     * <b>功能</b>：根据系统配置中的假期设置判断是否是节假日<br>
     * 
     * @author <font color='blue'>InfiniteKeys</font>
     * @date 2015年4月6日 下午5:10:17
     * @param date
     * @param holidays
     * @return
     */
    public static boolean isHoliday(Date date, String holidays) {
        /*
         * 节假日的定义: 周六,周日, 1.1--1.3; 5.1--5.7;
         */
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int monty = calendar.get(Calendar.MONTH);
        if (day == 0 || day == 6)
            return true;
        holidays = holidays != null ? holidays : "";
        String[] holidayarr = holidays.split(",");
        for (int i = 0; i < holidayarr.length; i++) {
            try {
                String[] yd = holidayarr[i].split("-");
                if (yd[0].trim().equals(String.valueOf(monty + 1)) && yd[1].trim().equals(String.valueOf(day)))
                    return true;
            } catch (Exception e) {
                System.out.println("节假日" + holidayarr[i] + "有误");
            }

        }
        return false;
    }

    /**
     * A String for a space character.
     * 
     * @since 3.2
     */
    public static final String SPACE = " ";

    /**
     * The empty String {@code ""}.
     * 
     * @since 2.0
     */
    public static final String EMPTY = "";

    /**
     * A String for linefeed LF ("\n").
     * 
     * @see <a href=
     *      "http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">JLF:
     *      Escape Sequences for Character and String Literals</a>
     * @since 3.2
     */
    public static final String LF = "\n";

    /**
     * A String for carriage return CR ("\r").
     * 
     * @see <a href=
     *      "http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">JLF:
     *      Escape Sequences for Character and String Literals</a>
     * @since 3.2
     */
    public static final String CR = "\r";

    /**
     * Represents a failed index search.
     * 
     * @since 2.1
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * <p>
     * {@code FKStringUtils} instances should NOT be constructed in standard
     * programming. Instead, the class should be used as
     * {@code FKStringUtils.trim(" foo ");}.
     * </p>
     * 
     * <p>
     * This constructor is public to permit tools that require a JavaBean
     * instance to operate.
     * </p>
     */
    public FKStringUtils() {
        super();
    }

    // Empty checks
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Checks if a CharSequence is empty ("") or null.
     * </p>
     * 
     * <pre>
     * FKStringUtils.isEmpty(null)      = true
     * FKStringUtils.isEmpty("")        = true
     * FKStringUtils.isEmpty(" ")       = false
     * FKStringUtils.isEmpty("bob")     = false
     * FKStringUtils.isEmpty("  bob  ") = false
     * </pre>
     * 
     * <p>
     * NOTE: This method changed in Lang version 2.0. It no longer trims the
     * CharSequence. That functionality is available in isBlank().
     * </p>
     * 
     * @param cs
     *            the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to
     *        isEmpty(CharSequence)
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * <p>
     * Checks if a CharSequence is not empty ("") and not null.
     * </p>
     * 
     * <pre>
     * FKStringUtils.isNotEmpty(null)      = false
     * FKStringUtils.isNotEmpty("")        = false
     * FKStringUtils.isNotEmpty(" ")       = true
     * FKStringUtils.isNotEmpty("bob")     = true
     * FKStringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     * 
     * @param cs
     *            the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not empty and not null
     * @since 3.0 Changed signature from isNotEmpty(String) to
     *        isNotEmpty(CharSequence)
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !FKStringUtils.isEmpty(cs);
    }

}
