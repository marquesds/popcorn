package me.lucasmarques.popcorn.utils;

import me.lucasmarques.popcorn.infra.persistence.DatabaseName;
import me.lucasmarques.popcorn.infra.persistence.mariadb.ConnectionDriver;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestUtils {

    public static void cleanDatabase() {
        ConnectionDriver driver = new ConnectionDriver();

        try {
            String sql1 = movieRelationshipSql(DatabaseName.MOVIES_DIRECTORS.value);
            String sql2 = movieRelationshipSql(DatabaseName.MOVIES_ACTORS.value);
            String sql3 = String.format("DELETE FROM %s WHERE id NOT IN ('3314fc8d-872b-4751-ae34-7e34bbc8022f', '52bbc3a3-f640-4ade-831f-89ab661be668')",
                    DatabaseName.MOVIES.value);
            String sql4 = String.format("DELETE FROM %s WHERE id NOT IN ('f8bdd258-530f-4f2d-8b1e-6fd9393f02fe', '6761ce6d-e93d-4830-9e05-c6bd53d1ed32', " +
                    "'76a7d400-ab41-4e1d-b397-06cd19ff59c4', '8111fd8d-682a-4954-bf9c-2f26f8a0ad05', " +
                    "'4aae0743-ac6f-4162-a224-a387e577970f', 'a36c4e14-8bd5-4354-aaaa-45a9d1c4f219', " +
                    "'d9fc6344-b307-4d4a-b0ba-d682eed532b8', '0f2b0491-2c4d-404b-a4f0-c89eb8c06ebb', " +
                    "'cf9b01f0-2f58-4cc0-81aa-63381ad40e91', 'c5157f90-119d-494f-a057-da0bd4fd05c5', " +
                    "'4c8318fb-49c8-4ad5-8b75-75a2299b5de5', 'ed03b469-4737-43a5-a50f-71a596c28daf', " +
                    "'b63abb51-2971-491c-bb22-96a9e826f717', '60a14628-4217-47ec-b1ef-8c795e1a18a3', " +
                    "'eae3b083-3524-44e4-9f67-98963bd68dc6', '510e87a6-101a-42d2-9770-4bc886ef4b9c', " +
                    "'2135c451-5def-4c26-bfd4-c77771729d4b', '472c5cbc-6e7e-4a2a-9aca-849a5e9bc80c')", DatabaseName.ACTORS.value);

            String sql5 = String.format("DELETE FROM %s WHERE id NOT IN ('df2d0cf2-e60e-4e12-a0b9-d70012a51454', '024d05f1-bf11-4267-b1af-9ae363614e40')", DatabaseName.DIRECTORS.value);

            ResultSet resultSet1 = driver.executeSql(sql1);
            ResultSet resultSet2 = driver.executeSql(sql2);
            ResultSet resultSet3 = driver.executeSql(sql3);
            ResultSet resultSet4 = driver.executeSql(sql4);
            ResultSet resultSet5 = driver.executeSql(sql5);
            resultSet1.close();
            resultSet2.close();
            resultSet3.close();
            resultSet4.close();
            resultSet5.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }

    private static String movieRelationshipSql(String databaseName) {
        return String.format("DELETE FROM %s WHERE movie_id NOT IN ('3314fc8d-872b-4751-ae34-7e34bbc8022f', '52bbc3a3-f640-4ade-831f-89ab661be668')", databaseName);
    }

}
