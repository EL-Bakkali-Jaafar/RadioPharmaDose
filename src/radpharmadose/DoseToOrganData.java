/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radpharmadose;

import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author jaafar
 */
public class DoseToOrganData {
       double[] array_data  = new double[27];
      String [] array_organ = new String []{
"Adrenals ",
"Bladder ",
"Bone surfaces ",
"Brain ",
"Breast ",
"Gall bladder ",
"Stomach ",
"Small Intestine",
"Colon ",
"Upper Large Intestine",
"Lower Large Intestine",
"Heart ",
"Kidneys ",
"Liver ",
"Lungs ",
"Muscles ",
"Oesophagus ",
"Ovaries ",
"Pancreas ",
"Red marrow ",
"Skin",
"Spleen",
"Testes",
"Thymus",
"Thyroid",
"Uterus",
"Remaining organs"
} ;
    
public                    DoseToOrganData(){
        
    };
public static             DoseToOrganData instance; 
public static             DoseToOrganData getInstance() {
        if (null == instance) { // Premier appel
            instance = new DoseToOrganData();
        }
         return instance;
   }
public double []          GetArray_Data(){
         
         return this.array_data;
     }
public void               ShowEffectiveDose(JTextField _jTextFiled){
    
     _jTextFiled.setText(String.valueOf(this.array_data[27]));
    } 
public void               PrintData(){
    
     for (int i=0; i<27; i++) System.out.println(" PrintData; i= "+i+"-> "+array_data[i]);
        
    }
public void               RecordData( double[] _array_data){
        
       this.array_data= _array_data;
       
       
        
    }    
public void               FillDoseToOrganTable(JTable _t){
        
       for (int i=0; i<27; i++) {
        System.out.println(GetArray_Data()[i]);
        
        
       _t.setValueAt(this.array_organ[i], i,0);
       _t.setValueAt(this.array_data[i], i,1);

        
        } 
    }


}
