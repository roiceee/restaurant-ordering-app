package model;

public class TableDataObject {
    Object[][] rows;
    Object[] columns;

    public TableDataObject() {
        this.rows = new Object[0][0];
        this.columns = new Object[0];
    }
    public TableDataObject(Object[][] rows, Object[] columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public Object[][] getRows() {
        return rows;
    }

    public Object[] getColumns() {
        return columns;
    }
}
