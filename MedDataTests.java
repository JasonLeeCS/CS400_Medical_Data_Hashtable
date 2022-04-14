// --== CS400 File Header Information ==--
// Name: Tom Rosen
// Email: trrosen@wisc.edu
// Team: CI - Blue
// TA: Harper
// Lecturer: Florian
// Notes to Grader:
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import org.junit.platform.console.ConsoleLauncher;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.function.Executable;
public class MedDataTests {
    /**
     * Method that tests addPatient() in MedDataBackEnd.java
     * 
     * @author Darren Seubert, Backend Developer
     */
    @Test
    public void backend_AddPatientTest() {
        MedDataBackEnd medDataBackEnd = new MedDataBackEnd();
        assertEquals(false, medDataBackEnd.addPatient(-1, "P0", 50.0, 25.0));
        assertEquals(false, medDataBackEnd.addPatient(5, null, 75.0, 12.2));
        assertEquals(false, medDataBackEnd.addPatient(7, "", 80.0, 15.0));
        assertEquals(false, medDataBackEnd.addPatient(10, "P0", -1, 45.0));
        assertEquals(null, medDataBackEnd.root);
        assertEquals(true, medDataBackEnd.addPatient(2, "P1", 15, 25.0));
        assertEquals(false, medDataBackEnd.addPatient(2, "P1", 15, 25.0));
        assertEquals(false, medDataBackEnd.root == null);
        assertEquals(true, medDataBackEnd.addPatient(20, "P2", 180.0, 72.0));
        assertEquals(true, medDataBackEnd.addPatient(22, "P2", 180.0, 70.0));
        assertEquals(true, medDataBackEnd.addPatient(20, "P2", 185.0, 69.0));
        assertEquals(true, medDataBackEnd.addPatient(20, "P3", 180.0, 65.0));
    }
     /**
     * Method that tests Insertion Case 2, Repair Case 1 Parent isRightChild and Double Rotate
     *  needed from Lecture
     * 
     * @author Darren Seubert, Backend Developer
     */
    @Test
    public void backend_RepairCase1RightChildDoubleRotateTest() {
        MedDataBackEnd medDataBackEnd = new MedDataBackEnd();
        medDataBackEnd.addPatient(5, "P1", 49.0, 25.0);
        medDataBackEnd.addPatient(7, "P2", 63.0, 35.0);
        medDataBackEnd.addPatient(6, "P3", 50.0, 30.0);

        assertEquals(6, medDataBackEnd.root.age);
        assertEquals(5, medDataBackEnd.root.leftChild.age);
        assertEquals(7, medDataBackEnd.root.rightChild.age);
        assertEquals(true, medDataBackEnd.root.isBlack);
        assertEquals(false, medDataBackEnd.root.leftChild.isBlack);
        assertEquals(false, medDataBackEnd.root.rightChild.isBlack);

        medDataBackEnd.addPatient(9, "P4", 70.0, 56.0);
        medDataBackEnd.addPatient(8, "P5", 65.0, 50.0);

        assertEquals(6, medDataBackEnd.root.age);
        assertEquals(5, medDataBackEnd.root.leftChild.age);
        assertEquals(8, medDataBackEnd.root.rightChild.age);
        assertEquals(7, medDataBackEnd.root.rightChild.leftChild.age);
        assertEquals(9, medDataBackEnd.root.rightChild.rightChild.age);
        assertEquals(true, medDataBackEnd.root.isBlack);
        assertEquals(true, medDataBackEnd.root.leftChild.isBlack);
        assertEquals(true, medDataBackEnd.root.rightChild.isBlack);
        assertEquals(false, medDataBackEnd.root.rightChild.leftChild.isBlack);
        assertEquals(false, medDataBackEnd.root.rightChild.rightChild.isBlack);
    }

    /**
     * Method that tests numberOfPeopleOverAge() in MedDataBackEnd.java
     * 
     * @author Darren Seubert, Backend Developer
     */
    @Test
    public void backend_NumberOfPeopleOverAgeTest() {
        MedDataBackEnd medDataBackEnd = new MedDataBackEnd();
        medDataBackEnd.addPatient(33, "P1", 250, 70.0);
        medDataBackEnd.addPatient(47, "P2", 140, 68.0);
        medDataBackEnd.addPatient(80, "P3", 175, 65.0);
        medDataBackEnd.addPatient(65, "P4", 180, 67.0);
        medDataBackEnd.addPatient(72, "P5", 225, 57.0);
        medDataBackEnd.addPatient(15, "P6", 100, 68.0);
        medDataBackEnd.addPatient(27, "P7", 200, 65.0);
        medDataBackEnd.addPatient(52, "P8", 160, 75.3);
        medDataBackEnd.addPatient(72, "P9", 170, 68.2);
        medDataBackEnd.addPatient(11, "P10", 90, 55.0);
        medDataBackEnd.addPatient(50, "P11", 230, 65.0);
        medDataBackEnd.addPatient(100, "P12", 150, 53.0);

        assertEquals(12, medDataBackEnd.numberOfPeopleOverAge(0));
        assertEquals(6, medDataBackEnd.numberOfPeopleOverAge(50));
        assertEquals(0, medDataBackEnd.numberOfPeopleOverAge(100));

        medDataBackEnd.addPatient(72, "P13", 180, 54.3);
        medDataBackEnd.addPatient(11, "P14", 75, 67.0);
        medDataBackEnd.addPatient(50, "P15", 200, 65.0);
        medDataBackEnd.addPatient(101, "P16", 140, 68.2);

        assertEquals(16, medDataBackEnd.numberOfPeopleOverAge(0));
        assertEquals(8, medDataBackEnd.numberOfPeopleOverAge(50));
        assertEquals(1, medDataBackEnd.numberOfPeopleOverAge(100));
    }

    public static void main(String[] args) {
        String className = MethodHandles.lookup().lookupClass().getName();
        String classPath = System.getProperty("java.class.path").replace(" ", "\\ ");
        String[] arguments = new String[] {
                "-cp",
                classPath,
                "--include-classname=.*",
                "--select-class=" + className
        };
//        ConsoleLauncher.main(arguments);
  }
    
    // Data Wrangler Code Tests
    
    /**
     *  Tests that the file is loaded
     *  
     *  @author Jason Lee, Data Wrangler
     */
    @Test
    public void dataWrangler_TestFileSize()
    {
        MedDataLoader loader = new MedDataLoader();
        
        boolean error = false;
        List <MedDataInterface> ls = null;
        try
        {
          ls = loader.loadAllFilesInDirectory("./data/");
        }
        catch(FileNotFoundException e)
        {
          error = true;
        }
        assertFalse(error);
    }

    /**
     *  Tests that the first value loaded is correct
     *  
     *  @author Jason Lee, Data Wrangler
     */
    @Test
    public void dataWrangler_TestInitialValue()
    {
        MedDataLoader loader = new MedDataLoader();

        boolean error = false;
        List <MedDataInterface> ls = null;
        try
        {
                ls = loader.loadAllFilesInDirectory("./data/");

        }
        catch(FileNotFoundException e)
        {
                error = true;
        }
        assertEquals(51, ls.get(0).getAge());
    }
    
    
    /**
     *  Tests that the last value loaded is correct
     *  
     *  @author Jason Lee, Data Wrangler
     */
    @Test
    public void dataWrangler_TestLastValue()
    {
      MedDataLoader loader = new MedDataLoader();
      
      boolean error = false;
      List <MedDataInterface> ls = null;
      try
      {
        ls = loader.loadAllFilesInDirectory("./data/");
      }
      catch(FileNotFoundException e)
      {
        error = true;
      }
      
      assertEquals(61, ls.get(0).getAge());
    }
}