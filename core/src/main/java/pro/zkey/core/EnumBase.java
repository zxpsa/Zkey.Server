package pro.zkey.core;

/**
 * Created by ps on 2017/6/22.
 */
public enum EnumBase {
    AA ("颜色",10000),
    AA2("颜色1",100001),
    AA3("颜色5",100002),
    AA5("颜色3",100003);

    // 成员变量
    private String text;
    private int code;
    EnumBase(String text, int code) {
        this.text = text;
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
