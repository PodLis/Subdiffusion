package net.mz.rb.subdiffusion;

public class VisiblePattern {
    private String name;
    private int patternID, editID, deleteID;

    VisiblePattern(String name, int patternID, int editID, int deleteID){
        this.name = name;
        this.patternID = patternID;
        this.editID = editID;
        this.deleteID = deleteID;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public int getPatternID() {
        return patternID;
    }

    public void setPatternID(int patternID) {
        this.patternID = patternID;
    }

    public int getEditID() {
        return editID;
    }

    public void setEditID(int editID) {
        this.editID = editID;
    }

    public int getDeleteID() {
        return deleteID;
    }

    public void setDeleteID(int deleteID) {
        this.deleteID = deleteID;
    }
}
