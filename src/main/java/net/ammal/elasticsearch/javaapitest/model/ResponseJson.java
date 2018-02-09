package net.ammal.elasticsearch.javaapitest.model;


public class ResponseJson {

    private final String msg;

    private final String result;

    private final String payload;

    public ResponseJson(String msg, String result, String payload) {
        this.msg = msg;
        this.result = result;
        this.payload = payload;
    }

    public String getMsg() {
        return msg;
    }

    public String getResult() {
        return result;
    }

    public String getPayload() {
        return payload;
    }
}
