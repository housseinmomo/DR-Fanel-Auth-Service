package com.nethealth.utils;

import java.util.HashMap;

import org.springframework.http.HttpStatus;

public class ResponseMessage {
	
	HttpStatus status;
    HashMap<String, Object> response;

   
    public ResponseMessage(){
        this.response = new HashMap<>();
    }
    

    public ResponseMessage(HttpStatus status) {
        this();
        this.status = status;
    }
    

    public HttpStatus getStatus() {
        return status;
    }
    

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
    

    public HashMap<String, Object> getResponse() {
        return response;
    }
    

    public void addEntry(String key, Object value){
        if(!this.response.containsKey(key)){
            this.response.put(key, value);
        } else {
            this.updateEntry(key, value);
        }
    }
    
    public void updateEntry(String key, Object value){
        if(this.response.containsKey(key)){
            this.response.replace(key, value);
        } else {
            this.response.put(key, value);
        }
    }
    
    public void removeEntry(String key){
        this.response.remove(key);
    }


}
