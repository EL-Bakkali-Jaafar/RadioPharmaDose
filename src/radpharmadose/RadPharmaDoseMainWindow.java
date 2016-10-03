/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radpharmadose;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.tools.Executable;
/**
 *
 * @author jaafar
 * 
 * 
 * 
 */
public final class RadPharmaDoseMainWindow extends javax.swing.JFrame {
    
    // Tatto Look and Feel
final   String     _HiFiLookAndFeel        ="com.jtattoo.plaf.hifi.HiFiLookAndFeel",
                   _AcrylLookAndFeel       ="com.jtattoo.plaf.acryl.AcrylLookAndFeel",
                   _AeroLookAndFeel        ="com.jtattoo.plaf.aero.AeroLookAndFeel",
                   _AluminiumLookAndFeel   ="com.jtattoo.plaf.aluminium.AluminiumLookAndFeel",
                   _BernsteinLookAndFeel   ="com.jtattoo.plaf.bernstein.BernsteinLookAndFeel",
                   _LunaLookAndFeel        ="com.jtattoo.plaf.luna.LunaLookAndFeel";

        String      PatientName,
                    Indication,
                    Comment,
                    HospitalName,
                    SelectedRadioIsotope="",
                    SelectedRadioPharmaceutical="",
                    _DBPath="",
                    pdf_filename;
                          
        
String[] array_RadioIsotopes = {
"Carbon-11",  //RadioIsotpe ID_2
"Carbon-14",   //RadioIsotpe ID_3
"Oxygen-15", //RadioIsotpe ID_4
"Fluorine-18", //RadioIsotpe ID_5
"Chromium-51", //RadioIsotpe ID_6
"Gallium-67", //RadioIsotpe ID_7
"Gallium-68", //RadioIsotpe ID_8
"Selenium-75", //RadioIsotpe ID_9
"Technetium-99m", //RadioIsotpe ID_10
"Indium-111", //RadioIsotpe ID_11
"Iodine-123", //RadioIsotpe ID_12
"Iodine-124", //RadioIsotpe ID_13
"Iodine-125", //RadioIsotpe ID_14
"Iodine-131", //RadioIsotpe ID_15
"Thallium-201" //RadioIsotpe ID_16
} ;

  double[]                  array_data                    = new double[28];
  double                    mFactor                       = 1;   
  double                    activity_value                = 0.0;
  double                    PatientWeight;
  double                    PatientHeight;
  int                       PatientAge;
  int                       ExamenID;
  int                       StartLine,EndLine;
  static int                identificator                 = 0;
  RegistredPatientsData     myRegistredPatientsData ;
  RegistredPatientsSqliteDB myRegistredPatientsSqliteDB;
  DoseToOrganData           myDoseToOrganData;
  

    /**
     * Creates new form RadPharmaDose_MainWindow
     * @param aclass
     * @return 
     * @throws java.lang.Exception 
     */
  @SuppressWarnings("empty-statement")
  
    public void          cleaning(){
    
    TextField_AdministredActivity.setText("");
    _Comment.setText("");
    _ExamenID.setText("");
    _HospitalName.setText("");
    _Indication.setText("");
    _PatientAge.setText("");;
    _PatientHeight.setText("");
    _PatientName.setText("");
    _PatientWeight.setText("");
    container.setText("");
    
}
    public static String GetJarContainingFolder(Class aclass) throws Exception {
CodeSource codeSource = aclass.getProtectionDomain().getCodeSource();
File jarFile;
if (codeSource.getLocation() != null) {
jarFile = new File(codeSource.getLocation().toURI());
} else {
String path = aclass.getResource(aclass.getSimpleName() + ".class").getPath();
String jarFilePath = path.substring(path.indexOf(":") + 1, path.indexOf("!"));
jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
jarFile = new File(jarFilePath);
}
return jarFile.getParentFile().getAbsolutePath();
}
    public String[]      GetInstalledPrinter(){
    String[]_printer= new String[6];
    PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
    System.out.println("Number of print services: " + printServices.length);
    int i=-1;    
    for (PrintService printer : printServices) {
    System.out.println("Printer: " + printer.getName()); 
    _printer[i]=printer.getName();
    i++;
    };
    return _printer;
}
    public String[]      GetRadioPharmaceuticalsList(String _RadioIsotope){
    
   String[] _RadioPharmaceuticalsList=null;
    if (_RadioIsotope.equals("Carbon-11")) {
     _RadioPharmaceuticalsList=new String[1];;
     _RadioPharmaceuticalsList[4]="C11-thymidine";


  }else if (_RadioIsotope.equals("Carbon-14")) {
     _RadioPharmaceuticalsList=new String[2];
     _RadioPharmaceuticalsList[0]="C14-neutral_fat_free_fatty_acids";
     _RadioPharmaceuticalsList[1]="C14-urea";
     
     
  }
  else if (_RadioIsotope.equals("Oxygen-15")) {
     _RadioPharmaceuticalsList=new String[1];
     _RadioPharmaceuticalsList[0]="15O-water";
     
  }else if (_RadioIsotope.equals("Fluorine-18")) {
     _RadioPharmaceuticalsList=new String[1];
     _RadioPharmaceuticalsList[0]="F18-FDG";
      
  }
  else if (_RadioIsotope.equals("Chromium-51")) {
     _RadioPharmaceuticalsList=new String[1];
     _RadioPharmaceuticalsList[0]="51Cr-EDTA";
  
  }else if (_RadioIsotope.equals("Gallium-67")) {
     _RadioPharmaceuticalsList=new String[1];
     _RadioPharmaceuticalsList[0]="67Ga-citrate";
  
  }else if (_RadioIsotope.equals("Gallium-68")) {
     _RadioPharmaceuticalsList=new String[1];
     _RadioPharmaceuticalsList[0]="68Ga-EDTA";
  
  }else if (_RadioIsotope.equals("Selenium-75")) {
     _RadioPharmaceuticalsList=new String[1];
     _RadioPharmaceuticalsList[0]="75Se-HCAT";
  
  }
  else if (_RadioIsotope.equals("Technetium-99m")) {
     _RadioPharmaceuticalsList=new String[21];
     _RadioPharmaceuticalsList[0]="99mTc-colloids_small_intratumoral injection";
     _RadioPharmaceuticalsList[1]="99mTc-DMSA";
     _RadioPharmaceuticalsList[2]="99mTc-DTPA";
     _RadioPharmaceuticalsList[3]="99mTc-EC_Acute_unilateral_renal_blockage";
     _RadioPharmaceuticalsList[4]="99mTc-ECD";
     _RadioPharmaceuticalsList[5]="99mTc-furifosmin";
     _RadioPharmaceuticalsList[6]="99mTc-HIG";
     _RadioPharmaceuticalsList[7]="99mTc-HM-PAO";
     _RadioPharmaceuticalsList[8]="99mTc-IDA_derivatives";
     _RadioPharmaceuticalsList[9]="99mTc-MAA_normal_renal_funkt";
     _RadioPharmaceuticalsList[10]="99mTc-MAG3";
     _RadioPharmaceuticalsList[11]="99mTc-markers_non-absorbable";
     _RadioPharmaceuticalsList[12]="99mTc-MIBI";
     _RadioPharmaceuticalsList[13]="99mTc-monoclonal_antibodies_fragments";
     _RadioPharmaceuticalsList[14]="99mTc-pertechnegas";
     _RadioPharmaceuticalsList[15]="99mTc-pertechnetate";
     _RadioPharmaceuticalsList[16]="99mTc-phosphates_and_phosphonates";
     _RadioPharmaceuticalsList[17]="99mTc-RBC";
     _RadioPharmaceuticalsList[18]="99mTc-Technegas";
     _RadioPharmaceuticalsList[19]="99mTc-tetrofosmin";
     _RadioPharmaceuticalsList[20]="99mTc-WBC";
    
  }
 else if (_RadioIsotope.equals("Indium-111")) {
     _RadioPharmaceuticalsList=new String[3];
     _RadioPharmaceuticalsList[0]="111In-HIG";
     _RadioPharmaceuticalsList[1]="111In-monoclonal_antibodies_fragments";
     _RadioPharmaceuticalsList[2]="111In-octreotide";

  } 
  
else if (_RadioIsotope.equals("Iodine-123")) {
     _RadioPharmaceuticalsList=new String[3];
     _RadioPharmaceuticalsList[0]="123I-iodo_hippurate";
     _RadioPharmaceuticalsList[1]="123I-MIBG";
     _RadioPharmaceuticalsList[2]="123I-monoclonal_antibodies_fragments";

  }   

 else if (_RadioIsotope.equals("Iodine-131")) {
     _RadioPharmaceuticalsList=new String[3];
     _RadioPharmaceuticalsList[0]="131I-iodo_hippurate";
     _RadioPharmaceuticalsList[1]="131I-iodo_monoclonal_antibodies_fragments";
     _RadioPharmaceuticalsList[2]="131I-norcholesterol";

  }    

    else if (_RadioIsotope.equals("Thallium-201")) {
     _RadioPharmaceuticalsList=new String[1];
     _RadioPharmaceuticalsList[0]="201Tl-ion";
     
  }    

  return _RadioPharmaceuticalsList;  
}
    public void          GetSelectedPatientAgeDomain(){
    
    if(RadioButton_Adult.isSelected()==true) {
        StartLine = 1;
        EndLine   = 28;
    
    }else if (RadioButton_15years.isSelected()==true) {
       StartLine = 29;
       EndLine   = 56;
    
    } else if (RadioButton_10years.isSelected()==true) {
       StartLine = 57;
       EndLine   = 84;
    
    }else if (RadioButton_5years.isSelected()==true) {
       StartLine = 85;
       EndLine   = 112;
    
    }else if (RadioButton_1year.isSelected()==true) {
       StartLine = 113;
       EndLine   = 140;
    }
    }  
    public void          GenerateMedicalRapport(){
       // myRegisredPatientsData = RegistredPatientsData
       DoseToOrganData myDoseToOrganData = DoseToOrganData.getInstance();

       String _doses_to_organs="";
       for (int i=0; i< 27; i++) _doses_to_organs+="\n"+myDoseToOrganData.array_organ[i]+" : "+array_data[i]+" mGy";
       String _text= 
               
      "Medical RadioPharmaceutical Administred Patient Report"+
      "\n######################################################"+
      "\nPatient's Info"+
      "\n______________________________________________________"+
      "\nHospital Name: "+HospitalName+
      "\nRecord Date: "+ myRegistredPatientsData.Get_DateNow()+
      "\nExamen ID: "+ExamenID+"\n"+
      "Patient Name: "+PatientName+
      "\nIndication: "+Indication+
      "\nRdiopharmaceutical data"+
      "\n______________________________________________________"+
      "\nRadioIsotope: "+RadioIsotopeList.getSelectedValue()+
      "\nRadiopharmaceutical product: "+RadiopharmaceuticalList.getSelectedValue()+
      "\nAdministred Activity: "+TextField_AdministredActivity.getText()+" Mbq"+
      "\nEffective Dose: "+ myRegistredPatientsSqliteDB.Get_sqlite_Effective_dose()+" mGy";
       
    container.setText(_text+_doses_to_organs);
    
    
    }
    public void          PrintPDF(String fileName){
	     try{
	             Executable ex = new Executable();
	             ex.openDocument(fileName);
	             ex.printDocument(fileName);
	     }catch(IOException e){
	             e.printStackTrace();
	     }
	}
    public void          RecordEffectiveDoseValueInDB (){
         
       if (RadioButton_Mbq.isSelected()==true){ mFactor=1;} else {mFactor=37;}
       double _EffectiveDose= mFactor*activity_value *array_data[27];
        myRegistredPatientsSqliteDB.passDoseData(_EffectiveDose);
        
    }
    public void          RecordPatientInfoInDB (){
        myRegistredPatientsSqliteDB.passPatientData(myRegistredPatientsData);
        
    }   
    public void          ReadDataStratedWithSpecificLine(int startLine, int endLine, String FileFullPath){
     BufferedReader in=null;   
        try {
      in = new BufferedReader (new FileReader(FileFullPath));
        }
	 catch (FileNotFoundException ex) {
        }     
	    String info = "";
	   
	     try { 
                 
	    for (int i = 0; i < startLine; i++) { info = in.readLine(); }
	    for (int i = startLine; i < endLine + 1; i++) {
                
	        info = in.readLine();
                array_data[i-startLine]=Double.parseDouble(info);
                System.out.println(info);
	    }
  
	    in.close(); }
            catch (IOException ex) {
                
        } 
           
    }
    public void          RadioIsotopeListListener(){
        
      RadioIsotopeList. addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting()){
                JList source = (JList)event.getSource();
                String selected = source.getSelectedValue().toString();
                String[] array_radiopharnaceuticals= GetRadioPharmaceuticalsList(selected);
                
                RadiopharmaceuticalList.setListData(array_radiopharnaceuticals);
                
            }
        });
        RadioIsotopeList.setListData(array_RadioIsotopes);
               
    }
    public void          SetLookAndFeel(String s){
        
 try {      
        UIManager.setLookAndFeel(s);
        SwingUtilities.updateComponentTreeUI(this);
this.pack();
   
} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

 }
    public void          SetupButtonGroup(){
        buttonGroup_PatientAgeDomain.add(RadioButton_Adult);
        buttonGroup_PatientAgeDomain.add(RadioButton_15years);
        buttonGroup_PatientAgeDomain.add(RadioButton_10years);
        buttonGroup_PatientAgeDomain.add(RadioButton_5years);
        buttonGroup_PatientAgeDomain.add(RadioButton_1year);
        buttonGroupRadioActivityUnity.add(RadioButton_Mbq);
        buttonGroupRadioActivityUnity.add(RadioButton_mCi);
        
    }
    public void          UpdateDataAccordingToActivityValue(){
    
    activity_value=Double.parseDouble(TextField_AdministredActivity.getText());

    for (int i=0;i< array_data.length; i++) array_data[i]=mFactor*activity_value *array_data[i];
    
    
    }
    public void          WritePatientDataToSqliteDB() throws SQLException{
    myRegistredPatientsSqliteDB.InsertData(identificator++);
    myRegistredPatientsSqliteDB.GetCountRowDB();
    System.out.println("identificator: "+identificator);
    
    }
    public               RadPharmaDoseMainWindow() {
        initComponents();
       
        SetLookAndFeel(_BernsteinLookAndFeel);
        RadioIsotopeListListener();
        SetupButtonGroup();
        container.setEditable(false);
 try {
     
     
      String app_path=GetJarContainingFolder(RadPharmaDoseMainWindow.class);
      
            System.out.println( " pass 1" );

     // 
     
  myRegistredPatientsSqliteDB = new RegistredPatientsSqliteDB();
              System.out.println( " pass 2" );

_DBPath=app_path+"/data/RegistredPatientsSqliteDB/RegistredPatientsSqliteDB.db";
  myRegistredPatientsSqliteDB.SetPath(_DBPath);
              System.out.println(app_path );

  //
  myRegistredPatientsSqliteDB.SetupSqliteDataBase();
              System.out.println( " pass 4" );

  //
  //
  myRegistredPatientsSqliteDB.close();
                System.out.println( " pass 5" );

  
  
        }
	 catch (Exception e) {
             
             
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        } 
    

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        buttonGroup_PatientAgeDomain = new javax.swing.ButtonGroup();
        buttonGroupRadioActivityUnity = new javax.swing.ButtonGroup();
        TabbedPane_RadPharmaDose = new javax.swing.JTabbedPane();
        Panel_RadPharmaDose = new javax.swing.JPanel();
        lbl_hostpital_name = new javax.swing.JLabel();
        _HospitalName = new javax.swing.JTextField();
        lbl_patient_name = new javax.swing.JLabel();
        _PatientName = new javax.swing.JTextField();
        lbl_patient_age = new javax.swing.JLabel();
        _PatientAge = new javax.swing.JTextField();
        lbl_patient_weight = new javax.swing.JLabel();
        _PatientWeight = new javax.swing.JTextField();
        lbl_patient_height = new javax.swing.JLabel();
        _PatientHeight = new javax.swing.JTextField();
        lbl_indication = new javax.swing.JLabel();
        _Indication = new javax.swing.JTextField();
        lbl_comment = new javax.swing.JLabel();
        _Comment = new javax.swing.JTextField();
        btn_registration = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lbl_patient_name1 = new javax.swing.JLabel();
        _ExamenID = new javax.swing.JTextField();
        btn_clean = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        RadiopharmaceuticalList = new javax.swing.JList<>();
        lbl_radiopharmaceutical = new javax.swing.JLabel();
        RadioIsotope_List = new javax.swing.JScrollPane();
        RadioIsotopeList = new javax.swing.JList<>();
        lbl_AdministredActivity = new javax.swing.JLabel();
        TextField_AdministredActivity = new javax.swing.JTextField();
        RadioButton_Mbq = new javax.swing.JRadioButton();
        RadioButton_mCi = new javax.swing.JRadioButton();
        lbl_PatientAgeDomain = new javax.swing.JLabel();
        RadioButton_Adult = new javax.swing.JRadioButton();
        RadioButton_10years = new javax.swing.JRadioButton();
        RadioButton_15years = new javax.swing.JRadioButton();
        RadioButton_5years = new javax.swing.JRadioButton();
        RadioButton_1year = new javax.swing.JRadioButton();
        jSeparator2 = new javax.swing.JSeparator();
        btn_showTable = new javax.swing.JButton();
        btn_calculation = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        container = new javax.swing.JEditorPane();
        btn_edit = new javax.swing.JButton();
        btn_export_as_pdf = new javax.swing.JButton();
        btn_print = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        MenuBar = new javax.swing.JMenuBar();
        Menu_File = new javax.swing.JMenu();
        MenuItem_Exit = new javax.swing.JMenuItem();
        Menu_Tools = new javax.swing.JMenu();
        MenuItem_PatientDB = new javax.swing.JMenuItem();
        Menu_LookAndFeel = new javax.swing.JMenu();
        MenuItem_Acryl = new javax.swing.JMenuItem();
        MenuItem_Aero = new javax.swing.JMenuItem();
        MenuItem_Aluminium = new javax.swing.JMenuItem();
        MenuItem_Bernstein = new javax.swing.JMenuItem();
        MenuItem_HIFI = new javax.swing.JMenuItem();
        MenuItem_Luna = new javax.swing.JMenuItem();
        Menu_Help = new javax.swing.JMenu();
        MenuItem_About = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RadPharmaDose");
        setName("Main_frame"); // NOI18N
        setType(java.awt.Window.Type.UTILITY);

        TabbedPane_RadPharmaDose.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N

        lbl_hostpital_name.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        lbl_hostpital_name.setText("Hospital Name");

        lbl_patient_name.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        lbl_patient_name.setText("Patient Name");

        _PatientName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _PatientNameActionPerformed(evt);
            }
        });

        lbl_patient_age.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        lbl_patient_age.setText("Patient Age");

        _PatientAge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _PatientAgeActionPerformed(evt);
            }
        });

        lbl_patient_weight.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        lbl_patient_weight.setText("Patient Weight (Kg)");

        lbl_patient_height.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        lbl_patient_height.setText("Patient Height (cm)");

        lbl_indication.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        lbl_indication.setText("Indication");

        lbl_comment.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        lbl_comment.setText("Comment");

        btn_registration.setFont(new java.awt.Font("Purisa", 1, 18)); // NOI18N
        btn_registration.setText("Registration");
        btn_registration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrationActionPerformed(evt);
            }
        });

        lbl_patient_name1.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        lbl_patient_name1.setText("Examen ID");

        _ExamenID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ExamenIDActionPerformed(evt);
            }
        });

        btn_clean.setFont(new java.awt.Font("Purisa", 1, 18)); // NOI18N
        btn_clean.setText("Clean");
        btn_clean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cleanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_RadPharmaDoseLayout = new javax.swing.GroupLayout(Panel_RadPharmaDose);
        Panel_RadPharmaDose.setLayout(Panel_RadPharmaDoseLayout);
        Panel_RadPharmaDoseLayout.setHorizontalGroup(
            Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_RadPharmaDoseLayout.createSequentialGroup()
                .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Panel_RadPharmaDoseLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lbl_patient_height, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(lbl_patient_weight, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                                                .addComponent(lbl_patient_age, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(lbl_patient_name, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(lbl_patient_name1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lbl_hostpital_name, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(lbl_indication, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbl_comment, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_Comment, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                            .addComponent(_Indication)
                            .addComponent(_HospitalName)
                            .addComponent(_ExamenID)
                            .addComponent(_PatientName)
                            .addComponent(_PatientAge)
                            .addComponent(_PatientWeight)
                            .addComponent(_PatientHeight)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_RadPharmaDoseLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_clean, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_registration, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        Panel_RadPharmaDoseLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lbl_comment, lbl_hostpital_name, lbl_indication, lbl_patient_age, lbl_patient_name});

        Panel_RadPharmaDoseLayout.setVerticalGroup(
            Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_RadPharmaDoseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_hostpital_name)
                    .addComponent(_HospitalName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_patient_name1)
                    .addComponent(_ExamenID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_patient_name)
                    .addComponent(_PatientName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_patient_age)
                    .addComponent(_PatientAge, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_patient_weight)
                    .addComponent(_PatientWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_patient_height)
                    .addComponent(_PatientHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_indication)
                    .addComponent(_Indication, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_comment)
                    .addComponent(_Comment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(Panel_RadPharmaDoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_registration)
                    .addComponent(btn_clean))
                .addContainerGap())
        );

        Panel_RadPharmaDoseLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lbl_comment, lbl_hostpital_name, lbl_indication, lbl_patient_age, lbl_patient_height, lbl_patient_name, lbl_patient_weight});

        TabbedPane_RadPharmaDose.addTab("Patient Registration", Panel_RadPharmaDose);

        jLabel8.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        jLabel8.setText("RadioIsotope");

        RadiopharmaceuticalList.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        jScrollPane1.setViewportView(RadiopharmaceuticalList);

        lbl_radiopharmaceutical.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        lbl_radiopharmaceutical.setText("RadioPharmaceutical product");

        RadioIsotopeList.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        RadioIsotopeList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Carbon-11", "Carbon-14", "Oxgyne-15", "Fluorine-18", "Galluim-67", "Galluim-68", "Selenium-75", "Technetium-99m", "Indium-111", "Iodine-123", "Iodine-131", "Thalluim-201" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        RadioIsotope_List.setViewportView(RadioIsotopeList);

        lbl_AdministredActivity.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        lbl_AdministredActivity.setText("Administred Activity");

        TextField_AdministredActivity.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        TextField_AdministredActivity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextField_AdministredActivityActionPerformed(evt);
            }
        });

        RadioButton_Mbq.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        RadioButton_Mbq.setText("Mbq");

        RadioButton_mCi.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        RadioButton_mCi.setText("mCi");

        lbl_PatientAgeDomain.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        lbl_PatientAgeDomain.setText("Patient Age domain");

        RadioButton_Adult.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        RadioButton_Adult.setText("Adult");

        RadioButton_10years.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        RadioButton_10years.setText("10 years");

        RadioButton_15years.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        RadioButton_15years.setText("15 years");

        RadioButton_5years.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        RadioButton_5years.setText("5 years");

        RadioButton_1year.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        RadioButton_1year.setText("1 year");
        RadioButton_1year.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioButton_1yearActionPerformed(evt);
            }
        });

        btn_showTable.setFont(new java.awt.Font("Purisa", 1, 18)); // NOI18N
        btn_showTable.setText("Show table");
        btn_showTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showTableActionPerformed(evt);
            }
        });

        btn_calculation.setFont(new java.awt.Font("Purisa", 1, 18)); // NOI18N
        btn_calculation.setText("Calculation");
        btn_calculation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_calculationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbl_PatientAgeDomain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_AdministredActivity, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(RadioButton_Adult, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(RadioButton_15years, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(RadioButton_10years, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(RadioButton_5years, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(RadioButton_1year, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btn_calculation, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_showTable, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbl_radiopharmaceutical, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(TextField_AdministredActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20)
                                        .addComponent(RadioButton_Mbq, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(RadioButton_mCi, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(RadioIsotope_List)
                                    .addComponent(jScrollPane1))))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {RadioButton_Mbq, RadioButton_mCi});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(RadioIsotope_List, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_radiopharmaceutical)
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(RadioButton_mCi)
                        .addComponent(RadioButton_Mbq)
                        .addComponent(TextField_AdministredActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_AdministredActivity))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_PatientAgeDomain, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RadioButton_Adult)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(RadioButton_10years)
                                .addComponent(RadioButton_15years)
                                .addComponent(RadioButton_5years)
                                .addComponent(RadioButton_1year)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(96, 96, 96)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_showTable)
                    .addComponent(btn_calculation))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {RadioButton_10years, RadioButton_15years, RadioButton_1year, RadioButton_5years, RadioButton_Adult});

        TabbedPane_RadPharmaDose.addTab("Radiopharmaceutical dose calculation", jPanel1);

        container.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        jScrollPane3.setViewportView(container);

        btn_edit.setFont(new java.awt.Font("Purisa", 1, 18)); // NOI18N
        btn_edit.setText("Edit");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        btn_export_as_pdf.setFont(new java.awt.Font("Purisa", 1, 18)); // NOI18N
        btn_export_as_pdf.setText("Export as PDF");
        btn_export_as_pdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_export_as_pdfActionPerformed(evt);
            }
        });

        btn_print.setFont(new java.awt.Font("Purisa", 1, 18)); // NOI18N
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE))
                    .addComponent(jSeparator3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_export_as_pdf, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_edit, btn_print});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_print)
                    .addComponent(btn_export_as_pdf)
                    .addComponent(btn_edit))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_edit, btn_export_as_pdf, btn_print});

        TabbedPane_RadPharmaDose.addTab("Medical Report", jPanel2);

        Menu_File.setText("File");
        Menu_File.setFont(new java.awt.Font("Kinnari", 1, 18)); // NOI18N
        Menu_File.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_FileActionPerformed(evt);
            }
        });

        MenuItem_Exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        MenuItem_Exit.setText("&Exit");
        MenuItem_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_ExitActionPerformed(evt);
            }
        });
        Menu_File.add(MenuItem_Exit);

        MenuBar.add(Menu_File);

        Menu_Tools.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Menu_Tools.setText("Tools");

        MenuItem_PatientDB.setText("Patients DB");
        MenuItem_PatientDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_PatientDBActionPerformed(evt);
            }
        });
        Menu_Tools.add(MenuItem_PatientDB);

        Menu_LookAndFeel.setText("Look And Feel");

        MenuItem_Acryl.setText("Acryl");
        MenuItem_Acryl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_AcrylActionPerformed(evt);
            }
        });
        Menu_LookAndFeel.add(MenuItem_Acryl);

        MenuItem_Aero.setText("Aero");
        MenuItem_Aero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_AeroActionPerformed(evt);
            }
        });
        Menu_LookAndFeel.add(MenuItem_Aero);

        MenuItem_Aluminium.setText("Aluminium");
        MenuItem_Aluminium.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_AluminiumActionPerformed(evt);
            }
        });
        Menu_LookAndFeel.add(MenuItem_Aluminium);

        MenuItem_Bernstein.setText("Bernstein");
        MenuItem_Bernstein.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_BernsteinActionPerformed(evt);
            }
        });
        Menu_LookAndFeel.add(MenuItem_Bernstein);

        MenuItem_HIFI.setText("Hifi");
        MenuItem_HIFI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_HIFIActionPerformed(evt);
            }
        });
        Menu_LookAndFeel.add(MenuItem_HIFI);

        MenuItem_Luna.setText("Luna");
        MenuItem_Luna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_LunaActionPerformed(evt);
            }
        });
        Menu_LookAndFeel.add(MenuItem_Luna);

        Menu_Tools.add(Menu_LookAndFeel);

        MenuBar.add(Menu_Tools);

        Menu_Help.setText("Help");

        MenuItem_About.setText("About");
        MenuItem_About.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_AboutActionPerformed(evt);
            }
        });
        Menu_Help.add(MenuItem_About);

        MenuBar.add(Menu_Help);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TabbedPane_RadPharmaDose)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TabbedPane_RadPharmaDose)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void MenuItem_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_ExitActionPerformed
     System.exit(0);
    }//GEN-LAST:event_MenuItem_ExitActionPerformed

    private void Menu_FileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_FileActionPerformed
        
        
        
    }//GEN-LAST:event_Menu_FileActionPerformed

    private void _PatientNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__PatientNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event__PatientNameActionPerformed

    private void _PatientAgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__PatientAgeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event__PatientAgeActionPerformed

    private void MenuItem_AcrylActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_AcrylActionPerformed
              SetLookAndFeel(_AcrylLookAndFeel);

        
        
        
    }//GEN-LAST:event_MenuItem_AcrylActionPerformed

    private void MenuItem_AeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_AeroActionPerformed
              SetLookAndFeel(_AeroLookAndFeel);
    }//GEN-LAST:event_MenuItem_AeroActionPerformed

    private void MenuItem_AluminiumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_AluminiumActionPerformed
              SetLookAndFeel(_AluminiumLookAndFeel);
    }//GEN-LAST:event_MenuItem_AluminiumActionPerformed

    private void RadioButton_1yearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioButton_1yearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RadioButton_1yearActionPerformed

    private void btn_registrationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrationActionPerformed

    PatientName         = _PatientName.getText();
    HospitalName        = _HospitalName.getText();
    Indication          = _Indication.getText();
    Comment             = _Comment.getText();
    ExamenID            =  Integer.parseInt(_ExamenID.getText());
    PatientAge          =  Integer.parseInt(_PatientAge.getText());
    PatientWeight       =  Double.parseDouble(_PatientWeight.getText());
    PatientHeight       =  Double.parseDouble(_PatientHeight.getText());
    
    System.out.println(PatientName);
    System.out.println(HospitalName);
    System.out.println(Indication);
    System.out.println(Comment);
    System.out.println(ExamenID);
    System.out.println(PatientAge);
    System.out.println(PatientWeight);
    System.out.println(PatientHeight);
    
   // 
    myRegistredPatientsData = new RegistredPatientsData();
    myRegistredPatientsData.RecordData(PatientName, PatientAge, PatientWeight, PatientHeight, ExamenID, Indication, Comment, HospitalName);
    myRegistredPatientsData.Set_DateNow();
  // 
    RecordPatientInfoInDB ();  
   
   //
    javax.swing.JOptionPane.showMessageDialog(null,"The patient data have been saved successfuly!"); 

        
        
        
    }//GEN-LAST:event_btn_registrationActionPerformed

    private void MenuItem_AboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_AboutActionPerformed
     java.awt.EventQueue.invokeLater(() -> {
         new about().setVisible(true);
     });
    }//GEN-LAST:event_MenuItem_AboutActionPerformed

    private void btn_showTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showTableActionPerformed
        // TODO add your handling code here:
   DoseTable myDoseTable = new DoseTable(array_data,RadiopharmaceuticalList.getSelectedValue()) ;
   myDoseTable.setVisible(true);
   GenerateMedicalRapport();
        
    }//GEN-LAST:event_btn_showTableActionPerformed

    private void _ExamenIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ExamenIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event__ExamenIDActionPerformed

    private void MenuItem_LunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_LunaActionPerformed
              SetLookAndFeel(_LunaLookAndFeel);
    }//GEN-LAST:event_MenuItem_LunaActionPerformed

    private void MenuItem_BernsteinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_BernsteinActionPerformed
              SetLookAndFeel(_BernsteinLookAndFeel);
    }//GEN-LAST:event_MenuItem_BernsteinActionPerformed

    private void TextField_AdministredActivityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextField_AdministredActivityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextField_AdministredActivityActionPerformed
    
    private void btn_calculationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_calculationActionPerformed
    
    GetSelectedPatientAgeDomain();
         
     String app_path="";
     try {
       app_path=GetJarContainingFolder(RadPharmaDoseMainWindow.class);
    
        }
	 catch (Exception ex) {
        }   
     
     SelectedRadioPharmaceutical=RadiopharmaceuticalList.getSelectedValue();
             
          
     this.ReadDataStratedWithSpecificLine(StartLine, EndLine,   app_path+"/data/radiopharmaceutical_data/"+SelectedRadioPharmaceutical+".rfm");

     UpdateDataAccordingToActivityValue(); 
     for ( int i=0; i< array_data.length; i++) System.out.println(array_data[i]);
     myDoseToOrganData = new DoseToOrganData();
     for (int i=0;i< array_data.length;i++) System.out.println("#############"+array_data[i]);
     myDoseToOrganData.RecordData(array_data);
     
     System.out.println("############# the data has been recored"
            );
     RecordEffectiveDoseValueInDB ();
    try {
        //
        WritePatientDataToSqliteDB();
          javax.swing.JOptionPane.showMessageDialog(null,"The calculation has been done, now click on Show Table button to view the absorbed dose to organs !"); 

    } catch (SQLException ex) {
        Logger.getLogger(RadPharmaDoseMainWindow.class.getName()).log(Level.SEVERE, null, ex);
    }
    
        
    }//GEN-LAST:event_btn_calculationActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        container.setEditable(true);
    }//GEN-LAST:event_btn_editActionPerformed

    private void MenuItem_PatientDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_PatientDBActionPerformed
        // TODO add your handling code here:
          java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new PatientsDBTable( _DBPath).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(RadPharmaDoseMainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });    
        
        
    }//GEN-LAST:event_MenuItem_PatientDBActionPerformed

    private void MenuItem_HIFIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_HIFIActionPerformed
              SetLookAndFeel(_HiFiLookAndFeel);
    }//GEN-LAST:event_MenuItem_HIFIActionPerformed

    private void btn_export_as_pdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_export_as_pdfActionPerformed
 
       String pdf_filename="MedicalRaport_"+ExamenID+".pdf"; 
        Document document = new Document();
      try
      {
         PdfWriter writer = PdfWriter.getInstance(document, new java.io.FileOutputStream(pdf_filename));
         document.open();
         document.add(new Paragraph(container.getText()));
         document.close();
         writer.close();
        javax.swing.JOptionPane.showMessageDialog(null,"The PDF file was successfuly created!"); 

      } catch (DocumentException e)
      {
         e.printStackTrace();
      } catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }    }//GEN-LAST:event_btn_export_as_pdfActionPerformed
    
    private void btn_cleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cleanActionPerformed
        // TODO add your handling code here:
     cleaning();  
            javax.swing.JOptionPane.showMessageDialog(null,"The cleaning was excuted successfuly!"); 

        
    }//GEN-LAST:event_btn_cleanActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
PrintPDF(pdf_filename);
    }//GEN-LAST:event_btn_printActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RadPharmaDoseMainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RadPharmaDoseMainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RadPharmaDoseMainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RadPharmaDoseMainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RadPharmaDoseMainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenuItem MenuItem_About;
    private javax.swing.JMenuItem MenuItem_Acryl;
    private javax.swing.JMenuItem MenuItem_Aero;
    private javax.swing.JMenuItem MenuItem_Aluminium;
    private javax.swing.JMenuItem MenuItem_Bernstein;
    private javax.swing.JMenuItem MenuItem_Exit;
    private javax.swing.JMenuItem MenuItem_HIFI;
    private javax.swing.JMenuItem MenuItem_Luna;
    private javax.swing.JMenuItem MenuItem_PatientDB;
    private javax.swing.JMenu Menu_File;
    private javax.swing.JMenu Menu_Help;
    private javax.swing.JMenu Menu_LookAndFeel;
    private javax.swing.JMenu Menu_Tools;
    private javax.swing.JPanel Panel_RadPharmaDose;
    private javax.swing.JRadioButton RadioButton_10years;
    private javax.swing.JRadioButton RadioButton_15years;
    private javax.swing.JRadioButton RadioButton_1year;
    private javax.swing.JRadioButton RadioButton_5years;
    private javax.swing.JRadioButton RadioButton_Adult;
    private javax.swing.JRadioButton RadioButton_Mbq;
    private javax.swing.JRadioButton RadioButton_mCi;
    private javax.swing.JList<String> RadioIsotopeList;
    private javax.swing.JScrollPane RadioIsotope_List;
    private javax.swing.JList<String> RadiopharmaceuticalList;
    private javax.swing.JTabbedPane TabbedPane_RadPharmaDose;
    private javax.swing.JTextField TextField_AdministredActivity;
    private javax.swing.JTextField _Comment;
    private javax.swing.JTextField _ExamenID;
    private javax.swing.JTextField _HospitalName;
    private javax.swing.JTextField _Indication;
    private javax.swing.JTextField _PatientAge;
    private javax.swing.JTextField _PatientHeight;
    private javax.swing.JTextField _PatientName;
    private javax.swing.JTextField _PatientWeight;
    private javax.swing.JButton btn_calculation;
    private javax.swing.JButton btn_clean;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_export_as_pdf;
    private javax.swing.JButton btn_print;
    private javax.swing.JButton btn_registration;
    private javax.swing.JButton btn_showTable;
    private javax.swing.ButtonGroup buttonGroupRadioActivityUnity;
    private javax.swing.ButtonGroup buttonGroup_PatientAgeDomain;
    private javax.swing.JEditorPane container;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lbl_AdministredActivity;
    private javax.swing.JLabel lbl_PatientAgeDomain;
    private javax.swing.JLabel lbl_comment;
    private javax.swing.JLabel lbl_hostpital_name;
    private javax.swing.JLabel lbl_indication;
    private javax.swing.JLabel lbl_patient_age;
    private javax.swing.JLabel lbl_patient_height;
    private javax.swing.JLabel lbl_patient_name;
    private javax.swing.JLabel lbl_patient_name1;
    private javax.swing.JLabel lbl_patient_weight;
    private javax.swing.JLabel lbl_radiopharmaceutical;
    // End of variables declaration//GEN-END:variables
}
