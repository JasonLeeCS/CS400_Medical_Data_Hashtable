import java.util.ArrayList;
// --== CS400 Project Two File Header ==--
// Name: Darren Seubert
// Email: dpsuebert@wisc.edu
// Team: Blue
// Group: CI
// TA: Harper
// Lecturer: Florian
// Notes to Grader:
/**
 * 
 */
interface MedDataBackEndInterface {
    public boolean addPatient(int age, String name, double weight, double height);
    public boolean containsPatient(int age, String name, double weight, double height) throws IllegalArgumentException;
    public void rotate(MedData child, MedData parent) throws IllegalArgumentException;
    public void enforceRBTreePropertiesAfterInsert(MedData insertedRedNode);
    public ArrayList<MedData> peopleOverAge(int age);
    public int numberOfPeopleOverAge(int age);
    public String outputBMIOverAge(int age);
    public int size();
    public boolean isEmpty();
}
/**
 * 
 */
public class MedDataBackEnd implements MedDataBackEndInterface {
    public MedData root; // reference to root node of tree, null when empty
    public int size = 0; // the number of values in the tree
    /**
     * 
     */
    @Override
    public boolean addPatient(int age, String name, double weight, double height) {
        // null references cannot be stored within this tree
        if (age < 0 || name == null || name == "" || weight < 0 || height < 0) {
            System.out.println("Error: Age, Name, Weight, or Height Not Set Properly");
            return false;
        }
        MedData newNode = new MedData(age, name, weight, height);
        if (root == null) {
            root = newNode;
            size++;
            root.isBlack = true;
            return true;
        } else { // add first node to an empty tree
            boolean returnValue = addPatientHelper(newNode, root); // recursively insert into subtree
            if (returnValue) {
                size++;
            } else {
                System.out.println("Error: Patient is Already in System");
            }
            root.isBlack = true;
            return returnValue;
        }
    }
    /**
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree by
     * the newNode in that position.
     * 
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the newNode
     *                should be inserted as a descenedent beneath
     * @return true is the value was inserted in subtree, false if not
     */
    private boolean addPatientHelper(MedData newNode, MedData subtree) {
        // do not allow duplicate values to be stored within this tree
        if (newNode.age == subtree.age && newNode.name.equals(subtree.name) && newNode.weight == subtree.weight) {
            return false;
        } else if (newNode.age < subtree.age) { // store newNode within left subtree of subtree
            if (subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
            } else { // otherwise continue recursive search for location to insert
                return addPatientHelper(newNode, subtree.leftChild);
            }
        } else { // store newNode within the right subtree of subtree
            if (subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
            } else { // otherwise continue recursive search for location to insert
                return addPatientHelper(newNode, subtree.rightChild);
            }
        }
    }
    /**
     * 
     */
    @Override
    public boolean containsPatient(int age, String name, double weight, double height) {
        if (age < 0 || name == null || name == "" || weight < 0 || height < 0) {
            throw new IllegalArgumentException("Error: Age, Name, or Weight Entered not Valid");
        }
        return this.containsPatientHelper(age, name, weight, height, root);
    }
    /**
     * Recursive helper method that recurses through the tree and looks for the
     * value *data*.
     * 
     * @param data    the data value to look for
     * @param subtree the subtree to search through
     * @return true of the value is in the subtree, false if not
     */
    private boolean containsPatientHelper(int age, String name, double weight, double height, MedData subtree) {
        if (subtree == null) { // we are at a null child, value is not in tree
            return false;
        } else {
            if (age == subtree.age && name.equals(subtree.name) && weight == subtree.weight) { // we found it :)
                return true;
            } else if (age < subtree.age) { // go left in the tree
                return containsPatientHelper(age, name, weight, height, subtree.leftChild);
            } else {
                return containsPatientHelper(age, name, weight, height, subtree.rightChild);
             }
         }
     }
    /**
     * Performs the rotation operation on the provided nodes within this tree. When
     * the provided child is a leftChild of the provided parent, this method will
     * perform a right rotation. When the provided child is a rightChild of the
     * provided parent, this method will perform a left rotation. When the provided
     * nodes are not related in one of these ways, this method will throw an
     * IllegalArgumentException.
     * 
     * @param child  is the node being rotated from child to parent position
     *               (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *               (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent node
     *                                  references are not initially (pre-rotation)
     *                                  related that way
     */
    @Override
    public void rotate(MedData child, MedData parent) throws IllegalArgumentException {
        // Make sure the child and parent are not null
        if (child == null || parent == null) {
            throw new IllegalArgumentException();
        }
        // Make sure the child and parent have the correct relationship
        if ((child.parent != parent) || ((parent.leftChild != child) && (parent.rightChild != child))) {
            throw new IllegalArgumentException();
        }
        if (child.isLeftChild()) { // child is leftChild of the parent, do right rotation
            if (root == parent) { // If the parent is root, let the child to be root
                root = child;
            }
            MedData tmp = child.rightChild;
            child.rightChild = parent;
            parent.leftChild = tmp;
            child.parent = parent.parent;
            parent.parent = child;
            if (root != child) {
                if (child.parent.leftChild == parent) {
                    child.parent.leftChild = child;
                } else {
                    child.parent.rightChild = child;
                }
            }
        } else { // child is rightChild of the parent, do left rotation
            if (root == parent) { // If the parent is root, let the child to be root
                root = child;
            }
            MedData tmp = child.leftChild;
            child.leftChild = parent;
            parent.rightChild = tmp;
            child.parent = parent.parent;
            parent.parent = child;
            if (root != child) {
                if (child.parent.leftChild == parent) {
                    child.parent.leftChild = child;
                } else {
                    child.parent.rightChild = child;
                }
            }
        }
    }
    /**
     * Method that enforces the Red-Black Tree Properties after the given RED node
     * is inserted into the tree
     * 
     * @param insertedRedNode Red node that was just inserted into the tree
     */
    public void enforceRBTreePropertiesAfterInsert(MedData insertedRedNode) {
        MedData parentOfInserted = insertedRedNode.parent;
        // Root found or Insertion Case 1: return
        if (parentOfInserted == null || parentOfInserted.parent == null || parentOfInserted.isBlack) {
            return;
        }
        MedData grandparentOfInserted = parentOfInserted.parent;
        // Insertion Case 2: Get Uncle
        MedData uncleOfInserted;
        if (parentOfInserted.isLeftChild()) { // Parent isLeftChild
            uncleOfInserted = grandparentOfInserted.rightChild;
        } else { // Parent isRightChild
            uncleOfInserted = grandparentOfInserted.leftChild;
        }
        // Repair Case 2: Uncle is RED
        if (uncleOfInserted != null && uncleOfInserted.isBlack == false) {
            parentOfInserted.isBlack = true;
            grandparentOfInserted.isBlack = false;
            uncleOfInserted.isBlack = true;
            enforceRBTreePropertiesAfterInsert(grandparentOfInserted);
            // Repair Case 1: Uncle is BLACK or null
        } else if (parentOfInserted.isLeftChild()) { // Parent isLeftChild
            if (!insertedRedNode.isLeftChild()) { // Double rotate if needed
                rotate(insertedRedNode, parentOfInserted);
                parentOfInserted = insertedRedNode;
            }
            rotate(parentOfInserted, grandparentOfInserted);
            // Recolor
            parentOfInserted.isBlack = true;
            grandparentOfInserted.isBlack = false;
        } else { // Parent isRightChild
            if (insertedRedNode.isLeftChild()) { // Double rotate if needed
                rotate(insertedRedNode, parentOfInserted);
                parentOfInserted = insertedRedNode;
            }
            rotate(parentOfInserted, grandparentOfInserted);
            // Recolor
            parentOfInserted.isBlack = true;
            grandparentOfInserted.isBlack = false;
        }
    }
    /**
     * Search for people given a minimum age
     * 
     * @param age The minimum allowed age
     * @return List of people over a the given age
     */
    public ArrayList<MedData> peopleOverAge(int age) {
        ArrayList<MedData> list = new ArrayList<MedData>();
        peopleOverAgeHelper(age, root, list);
        return list;
    }
    /**
     * Search for people given a minimum age
     * 
     * @param minAge The mimimum allowed age
     *     * @return Integer of how many people were found in the tree
    */
   @Override
   public int numberOfPeopleOverAge(int age) {
       ArrayList<MedData> list = new ArrayList<MedData>();
       peopleOverAgeHelper(age, root, list);
       return list.size();
   }
   /**
    * Recursive helper method for numberOfPeopleOverAge()
    * 
    * @param minAge The minimum age we are looking to count
    * @param current The current root node of the subtree
    * @param list The list that contains all the nodes >= minAge
    */
   private void peopleOverAgeHelper(int age, MedData current, ArrayList<MedData> list) {
       if (!(current == null)) { // End of the branch of the tree if statement returns false
           if (current.age > age) { // Checks if current has correct parameters
               list.add(current);
           }
           peopleOverAgeHelper(age, current.leftChild, list); // Traverses left
           peopleOverAgeHelper(age, current.rightChild, list); // Traverses right
       }
   }
   /**
    * Method that calculates the BMIs for patients over the given age
    * 
    * @param age The minimum age allowed
    * @return String of output for the patients and their BMIs
    */
   @Override
   public String outputBMIOverAge(int age) {
       ArrayList<MedData> patientNodes = peopleOverAge(age);
       ArrayList<Double> BMIs = new ArrayList<Double>();
       double currentWeight = 0;
       double currentHeight = 0;
       double currentBMI = 0;
       String outputString = "";
       for (int i = 0; i < patientNodes.size(); i++) {
           currentWeight = patientNodes.get(i).weight;
           currentHeight = patientNodes.get(i).height;
           currentBMI = (currentWeight / Math.pow(currentHeight, 2)) * 703.0;

           BMIs.add(currentBMI);
           outputString = outputString + patientNodes.get(i).name + " Age: " +
                   patientNodes.get(i).age + " BMI: " + BMIs.get(i) + "\n";
        }
       if (outputString.equals("")) {
           outputString = "None Found";
       }
       return outputString;
   }
   /**
    * Get the size of the tree (its number of nodes).
    * 
    * @return the number of nodes in the tree
    */
   @Override
   public int size() {
       return size;
   }
   /**
    * Method to check if the tree is empty (does not contain any node).
    * 
    * @return true of this.size() return 0, false if this.size() > 0
    */
   @Override
   public boolean isEmpty() {
       return this.size() == 0;
   }
}
