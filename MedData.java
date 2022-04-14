// --== CS400 Project One File Header ==--
// Name: Jason Lee
// Email: jlee967@wisc.edu 
// Team: Blue
// Group: CI
// TA: C
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

interface MedDataInterface
{
  public String getName();
  public int getAge();
  public double getWeight();
  public double getHeight();
}

public class MedData implements MedDataInterface 
{
  public int age = -1;
  public String name = "";
  public double weight = -1;
  public double height = -1;
  public boolean isBlack;
  public MedData parent; // null for root node
  public MedData leftChild;
  public MedData rightChild;
  public MedData next;

  public MedData(int age, String name, double weight, double height)
  {
    this.age = age;
    this.name = name;
    this.weight = weight;
    this.height = height;
    isBlack = false;
  }

  /**
   * @return true when this node has a parent and is the left child of that
   *         parent, otherwise return false
   */
  public boolean isLeftChild()
  {
    return parent != null && parent.leftChild == this;
  }


  public void setName(String name)
  {
          this.name = name;
  }

  public void setAge(int age)
  {
          this.age = age;
  }

  public void setWeight(double weight)
  {
          this.weight = weight;
  }

  public void setHeight(double height)
  {
          this.height = height;
  }
  public boolean hasNext()
  {
          return next != null;
  }

  public MedData getNext()
  {
          return next;
  }

  public void setNext(MedData node)
  {
          next = node;
  }
  /**
   * Gets the name of the patient
   * 
   * @return returns the name of the patient
   */
  @Override
  public String getName() 
  {
    return this.name;
  }

  /**
   * Gets the age of the patient
   * 
   * @return returns the age of the patient
   */
  @Override
  public int getAge()
  {
    return this.age;
  }

  /**
   * Gets the weight of the patient
   * 
   * @return returns the weight of the patient
   */
  @Override
  public double getWeight()
  {
    return this.weight;
  }

  /**
   * Get height of patient
   * 
   * @return returns the height of the patient
   */
  @Override
  public double getHeight()
  {
    return this.height;
  }
}
