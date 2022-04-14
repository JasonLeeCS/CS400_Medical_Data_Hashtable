// --== CS400 Project Two File Header ==--
// Name: Richard Yang
// Email: ryang247@wisc.edu
// Team: Blue
// Group: CI
// TA: Harper
// Lecturer: Florian
// Notes to Grader:

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;


// interface (implemented with proposal)

interface medDataFrontEndInterface {
        public void run(MedDataBackEndInterface medDataEngine);

        // Here is a sample of the minimal set of options that 
        // this front end will support:

        // medData Command Menu:
        // 1. Insert New Patient into Database
        // 2. Search if a Patient Exists in the DataBase
        // 3. Find how many people are over a Certain Age
        // 4. Output a list of names and their corresponding BMI
        // 5. Quit
}

// public class (implemented primarily in final app week)

public class MedDataFrontEnd implements medDataFrontEndInterface {
        Scanner scnr = new Scanner(System.in);
         public static void main(String[] args) {
                MedDataBackEndInterface medDataEngine = new MedDataBackEnd();
                medDataFrontEndInterface medDataFrontEnd = new MedDataFrontEnd();
                medDataFrontEnd.run(medDataEngine);
        }
        @Override
        public void run(MedDataBackEndInterface medDataEngine) {
                mainMenu(medDataEngine);
                scnr.close();
                System.out.println("Goodbye!");
        }
        private void mainMenu(MedDataBackEndInterface medDataEngine) {
          int inputNumber = -1;
          boolean hasValidInput = false;

          do {
                  System.out.println("This Program is Case Sensitive");
                  System.out.println("1. Insert New Patient into Database");
                  System.out.println("2. Search if a Patient Exists in the DataBase");
                  System.out.println("3. Find how many people are over a Certain Age");
                  System.out.println("4. Output a list of names and their corresponding BMI");
                  System.out.println("5. Quit");

                  if (scnr.hasNextInt()) {
                          inputNumber = scnr.nextInt();

                          if (inputNumber > 5 || inputNumber < 1) {
                                  System.out.println("Error: Invalid Input");
                                  scnr.nextLine();
                          } else {
                                  scnr.nextLine();
                                  hasValidInput = true;
                          }
                  } else {
                          System.out.println("Error: Invalid Input");
                          scnr.nextLine();
                  }
          } while (!hasValidInput);

          if (inputNumber == 1) {
                  insertMedData(medDataEngine);
          } else if (inputNumber == 2) {
                  contains(medDataEngine);
          } else if (inputNumber == 3) {
                  overAge(medDataEngine);
          } else if (inputNumber == 4) {
                  outputBMI(medDataEngine);
          } else {
                  return;
          }

  }
        private void insertMedData(MedDataBackEndInterface medDataEngine) {
          boolean hasValidInput = false;
          int medAge = -1;
          String medName = "";
          double medWeight = -1;
          double medHeight = -1;


          do {
            System.out.println("Enter the Name of Patient: ");

            if (scnr.hasNext()) {
                    medName = scnr.nextLine().trim();
                    hasValidInput = true;
            } else {
                    System.out.println("Error: Invalid Input");
                    scnr.nextLine();
            }
    } while (!hasValidInput);

    hasValidInput = false;
    do {
            System.out.println("Enter the Height of Patient: ");

            if (scnr.hasNextDouble()) {
                    medHeight = scnr.nextDouble();
                    scnr.nextLine();
                    hasValidInput = true;
            } else {
                    System.out.println("Error: Invalid Input");
                    scnr.nextLine();
            }
    } while (!hasValidInput);

    hasValidInput = false;
    do {
            System.out.println("Enter the Weight of Patient: ");

            if (scnr.hasNextDouble()) {
                    medWeight = scnr.nextDouble();
                    scnr.nextLine();
                    hasValidInput = true;
            } else {
                    System.out.println("Error: Invalid Input");
                    scnr.nextLine();
            }
    } while (!hasValidInput);

    hasValidInput = false;
    do {
            System.out.println("Enter the Age of Patient: ");

            if (scnr.hasNextInt()) {
                    medAge = scnr.nextInt();
                    scnr.nextLine();
                    hasValidInput = true;
            } else {
                    System.out.println("Error: Invalid Input");
                    scnr.nextLine();
            }
    } while (!hasValidInput);

    hasValidInput = false;

    //TODO CHECK THAT ALL VALUES ARE INITIALIZED
    medDataEngine.addPatient(medAge, medName, medWeight, medHeight);
    System.out.println("Patient Sucessfully Added!");

    mainMenu(medDataEngine);
}

          private void overAge(MedDataBackEndInterface medDataEngine) {
                  boolean hasValidInput = false;
                  int overAge = -1;

                  do {
                          System.out.println("Enter a valid integer for Age: ");
                          if (scnr.hasNextInt()) {
                                  overAge = scnr.nextInt();
                                  scnr.nextLine();
                                  hasValidInput = true;
                          } else {
                                  System.out.println("Error: Invalid Input");
                                  scnr.nextLine();
                          }
                  } while (!hasValidInput);

                  try {
                          ArrayList<MedData> list = medDataEngine.peopleOverAge(overAge);

                          System.out.println("Results:");
                          for (int i = 0; i < list.size(); i++) {
                                  System.out.println("Name: "+ list.get(i).getName() +" Age: " +list.get(i).getAge() +" Weight: " + list.get(i).getWeight());
                          }
                  } catch (NoSuchElementException e) {
                          System.out.println("No Patients Found");
                  }

                  mainMenu(medDataEngine);
          }
          private void outputBMI(MedDataBackEndInterface medDataEngine) {
                  System.out.println(medDataEngine.outputBMIOverAge(0));
                  mainMenu(medDataEngine);
          }

          private void contains(MedDataBackEndInterface medDataEngine) {
                  boolean hasValidInput = false;
                  int containsAge = -1;
                  String containsName = "";
                  double containsWeight = 0.0;
                  double containsHeight = 0.0;
                  do {
                          System.out.println("Enter a Patient's Age: ");
                          if (scnr.hasNextInt()) {
                                  containsAge = scnr.nextInt();
                                  scnr.nextLine();
                                  hasValidInput = true;
                          } else {
                                  System.out.println("Error: Invalid Input");
                                  scnr.nextLine();
                          }} while (!hasValidInput);
                  hasValidInput = false;

                  do {
                          System.out.println("Enter a Patient's Name: ");
                          if (scnr.hasNext()) {
                                  containsName = scnr.next();
                                  scnr.nextLine();
                                  hasValidInput = true;
                          } else {
                                  System.out.println("Error: Invalid Input");
                                  scnr.nextLine();
                          }
                  }while (!hasValidInput);
                  hasValidInput = false;

                  do {
                          System.out.println("Enter a Patient's Height: ");
                          if (scnr.hasNextDouble()) {
                                  containsHeight = scnr.nextDouble();
                                  scnr.nextLine();
                                  hasValidInput = true;
                          } else {
                                  System.out.println("Error: Invalid Input");
                                  scnr.nextLine();
                          }
                  }while (!hasValidInput);
                          hasValidInput = false;

                  do {
                          System.out.println("Enter a Patient's Weight: ");
                          if (scnr.hasNextDouble()) {
                                  containsWeight = scnr.nextDouble();
                                  scnr.nextLine();
                                  hasValidInput = true;
                          } else {
                                  System.out.println("Error: Invalid Input");
                                  scnr.nextLine();
                          }
                  }while (!hasValidInput);
                  hasValidInput = false;

                  try {
                          if(medDataEngine.containsPatient(containsAge, containsName, containsWeight, containsHeight)) {
                                  System.out.println("Patient Exists");
                          }
                          else{
                                  System.out.println("Patient Does Not Exist");
                          }

                  } catch (IllegalArgumentException e) {
                          System.out.println("Invalid Input");
                  }

                  mainMenu(medDataEngine);
          }
        }
