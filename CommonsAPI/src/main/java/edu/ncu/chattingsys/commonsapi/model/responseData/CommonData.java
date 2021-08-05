package edu.ncu.chattingsys.commonsapi.model.responseData;
public class CommonData {
    private boolean success;
    private String msg;
    private int code;

    public CommonData(){}
    public CommonData(boolean success){this.success=success;}
    public CommonData(boolean success, String msg){this.success=success;this.msg=msg;}

    public static CommonData sucess(){
        return new CommonData(true);
    }
    public static CommonData sucess(String msg){
        CommonData t=new CommonData(true);
        t.setMsg(msg);
        return t;
    }
    public static CommonData fail(String msg){
        return new CommonData(false,msg);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }



}
