package com.github.xingshuangs.iot.protocol.rtsp.constant;


/**
 * key名
 *
 * @author xingshuang
 */
public class RtspCommonKey {

    private RtspCommonKey() {
        // NOOP
    }

    /**
     * 换行
     */
//    public static final String CRLF = System.getProperty("line.separator");
    public static final String CRLF = "\r\n";

    /**
     * 冒号
     */
    public static final String COLON = ":";

    /**
     * 逗号
     */
    public static final String COMMA = ",";

    /**
     * 空格
     */
    public static final String SP = " ";

    /**
     * 分号
     */
    public static final String SEMICOLON = ";";

    /**
     * 等号
     */
    public static final String EQUAL = "=";

    /**
     * 序列号
     */
    public static final String C_SEQ = "CSeq";

    /**
     * 序列号
     */
    public static final String C_SEQ1 = "Cseq";

    /**
     * 会话
     */
    public static final String SESSION = "Session";

    /**
     * 通道
     */
    public static final String TRANSPORT = "Transport";

}
