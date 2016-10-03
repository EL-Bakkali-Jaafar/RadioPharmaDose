/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radpharmadose;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author jaafar
 */


 
public class  RegistredPatientsData {
public static RegistredPatientsData instance; 
public static RegistredPatientsData getInstance() {
        if (null == instance) {
            
System.out.println("singleton RegistredPatientsData is NULL");
            instance = new RegistredPatientsData();
        }
        return instance;
    }
    private String   recorded_date = null; 
    private String PatientName    =  "";
    private int    PatientAge     =   0;
    private double PatientWeight  = 0.0;
    private double PatientHeight  = 0.0;
    private int    ExamenID       =   0;
    private String Indication     =  "";
    private String Comment        =  "";
    private String HospitalName   =  "";
    
    
   int         Get_PatientAge(){

return this.PatientAge;
}
   int         Get_ExamenID(){

return this.ExamenID;
}
   double      Get_PatientWeight(){

return this.PatientWeight;
}
   double      Get_PatientHeight(){

return this.PatientHeight;
}
   String      Get_DateNow(){

return this.recorded_date;
}
   String      Get_Comment(){

return this.Comment;
}
   String      Get_HospitalName(){

return this.HospitalName;
}
   String      Get_Indication(){

return this.Indication;
}
   String      Get_PatientName(){

return this.PatientName;
}
   public      RegistredPatientsData() {
    }
   public void RecordData(String PatientName,int PatientAge,double PatientWeight,double PatientHeight,int ExamenID,String Indication,String Comment,String HospitalName){
  System.out.println("Begin recording");
  
this.Comment         = Comment;
this.ExamenID        = ExamenID;
this.HospitalName    = HospitalName;
this.Indication      = Indication;
this.PatientAge      = PatientAge;
this.PatientHeight   = PatientHeight;
this.PatientName     = PatientName;
this.PatientWeight   = PatientWeight;
    
}
   public void Set_Comment(String _Comment){
   this.Comment=_Comment;
}
  public void Set_DateNow(){
      
DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
Date date = new Date();
String _date= dateFormat.format(date);  
this.recorded_date=_date;
}
   public void Set_HospitalName(String _HospitalName){

this.HospitalName=_HospitalName;
}
   public void Set_Indication(String _Indication){

this.Indication=_Indication;
}
   public void Set_PatientName(String _PatientName){

this.PatientName=_PatientName;
}
   public void Set_PatientAge(int _PatientAge){

this.PatientAge=_PatientAge;
}
   public void Set_ExamenID(int _ExamenID){

this.ExamenID=_ExamenID;
}
   public void Set_PatientWeight(double _PatientWeight){

this.PatientWeight=_PatientWeight;
}
   public void Set_PatientHeight(double _PatientHeight){

this.PatientHeight=_PatientHeight;
}    
}
