import org.sqlite.JDBC;
import java.io.File;
import java.sql.*;



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

    /**
     * Запись в базу нового объекта
     *
     * @param name
     * @throws SQLException
     */
    public void insertName(String name) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO my_table(name) VALUES(?);")) {
            statement.setString(1, name);
            statement.execute();
            connection.commit();
        } catch (Throwable e) {
            LogWork.logWrite("Atention  --  " + e.toString());
            connection.rollback();
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
