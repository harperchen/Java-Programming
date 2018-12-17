package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ServerDB {

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;

    public static void InitDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:server.db");
            statement = connection.createStatement();
            InitKSXX();
            InitBRXX();
            InitKSYS();
            InitHZXX();
            InitGHXX();
            statement.close();
            connection.close();
        } catch (Exception e) {

        }
        System.out.println("Connect Datebase successfully!");
    }

    private static void InitBRXX() {
        try {
            String sql = "CREATE TABLE T_BRXX " +
                    "(BRBH  CHAR(6) PRIMARY KEY NOT NULL," +
                    " BRMC  CHAR(10)            NOT NULL," +
                    " DLKL  CHAR(8)             NOT NULL," +
                    " YCJE  DECIMAL(10, 2)      NOT NULL," +
                    " DLRQ  DateTime)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_BRXX (BRBH, BRMC, DLKL, YCJE) VALUES ('000001', 'BR1', 'BR1', 5)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_BRXX (BRBH, BRMC, DLKL, YCJE) VALUES ('000002', 'BR2', 'BR2', 2)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_BRXX (BRBH, BRMC, DLKL, YCJE) VALUES ('000003', 'BR3', 'BR3', 3)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_BRXX (BRBH, BRMC, DLKL, YCJE) VALUES ('000004', 'BR4', 'BR4', 4)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_BRXX (BRBH, BRMC, DLKL, YCJE) VALUES ('000005', 'BR5', 'BR5', 5)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_BRXX (BRBH, BRMC, DLKL, YCJE) VALUES ('000006', 'BR6', 'BR6', 6)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_BRXX (BRBH, BRMC, DLKL, YCJE) VALUES ('000007', 'BR7', 'BR7', 7)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_BRXX (BRBH, BRMC, DLKL, YCJE) VALUES ('000008', 'BR8', 'BR8', 8)";
            statement.executeUpdate(sql);
            sql = "CREATE UNIQUE INDEX I_BRXX ON T_BRXX(BRBH)";
            statement.executeUpdate(sql);
        } catch (Exception e) {

        }
        System.out.println("Table T_BRXX created successfully");
    }

    private static void InitKSXX() {
        try {
            String sql = "CREATE TABLE T_KSXX " +
                    "(KSBH  CHAR(6) PRIMARY KEY NOT NULL," +
                    " KSMC  CHAR(10)            NOT NULL," +
                    " PYZS  CHAR(8)             NOT NULL)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSXX (KSBH, KSMC, PYZS) VALUES ('000001', '内科', 'NK')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSXX (KSBH, KSMC, PYZS) VALUES ('000002', '外科', 'WK')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSXX (KSBH, KSMC, PYZS) VALUES ('000003', '耳鼻喉科', 'EBHK')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSXX (KSBH, KSMC, PYZS) VALUES ('000004', '放射科', 'FSK')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSXX (KSBH, KSMC, PYZS) VALUES ('000005', '口腔科', 'KQK')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSXX (KSBH, KSMC, PYZS) VALUES ('000006', '美容外科', 'MRWK')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSXX (KSBH, KSMC, PYZS) VALUES ('000007', '儿科', 'EK')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSXX (KSBH, KSMC, PYZS) VALUES ('000008', '急诊科', 'JZK')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSXX (KSBH, KSMC, PYZS) VALUES ('000009', '妇科', 'FK')";
            statement.executeUpdate(sql);
            sql = "CREATE UNIQUE INDEX I_KSXX ON T_KSXX(KSBH)";
            statement.executeUpdate(sql);
        } catch (Exception e) {

        }
        System.out.println("Table T_KSXX created successfully");
    }

    //索引 医生 号码 对应
    private static void InitKSYS() {
        try {
            String sql = "CREATE TABLE T_KSYS " +
                    "(YSBH  CHAR(6) PRIMARY KEY NOT NULL," +
                    " KSBH  CHAR(6)             NOT NULL," +
                    " YSMC  CHAR(10)            NOT NULL," +
                    " PYZS  CHAR(4)             NOT NULL," +
                    " DLKL  CHAR(8)             NOT NULL," +
                    " SFZJ  BOOLEAN             NOT NULL," +
                    " DLRQ  DATETIME)";

            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSYS (YSBH, KSBH, YSMC, PYZS, DLKL, SFZJ) VALUES ('000001', '000002', '汪道文', 'WDW', 'WDW', 1)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSYS (YSBH, KSBH, YSMC, PYZS, DLKL, SFZJ) VALUES ('000002', '000005', '陆再英', 'LZY', 'LZY', 1)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSYS (YSBH, KSBH, YSMC, PYZS, DLKL, SFZJ) VALUES ('000003', '000007', '郭小梅', 'GXM', 'GXM', 1)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSYS (YSBH, KSBH, YSMC, PYZS, DLKL, SFZJ) VALUES ('000004', '000001', '刘正湘', 'LZX', 'LZX', 1)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSYS (YSBH, KSBH, YSMC, PYZS, DLKL, SFZJ) VALUES ('000005', '000002', '唐家荣', 'TGX', 'TJR', 0)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSYS (YSBH, KSBH, YSMC, PYZS, DLKL, SFZJ) VALUES ('000006', '000006', '王炎', 'WY', 'WY', 1)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSYS (YSBH, KSBH, YSMC, PYZS, DLKL, SFZJ) VALUES ('000007', '000009', '吴杰', 'WJ', 'WJ', 1)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSYS (YSBH, KSBH, YSMC, PYZS, DLKL, SFZJ) VALUES ('000008', '000001', '杨晓芸', 'YXY', 'YXY', 0)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSYS (YSBH, KSBH, YSMC, PYZS, DLKL, SFZJ) VALUES ('000009', '000004', '丁虎', 'DH', 'DH', 0)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSYS (YSBH, KSBH, YSMC, PYZS, DLKL, SFZJ) VALUES ('000010', '000003', '周伦', 'ZL', 'ZL', 0)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_KSYS (YSBH, KSBH, YSMC, PYZS, DLKL, SFZJ) VALUES ('000011', '000008', '林立', 'LL', 'LL', 0)";
            statement.executeUpdate(sql);
            sql = "CREATE INDEX I_KSYS ON T_KSYS(YSBH, KSBH)";
            statement.executeUpdate(sql);
        } catch (Exception e) {

        }
        System.out.println("Table T_KSYS created successfully");
    }

    private static void InitHZXX() {
        try {
            String sql = "CREATE TABLE T_HZXX " +
                    "(HZBH  CHAR(6) PRIMARY KEY NOT NULL," +
                    " HZMC  CHAR(12)            NOT NULL," +
                    " PYZS  CHAR(4)             NOT NULL," +
                    " KSBH  CHAR(6)             NOT NULL," +
                    " SFZJ  BOOLEAN             NOT NULL," +
                    " GHRS  INT                 NOT NULL," +
                    " GHFY  DECIMAL(8, 2)       NOT NULL)";

            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000001', '内科普通号', 'NKPT', '000001', 0, 2, 1.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000002', '内科专家号', 'NKZJ', '000001', 1, 2, 2.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000003', '外科普通号', 'WKPT', '000002', 0, 3, 1.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000004', '外科专家号', 'WKZJ', '000002', 1, 3, 2.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000005', '妇科专家号', 'FKZJ', '000009', 1, 2, 2.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000006', '儿科专家号', 'EKZJ', '000007', 1, 4, 2.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000007', '鼻科号', 'BK', '000003', 0, 3, 1.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000008', '牙周科号', 'YZK', '000005', 1, 2, 2.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000009', '放射介入号', 'FSJR', '000004', 0, 4, 1.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000010', '急诊呼吸号', 'JZHX', '000008', 0, 10, 1.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000011', '皮肤科号', 'PFK', '000006', 1, 3, 2.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000012', '耳科号', 'EK', '000003', 1, 3, 2.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000013', '放射专家号', 'FSZJ', '000004', 1, 4, 2.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000014', '牙龈科号', 'YYK', '000005', 0, 5, 1.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000015', '整形科', 'ZXK', '000006', 0, 3, 1.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000016', '儿科普通号', 'EKPT', '000007', 0, 7, 1.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000017', '急诊专家号', 'JZZJ', '000008', 1, 5, 2.00)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO T_HZXX (HZBH, HZMC, PYZS, KSBH, SFZJ, GHRS, GHFY) VALUES ('000018', '妇科普通号', 'FKPT', '000009', 0, 5, 1.00)";
            statement.executeUpdate(sql);
            sql = "CREATE INDEX I_HZXX ON T_HZXX(HZBH, KSBH)";
            statement.executeUpdate(sql);
        } catch (Exception e) {
        }
        System.out.println("Table T_HZXX created successfully");
    }

    private static void InitGHXX() {
        try {
            String sql = "CREATE TABLE T_GHXX " +
                    "(GHBH  CHAR(6) PRIMARY KEY NOT NULL," +
                    " HZBH  CHAR(6)             NOT NULL," +
                    " YSBH  CHAR(6)             NOT NULL," +
                    " BRBH  CHAR(6)             NOT NULL," +
                    " GHRC  INT                 NOT NULL," +
                    " THBZ  BOOLEAN             NOT NULL," +
                    " GHFY  DECIMAL(8, 2)       NOT NULL," +
                    " RQSJ  DATETIME            NOT NULL)";
            statement.executeUpdate(sql);
            sql = "CREATE INDEX I_GHXX ON T_GHXX(GHBH, HZBH, YSBH, BRBH, GHRC)";
            statement.executeUpdate(sql);
        } catch (Exception e) {

        }
        System.out.println("Table T_GHXX created successfully");
    }

    public static ResultSet sqlExecute(String sql) {
        try {
            connection = null;
            statement = null;
            resultSet = null;
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:server.db");
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            if (sql.contains("UPDATE") || sql.contains("INSERT") || sql.contains("DELETE")) {
                statement.executeUpdate(sql);
                connection.commit();
            }
            else if(sql.contains("SELECT")){
                resultSet = statement.executeQuery(sql);
            }
            else if(sql.contains("TRANSACTION")){
                statement.execute(sql);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return resultSet;
    }

    public static void sqlExit(){
        try {
            resultSet.close();
            statement.close();
            connection.close();
        }catch(Exception e){

        }
    }

}

