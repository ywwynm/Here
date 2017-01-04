package util;

/**
 * Created by 张启 on 2016/4/17.
 * string util
 */
public class StringUtil {

    private StringUtil() {}

    public static String unescape(String src) {
        StringBuilder sb = new StringBuilder();
        int lastPos = 0;
        final int len = src.length();
        while (lastPos < len) {
            int pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                char ch;
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(
                            src.substring(pos + 2, pos + 6), 16);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(
                            src.substring(pos + 1, pos + 3), 16);
                    lastPos = pos + 3;
                }
                sb.append(ch);
            } else {
                if (pos == -1) {
                    sb.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    sb.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return sb.toString();
    }

    public static String escape(String src) {
        StringBuilder sb = new StringBuilder();
        final int len = src.length();
        for (int i = 0; i < len; i++) {
            char ch = src.charAt(i);
            if (Character.isDigit(ch) || Character.isLowerCase(ch)
                    || Character.isUpperCase(ch)) {
                sb.append(ch);
            } else if (ch < 256) {
                sb.append("%");
                if (ch < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toString(ch, 16));
            } else {
                sb.append("%u");
                sb.append(Integer.toString(ch, 16));
            }
        }
        return sb.toString();
    }

}
