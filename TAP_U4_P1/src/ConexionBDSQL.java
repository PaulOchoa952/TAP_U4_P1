
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author p_a_u
 */
public class ConexionBDSQL {
  Connection conexion;
  Statement transaccion;
  ResultSet cursor;
  
  
  public ConexionBDSQL(){
  try{
    Class.forName("com.mysql.jdbc.Driver");
    conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/tap_tarea1?zeroDateTimeBehavior=convertToNull","root","paul");
    transaccion=conexion.createStatement();
    }catch(SQLException | ClassNotFoundException ex){
              Logger.getLogger(ConexionBDSQL.class.getName()).log(Level.SEVERE, null, ex);

  }
  
  }//constructor
  public boolean insertar(Productos p){
    String SQL_Insert="INSERT INTO PRODUCTOS VALUES(NULL,'%DESC%',"+p.precio+","+p.existencia+")";
    
    SQL_Insert=SQL_Insert.replaceAll("%DESC%",p.descripcion);
      try {
          transaccion.execute(SQL_Insert);
      } catch (SQLException ex) {
         return false;
      }
      return true;
}//insertar
  
 public ArrayList<String[]>consultarTodos(){
  ArrayList<String[]> resultado=new ArrayList<String[]>();
      try {
          cursor=transaccion.executeQuery("SELECT*FROM PRODUCTOS");
          if(cursor.next()){
              do{
              String[]renglon={cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)};
              
              resultado.add(renglon);
              }while(cursor.next());
          }
      } catch (SQLException ex) {
          Logger.getLogger(ConexionBDSQL.class.getName()).log(Level.SEVERE, null, ex);
      }
      return resultado;
  }  
  public Productos consultarID(String ID){
  Productos ProductoResultado=new Productos();
      try {
        cursor =transaccion.executeQuery("SELECT*FROM PRODUCTOS WHERE ID="+ID);
        if(cursor.next()){
            ProductoResultado.descripcion=cursor.getString(2);
            ProductoResultado.precio=Float.parseFloat(cursor.getString(3));
            ProductoResultado.existencia=Integer.parseInt(cursor.getString(4));    
        }//if
      } catch (SQLException ex) {
          Logger.getLogger(ConexionBDSQL.class.getName()).log(Level.SEVERE, null, ex);
      }
  return ProductoResultado ;
  }
   public boolean eliminar(String ID){
      try {
          transaccion.execute("DELETE FROM PRODUCTOS WHERE ID="+ID);
      } catch (SQLException ex) {
          Logger.getLogger(ConexionBDSQL.class.getName()).log(Level.SEVERE, null, ex);
          return false;
      }
 return true;
 } //eliminar
  
  
 public boolean actualizar(Productos P){
  String update="UPDATE PRODUCTOS SET DESCRIPCION='%DESC%',PRECIO="+P.precio+",EXISTENCIA="+P.existencia+" WHERE ID="+P.id;
    update=update.replaceAll("%DESC%",P.descripcion);
    
      try {
          transaccion.executeUpdate(update);
      } catch (SQLException ex) {
         return false;
      }
      return true;
 }
}
