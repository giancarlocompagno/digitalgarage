package it.digitalgarage.marketplace.commons.be.jpa;

import java.util.HashMap;
import java.util.Map;

public class Exists {
    private Class classExists;
    private Map<String,String> joinCondition = new HashMap<>();
    private Map<String,String> whereCondition = new HashMap<>();

    public Exists (Class classExists){
        this.classExists = classExists;
    }

    public Class getClassExists() {
        return classExists;
    }

    public Exists join(String key, String value){
        joinCondition.put(key, value);
        return this;
    }

    public Exists where(String key, String value){
        whereCondition.put(key, value);
        return this;
    }


    public Map<String, String> getJoinCondition() {
        return joinCondition;
    }

    public Map<String, String> getWhereCondition() {
        return whereCondition;
    }
}
