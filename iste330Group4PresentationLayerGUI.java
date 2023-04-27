import objects.Account;

import javax.swing.*;
import java.awt.*;
import java.util.*;


public class iste330Group4PresentationLayerGUI {

    private iste330Group4DataLayer dl;
    private Account account;

    private JFrame frame;
    private JPanel contentPanel;

    public iste330Group4PresentationLayerGUI() {

        this.dl = new iste330Group4DataLayer();
        this.account = null;

        this.frame = new JFrame();
        this.contentPanel = new JPanel();

    }

    /********************************
     * Render Setup & Helper Methods (MILES KRASSEN)
     ********************************/

    public void sendMsg(String msg) {

        System.out.println("[*] " + msg);
    }

    public void initFrame() {

        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(400,400));
        this.frame.pack();
        this.frame.setResizable(false);

    }

    public void showContent(Component component) {

        this.contentPanel.remove(0);
        this.contentPanel.add(component);

        this.frame.validate();
        this.frame.pack();
        this.frame.repaint();
    }

    public void showPopup(String msg) {

        showPopup(null, msg);
    }

    public void showPopup(String title, String msg) {

        JLabel message = new JLabel(msg);

        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void showPopup(String title, Component component) {

        JOptionPane.showMessageDialog(null, component, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void showPopupError(String e) {

        showPopupError("Error Message", e);
    }

    public void showPopupError(String title, String e) {

        JLabel error = new JLabel(e);

        JOptionPane.showMessageDialog(null, error, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**************************
     * General Builder Methods (MILES KRASSEN)
     **************************/

    public HashMap<String, JLabel> createLabels(String[] labels) {

        HashMap<String, JLabel> JLabels = new HashMap<>();

        for(String label: labels) {

            JLabels.put(label, new JLabel(label));
        }

        return JLabels;
    }

    public HashMap<String, JButton> createButtons(String[] labels) {

        HashMap<String, JButton> buttons = new HashMap<>();

        for(String label: labels) {

            buttons.put(label, new JButton(label));
        }

        return buttons;
    }

    public HashMap<String, JTextField> createTextFields(String[] ids) {

        HashMap<String, JTextField> fields = new HashMap<>();

        for(String id: ids) {

            fields.put(id, new JTextField(""));
        }

        return fields;
    }

    /***********************
     * Radio Builder Methods (MILES KRASSEN)
     ***********************/

    public HashMap<String, JRadioButton> createRadioButtons(String[] ids) {

        HashMap<String, JRadioButton> radioButtons = new HashMap<>();

        for(String id: ids) {

            radioButtons.put(id, new JRadioButton(id));
        }

        return radioButtons;
    }

    public ButtonGroup createButtonGroup(HashMap<String, JRadioButton> radios) {

        ButtonGroup buttonGroup = new ButtonGroup();

        for(JRadioButton radio: radios.values()) {

            buttonGroup.add(radio);
        }

        return buttonGroup;
    }

    public JPanel createRadioPanel(HashMap<String, JRadioButton> radios) {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        for(JRadioButton radio: radios.values()) {

            panel.add(radio);
        }

        return panel;
    }

    /***********************
     * Radio Helper Methods (MILES KRASSEN)
     ***********************/

    public String getSelectedRadioValue(HashMap<String, JRadioButton> radios) {

        String selected = "";

        for(JRadioButton radio: radios.values()) {

            if(radio.isSelected()){

                selected = radio.getText();
                break;
            }
        }

        return selected;
    }

    /****************
     * PROGRAM START (MILES KRASSEN)
     ****************/

    public void run() {

        initFrame();

        this.contentPanel.add(dbSetup());

        this.frame.add(this.contentPanel);
        this.frame.pack();
        this.frame.setVisible(true);
    }

    /********
     * MENUS
     ********/

    /**
     * Login Menu (MILES KRASSEN EXAMPLE)
     * @return JPanel
     */
    public JPanel loginMenu() {

        //login menu panel
        JPanel panel = new JPanel(new GridLayout(3, 1));

        //ids used for creating buttons
        String[] ids = new String[]{
                "Exit",
                "Login",
                "Create Account"
        };

        HashMap<String, JButton> buttons = createButtons(ids);

        //add buttons to panel
        for(String id: ids) {

            panel.add(buttons.get(id));
        }

        //listeners
        buttons.get("Exit").addActionListener(ignored -> {

            exitProgram();
        });

        buttons.get("Login").addActionListener(ignored -> {

            showContent(login());
        });

        buttons.get("Create Account").addActionListener(ignored -> {

            showContent(createAccount());

        });

        //MUST RETURN PANEL
        return panel;
    }

    /**
     * Main Menu
     * @return JPanel
     */
    public JPanel mainMenu() {

        JPanel panel = new JPanel(new GridLayout(5,1));

        String[] ids = new String[]{
                "Logout",
                "Account Settings",
                "Search By Interests",
                "Search By Name",
                "Search By Faculty Abstract"
        };

        HashMap<String, JButton> buttons = createButtons(ids);

        for(String id: ids) {

            panel.add(buttons.get(id));
        }

        buttons.get("Logout").addActionListener(ignored -> {

            showContent(loginMenu());
        });

        //******* add rest of listeners

        return panel;
    }

    /****************
     * MAIN METHODS
     ***************/

    /**
     * Exit Program (MILES KRASSEN)
     */
    public void exitProgram() {

        this.frame.dispose();
        this.dl.close();

        sendMsg("Terminated - " + (new java.util.Date()));
        System.exit(0);
    }

    /**
     * Database Setup (MILES KRASSEN EXAMPLE)
     * @return JPanel
     */
    public JPanel dbSetup() {

        //database panel
        JPanel panel = new JPanel(new GridLayout(4,2));

        //create input tags for labels and fields
        String[] tags = new String[]{
            "Username",
            "Password",
            "Database"
        };

        HashMap<String, JLabel> labels = createLabels(tags);
        HashMap<String, JTextField> fields = createTextFields(tags);

        //update fields that need custom attention
        fields.get("Database").setText("iste330group4");
        fields.replace("Password", new JPasswordField(""));

        JButton submit = new JButton("Connect");

        //add to database panel
        for(String tag: tags) {

            panel.add(labels.get(tag));
            panel.add(fields.get(tag));
        }

        panel.add(new JLabel());
        panel.add(submit);

        //listen for submit event
        submit.addActionListener(ignored -> {

            if(this.dl.connect(
                    fields.get("Username").getText(),
                    fields.get("Password").getText(),
                    fields.get("Database").getText()
            )) {

                showContent(loginMenu());
            }else {

                showPopupError("Database Connection Failed", "Is your database setup correctly?");
            }
        });

        return panel;
    }

    /**
     * Login (MILES KRASSEN EXAMPLE)
     * @return JPanel
     */
    public JPanel login() {

        //login panel
        JPanel panel = new JPanel(new GridLayout(3,2));

        //tags for labels and fields
        String[] tags = new String[]{
            "AccountID",
            "Password"
        };

        HashMap<String, JLabel> labels = createLabels(tags);
        HashMap<String, JTextField> fields = createTextFields(tags);

        fields.replace("Password", new JPasswordField(""));

        JButton submit = new JButton("Login");
        JButton back = new JButton("Back");

        //add to panel
        for(String tag: tags) {

            panel.add(labels.get(tag));
            panel.add(fields.get(tag));
        }

        panel.add(back);
        panel.add(submit);

        //listen for back
        back.addActionListener(ignored -> {

            showContent(loginMenu());
        });

        //listen for submit
        submit.addActionListener(ignored -> {

            //validate accountID since we're converting to a number - MUST DO FOR INT CONVERSIONS
            String accountID = fields.get("AccountID").getText().trim();

            if(!accountID.matches("[0-9]+")) {

                showPopupError("Please enter a valid accountID!");
                return;
            }

            //interact with datalayer
            this.account = this.dl.getAccount(Integer.parseInt(accountID), fields.get("Password").getText());

            if(this.account == null) {

                showPopupError("Login Failed", "Your accountID and or password was incorrect!");

            }else {

                showContent(mainMenu());
            }
        });

        return panel;
    }

    /**
     * Create Account (MILES KRASSEN EXAMPLE)
     * @return JPanel
     */
    public JPanel createAccount() {

        //create account panel
        JPanel panel = new JPanel(new GridLayout(10, 2));

        //choose role radio button popup
        HashMap<String, JRadioButton> roleRadioButtons = createRadioButtons(new String[]{"Student", "Faculty", "Guest"});

        ButtonGroup roleGroup = createButtonGroup(roleRadioButtons);//needed for grouping radio buttons, not used anywhere else.

        do {

            //createRadioPanel() adds and formats the radio buttons into their own panel
            showPopup("Pick a Role", createRadioPanel(roleRadioButtons));

        }while (getSelectedRadioValue(roleRadioButtons).equals(""));

        //choose preferred contact radio button popup
        HashMap<String, JRadioButton> preferredContactRadioButtons = createRadioButtons(new String[]{"Phone", "Email"});

        ButtonGroup preferredContactGroup = createButtonGroup(preferredContactRadioButtons);//needed for grouping radio buttons, not used anywhere else.

        do {

            //createRadioPanel() adds and formats the radio buttons into their own panel
            showPopup("Pick a Preferred Contact Method", createRadioPanel(preferredContactRadioButtons));

        }while (getSelectedRadioValue(preferredContactRadioButtons).equals(""));

        //tags used as IDs for and labels for creating JLabels and JTextFields
        String[] tags = new String[]{
                "First Name",
                "Last Name",
                "Email",
                "Phone Number",
                "Building",
                "Office Number",
                "New Password"
        };

        HashMap<String, JLabel> labels = createLabels(tags);
        HashMap<String, JTextField> fields = createTextFields(tags);

        fields.replace("New Password", new JPasswordField(""));

        for(String id: tags) {

            //if faculty, don't add these choices to the panel
            if(!getSelectedRadioValue(roleRadioButtons).equals("Faculty") && (id.equals("Building") || id.equals("Office Number")))
                continue;

            panel.add(labels.get(id));
            panel.add(fields.get(id));
        }

        //add the save button
        JButton save = new JButton("Save");

        //add the cancel button
        JButton cancel = new JButton("Cancel");

        panel.add(cancel);
        panel.add(save);

        //listener for cancel button click
        cancel.addActionListener(ignored -> {

            showContent(loginMenu());
        });

        //listener for save button click action
        save.addActionListener(ignored -> {

            String preferredContact = getSelectedRadioValue(preferredContactRadioButtons);

            int roleID = switch (getSelectedRadioValue(roleRadioButtons)) {
                case "Student" -> 1;
                case "Faculty" -> 2;
                case "Guest" -> 3;
                default -> -1;
            };

            int accountID = this.dl.addAccount(
                    fields.get("First Name").getText(),
                    fields.get("Last Name").getText(),
                    fields.get("New Password").getText(),
                    preferredContact,
                    fields.get("Email").getText(),
                    fields.get("Phone Number").getText(),
                    fields.get("Building").getText(),
                    fields.get("Office Number").getText(),
                    roleID
            );

            if(accountID != -1) {

                showPopup("Account Created Successfully","YOUR accountID: " + accountID);
            }else {

                showPopupError("Failed to Create Your Account");
            }

            showContent(loginMenu());//needed to go back to previous menu

        });

        //MUST RETURN PANEL OR IT WON'T DISPLAY
        return panel;
    }

    public static void main(String[] args) {

       iste330Group4PresentationLayerGUI gui = new iste330Group4PresentationLayerGUI();
       gui.run();
    }




}
