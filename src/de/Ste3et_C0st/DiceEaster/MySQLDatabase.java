package de.Ste3et_C0st.DiceEaster;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;

public class MySQLDatabase
{
  public static Connection getConnection(String host, String username, String password)
    throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
  {
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    return DriverManager.getConnection("jdbc:mysql://" + host + "?user=" + username + "&password=" + password);
  }
  
  public static ResultSet executeQuery(String query, Connection conn)
  {
    try
    {
      Statement s = conn.createStatement();
      s.execute(query);
      return s.getResultSet();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static ResultSet executeQuery(String query, Object[] objs, Connection conn)
  {
    try
    {
      PreparedStatement stmnt = conn.prepareStatement(query);
      int i = 0;
      for (Object o : objs)
      {
        i++;
        if ((o instanceof String)) {
          stmnt.setString(i, (String)o);
        } else if ((o instanceof Integer)) {
          stmnt.setInt(i, ((Integer)o).intValue());
        } else if ((o instanceof Date)) {
          stmnt.setDate(i, (Date)o);
        } else if ((o instanceof Array)) {
          stmnt.setArray(i, (Array)o);
        } else if ((o instanceof Blob)) {
          stmnt.setBlob(i, (Blob)o);
        } else if ((o instanceof Boolean)) {
          stmnt.setBoolean(i, ((Boolean)o).booleanValue());
        } else if ((o instanceof BigDecimal)) {
          stmnt.setBigDecimal(i, (BigDecimal)o);
        } else if ((o instanceof Byte)) {
          stmnt.setByte(i, ((Byte)o).byteValue());
        } else if ((o instanceof Byte[])) {
          stmnt.setBytes(i, (byte[])o);
        } else if ((o instanceof Clob)) {
          stmnt.setClob(i, (Clob)o);
        } else if ((o instanceof Double)) {
          stmnt.setDouble(i, ((Double)o).doubleValue());
        } else if ((o instanceof Long)) {
          stmnt.setLong(i, ((Long)o).longValue());
        } else if ((o instanceof Float)) {
          stmnt.setFloat(i, (float)((Long)o).longValue());
        } else if ((o instanceof URL)) {
          stmnt.setURL(i, (URL)o);
        } else if ((o instanceof Timestamp)) {
          stmnt.setTimestamp(i, (Timestamp)o);
        } else if ((o instanceof Time)) {
          stmnt.setTime(i, (Time)o);
        } else if ((o instanceof Ref)) {
          stmnt.setRef(i, (Ref)o);
        } else if ((o instanceof RowId)) {
          stmnt.setRowId(i, (RowId)o);
        } else if ((o instanceof Short)) {
          stmnt.setShort(i, ((Short)o).shortValue());
        } else {
          stmnt.setObject(i, o);
        }
      }
      stmnt.execute();
      ResultSet rs = stmnt.getResultSet();
      conn.close();
      stmnt.close();
      return rs;
    }
    catch (SQLException localSQLException) {}
    return null;
  }
}
