/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author 
 */
public class connection {
 static String constring = "jdbc:mysql://localhost/portal";
 static String username = "root";
 static String password = "admin";
 
 
  public static boolean Insert(String sql){
      Connection con = null;
      Statement s = null;
     try
     {
        con = DriverManager.getConnection(constring, username, password);
        //prepare stmnset
        s = con.prepareStatement(sql);
        s.execute(sql);
        return true;
     }
     catch(SQLException e){
         JOptionPane.showMessageDialog(null, e);
         return false;
     }finally{
          try{
              if(s!=null)s.close();
              if(con!=null)con.close();
          }catch(SQLException e){
             JOptionPane.showMessageDialog(null, e); 
          }
      } 
  }    

  public static boolean Update(String sql){
     Connection con = null;
     Statement s = null;
      try
     {
        con = DriverManager.getConnection(constring, username, password);
        //prepare stmnset
        s = con.prepareStatement(sql);
        s.execute(sql);
        return true;
     }
     catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
         return false;
     }finally{
          try{
              if(s!=null)s.close();
              if(con!=null)con.close();
          }catch(Exception e){
             JOptionPane.showMessageDialog(null, e); 
          }
      }
  }   
 public static String fetchItem(String sql,String columName){
     Connection con = null;
     Statement s = null;
     try{
         con = DriverManager.getConnection(constring, username, password); 
         s = con.prepareStatement(sql);
        
        ResultSet rs = s.executeQuery(sql);
        String col=null;
        //loop
        while(rs.next()){
            
           col = rs.getString(columName);
            
             //new JOptionPane().showMessageDialog(null, name);
        }
        return col;
     }catch(Exception e){
         JOptionPane.showMessageDialog(null, e);
         return null;
     }finally{
          try{
              if(s!=null)s.close();
            }catch(Exception e){
             JOptionPane.showMessageDialog(null, e); 
          }
      }
 }
   public static ArrayList fetch(String sql,String columName){
     Connection con = null;
     Statement s = null;
     ArrayList<String> data=new ArrayList<>();
     try{
         con = DriverManager.getConnection(constring, username, password); 
         s = con.prepareStatement(sql);
        
        ResultSet rs = s.executeQuery(sql);
        String col=null;
        //loop
        while(rs.next()){
            
           col = rs.getString(columName);
           data.add(col);
            
        }
        return data;
     }catch(Exception e){
         JOptionPane.showMessageDialog(null, e);
         return null;
     }finally{
          try{
              if(s!=null)s.close();
              if(con!=null)con.close();
          }catch(Exception e){
             JOptionPane.showMessageDialog(null, e); 
          }
      }
 } 
}
