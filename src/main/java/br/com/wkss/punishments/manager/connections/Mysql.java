package br.com.wkss.punishments.manager.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class Mysql {

    private String host, database, user, password;
    private int port;
    private Connection connection;
    private Statement statement;
    String[] credentials;

    public Statement getStatement() {
        return statement;
    }

    public String getDatabase() {
        return database;
    }

    private boolean problem = false;

    public boolean hasProblem() {
        return problem;
    }

    public Mysql() {

        host = "localhost";
        port = Integer.valueOf(3306);
        database = "punish";
        user = "root";
        password = "";
        credentials = new String[]{
                host, database,
                user, password
        };
    }

    public synchronized void connect() {
        if (problem) {
            System.out.println("Nao foi possivel iniciar tentativa de conexao pois um problema foi detectado.");
            return;
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties = new Properties();
            properties.setProperty("user", user);
            properties.setProperty("password", password);
            properties.setProperty("useSSL", "true");
            properties.setProperty("autoReconnect", "true");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", properties);
            statement = connection.createStatement();
            return;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        problem = true;
        return;
    }

    public synchronized void disconnect() {
        try {
            statement.close();
            connection.close();
            return;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return;
    }

    private String table(String name, String primaryKey, boolean autoIncrement, String... columns) {
        String command = "CREATE TABLE IF NOT EXISTS `" + database + "`.`" + name.toLowerCase() + "` (";
        StringBuilder builder = new StringBuilder();
        for (String string : columns) {
            if (builder.length() > 0) builder.append(", ");
            builder.append(string);
        }
        command += builder.toString().trim() + ", PRIMARY KEY(`" + primaryKey.toLowerCase() + "`)) ENGINE = InnoDB DEFAULT CHARSET = UTF8";
        command += (autoIncrement ? " AUTO_INCREMENT = 1" : "") + ";";
        return command;
    }

    public synchronized void setup() {
        try {
            statement.executeUpdate(table("punishments", "id", true, "`id` INT NOT NULL AUTO_INCREMENT", "`indentifier` VARCHAR(64) NOT NULL", "`data` TEXT NOT NULL", "`reason` VARCHAR(64) NOT NULL",));
            return;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        problem = true;
        return;
    }
}