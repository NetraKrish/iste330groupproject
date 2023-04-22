import objects.*;

import java.util.List;
import java.util.Scanner;

public class iste330Group4PresentationLayer {

    private iste330Group4DataLayer dl;
    private Scanner reader;

    private Account account;

    public iste330Group4PresentationLayer() {

        this.dl = new iste330Group4DataLayer();
        this.reader = new Scanner(System.in);
        this.account = null;
    }

    /***********************
     * Output Helper Methods
     ***********************/

    public void sendError(String e) {

        System.out.println("[!] Error:\n>>> " + e);
    }

    public void sendError(String type, String e) {

        System.out.println("[!] " + type + ":\n>>> " + e);
    }

    public void sendMsg(String msg) {

        System.out.println("[*] " + msg);
    }

    public void sendMsg(String key, String msg) {

        System.out.println("[" + key + "] " + msg);
    }

    public void invalidCmd() {

        sendError("Invalid Choice", "Please choose a listed choice!");
    }

    public void lineBreak() {

        System.out.print("\n");
    }

    /*****************
     * Database Setup
     *****************/

    public void run() {

        lineBreak();

        sendMsg("SETUP", "Database Connection");

        sendMsg("Username: ");
        String user = this.reader.nextLine();

        sendMsg("Password: ");
        String pass = this.reader.nextLine();

        sendMsg("Database (TYPE: 'iste330group4'): ");
        String db = this.reader.nextLine();

        lineBreak();

        if(this.dl.connect(user, pass, db)) {

            loginMenu();
            mainMenu();
        }else {

            sendError("Database Failed to Connect!");
            run();
        }

    }

    /****************
     * MENUS SECTION
     ****************/

    /**
     * LOGIN MENU
     */
    public void loginMenu() {

        boolean loggedIn = false;

        while(!loggedIn) {

            lineBreak();

            sendMsg("Login Menu");
            sendMsg("0", "Exit");
            sendMsg("1", "Login");
            sendMsg("2", "Create Account");

            lineBreak();

            sendMsg("Choice: ");

            switch (this.reader.nextLine().trim()) {

                case "0":

                    exitProgram();
                    break;

                case "1":

                    loggedIn = login();
                    break;

                case "2":

                    createAccount();
                    break;

                default: invalidCmd();
            }
        }
    }

    /**
     * MAIN MENU
     */
    public void mainMenu() {

        while(true) {

            lineBreak();

            sendMsg("Main Menu");
            sendMsg("0", "Logout");
            sendMsg("1", "Account Settings");
            sendMsg("2", "Search By Interests");
            sendMsg("3", "Search By Name");
            sendMsg("4", "Search By Faculty Abstract");

            lineBreak();

            sendMsg("Choice: ");

            switch (this.reader.nextLine().trim()) {

                case "0":

                    loginMenu();
                    break;

                case "1":

                    accountSettingsMenu();
                    break;

                case "2":

                    interestSearchMenu();
                    break;

                case "3":

                    nameSearchMenu();
                    break;

                case "4":

                    searchFacultyAbstracts();
                    break;

                default: invalidCmd();
            }
        }
    }

    /**
     * ACCOUNT MENU
     */
    public void accountSettingsMenu() {

        boolean back = false;

        while(!back) {

            lineBreak();

            sendMsg("Account Settings Menu");
            sendMsg("0", "Back to Main Menu");
            sendMsg("1", "Account Actions");
            sendMsg("2", "Interest Actions");

            if(this.account.getRoleID() == 2) {

                sendMsg("3", "Abstract Actions");
            }

            lineBreak();

            sendMsg("Choice: ");

            switch(this.reader.nextLine().trim()) {

                case "0":

                    back = true;
                    break;

                case "1":

                    accountMenu();
                    break;

                case "2":

                    interestMenu();
                    break;

                case "3":

                    if(this.account.getRoleID() != 2) {
                        invalidCmd();
                        break;
                    }

                    facultyAbstractMenu();
                    break;

                default: invalidCmd();
            }
        }
    }

    public void accountMenu() {

        boolean back = false;

        while(!back) {

            lineBreak();

            sendMsg("Account Menu");
            sendMsg("0", "Back to Account Settings Menu");
            sendMsg("1", "View Account Info");
            sendMsg("2", "Change Password");
            sendMsg("3", "Edit Account");
            sendMsg("4", "Edit Contact Info");

            if(this.account.getRoleID() == 2) {

                sendMsg("5", "Edit Location Info");
            }

            lineBreak();

            sendMsg("Choice: ");

            switch(this.reader.nextLine().trim()) {

                case "0":

                    back = true;
                    break;

                case "1":

                    showAccountInfo();
                    break;

                case "2":

                    updatePassword();
                    break;

                case "3":

                    updateAccount();
                    break;

                case "4":

                    updateContact();
                    break;

                case "5":

                    if(this.account.getRoleID() != 2) {
                        invalidCmd();
                        break;
                    }

                    updateLocation();
                    break;

                default: invalidCmd();
            }
        }
    }

    /**
     * INTEREST MENU
     */
    public void interestMenu() {

        boolean back = false;

        while(!back) {

            lineBreak();

            sendMsg("Interest Menu");
            sendMsg("0", "Back to Account Settings Menu");
            sendMsg("1", "View Interests");
            sendMsg("2", "Add an Interest");
            sendMsg("3", "Remove an Interest");

            lineBreak();

            sendMsg("Choice: ");

            switch(this.reader.nextLine().trim()) {

                case "0":

                    back = true;
                    break;

                case "1":

                    showInterests();
                    break;

                case "2":

                    addInterest();
                    break;

                case "3":

                    removeInterest();
                    break;

                default: invalidCmd();
            }
        }
    }

    /**
     * FACULTY ABSTRACT MENU
     */
    public void facultyAbstractMenu() {

        boolean back = false;

        while(!back) {

            lineBreak();

            sendMsg("Abstract Menu");
            sendMsg("0", "Back to Account Settings Menu");
            sendMsg("1", "View Abstracts");
            sendMsg("2", "Add Abstract");
            sendMsg("3", "Remove Abstract");

            lineBreak();

            sendMsg("Choice: ");

            switch(this.reader.nextLine().trim()) {

                case "0":

                    back = true;
                    break;

                case "1":

                    showAbstracts();
                    break;

                case "2":

                    addAbstract();
                    break;

                case "3":

                    removeAbstract();
                    break;

                default: invalidCmd();
            }
        }
    }

    /**
     * INTEREST SEARCH MENU
     */
    public void interestSearchMenu() {

        boolean back = false;

        while(!back) {

            lineBreak();

            sendMsg("Interest Search Menu");
            sendMsg("0", "Back to Main Menu");
            sendMsg("1", "Search Students");
            sendMsg("2", "Search Faculty");

            lineBreak();

            sendMsg("Choice: ");

            switch(this.reader.nextLine().trim()) {

                case "0":

                    back = true;
                    break;

                case "1":

                    searchStudentInterests();
                    break;

                case "2":

                    searchFacultyInterests();
                    break;

                default: invalidCmd();
            }
        }
    }

    /**
     * NAME SEARCH MENU
     */
    public void nameSearchMenu() {

        boolean back = false;

        while(!back) {

            lineBreak();

            sendMsg("Name Search Menu");
            sendMsg("0", "Back to Main Menu");
            sendMsg("1", "Search Students");
            sendMsg("2", "Search Faculty");

            lineBreak();

            sendMsg("Choice: ");

            switch(this.reader.nextLine().trim()) {

                case "0":

                    back = true;
                    break;

                case "1":

                    searchStudentNames();
                    break;

                case "2":

                    searchFacultyNames();
                    break;

                default: invalidCmd();
            }
        }
    }

    /***************
     * MAIN METHODS
     ***************/

    /**
     * EXIT PROGRAM
     */
    public void exitProgram() {

        this.dl.close();

        sendMsg("Terminated - " + (new java.util.Date()));
        System.exit(0);
    }

    /**
     * LOGIN
     * @return login status
     */
    public boolean login() {

        sendMsg("AccountID: ");
        String accountID = this.reader.nextLine();

        sendMsg("Password: ");
        String password = this.reader.nextLine();

        this.account = this.dl.getAccount(Integer.parseInt(accountID), password);

        if(this.account == null) {

            sendError("Login Failed", "Your accountID and or password was incorrect!");
            return false;

        }else {

            return true;
        }
    }

    /**
     * CREATE ACCOUNT
     */
    public void createAccount() {

        String firstName, lastName, email, phoneNumber, preferredContact, building = "", officeNumber = "", password;

        int roleID = -1;

        while(roleID == -1) {

            lineBreak();

            sendMsg("Choose a Role:");
            sendMsg("1", "Student");
            sendMsg("2", "Faculty");
            sendMsg("3", "Guest");

            lineBreak();

            sendMsg("Choice: ");

            roleID = switch (this.reader.nextLine().trim()) {
                case "1" -> 1;
                case "2" -> 2;
                case "3" -> 3;
                default -> -1;
            };

            if(roleID == -1) invalidCmd();
        }

        sendMsg("First Name: ");
        firstName = this.reader.nextLine();

        sendMsg("Last Name: ");
        lastName = this.reader.nextLine();

        sendMsg("Email: ");
        email = this.reader.nextLine();

        sendMsg("Phone: ");
        phoneNumber = this.reader.nextLine();

        lineBreak();

        preferredContact = null;

        while(preferredContact == null) {

            lineBreak();

            sendMsg("Choose a Preferred Contact Method:");
            sendMsg("1", "Email");
            sendMsg("2", "Phone");

            lineBreak();

            sendMsg("Choice: ");

            preferredContact = switch (this.reader.nextLine().trim()) {
                case "1" -> "Email";
                case "2" -> "Phone";
                default -> null;
            };

            if(preferredContact == null) invalidCmd();
        }

        if(roleID == 2) {

            sendMsg("Building: ");
            building = this.reader.nextLine();

            sendMsg("Office Number: ");
            officeNumber = this.reader.nextLine();

        }

        sendMsg("New Password: ");
        password = this.reader.nextLine();

        int accountID = this.dl.addAccount(firstName, lastName, password, preferredContact, email, phoneNumber, building, officeNumber, roleID);

        if(accountID != -1) {

            lineBreak();

            sendMsg("Account Created Successfully!");
            sendMsg("YOUR accountID: " + accountID);
        }
    }

    /**
     * SHOW ACCOUNT INFO
     */
    public void showAccountInfo() {

        Contact contact = this.dl.getContact(this.account.getAccountID());
        Office location = this.dl.getOffice(this.account.getAccountID());

        lineBreak();

        sendMsg("Account Info");
        sendMsg("First Name", this.account.getFirstName());
        sendMsg("Last Name", this.account.getLastName());
        sendMsg("Preferred Contact Method", this.account.getPreferedContact());
        sendMsg("Account Type", switch(this.account.getRoleID()) {
            case 1 -> "Student";
            case 2 -> "Faculty";
            case 3 -> "Guest";
            default -> "Unknown? Hmm...";
        });

        lineBreak();

        sendMsg("Contact Info");
        sendMsg("Email", contact.getEmail());
        sendMsg("Phone", contact.getPhone());

        if(this.account.getRoleID() == 2) {

            lineBreak();

            sendMsg("Location Info");
            sendMsg("Building", location.getBuilding());
            sendMsg("Office Number", location.getNumber());
        }

    }

    /**
     * UPDATE ACCOUNT
     */
    public void updateAccount() {

        boolean saved = false;

        while(!saved) {

            lineBreak();

            sendMsg("Choose a Field to Update:");
            sendMsg("1", "First Name");
            sendMsg("2", "Last Name");
            sendMsg("3", "Preferred Contact");
            sendMsg("SAVE", "Save Account Info");

            lineBreak();

            sendMsg("Choice: ");

            switch(this.reader.nextLine().toUpperCase().trim()) {

                case "1":

                    sendMsg("Current", this.account.getFirstName());
                    sendMsg("New: ");

                    this.account.setFirstName(this.reader.nextLine().trim());
                    break;

                case "2":

                    sendMsg("Current", this.account.getLastName());
                    sendMsg("New: ");

                    this.account.setLastName(this.reader.nextLine().trim());
                    break;

                case "3":

                    sendMsg("Current", this.account.getPreferedContact());

                    do {

                        lineBreak();

                        sendMsg("Choose a Preferred Contact Method:");
                        sendMsg("1", "Email");
                        sendMsg("2", "Phone");

                        lineBreak();

                        sendMsg("Choice: ");

                        this.account.setPreferedContact(switch (this.reader.nextLine()) {
                            case "1" -> "Email";
                            case "2" -> "Phone";
                            default -> null;
                        });

                        if(this.account.getPreferedContact() == null) invalidCmd();

                    }while(this.account.getPreferedContact() == null);

                    break;

                case "SAVE":

                    saved = true;
                    break;

                default: invalidCmd();
            }//switch end
        }//while end

        int effected = this.dl.updateAccount(
                this.account.getAccountID(),
                this.account.getFirstName(),
                this.account.getLastName(),
                this.account.getPreferedContact()
        );

        if(effected > 0) sendMsg("Successfully Updated Account!");
    }

    /**
     * UPDATE PASSWORD
     */
    public void updatePassword() {

        sendMsg("Enter a new password: ");

        if( this.dl.updatePassword(this.account.getAccountID(), this.reader.nextLine()) > 0)
            sendMsg("Successfully Updated Password!");
    }

    /**
     * UPDATE CONTACT
     */
    public void updateContact() {

        Contact contact = this.dl.getContact(this.account.getAccountID());

        boolean saved = false;

        while(!saved) {

            lineBreak();

            sendMsg("Choose a Field to Update:");
            sendMsg("1", "Email");
            sendMsg("2", "Phone");
            sendMsg("SAVE", "Save Contact Info");

            lineBreak();

            sendMsg("Choice: ");

            switch(this.reader.nextLine().toUpperCase().trim()) {

                case "1":

                    sendMsg("Current", contact.getEmail());
                    sendMsg("New: ");

                    contact.setEmail(this.reader.nextLine());
                    break;

                case "2":

                    sendMsg("Current", contact.getPhone());
                    sendMsg("New: ");

                    contact.setPhone(this.reader.nextLine());
                    break;

                case "SAVE":

                    saved = true;
                    break;

                default: invalidCmd();
            }//switch end
        }//while end

       if(this.dl.updateContact(contact.getAccountID(), contact.getEmail(), contact.getPhone()) > 0) {

           sendMsg("Successfully Updated Contact Info");
       }
    }

    /**
     * UPDATE LOCATION
     */
    public void updateLocation() {

        Office location = this.dl.getOffice(this.account.getAccountID());

        boolean saved = false;

        while(!saved) {

            lineBreak();

            sendMsg("Choose a Field to Update:");
            sendMsg("1", "Building");
            sendMsg("2", "Office Number");
            sendMsg("SAVE", "Save Location Info");

            lineBreak();

            switch(this.reader.nextLine().toUpperCase().trim()) {

                case "1":

                    sendMsg("Current", location.getBuilding());
                    sendMsg("New: ");

                    location.setBuilding(this.reader.nextLine());
                    break;

                case "2":

                    sendMsg("Current", location.getNumber());
                    sendMsg("New: ");

                    location.setNumber(this.reader.nextLine());
                    break;

                case "SAVE":

                    saved = true;
                    break;

                default: invalidCmd();
            }//switch end
        }//while end

        if(this.dl.updateOffice(location.getAccountID(), location.getBuilding(), location.getNumber()) > 0) {

            sendMsg("Successfully Updated Contact Info");
        }

    }

    /**
     * SHOW FACULTY ABSTRACTS
     */
    public void showAbstracts() {

        List<Abstract> abstracts = this.dl.getFacultyAbstracts(this.account.getAccountID());

        lineBreak();

        sendMsg("My Abstracts");

        for(Abstract anAbstract: abstracts) {

            lineBreak();

            sendMsg("ID", anAbstract.getAbstractID() + "");
            sendMsg("Title", anAbstract.getTitle());
            sendMsg("Body", anAbstract.getBody());
        }
    }

    /**
     * ADD FACULTY ABSTRACT
     */
    public void addAbstract() {

        String title, body;

        lineBreak();

        sendMsg("Add Abstract");
        sendMsg("Title: ");
        title = this.reader.nextLine();

        sendMsg("Body: ");
        body = this.reader.nextLine();

        if(this.dl.addFacultyAbstract(this.account.getAccountID(), title, body) > 0) {

            sendMsg("Successfully Added Abstract!");
        }
    }

    /**
     * REMOVE FACULTY ABSTRACT
     */
    public void removeAbstract() {

        lineBreak();

        sendMsg("Remove Abstract");
        sendMsg("Abstract ID: ");
        String abstractID = this.reader.nextLine();

        if(this.dl.removeFacultyAbstract(Integer.parseInt(abstractID)) > 0) {

            sendMsg("Successfully Removed Abstract With ID: " + abstractID);
        }
    }

    /**
     * SHOW INTERESTS
     */
    public void showInterests() {


    }

    /**
     * ADD INTEREST
     */
    public void addInterest() {


    }

    /**
     * REMOVE INTEREST
     */
    public void removeInterest() {


    }

    /**
     * SEARCH STUDENT INTERESTS
     */
    public void searchStudentInterests() {


    }

    /**
     * SEARCH FACULTY INTERESTS
     */
    public void searchFacultyInterests() {


    }

    /**
     * SEARCH STUDENT NAMES
     */
    public void searchStudentNames() {


    }

    /**
     * SEARCH FACULTY NAMES
     */
    public void searchFacultyNames() {


    }

    /**
     * SEARCH FACULTY ABSTRACTS
     */
    public void searchFacultyAbstracts() {


    }


    public static void main(String[] args) {

        iste330Group4PresentationLayer pl = new iste330Group4PresentationLayer();
        pl.run();
    }
}
