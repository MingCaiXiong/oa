package top.xiongmingcai.oa.service.exception;

public class BusinessException extends RuntimeException {
    private String code;
    private String massage;

    public BusinessException(String code, String msg) {
        super(code + ":" + msg);
        this.code = code;
        this.massage = msg;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}