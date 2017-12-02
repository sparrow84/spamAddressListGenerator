import org.sqlite.JDBC;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBWork {
    private static Connection connection;


    /**
     * Подключение к базе данных
     *
     * @throws Exception
     */
    public static void connect () throws Exception {
        //регистрация драйвера JDBC поставщика СУБД
        DriverManager.registerDriver(new JDBC());
        //получение соединеня с СУБД
        connection = DriverManager.getConnection(createUrlJdbc(ConfWork.getBasePath()));
        connection.setAutoCommit(false);
        LogWork.logWrite(connection.isClosed() ? "Connection CLOSED" : "Connection OPEN");
        LogWork.logWrite(connection.toString());
    }

    /**
     * Отключение от БД
     */
    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            LogWork.logWrite("Atention  --  " + e.toString());
            e.printStackTrace();
        }
        try {
            LogWork.logWrite(connection.isClosed() ? "Connection CLOSED" : "Connection OPEN");
        } catch (SQLException e) {
            LogWork.logWrite("Atention  --  " + e.toString());
            e.printStackTrace();
        }
    }

    public static void showDataTable () {
//        List<String> res = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT id,addr,time,count FROM addr_table;");
            while (resultSet.next()) {
                System.out.println(
                        resultSet.getString("addr") + "   " +
                        resultSet.getLong("time") + "   " +
                        resultSet.getInt("count")
                );
            }
        } catch (SQLException e) {
            LogWork.logWrite("Atention  --  " + e.toString());
            e.printStackTrace();
        }
    }


    /**
     * Запись в базу нового объекта
     *
     * @param addr   // example 217.125.14.62 or my.home.biz
     * @param time   // 51324654135746654
     * @param count  // 1
     * @throws SQLException
     */
    public static void insert(String addr, long time, int count) {
        //try (PreparedStatement statement = connection.prepareStatement("INSERT INTO my_table(name) VALUES(?);")) {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO addr_table (addr, time, count) VALUES (?,?,?);"
                )
        ) {
            statement.setString(1, addr);
            statement.setLong(2, time);
            statement.setInt(3, count);
            statement.execute();
            connection.commit();
        } catch (Throwable e) {
            LogWork.logWrite("Atention  --  " + e.toString());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LogWork.logWrite("Atention  --  " + e1.toString());
                e1.printStackTrace();
            }
        }
    }

    /**
    * DON'T WORK
    * */
    public static void deleteAllDataTable () {
        try (Statement statement = connection.createStatement()) {
            statement.executeQuery("DELETE FROM addr_table;");
        } catch (SQLException e) {
            LogWork.logWrite("Atention  --  " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * DON'T WORK
     * */
    public static void searchAddr (String addr) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM addr_table WHERE addr = ?;");
            while (resultSet.next()) {
                System.out.println(
                        resultSet.getString("addr") + "   " +
                                resultSet.getLong("time") + "   " +
                                resultSet.getInt("count")
                );
            }
        } catch (SQLException e) {
            LogWork.logWrite("Atention  --  " + e.toString());
            e.printStackTrace();
        }
    }


/*
    protected static ResultObject createResult(ResultSet resultSet) throws SQLException {
        return new ResultObject(resultSet.getInt("id"), resultSet.getString("name"));
    }
*/
    /**
     * Создание адреса для соединения с JDBC
     *
     * @param dbFilePath путь к файлу бд
     * @return адрес бд (строка)
     */
    protected static String createUrlJdbc(String dbFilePath) {
        //return String.format("jdbc:sqlite:%s", dbFile.getAbsolutePath());
        return String.format("jdbc:sqlite:%s", dbFilePath);
    }

    /**
     * Получение ресурса по его имени
     *
     * @param resourceName имя ресура
     * @return файл ресурса
     */
    protected static File getResource(String resourceName) {
        return new File(Main.class.getClassLoader().getResource(resourceName).getFile());
    }






    /* Create table

CREATE TABLE addr_table (
    id    INTEGER      PRIMARY KEY AUTOINCREMENT
                       UNIQUE
                       NOT NULL
                       DEFAULT (0),
    addr  VARCHAR (50),
    time  BIGINT,
    count INT
);

    */
}
