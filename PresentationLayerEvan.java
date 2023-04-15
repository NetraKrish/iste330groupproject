package HW.GroupProject;

public class PresentationLayerEvan {
    DataLayerEvan d1 = new DataLayerEvan();
    public PresentationLayerEvan(){
        d1.connect("root", "student", "iste330group4");

        // test data(already added to the database). KEEP THIS BECAUSE PASSWORDS ARE HASHED
        // d1.addTeachAcc("Tom", "Bombadil", "heyHo", "office visit", "email01@this.dontmatter", "0123456789", "forest", "a301");
        // d1.addTeachAcc("James", "Bond", "shaken", "email", "007@this.dontmatter", "0070070070", "MI6", "007");
        
        //d1.addStudAcc("jimmy", "john", "stud1", "phone", "email02@this.dontmatter", "987654321");
        //d1.addStudAcc("johnny", "jim", "stud2", "phone", "email03@this.dontmatter", "123459876");
        
        //d1.addPubAcc("john", "smith", "library", "none", "nunya", "beeswax");
        // System.out.println(d1.accID("Tom", "Bombadil", "heyHo"));
        // System.out.println(d1.accID("James", "Bond", "shaken"));
        // System.out.println(d1.accID("jimmy", "john", "stud1"));
        // System.out.println(d1.accID("johnny", "jim", "stud2"));
        // System.out.println(d1.accID("john", "smith", "library"));
        //d1.editContact(1, "changed", "YAY");
        //d1.editContact(1, "email01@this.dontmatter", "0123456789");
        //d1.editOffice(1, "changed", "YAY");
        //d1.editOffice(1, "forest", "a301");
        //d1.editAcc(1, "IT", "WORKED", "YAY");
        //d1.editAcc(1,"Tom", "Bombadil", "office visit");
        //d1.editPas(1, "stud1");
        //d1.editPas(1, "heyHo");
        //HH88P6Zmmgb4Dop8pwwvlzSGWmgNFesSwo0Vow6xJd0=
    
    
    }

    public static void main(String [] args){
        new PresentationLayerEvan();  // Create a new object. An Instantiation
        System.out.println("EOJ");
        System.exit(0);
     } // End of main method
} // End of Class






















