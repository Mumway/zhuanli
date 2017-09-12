package cn.com.mumway.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static final String NEW_LINE = "\n";// 换行符
	public static final String NEW_LINE_MARK = "#@n#";// 换行标示

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.print(StringUtil.getGBKSubString(" 22", 0, 4));
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static Set<Integer> splitString(String str, String split) {
		String[] arr = str.split(split);
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < arr.length; i++) {
			if (StringUtil.isNotNull(arr[i]))
				set.add(Integer.parseInt(arr[i]));
		}
		return set;
	}

	public static List<String> splitStringToArrayList(String str, String split) {
		List<String> list = new ArrayList<String>();
		if (StringUtil.isNotNull(str)) {
			String[] arr = str.split(split);
			for (int i = 0; i < arr.length; i++) {
				if (StringUtil.isNotNull(arr[i]))
					list.add(arr[i]);
			}
		}
		return list;
	}

	public static String convertSetToString(Set<Integer> set) {
		StringBuffer str = new StringBuffer();
		if (!set.isEmpty()) {
			int i = 1;
			Iterator<Integer> iterator = (Iterator<Integer>) set.iterator();
			while (iterator.hasNext()) {
				str.append(Integer.toString(iterator.next()));
				if (i < set.size())
					str.append(",");
				i++;
			}
		}
		return str.toString();
	}

	public static String getFileDir(String filePath) {
		int i = filePath.lastIndexOf(File.separator);
		return filePath.substring(0, i + 1);
	}

	public static String getFileName(String filePath) {
		int i = filePath.lastIndexOf(File.separator);
		return filePath.substring(i + 1, filePath.length());
	}

	public static ArrayList<Integer> getAllPosition(String str, String c) {
		ArrayList<Integer> allPos = new ArrayList<Integer>();
		for (int i = 0; i < str.length(); i++) {
			if (str.substring(i, i + 1).equals(c))
				allPos.add(i);
		}
		return allPos;
	}

	public static boolean isNull(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	public static boolean isNotNull(String str) {
		return !isNull(str);
	}

	public static String nullToString(String str) {
		if (str == null || "".equals(str.trim()) || "null".equals(str)) {
			return "";
		}
		return str.trim();
	}

	public static String replaseToNewLine(String str) {
		str = StringUtil.nullToString(str);
		String newStr = str.replaceAll(NEW_LINE_MARK, NEW_LINE);
		return newStr;
	}

	/**
	 * 换行
	 * 
	 * @param line
	 *            行数
	 * @return
	 */
	public static String getNewLine(int line) {
		StringBuffer newStr = new StringBuffer();
		for (int i = 0; i < line; i++) {
			newStr.append(NEW_LINE);
		}
		return newStr.toString();
	}

	public static String getGBKSubString(String str, int start, int end) {
		if (str == null)
			return "";
		byte b[];
		try {
			b = str.getBytes("GBK");
			if (end > b.length)
				end = b.length;
			if (start < 0 || start > b.length || start > end || end < 0)
				return "";
			else {
				int counterOfDoubleByte = 0;
				for (int i = 0; i < start; i++) {
					if (b[i] < 0)
						counterOfDoubleByte++;
				}
				if (counterOfDoubleByte % 2 != 0)
					start = start - 1;
				counterOfDoubleByte = 0;
				for (int i = 0; i < end; i++) {
					if (b[i] < 0)
						counterOfDoubleByte++;
				}
				if (counterOfDoubleByte % 2 != 0)
					end = end - 1;
				return new String(b, start, end - start, "GBK");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public static String getConvert3gpString(String str, int limitLength,
			String suffix) {
		try {
			return getLimitLengthString(str, limitLength, suffix);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String getLimitLengthString(String str, int len, String symbol)
			throws UnsupportedEncodingException {
		int counterOfDoubleByte = 0;
		byte b[] = str.getBytes("GBK");
		if (b.length <= len)
			return str;
		for (int i = 0; i < len; i++) {
			if (b[i] < 0)
				counterOfDoubleByte++;
		}
		if (counterOfDoubleByte % 2 == 0)
			return new String(b, 0, len, "GBK") + symbol;
		else
			return new String(b, 0, len - 1, "GBK") + symbol;
	}

	private static char mapTable[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9' };

	/**
	 * des:生成随机验证码
	 * 
	 * @param length
	 *            验证码长度
	 * @return
	 */
	public static String getVerifiCode(int length) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < length; ++i) {
			str.append(mapTable[(int) (mapTable.length * Math.random())]);
		}
		return str.toString();
	}

	/**
	 * 解析前台传来的性别字符串为数据库插入用的int值 男->1;女->0; male->1;female->0; 若未能识别，则默认为男性
	 * 
	 * @param sexString
	 * @return
	 */
	public static int parseSexString(String sexStr) {
		if (sexStr.equals("女") || sexStr.toLowerCase().equals("female")
				|| sexStr.equals("0")) {
			return 0;
		}
		return 1;
	}

	public static boolean isPic(String fileName) {
		String suffix = fileName.substring(fileName.lastIndexOf("."))
				.toLowerCase();
		if (suffix.equals(".jpg") || suffix.equals(".png")
				|| suffix.equals(".gif"))
			return true;
		else
			return false;
	}

	/**
	 * 替换html代码中特殊符号
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceHtmlSpecialCharS(String html) {
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;").replaceAll("\"", "&quot;");
	}

	/**
	 * 将list集合转为','分割的字符串
	 * 
	 * @param list
	 * @return
	 */
	public static final String List2CommaStr(List<?> list) {
		StringBuilder sb = new StringBuilder();
		for (Object object : list) {
			sb.append(object.toString()).append(",");
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}
	/**
	 * 取得文件扩展名
	 * @param filename
	 * @return
	 */
	public static final String fileExName(String filename) {
		if (isNull(filename)) {
			return null;
		}
		String[] splits = filename.split("[.]");
		if (splits.length<2) {
			return null;
		}
		return splits[splits.length-1].toLowerCase();
	}
	
	
	

}
