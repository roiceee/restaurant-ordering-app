package model;

public class MenuDataObject {
    Object[][] rows;
    Object[] columns;

    public MenuDataObject(Object[][] rows, Object[] columns) {
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
