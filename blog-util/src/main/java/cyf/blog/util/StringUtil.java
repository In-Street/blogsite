package cyf.blog.util;


import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	private static MessageDigest digest = null;
	private static final String dfltEncName = "UTF-8";
	public static final String SEARCH_SEPERATOR = " ,;|　";
	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String empty = "";
	public static final String enter = "\r\n";

	public static boolean isEmpty(final String s) {
        return s == null || "".equals(s) || s.trim().length() == 0;
    }

	public static boolean isNotEmpty(String string) {

		return null != string && !"".equals(string.trim());
	}

	/**
	 * 所有字段为空
	 * @param params
	 * @return
	 */
	public static boolean isAllBlank( String... params) {
		for (String s : params) {
			if(StringUtils.isNotBlank(s)){
				return false;
			}
		}
		return true;
	}

	public static String join(final String... params) {
		StringBuilder sb = new StringBuilder();
		for (String s : params)
			sb.append(s);
		return sb.toString();
	}

	/*--------------------------------------session hash----------------------------------*/
	public static final String hash(final String s) {
		if (s == null)
			return null;
		if (digest == null) {
			synchronized (StringUtil.class) {
				if (digest == null) {
					try {
						digest = MessageDigest.getInstance("MD5");
					} catch (NoSuchAlgorithmException nsae) {
						nsae.printStackTrace();
					}
				}
			}
		}
		digest.update(s.getBytes());
		return toHex(digest.digest());
	}

	public static final String toHex(byte hash[]) {
		if (hash == null)
			return null;
		StringBuffer buf = new StringBuffer(hash.length * 2);
		int i;

		for (i = 0; i < hash.length; i++) {
			if (((int) hash[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) hash[i] & 0xff, 16));
		}
		return buf.toString();
	}

	public static String decode(String s) {
		String str = null;

		if (s == null)
			return null;
		try {
			str = URLDecoder.decode(s, dfltEncName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String encode(String s) {
		String str = null;

		if (s == null)
			return null;
		try {
			str = URLEncoder.encode(s, dfltEncName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * rest 匹配为"_"的数据
	 */
	public static String toHorizontal(final String str) {
		if (isEmpty(str))
			return str;
		if (str.equals("_"))
			return null;
		return str;
	}

	/**
	 * 去掉[]的toString方法
	 * 
	 * @param o
	 * @return
	 */
	public static String toStringValue(Object o) {
		String tmp = toString(o);
		if (tmp == null || tmp.length() <= 2)
			return null;
		return tmp.substring(1, tmp.length() - 1);
	}

	public static String toString(Object o) {
		if (o == null)
			return null;
		if (o.getClass().isArray()) {
			String className = o.getClass().getName();
			if ("[I".equals(className)) {
				int[] a = (int[]) o;
				return Arrays.toString(a);
			} else if ("[J".equals(className)) {
				long[] a = (long[]) o;
				return Arrays.toString(a);
			} else if ("[S".equals(className)) {
				short[] a = (short[]) o;
				return Arrays.toString(a);
			} else if ("[C".equals(className)) {
				char[] a = (char[]) o;
				return Arrays.toString(a);
			} else if ("[B".equals(className)) {
				byte[] a = (byte[]) o;
				return Arrays.toString(a);
			} else if ("[F".equals(className)) {
				float[] a = (float[]) o;
				return Arrays.toString(a);
			} else if ("[D".equals(className)) {
				double[] a = (double[]) o;
				return Arrays.toString(a);
			} else if ("[Z".equals(className)) {
				boolean[] a = (boolean[]) o;
				return Arrays.toString(a);
			} else {
				Object[] a = (Object[]) o;
				return Arrays.toString(a);
			}
		} else {
			return o.toString();
		}
	}

	/**
	 * 判断字符串是否为数字组成
	 * 
	 * @param str
	 *            字符串
	 * @return true 数字、 false 非数字
	 */
	public static boolean isNumber(final String str) {
		if (null == str || "".equals(str))
			return false;
		String pattern = "^[0-9]*";
		return str.matches(pattern);
	}

	public static boolean isDecimal(final String str) {
		if (null == str || "".equals(str))
			return false;
		String pattern = "^[+-]?\\d+(\\.\\d+)?$";
		return str.matches(pattern);
	}

	/**
	 * 根据字符串返回Long数组 EG：101,205,303 ==> {101,205,303}
	 * 
	 * @param str
	 *            字符串
	 * @return Long数组
	 */
	public static Long[] getNumberArray(final String str) {

		// 字符串数组
		String sIds[] = null;
		// Long串数组
		Long lIds[] = null;

		// 字符串不为NULL切割字符串
		if (str != null && !"".equals(str)) {

			sIds = str.split(",");
			int length = 0;

			// 确定lIds长度
			for (int i = 0; i < sIds.length; i++) {

				if (isNumber(sIds[i])) {
					length++;
				}
			}

			lIds = new Long[length];

			// 转换Str数组->Long数组
			for (int i = 0; i < sIds.length; i++) {

				if (isNumber(sIds[i])) {
					lIds[i] = Long.parseLong(sIds[i]);
				}
			}

			return lIds;

		} else {
			return new Long[0];
		}

	}

	public static String getStringsValue(String... str) {

		String result = "";

		if (str == null)
			return result;

		for (int i = 0; i < str.length; i++) {
			if (str[i] != null && !str[i].equals("")) {
				String s = str[i].replace("'", "\\'");
				if (i < str.length - 1) {
					result = result + "'" + s + "',";
				} else {
					result = result + "'" + s + "'";
				}
			}
		}

		return result;
	}

	/**
	 * convert String to String array with special phrase, such
	 * as",space,dash,omit"
	 * 
	 * @param str
	 * @return
	 */
	public static String[] ConvertString2Array(String str) {
		return ConvertString2Array(str, null);
	}

	public static String[] ConvertString2Array(String str, String specialDash) {
		String[] result = new String[0];
		if (str == null)
			return result;
		String[] ret = null;
		if (specialDash == null) {
			ret = str.split("[" + SEARCH_SEPERATOR + "]");
		} else {
			ret = str.split(specialDash);
		}

		if (ret == null)
			return result;
		List<String> resultList = new ArrayList<String>();
		for (int i = 0; i < ret.length; i++) {
			if (ret[i] != null && !"".equals(ret[i])) {
				resultList.add(ret[i].trim());
			}
		}
		return resultList.toArray(result);
	}

	public static String generateString(int length) // 参数为返回随机数的长度
	{
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}

	public static String coverzore(int place,String value){
		if (value == null && ("").equals(value)) return "000000";
		
		if (value.length() > place) return value;
		
		StringBuilder sb = new StringBuilder();
		if (value.length()> 1 || value.length() <= place){
			for(int i=0;i<(place-value.length());i++){
				sb.append("0");
			}
			sb.append(value);
		}
		
		return sb.toString();
	}

	public static String replaceStrToStart(String str,int off,int limit){
		if (str == null || str == "") return str;
		if (limit < off || off >str.length() || limit > str.length())
			return str;
		String replace = str.substring(off,limit);
		String newR = "";
		for(int i=0;i<replace.length();i++){
			newR+="*";
		}
		return str.replace(replace,newR);
	}

	public static BigDecimal divide(BigDecimal a, BigDecimal b){
		return a.divide(b, 4, RoundingMode.FLOOR);
	}
	
	public static String format(int count, String targetStr) {
		if (targetStr.indexOf(".") != -1) {
			// 获取小数点的位置
			int num = 0;
			num = targetStr.indexOf(".");

			String dotAfter = targetStr.substring(0, num + 1);
			String afterData = targetStr.replace(dotAfter, "");
			if (afterData.length() < count) {
				afterData = afterData + "0";
			}
			return targetStr.substring(0, num) + "." + afterData.substring(0, count);
		} else {
			return targetStr;
		}
	}

	public static String removeLastSplist(String str,String separator){
		if (str == null) return str;
		if (!str.contains(separator)) return str;
		return str.substring(0, str.lastIndexOf(separator));
	}

	public static String trim(String str) {

		if (isEmpty(str)) {
			return "";
		}
		return str.trim().intern();
	}

	public static String sqlLike(String field) {

		field = trim(field);
		return "%" + field + "%";
	}

	public static String uuid() {

		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

	/**
	 * 获得从from到to的随机数(不包含后者to)
	 * @param from
	 * @param to
	 * @return
	 */
	public static int randomNumber(int from, int to) {

		return RandomUtils.nextInt(from, to);
	}

	public static boolean isUrl(String str) {

		String regex = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\/])+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 描述:获取 post 请求的 byte[] 数组
	 * <pre>
	 * 举例：
	 * </pre>
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static byte[] getRequestPostBytes(HttpServletRequest request)
			throws IOException {
		int contentLength = request.getContentLength();
		if(contentLength<0){
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {

			int readlen = request.getInputStream().read(buffer, i,
					contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		return buffer;
	}

	/**
	 * 描述:获取 post 请求内容
	 * <pre>
	 * 举例：
	 * </pre>
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getRequestPostStr(HttpServletRequest request)
			throws IOException {
		byte buffer[] = getRequestPostBytes(request);
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = "UTF-8";
		}
		return new String(buffer, charEncoding);
	}

	/**
	 * 格式化 打印json
	 * */
	public static String formatJson(String jsonStr) {

        try {
            if (isEmpty(jsonStr)) {
                return empty;
            }
            StringBuilder sb = new StringBuilder();
            char last;
            char current = '\0';
            int indent = 0;
            boolean isInQuotationMarks = false;
            for (int i = 0; i < jsonStr.length(); i++) {
                last = current;
                current = jsonStr.charAt(i);
                switch (current) {
                    case '"':
                        if (last != '\\'){
                            isInQuotationMarks = !isInQuotationMarks;
                        }
                        sb.append(current);
                        break;
                    case '{':
                    case '[':
                        sb.append(current);
                        if (!isInQuotationMarks) {
                            sb.append('\n');
                            indent++;
                            addIndentBlank(sb, indent);
                        }
                        break;
                    case '}':
                    case ']':
                        if (!isInQuotationMarks) {
                            sb.append('\n');
                            indent--;
                            addIndentBlank(sb, indent);
                        }
                        sb.append(current);
                        break;
                    case ',':
                        sb.append(current);
                        if (last != '\\' && !isInQuotationMarks) {
                            sb.append('\n');
                            addIndentBlank(sb, indent);
                        }
                        break;
                    default:
                        sb.append(current);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

	/**
	 * 添加space
	 *
	 * @param sb
	 * @param indent
	 * @author lizhgb
	 * @Date 2015-10-14 上午10:38:04
	 */
	private static void addIndentBlank(StringBuilder sb, int indent) {
		for (int i = 0; i < indent; i++) {
			sb.append('\t');
		}
	}
}
