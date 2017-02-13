/**
 *
 */
package edu.muc.platform.plugin.tools;

import java.util.Date;

import org.beetl.core.Context;
import org.beetl.core.Format;
import org.beetl.core.Function;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年7月3日 下午4:13:00
 */
public class StringFormat implements Function {


    /* (non-Javadoc)
     * @see org.beetl.core.Function#call(java.lang.Object[], org.beetl.core.Context)
     */
    @Override
    public Object call(Object[] arg0, Context ctx) {
        // TODO Auto-generated method stub
        String o = (String) arg0[0];
        Integer i = (Integer) arg0[1];
        System.out.println(new Date() + o + "ksdfjs" + i);
        if (o != null) {
            o = subTextString(o, i);
            try {
                //  ctx.byteWriter.write(o.getBytes());
                ctx.byteWriter.writeString(o.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }

    //截取字符串长度(中文2个字节，半个中文显示一个)  
    public static String subTextString(String str, int len) {
        if (str.length() < len / 2) return str;
        int count = 0;
        StringBuffer sb = new StringBuffer();
        String[] ss = str.split("");
        for (int i = 1; i < ss.length; i++) {
            count += ss[i].getBytes().length > 1 ? 2 : 1;
            sb.append(ss[i]);
            if (count >= len) break;
        }
        //不需要显示...的可以直接return sb.toString();  
        return (sb.toString().length() < str.length()) ? sb.append("...").toString() : str;
    }

    public static String cutLenth(String value, int num) {
        StringBuffer sb = new StringBuffer();
        int charlen = 0;
        int strlength = value.length();
        for (int i = 0; i < strlength; i++) {
            int asciiCode = value.codePointAt(i);
            if (asciiCode >= 0 && asciiCode <= 255) {
                charlen += 1;
            } else {
                charlen += 2;
            }
            if (charlen <= num) {
                sb.append(value.charAt(i));
            } else {
                break;
            }
        }
        return sb.toString().length() < strlength ? sb.toString() + "..." : sb.toString();
    }


}
