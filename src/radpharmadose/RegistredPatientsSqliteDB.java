/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radpharmadose;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jaafar
 */
public class RegistredPatientsSqliteDB 
{   
private static                   RegistredPatientsSqliteDB instance;     
public void   AddNewRow(JTable _JTable,  int ID,  String PATIENT_NAME, int  ExamenID,  String INDICATION, double EFFECTIVE_DOSE,String RECORD_DATE ){
    DefaultTableModel dm = (DefaultTableModel)_JTable.getModel();
    dm.addRow( new Object[]{ID, ExamenID,PATIENT_NAME,INDICATION, EFFECTIVE_DOSE, RECORD_DATE });
}
public void   ClearTable(JTable _JTable){
    DefaultTableModel dm = (DefaultTableModel)_JTable.getModel();
    dm.getDataVector().removeAllElements();
    
}
public void   DumpDataTojTable( JTable _JTable, String _DBPath) throws SQLException{
    
    Connection _connection ;
    Statement stmt ;
    double EFFECTIVE_DOSE;
    int    ID;
    int    ExamenID;
    String PATIENT_NAME;
    String INDICATION ;
    String RECORD_DATE;
    
    
    GetCountRowDB() ;
    
    
    try {
        Class.forName("org.sqlite.JDBC");
        _connection = DriverManager.getConnection("jdbc:sqlite:" + _DBPath);
        _connection.setAutoCommit(false);
        
        stmt = _connection.createStatement();
        try (ResultSet rs = stmt.executeQuery( "SELECT * FROM PATIENTS_DB;" )) {
            int inc=0;
            while ( rs.next() ) {
                
                ID             = rs.getInt("ID");
                PATIENT_NAME   = rs.getString("PATIENT_NAME");
                ExamenID       = rs.getInt("EXAMEN_ID");
                INDICATION     = rs.getString("INDICATION");
                EFFECTIVE_DOSE = rs.getDouble("EFFECTIVE_DOSE");
                RECORD_DATE    = rs.getString("RECORD_DATE");
                
                //  _JTable.setValueAt(ID, inc,0);
                // _JTable.setValueAt(ExamenID, inc,1);
                // _JTable.setValueAt(PATIENT_NAME, inc,2);
                //_JTable.setValueAt(INDICATION, inc,3);
                //_JTable.setValueAt(EFFECTIVE_DOSE, inc,4);
                //_JTable.setValueAt(RECORD_DATE, inc,5);
                AddNewRow(
                        _JTable,
                        ID,
                        PATIENT_NAME,
                        ExamenID,
                        INDICATION,
                        EFFECTIVE_DOSE,
                        RECORD_DATE
                );
                System.out.println( "ID = " + ID );
                System.out.println( "ExamenID = " + ExamenID );
                System.out.println( "PATIENT_NAME = " + PATIENT_NAME );
                System.out.println( "INDICATION = " + INDICATION );
                System.out.println( "EFFECTIVE_DOSE = " + EFFECTIVE_DOSE );
                System.out.println("RECORD_DATE = " + RECORD_DATE);
                System.out.println();
                inc++;
            }
        }
        stmt.close();
        _connection.close();
    } catch ( ClassNotFoundException | SQLException e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
    }
    
    
}  
public void   FindPatientData_with_filter( String _filter, String value,JTable _JTable, String _DBPath){
    
    Connection _connection ;
    Statement stmt ;
    double EFFECTIVE_DOSE;
    int    ID;
    int    ExamenID;
    String PATIENT_NAME;
    String INDICATION ;
    String RECORD_DATE;
    
    
//ClearTable(_JTable);

String _result_Name="",Examen_ID_result="", INDICATION_result="",RECORD_DATE_result="";


try {
    Class.forName("org.sqlite.JDBC");
    _connection = DriverManager.getConnection("jdbc:sqlite:" + this.DBPath);
    _connection.setAutoCommit(false);
    
    stmt = _connection.createStatement();
    try (ResultSet rs = stmt.executeQuery( "SELECT * FROM PATIENTS_DB;" )) {
        int inc=0;
        while ( rs.next() ) {
            
            ID             = rs.getInt("ID");
            PATIENT_NAME   = rs.getString("PATIENT_NAME");
            ExamenID       = rs.getInt("EXAMEN_ID");
            INDICATION     = rs.getString("INDICATION");
            EFFECTIVE_DOSE = rs.getDouble("EFFECTIVE_DOSE");
            RECORD_DATE    = rs.getString("RECORD_DATE");
            if (_filter.equals("Name")) {
                
                System.out.println("FILTER : "+_filter);
                
                if (PATIENT_NAME.toLowerCase().contains(value.toLowerCase()))
                {                System.out.println("PATIENT NAME : "+PATIENT_NAME);
                _result_Name="OK";
                AddNewRow(_JTable,   ID,  PATIENT_NAME,  ExamenID, INDICATION, EFFECTIVE_DOSE, RECORD_DATE );
                inc++;  
                
                }
                if( _result_Name.equals(""))  {
                    javax.swing.JOptionPane.showMessageDialog(null,"The current patient is not found in DataBase !");
                    
                }
            }
            
            
            if (_filter.equals("Examen ID")) {
                
                System.out.println("FILTER : "+_filter);
                
                if (ExamenID==Integer.parseInt(value))
                    
                {                System.out.println("Examen ID : "+ExamenID); 
                Examen_ID_result="OK";
                AddNewRow(_JTable,   ID,  PATIENT_NAME,  ExamenID,      INDICATION, EFFECTIVE_DOSE, RECORD_DATE );
                inc++;
                
                }
                if( Examen_ID_result.equals(""))  {
                    javax.swing.JOptionPane.showMessageDialog(null,"The current patient is not found in DataBase !");
                    
                }
            }
            
            
            if (_filter.equals("Indication")) {
                
                System.out.println("FILTER : "+_filter);
                
                if (INDICATION.toLowerCase().contains(value.toLowerCase()))
                    
                {                System.out.println("Indication : "+INDICATION); 
                INDICATION_result="OK";
                AddNewRow(_JTable,   ID,  PATIENT_NAME,  ExamenID,      INDICATION, EFFECTIVE_DOSE, RECORD_DATE );
                inc++;
                
                }
                if( INDICATION_result.equals(""))  {
                    javax.swing.JOptionPane.showMessageDialog(null,"The current patient is not found in DataBase !");
                    
                }
            }
            
            if (_filter.equals("date")) {
                
                System.out.println("FILTER : "+_filter);
                
                if (INDICATION.toLowerCase().contains(value.toLowerCase()))
                    
                {                System.out.println("date : "+RECORD_DATE); 
                RECORD_DATE_result="OK";
                AddNewRow(_JTable,   ID,  PATIENT_NAME,  ExamenID,      INDICATION, EFFECTIVE_DOSE, RECORD_DATE );
                inc++;
                
                }
                if( RECORD_DATE_result.equals(""))  {
                    javax.swing.JOptionPane.showMessageDialog(null,"The current patient is not found in DataBase !");
                    
                }
            } 
            
          
        }
    }
    stmt.close();
    _connection.close();
    
    
} catch ( ClassNotFoundException | SQLException e ) {
    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    System.exit(0);
}     


}
public void   GetCountRowDB() throws SQLException{
    Connection _connection = null ;
    Statement stmt;
    
    try
    {
        
        
        Class.forName("org.sqlite.JDBC");
        System.out.println("====================");
        
        _connection = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
        System.out.println("Opened database successfully");
        stmt = _connection.createStatement();
        
        
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS COUNT FROM PATIENTS_DB");
        
        while(rs.next()) {
            raw_count= rs.getInt("COUNT");
            System.out.println("The count is " + rs.getInt("COUNT"));
        }
        
    }
    catch ( ClassNotFoundException | SQLException e )
    {
//Create a Statement class to execute the SQL statement
        
        
        //Closing the connection
        _connection.close();    
        
    }
    
}
String        Get_Record_Date(){ 
    return this.sqlite_Record_date;
}
double        Get_sqlite_Effective_dose(){
return this.sqlite_Effective_dose;
}
int           Get_sqlite_ExamenID(){
    return this.sqlite_ExamenID;
}
String        Get_sqlite_Indication(){
return this.sqlite_Indication;
}
String        Get_sqlite_PatientName(){
return this.sqlite_PatientName;
}
String        GetdBPath(){
    
    return this.DBPath;
}
public void   InsertData(int Identificator){
    
    Connection _connection ;
    Statement _Statement;
    try {
        Class.forName("org.sqlite.JDBC");
        _connection = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
        System.out.println( DBPath);
        
        _connection.setAutoCommit(false);
        System.out.println("Opened database successfully");
        
        _Statement = _connection.createStatement();
        String sql = "INSERT INTO PATIENTS_DB (EXAMEN_ID,PATIENT_NAME,INDICATION,EFFECTIVE_DOSE,RECORD_DATE) " +
                "VALUES ( "+String.valueOf(this.sqlite_ExamenID)+" , '"+this.sqlite_PatientName+"' ,'"+this.sqlite_Indication+"' , "+ String.valueOf(this.sqlite_Effective_dose)+" , '"+this.sqlite_Record_date+"') ;";
        
        
        _Statement.executeUpdate(sql);
        _Statement.close();
        _connection.commit();
        _connection.close();
        
        System.err.println( "the data has been insered successfully");
        
    } catch ( ClassNotFoundException | SQLException e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
    }
    
    
}  
public void   RemoveAllRows(JTable _JTable){
  DefaultTableModel model=(DefaultTableModel)_JTable.getModel();
            int rc= model.getRowCount();
            for(int i = 0;i<rc;i++){
                model.removeRow(0);
            }     
    
}
public void   SetPath(String dBPath) {
    this.DBPath = dBPath;
}
public void   Set_Record_Date(String _Record_date){
    this.sqlite_Record_date=_Record_date;
    
}
    /**
     *
     * @param _Effective_dose
     */
public void   Set_sqlite_Effective_dose (double _Effective_dose ){
this.sqlite_Effective_dose =_Effective_dose ;
}
public void   Set_sqlite_ExamenID(int _ExamenID){
    this.sqlite_ExamenID=_ExamenID;
}
public void   Set_sqlite_Indication(String _Indication){
this.sqlite_Indication=_Indication;
}
public void   Set_sqlite_PatientName(String _PatientName){
    this.sqlite_PatientName=_PatientName;
}
public void   SetupSqliteDataBase() {
    
    System.out.println("=========###########==========");

 //###
  //DBPath="./patients.db";  
    
    ///###
    
File f = new File(DBPath);
{ 

Connection _connection = null;
Statement stmt = null;
try 
{
Class.forName("org.sqlite.JDBC");
System.out.println("====================");

_connection = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
System.out.println("Opened database successfully");
stmt = _connection.createStatement();
String sql = "CREATE TABLE IF NOT EXISTS PATIENTS_DB " +
                   "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                   " EXAMEN_ID           INT    NOT NULL, " + 
                   " PATIENT_NAME            TEXT     NOT NULL, " + 
                   " INDICATION       TEXT, " + 
                   " EFFECTIVE_DOSE         REAL, "+
                   " RECORD_DATE         TEXT NOT NULL)"; 

stmt.executeUpdate(sql);
stmt.close();
_connection.close();

System.out.println("the table PATIENTS_DB has been successfully created");

}
catch ( ClassNotFoundException | SQLException e ) 
{
System.err.println( e.getClass().getName() + ": " + e.getMessage() );

System.exit(0);
}
System.out.println("Table created successfully");
}
}  
public void   UpdateRowCount(JTable _JTable) throws SQLException{
    GetCountRowDB();
    
    DefaultTableModel dm = (DefaultTableModel)_JTable.getModel();
    dm.setRowCount(raw_count);
    System.out.println(" number of raws : "+raw_count);
}
public void   close() {
    try {
        connection.close();
        statement.close();
    } catch (SQLException e)
    {
        System.out.println(e.getMessage());}
}
public void   passDoseData(double _EffectiveDose){
this.sqlite_Effective_dose=    _EffectiveDose;     }
public void   passPatientData(RegistredPatientsData myRegistredPatientsData ){
    this.sqlite_ExamenID=myRegistredPatientsData.Get_ExamenID();
    this.sqlite_Indication= myRegistredPatientsData.Get_Indication();
    this.sqlite_PatientName= myRegistredPatientsData.Get_PatientName();
    this.sqlite_Record_date= myRegistredPatientsData.Get_DateNow();
}
public static                    RegistredPatientsSqliteDB getInstance() {
    if (null == instance)
    {
        instance = new RegistredPatientsSqliteDB();
    }
    return instance;
}
private String                   sqlite_PatientName    =   "",
                                 sqlite_Indication     =   "",
                                 DBPath                =   "";
private final Connection         connection            = null;
int                              raw_count=0;
private double                   sqlite_Effective_dose =  0.0;
private int                      sqlite_ExamenID       =    0;  
private String sqlite_Record_date = "";
private final Statement          statement             = null;
public                           RegistredPatientsSqliteDB() {
        
    }
}
    
