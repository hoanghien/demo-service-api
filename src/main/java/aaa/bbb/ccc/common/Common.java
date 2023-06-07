package aaa.bbb.ccc.common;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Common {

    public static SimpleDateFormat sdf = new SimpleDateFormat();
    public static final String DATE_yyyyMMdd = "yyyy-MM-dd";
    public static final String DATE_yyyyMMddhhmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_yyyyMMddhhmmssSSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_yyyyMMddhhmmssSSS_NOSPACE = "yyyyMMddHHmmssSSS";
    public static final String DATE_yyyyMMddHH = "yyyy-MM-dd HH";
    public static final String DATE_ddMMMyyyy = "dd/MMM/yyyy";
    public static final String DATE_ddMMMyyyyHH = "dd/MMM/yyyy:HH";
    private static final Random random = new Random();
    private static final char[] chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] charsDigit = "0123456789".toCharArray();
    private static final String uniChars = "àáảãạâầấẩẫậăằắẳẵặèéẻẽẹêềếểễệđ"
            + "îìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵÀÁẢÃẠÂẦẤẨẪẬĂẰẮẲ"
            + "ẴẶÈÉẺẼẸÊỀẾỂỄỆĐÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴÂĂĐÔƠƯ";


    private static final String noneChars = "aaaaaaaaaaaaaaaaaeeeeeeeeeeediiiiiio"
            + "oooooooooooooooouuuuuuuuuuuyyyyyAAAAAAAAAAAAAA"
            + "AAAEEEEEEEEEEEDIIIIIOOOOOOOOOOOOOOOOOUUUUUUUUUUUYYYYYAADOOU";

    public static boolean isBlank(String val) {
        return (val == null) || ("".equals(val.trim()));
    }

    public static boolean isEmpty(List lst) {
        if (lst != null && !lst.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public synchronized static String dateToString(Date value, String format) {
        sdf.applyPattern(format);
        return sdf.format(value);
    }

    public synchronized static Date strToDate(String value, String format) throws Exception {
        sdf.applyPattern(format);
        return sdf.parse(value);
    }

    public static String getValueByTagName(String input, String tagName) {
        if (input == null) {
            return null;
        }
        String startTag = "<" + tagName + ">";
        String endTag = "</" + tagName + ">";
        int start = input.indexOf(startTag);
        int end = input.indexOf(endTag);
        if (start < 0 || end < 0) {
            return null;
        }
        String result = input.substring(start + startTag.length(), end);
        return result;
    }

    public static String normalizeMsIsdn(String phoneNumber) {
        String result = phoneNumber;
        try {
            String pattern = "^0";
            result = result.replaceAll(pattern, "");
            if (result.length() > 9 && result.startsWith("84")) {
                pattern = "^84";
                result = result.replaceAll(pattern, "");
            }
            if (result.length() > 9 && result.startsWith("+84")) {
                pattern = "^\\+84";
                result = result.replaceAll(pattern, "");
            }
        } catch (Exception ex) {
            result = phoneNumber;
        }

        return result;
    }

    public static String hideData(String msisdn, Integer numHide) {
        String newMsisdn = "";
        if (numHide > 0) {
            newMsisdn = msisdn.substring(0, msisdn.length() - numHide);
            String suffix = "";
            for (int i = 0; i < numHide; i++) {
                suffix += "x";
            }
            newMsisdn = newMsisdn + suffix;
        }
        return newMsisdn;
    }

    public static String randomString(int length) {
        char[] buf = new char[length];
        for (int idx = 0; idx < length; ++idx) {
            buf[idx] = chars[random.nextInt(chars.length)];
        }
        return new String(buf);
    }

    public static String randomDigit(int length) {
        char[] buf = new char[length];
        for (int idx = 0; idx < length; ++idx) {
            buf[idx] = charsDigit[random.nextInt(charsDigit.length)];
        }
        return new String(buf);
    }

//    public static void main(String[] args) {
//        try {
//            System.out.println(Common.hideData("909216935", 4));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

    public static String unicode2ASII(String s) {
        String ret = "";
        try {
            if (s == null) {
                return s;
            }
            for (int i = 0; i < s.length(); i++) {
                int pos = uniChars.indexOf(s.charAt(i));
                if (pos >= 0) {
                    ret += noneChars.charAt(pos);
                } else {
                    ret += s.charAt(i);
                }
            }
        } catch (Exception ex) {}
        return ret;
    }

    public static String getValueFromXmlSOAP(String xmlResponse, String tagName) throws Exception {
        Document xmlDoc = loadXMLString(xmlResponse);
        NodeList nodeList = xmlDoc.getElementsByTagName(tagName);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node x = nodeList.item(i);
            if (tagName.equals(x.getNodeName())) {
                return x.getFirstChild().getNodeValue();
            }
        }
        return null;
    }

    public static Document loadXMLString(String xmlResponse) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlResponse));

        return db.parse(is);
    }
}
