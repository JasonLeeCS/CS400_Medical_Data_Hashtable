import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

// --== CS400 Project One File Header ==--
// Name: Jason Lee
// Email: jlee967@wisc.edu 
// Team: Blue
// Group: CI
// TA: C
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

interface MedDataLoaderInterface
{
  public List<MedDataInterface> loadFile(String csvFilePath) throws FileNotFoundException;
  public List<MedDataInterface> loadAllFilesInDirectory(String directoryPath)
      throws FileNotFoundException;
}

public class MedDataLoader implements MedDataLoaderInterface
{
  public List<String> list1 = new ArrayList<>();


  @Override
  public List<MedDataInterface> loadFile(String csvFilePath) throws FileNotFoundException
  {
    String line = "";
    int name = -1;
    int age = -1;
    int weight = -1;
    int height = -1;

    List <MedDataInterface> list = new ArrayList<MedDataInterface>();
    try
    {
      BufferedReader br = new BufferedReader(new FileReader(csvFilePath));

      if((line = br.readLine()) != null)
      {
        String [] line1 = line.split(",");
        for(int j = 0; j < line1.length; j++)
        {
//              System.out.println(line1[j]);
        }
        for(int i = 0; i < line1.length; i++)
        {
          if(line1[i].equals("\"name\"") || line1[i].equals("name"))
          {
            name = i;
//            System.out.println(name + "name here");
          }
          else if(line1[i].equals("\"age\"") || line1[i].equals("age"))
          {
            age = i;
           // System.out.println(age + "age here");
          }
          else if(line1[i].equals("\"weight\"") || line1[i].equals("weight"))
          {
            weight = i;
          }
          else if(line1[i].equals("\"height\"") || line1[i].equals("height"))
          {
            height = i;
           // System.out.println(height + "height here");
          }
        }
      }
      
      if(name == -1|| age == -1 || weight == -1 || height == -1)
      {
        throw new FileNotFoundException("CSV file not accepted type");
      }

      while((line = br.readLine()) != null)
      {
        String[] val = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        String nameHolder = "";
        Double weightHolder = 0.0;
        Double heightHolder = 0.0;
        MedData values = new MedData(age, nameHolder, weightHolder, heightHolder);
        values.setName(val[name].replaceAll("\"",""));

        String ageStr = val[age].replaceAll("\"","");
        int patientAge = Integer.parseInt(ageStr);
        values.setAge(patientAge);

        String weightStr = val[weight].replaceAll("\"","");
        double patientWeight = Double.parseDouble(weightStr);
        values.setWeight(patientWeight);

        String heightStr = val[height].replaceAll("\"","");
        double patientHeight = Double.parseDouble(heightStr);
        values.setHeight(patientHeight);

        list.add(values);

        System.out.println(values.getAge() + " " + values.getName() + " " + values.getWeight() + " " + values.getHeight());

      }

      br.close();
    }

    
    catch(FileNotFoundException e)
    {
      System.out.println("File not found when using LoadFile");
      e.printStackTrace();
    }
    catch(IOException e2)
    {
      System.out.println("IOException when using LoadFile");
      e2.printStackTrace();
    }

    return list;
  }@Override
  public List<MedDataInterface> loadAllFilesInDirectory(String directoryPath) throws FileNotFoundException
  {
    // TODO Auto-generated method stub
    File folder = new File(directoryPath);
    File [] fileList = folder.listFiles();

    List <MedDataInterface> ls = new ArrayList<MedDataInterface>();

    for(File f : fileList)
    {
      if(f.isFile())
      {
        List <MedDataInterface> temp = new ArrayList<MedDataInterface>();
        temp = loadFile(f.getAbsolutePath());

        if(temp == null)
        {
                return temp;
        }
        ls.addAll(temp);
      }
    }
    return ls;
  }
  public static void main(String [] args)
  {
        MedDataLoader ml = new MedDataLoader();
        try
        {
                ml.loadFile("D://School Work//Eclipse Work//ProjectTwo//src//Book2.csv");

        }
        catch (FileNotFoundException e)
        {
                System.out.println("cant find");
        }
  }
}
