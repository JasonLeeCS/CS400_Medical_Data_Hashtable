import java.io.FileNotFoundException;
import java.util.List;
// --== CS400 Project Two File Header ==--
// Name: Tom Rosen
// Email: trrosen@wisc.edu 
// Team: Blue
// Group: CI
// TA: Harper
// Lecturer: Florian Heimerl
// Notes to Grader: <none>
public class MedDataApp {
    public static void main(String[] args) {
        System.out.println("Welcome to the Medical Data Application");
        List<MedDataInterface> data = null;
        try {
            data = new MedDataLoader().loadAllFilesInDirectory("./data/");
        } catch (FileNotFoundException e) {
            System.out.println("Error: File Not Found");
        }
        MedDataBackEndInterface engine = new MedDataBackEnd();
        if (data == null) {
            System.out.println("Error in MedDataLoader.java");
            return;
        }
        for(MedDataInterface medDat : data)
        {
                engine.addPatient(medDat.getAge(), medDat.getName(), medDat.getWeight(), medDat.getHeight());
        }
        medDataFrontEndInterface ui = new MedDataFrontEnd();
        ui.run(engine);
    }
}