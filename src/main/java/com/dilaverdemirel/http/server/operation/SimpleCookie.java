package com.dilaverdemirel.http.server.operation;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author dilaverd on 7/12/2017.
 */
public class SimpleCookie implements Serializable {
    private String name;
    private String value;
    private String path;
    private String domain;
    private boolean secure;
    private String comment;
    private int maxAge = -1;
    private int version = 0;
    private boolean isHttpOnly;

    private static final String OLD_COOKIE_PATTERN =
            "EEE, dd-MMM-yyyy HH:mm:ss z";
    private static final ThreadLocal<DateFormat> OLD_COOKIE_FORMAT =
            new ThreadLocal<DateFormat>() {
                protected DateFormat initialValue() {
                    DateFormat df =
                            new SimpleDateFormat(OLD_COOKIE_PATTERN, Locale.US);
                    df.setTimeZone(TimeZone.getTimeZone("GMT"));
                    return df;
                }
            };

    private static final String ancientDate;

    private static final String tspecials2NoSlash = "()<>@,;:\\\"[]?={} \t";


    static {
        ancientDate = OLD_COOKIE_FORMAT.get().format(new Date(10000));
    }

    public static void appendCookieValue( StringBuffer headerBuf,
                                          int version,
                                          String name,
                                          String value,
                                          String path,
                                          String domain,
                                          String comment,
                                          int maxAge,
                                          boolean isSecure,
                                          boolean isHttpOnly)
    {
        StringBuffer buf = new StringBuffer();
        // Servlet implementation checks name
        buf.append( name );
        buf.append("=");
        // Servlet implementation does not check anything else

        buf.append(value);

        // Comment=comment
        if ( comment!=null ) {
            buf.append ("; Comment=");
            maybeQuote2(version, buf, comment);
        }

        // Add domain information, if present
        if (domain!=null) {
            buf.append("; Domain=");
            maybeQuote2(version, buf, domain);
        }

        // Max-Age=secs ... or use old "Expires" format
        if (maxAge >= 0) {

            buf.append ("; Max-Age=");
            buf.append (maxAge);
            // IE6, IE7 and possibly other browsers don't understand Max-Age.
            // They do understand Expires, even with V1 cookies!
            // Wdy, DD-Mon-YY HH:MM:SS GMT ( Expires Netscape format )
            buf.append ("; Expires=");
            // To expire immediately we need to set the time in past
            if (maxAge == 0)
                buf.append( ancientDate );
            else
                OLD_COOKIE_FORMAT.get().format(
                        new Date(System.currentTimeMillis() +
                                maxAge*1000L),
                        buf, new FieldPosition(0));

        }

        // Path=path
        if (path!=null) {
            buf.append ("; Path=");
            maybeQuote2(version, buf, path, SimpleCookie.tspecials2NoSlash, false);
        }

        // Secure
        if (isSecure) {
            buf.append ("; Secure");
        }

        // HttpOnly
        if (isHttpOnly) {
            buf.append("; HttpOnly");
        }
        headerBuf.append(buf);
    }

    public static boolean containsCTL(String value, int version) {
        if( value==null) return false;
        int len = value.length();
        for (int i = 0; i < len; i++) {
            char c = value.charAt(i);
            if (c < 0x20 || c >= 0x7f) {
                if (c == 0x09)
                    continue; //allow horizontal tabs
                return true;
            }
        }
        return false;
    }

    public static boolean alreadyQuoted (String value) {
        if (value==null || value.length()==0) return false;
        return (value.charAt(0)=='\"' && value.charAt(value.length()-1)=='\"');
    }

    /**
     * Escapes any double quotes in the given string.
     *
     * @param s the input string
     * @param beginIndex start index inclusive
     * @param endIndex exclusive
     * @return The (possibly) escaped string
     */
    private static String escapeDoubleQuotes(String s, int beginIndex, int endIndex) {

        if (s == null || s.length() == 0 || s.indexOf('"') == -1) {
            return s;
        }

        StringBuffer b = new StringBuffer();
        for (int i = beginIndex; i < endIndex; i++) {
            char c = s.charAt(i);
            if (c == '\\' ) {
                b.append(c);
                //ignore the character after an escape, just append it
                if (++i>=endIndex) throw new IllegalArgumentException("Invalid escape character in cookie value.");
                b.append(s.charAt(i));
            } else if (c == '"')
                b.append('\\').append('"');
            else
                b.append(c);
        }

        return b.toString();
    }

    /**
     * Quotes values using rules that vary depending on Cookie version.
     * @param version
     * @param buf
     * @param value
     */
    public static int maybeQuote2 (int version, StringBuffer buf, String value) {
        return maybeQuote2(version,buf,value,false);
    }

    public static int maybeQuote2 (int version, StringBuffer buf, String value, boolean allowVersionSwitch) {
        return maybeQuote2(version,buf,value,null,allowVersionSwitch);
    }

    public static int maybeQuote2 (int version, StringBuffer buf, String value, String literals, boolean allowVersionSwitch) {
        if (value==null || value.length()==0) {
            buf.append("\"\"");
        }else if (containsCTL(value,version))
            throw new IllegalArgumentException("Control character in cookie value, consider BASE64 encoding your value");
        else if (alreadyQuoted(value)) {
            buf.append('"');
            buf.append(escapeDoubleQuotes(value,1,value.length()-1));
            buf.append('"');
        } else if (allowVersionSwitch && version==0) {
            buf.append('"');
            buf.append(escapeDoubleQuotes(value,0,value.length()));
            buf.append('"');
            version = 1;
        } else if (version==0) {
            buf.append('"');
            buf.append(escapeDoubleQuotes(value,0,value.length()));
            buf.append('"');
        } else if (version==1) {
            buf.append('"');
            buf.append(escapeDoubleQuotes(value,0,value.length()));
            buf.append('"');
        }else {
            buf.append(value);
        }
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isHttpOnly() {
        return isHttpOnly;
    }

    public void setHttpOnly(boolean isHttpOnly) {
        this.isHttpOnly = isHttpOnly;
    }

    public static String getAncientDate() {
        return ancientDate;
    }
}
