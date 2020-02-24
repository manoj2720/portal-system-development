package project;

import java.sql.*;
import java.util.*;

public class Table
{
  public static void main(String args[]) {
    try {
	   String url = "jdbc:mysql://localhost/portal";
		
		String user = "root";
		
		String password = "admin";

		Class.forName( "com.mysql.cj.jdbc.Driver" ); 
		
		
		Connection cx = DriverManager.getConnection( url, user, password );

      Statement st = cx.createStatement();
		String table = "user";
 
      String sql_drop = "DROP TABLE IF EXISTS " + table;

      System.out.println( "\n=> execute: " + sql_drop );
      st.executeUpdate( sql_drop );
///Creating the Invites table
      String table_def =
	    "Username VARCHAR(30),Name VARCHAR(40), Password VARCHAR(40),Status VARCHAR(40),Partner VARCHAR(50)";

      String sql_create = "CREATE TABLE " + table + "(" + table_def + ")";

      System.out.println( "\n=> execute: " + sql_create );
      st.executeUpdate( sql_create );
      String table1 = "Invites";
      
      String sql_drop1 = "DROP TABLE IF EXISTS " + table1;

      System.out.println( "\n=> execute: " + sql_drop1 );
      st.executeUpdate( sql_drop1 );

      String table_def1 =
	    "Invitee VARCHAR(50), Invited VARCHAR(50)";

      String sql_create1 = "CREATE TABLE " + table1 + "(" + table_def1 + ")";

      System.out.println( "\n=> execute: " + sql_create1 );
      st.executeUpdate( sql_create1 ); 
    }
    catch( Exception x ) {
      System.err.println( x );
    }
  }
}
