package util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DatabaseTableDataUtil {
    public static Object[][] getTableRows(ResultSet resultSet) {
        try {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            int numberOfRows = 0;
            while (resultSet.next()) {
                numberOfRows++;
            }
            resultSet.beforeFirst();
            Object[][] data = new Object[numberOfRows][numberOfColumns];
            int row = 0;
            while (resultSet.next()) {
                for (int column = 0; column < numberOfColumns; column++) {
                    data[row][column] = resultSet.getObject(column + 1);
                }
                row++;
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[0][0];
        }

    }

    public static String[] getTableColumns(ResultSet resultSet) {
        try {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            String[] objArray = new String[numberOfColumns];

            for (int i = 1; i <= numberOfColumns; i++) {
                objArray[i - 1] = metaData.getColumnName(i);
            }
            return objArray;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }
}
