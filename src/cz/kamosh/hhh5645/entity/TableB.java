package cz.kamosh.hhh5645.entity;
// Generated Apr 9, 2014 6:49:28 PM by Hibernate Tools 3.6.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TableB generated by hbm2java
 */
public class TableB implements java.io.Serializable {

    private long tableBId;
    private TableA tableA;
    private String tableBCharacter;
    private Integer tableBInteger;
    private Date tableBDate;
    private Boolean tableBBoolean;
    private Set tableCs = new HashSet(0);

    public TableB() {
    }

    public TableB(long tableBId) {
        this.tableBId = tableBId;
    }

    public TableB(long tableBId, TableA tableA, String tableBCharacter, Integer tableBInteger, Date tableBDate, Boolean tableBBoolean, Set tableCs) {
        this.tableBId = tableBId;
        this.tableA = tableA;
        this.tableBCharacter = tableBCharacter;
        this.tableBInteger = tableBInteger;
        this.tableBDate = tableBDate;
        this.tableBBoolean = tableBBoolean;
        this.tableCs = tableCs;
    }

    public long getTableBId() {
        return this.tableBId;
    }

    public void setTableBId(long tableBId) {
        this.tableBId = tableBId;
    }

    public TableA getTableA() {
        return this.tableA;
    }

    public void setTableA(TableA tableA) {
        this.tableA = tableA;
    }

    public String getTableBCharacter() {
        return this.tableBCharacter;
    }

    public void setTableBCharacter(String tableBCharacter) {
        this.tableBCharacter = tableBCharacter;
    }

    public Integer getTableBInteger() {
        return this.tableBInteger;
    }

    public void setTableBInteger(Integer tableBInteger) {
        this.tableBInteger = tableBInteger;
    }

    public Date getTableBDate() {
        return this.tableBDate;
    }

    public void setTableBDate(Date tableBDate) {
        this.tableBDate = tableBDate;
    }

    public Boolean getTableBBoolean() {
        return this.tableBBoolean;
    }

    public void setTableBBoolean(Boolean tableBBoolean) {
        this.tableBBoolean = tableBBoolean;
    }

    public Set getTableCs() {
        return this.tableCs;
    }

    public void setTableCs(Set tableCs) {
        this.tableCs = tableCs;
    }

}
