package cz.kamosh.hhh5645.entity;
// Generated Apr 9, 2014 6:49:28 PM by Hibernate Tools 3.6.0

import java.util.HashSet;
import java.util.Set;

/**
 * TableA generated by hbm2java
 */
public class TableA implements java.io.Serializable {

    private long tableAId;
    private String tableACharacter;
    private Set tableBs = new HashSet(0);

    public TableA() {
    }

    public TableA(long tableAId) {
        this.tableAId = tableAId;
    }

    public TableA(long tableAId, String tableACharacter, Set tableBs) {
        this.tableAId = tableAId;
        this.tableACharacter = tableACharacter;
        this.tableBs = tableBs;
    }

    public long getTableAId() {
        return this.tableAId;
    }

    public void setTableAId(long tableAId) {
        this.tableAId = tableAId;
    }

    public String getTableACharacter() {
        return this.tableACharacter;
    }

    public void setTableACharacter(String tableACharacter) {
        this.tableACharacter = tableACharacter;
    }

    public Set getTableBs() {
        return this.tableBs;
    }

    public void setTableBs(Set tableBs) {
        this.tableBs = tableBs;
    }

}
