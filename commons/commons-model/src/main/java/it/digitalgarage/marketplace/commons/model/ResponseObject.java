package it.digitalgarage.marketplace.commons.model;

public class ResponseObject<T> {
    private T body;

    public ResponseObject(){}

    public ResponseObject(T body){
        this.body = body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public T getBody() {
        return body;
    }
}
