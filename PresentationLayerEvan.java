package HW.GroupProject;

public class PresentationLayerEvan {
    DataLayerEvan d1 = new DataLayerEvan();
    public PresentationLayerEvan(){
        d1.connect("root", "student", "iste330group4");


        d1.addTeachAcc("Tom", "Bombadil", "heyHo", "office visit", "email01@this.dontmatter", "0123456789", "forest", "a301");
        d1.addTeachAcc("James", "Bond", "shaken", "email", "007@this.dontmatter", "0070070070", "MI6", "007");
        
        d1.addStudAcc("jimmy", "john", "stud1", "phone", "email02@this.dontmatter", "987654321");
        d1.addStudAcc("johnny", "jim", "stud2", "phone", "email03@this.dontmatter", "123459876");
        
        d1.addPubAcc("john", "smith", "library", "none", "nunya", "beeswax");
    
    
    }

    public static void main(String [] args){
        new PresentationLayerEvan();  // Create a new object. An Instantiation
        System.out.println("EOJ");
        System.exit(0);
     } // End of main method
} // End of Class






















