import objects.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.List;
import java.util.*;


public class iste330Group4PresentationLayerGUI {

    private iste330Group4DataLayer dl;
    private Account account;
    private Contact contact;

    private JFrame frame;
    private JPanel contentPanel;

    public iste330Group4PresentationLayerGUI() {

        this.dl = new iste330Group4DataLayer();
        this.account = null;
        this.contact = null;

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
        this.frame.setPreferredSize(new Dimension(800,400));
        this.frame.pack();
        this.frame.setResizable(true);

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

        JLabel error = new JLabel("Error: " + e);
        
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

        JPanel panel = new JPanel(new GridLayout(0,1));

        String[] ids = new String[]{
                "Logout",
                "Account Settings",
                "Search By Interests",
                "Search By Name",
                "Search By ID",
                "Search By Faculty Abstract",
                "Search Faculty Info"
        };

        HashMap<String, JButton> buttons = createButtons(ids);

        for(String id: ids) {

            panel.add(buttons.get(id));
        }

        buttons.get("Logout").addActionListener(ignored -> {

            showContent(loginMenu());
        });

        //******* add rest of listeners
         buttons.get("Account Settings").addActionListener(ignored -> {

            showContent(accountSettings());
        });
        
        buttons.get("Search By Interests").addActionListener(ignored -> {

            showContent(searchByInterest());
        });

        buttons.get("Search By Name").addActionListener(ignored -> {

            showContent(searchByName());
        });

        buttons.get("Search By ID").addActionListener(ignored -> {

            showContent(searchByID());
        });
        
        buttons.get("Search By Faculty Abstract").addActionListener(ignored -> {

            showContent(searchByFaculty());
        });
        buttons.get("Search Faculty Info").addActionListener(ignored -> {

            showContent(searchFacultyInfo());
        });

        return panel;
    }
    
     //////////////////////////////////////////////////////////////////////////////////////////Evan Jurdan
    public JPanel accountSettings() {
      JPanel panel = new JPanel(new GridLayout(0,1));
    
        if (account.getRoleID()==2){
            String[] ids = new String[]{
                "Main Menu",
                "Account Actions",
                "Interest Actions",
                "Abstract Actions"
      };
      
      HashMap<String, JButton> buttons = createButtons(ids);
      for(String id: ids) {
            panel.add(buttons.get(id));
       }
       
       buttons.get("Main Menu").addActionListener(ignored -> {

            showContent(mainMenu());
        });
      
        buttons.get("Account Actions").addActionListener(ignored -> {

            showContent(accountActions());
        });
        buttons.get("Interest Actions").addActionListener(ignored -> {

            showContent(interestActions());
        });
        buttons.get("Abstract Actions").addActionListener(ignored -> {

            showContent(abstractActions());
        });
        }
        else
        {
  
            String[] ids = new String[]{
                "Main Menu",
                "Account Actions",
                "Interest Actions",
                
      };
      
      HashMap<String, JButton> buttons = createButtons(ids);
      for(String id: ids) {
            panel.add(buttons.get(id));
       }
       
       buttons.get("Main Menu").addActionListener(ignored -> {

            showContent(mainMenu());
        });
      
        buttons.get("Account Actions").addActionListener(ignored -> {

            showContent(accountActions());
        });
        buttons.get("Interest Actions").addActionListener(ignored -> {

            showContent(interestActions());
        });
      
    
        }
      return panel;
      
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    public JPanel accountActions() {
      JPanel panel = new JPanel(new GridLayout(0,1));
      
      String[] ids = new String[]{
                "Main Menu",
                "View Account Info",
                "Change Password",
                "Edit Account",
                "Edit Contact Info"
      };
      
      HashMap<String, JButton> buttons = createButtons(ids);
      for(String id: ids) {
            panel.add(buttons.get(id));
       }
       
       
       buttons.get("Main Menu").addActionListener(ignored -> {

            showContent(mainMenu());
        });
        
        buttons.get("View Account Info").addActionListener(ignored -> {

            showContent(viewAccInfo());
        });
        
         buttons.get("Change Password").addActionListener(ignored -> {

            showContent(changePassword());
        });
        
        buttons.get("Edit Account").addActionListener(ignored -> {

            showContent(editAccount());
        });
        buttons.get("Edit Contact Info").addActionListener(ignored -> {

            showContent(editContactInfo());
        });
        
    
    
      return panel;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    public JPanel viewAccInfo() {
      JPanel panel = new JPanel(new GridLayout(0,1));
      Contact contact = this.dl.getContact(this.account.getAccountID());
      Office location = this.dl.getOffice(this.account.getAccountID());
      
      String[] ids = new String[]{
                "Account Actions"
      };
      
      HashMap<String, JButton> buttons = createButtons(ids);
      for(String id: ids) {
            panel.add(buttons.get(id));
      }
      
      buttons.get("Account Actions").addActionListener(ignored -> {

            showContent(accountActions());
      });
      

      String[] words = new String[]{
          "**Account Details**",
          "First Name: " + account.getFirstName(),
          "Last Name: " + account.getLastName(),
          "Preferred Contact: " + account.getPreferedContact(),
          "Account Type: " + switch(account.getRoleID()) {
                                    case 1 -> "Student";
                                    case 2 -> "Faculty";
                                    case 3 -> "Guest";
                                    default -> "Unknown? Hmm...";}
          
      };
      
      String[] words2 = new String[]{
          "Building: " + location.getBuilding(),
          "Office Number: " + location.getNumber()
          
      };
      
      
      
      String[] words3 = new String[]{
          "**Contact Information**",
          "Email: " + contact.getEmail(),
          "Phone: " + contact.getPhone()
          
      };
      
      HashMap<String, JLabel> labels = createLabels(words);
         for(String word: words) {
            panel.add(labels.get(word));
      }
      if (this.account.getRoleID() == 2) {
         HashMap<String, JLabel> labels2 = createLabels(words2);
            for(String word2: words2) {
               panel.add(labels2.get(word2));
         }
      }
      
      HashMap<String, JLabel> labels3 = createLabels(words3);
         for(String word3: words3) {
            panel.add(labels3.get(word3));
      }
      
    
      return panel;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public JPanel changePassword() {
      JPanel panel = new JPanel(new GridLayout(0,2));
      
      String[] ids = new String[]{
                "Account Actions",
                "Set Password"
      };
      
      HashMap<String, JButton> buttons = createButtons(ids);
      for(String id: ids) {
            panel.add(buttons.get(id));
      }
      
      
       String[] tags = new String[]{
           "Enter New Password:"   
       };
       
       HashMap<String, JLabel> labels = createLabels(tags);
       HashMap<String, JTextField> fields = createTextFields(tags);
       
       for(String tag: tags) {
         panel.add(labels.get(tag));
         panel.add(fields.get(tag));
       }
       
      buttons.get("Account Actions").addActionListener(ignored -> {

            showContent(accountActions());
      });
      
      buttons.get("Set Password").addActionListener(ignored -> {
         int acID = account.getAccountID();
         String pswd = fields.get("Enter New Password:").getText();
         if( this.dl.updatePassword(this.account.getAccountID(), fields.get("Enter New Password:").getText()) > 0)
            showPopup("Successfully Updated Password!");
            
      });
       
      return panel;
    }
    //////////////////////////////////////////////////////////////////////////////////////////
      public JPanel editAccount() {
         JPanel panel = new JPanel(new GridLayout(20,2));
         //Account account = this.dl.getAccount(this.account.getAccountID());
         Office location = this.dl.getOffice(this.account.getAccountID());
         
         String[] ids = new String[]{
                   "Account Actions",
                   "Save"
         };
         
         HashMap<String, JButton> buttons = createButtons(ids);
         for(String id: ids) {
               panel.add(buttons.get(id));
         }
         
         
         String[] tags = new String[]{
                   "First Name: ",
                   "Last Name: "
         };
         HashMap<String, JLabel> labels = createLabels(tags);
         HashMap<String, JTextField> fields = createTextFields(tags);
         
         for(String tag: tags) {
               panel.add(labels.get(tag));
               panel.add(fields.get(tag));
         }
         String[] tags2 = new String[]{
                   "Preferred Contact: "
         };
         
         HashMap<String, JLabel> labels2 = createLabels(tags2);
         
         for(String t: tags2) {
               panel.add(labels2.get(t));
         }
         
         String[] radioTags = new String[]{
                   "Phone",
                   "Email"
         };
         
         String[] facTags = new String[]{
                   "Building: ",
                   "Office Number: "
         };
         
         HashMap<String, JLabel> labels3 = createLabels(facTags);
         HashMap<String, JTextField> fields3 = createTextFields(facTags);

         
         
         HashMap<String, JRadioButton> conts = createRadioButtons(radioTags);
         
         for(String radioTag: radioTags) {
   
               conts.put(radioTag, new JRadioButton(radioTag));
         }
         
         ButtonGroup contactGroup = createButtonGroup(conts);
         for(JRadioButton cont: conts.values()) {
   
               contactGroup.add(cont);
               panel.add(cont);
         }
         
         if (this.account.getRoleID() == 2) {
            for(String facTag: facTags) {
                  panel.add(labels3.get(facTag));
                  panel.add(fields3.get(facTag));
            }
         }
         
          buttons.get("Account Actions").addActionListener(ignored -> {
   
               showContent(accountActions());
         });
         
         buttons.get("Save").addActionListener(ignored -> {
            
         
               //Office location = this.dl.getOffice(this.account.getAccountID());
               String selected = "";
               int acID = account.getAccountID();
               String fname = fields.get("First Name: ").getText();
               String lname = fields.get("Last Name: ").getText();
               String build = fields3.get("Building: ").getText();
               String loc = fields3.get("Office Number: ").getText();
               
               if(!fields.get("First Name: ").getText().isEmpty()) {
                  this.account.setFirstName(fname.trim());
                  
               }
               
               if(!fields.get("Last Name: ").getText().isEmpty()) {
                  this.account.setLastName(lname.trim());
                  
               }
               
               if(!fields3.get("Building: ").getText().isEmpty()) {
                  location.setBuilding(build.trim());
                  
               }
               
               if(!fields3.get("Office Number: ").getText().isEmpty()) {
                  location.setNumber(loc.trim());
                  
               }
               
               
              //radio
              for(JRadioButton cont: conts.values()) {
                    if(cont.isSelected()){
                       selected = cont.getText();
                       break;
                    }
               }
               
               if(!getSelectedRadioValue(conts).equals("")) {
                     this.account.setPreferedContact(switch (selected) {
                        case "Email" -> "Email";
                        case "Phone" -> "Phone";
                        default -> null;
                     });
                     
                     
                }
               
               int test = this.dl.updateOffice(location.getAccountID(), location.getBuilding(), location.getNumber());
               if(this.dl.updateAccount(acID, fname, lname, selected) > 0 || test > 0)
               showPopup("Saved Successfully!");
               

 
                                 
         });
         
         
         return panel;
      
      }
   ///////////////////////////////////////////////////////////////////////////////////////////////
   public JPanel editContactInfo() {
      JPanel panel = new JPanel(new GridLayout(10,2));
      Contact contact = this.dl.getContact(this.account.getAccountID());
      
      String[] ids = new String[]{
                "Account Actions",
                "Save"
      };
      
      HashMap<String, JButton> buttons = createButtons(ids);
      for(String id: ids) {
            panel.add(buttons.get(id));
      }
      
      String[] tags = new String[]{
                "Email: ",
                "Phone: "
      };
      HashMap<String, JLabel> labels = createLabels(tags);
      HashMap<String, JTextField> fields = createTextFields(tags);
      
      for(String tag: tags) {
            panel.add(labels.get(tag));
            panel.add(fields.get(tag));
      }

      

      
      buttons.get("Account Actions").addActionListener(ignored -> {

            showContent(accountActions());
      });
      
      buttons.get("Save").addActionListener(ignored -> {
            int acID = account.getAccountID();
            String email = fields.get("Email: ").getText();
            String phone = fields.get("Phone: ").getText();
            
             if(!fields.get("Email: ").getText().isEmpty()) {
                contact.setEmail(email);
             }
             
              if(!fields.get("Phone: ").getText().isEmpty()) {
                contact.setPhone(phone);  
             }

           
            if(this.dl.updateContact(contact.getAccountID(), contact.getEmail(), contact.getPhone()) > 0)
            showPopup("Saved Successfully!");
                              
      });


      
      
      
      return panel;
   }

    //////////////////////////////////////////////////////////////////////////////////////////
    public JPanel interestActions() {
        JPanel panel = new JPanel(new GridLayout(0,1));
        
        String[] ids = new String[]{
            "Back",
            "View Interests",
            "Add an Interest",
            "Remove an Interest"
        };
        //System.out.println(account.getAccountID());
        HashMap<String, JButton> buttons = createButtons(ids);
        for(String id: ids) {
              panel.add(buttons.get(id));
         }
         
         buttons.get("Back").addActionListener(ignored -> {
  
              showContent(accountSettings());
          });
        
          buttons.get("View Interests").addActionListener(ignored -> {
  
            showContent(showInterests(account.getAccountID())); 
        });
          
        buttons.get("Add an Interest").addActionListener(ignored -> {
  
            showContent(addInterest());
        });

          buttons.get("Remove an Interest").addActionListener(ignored -> {
  
            showContent(removeInterest());
        });
      return panel;
        
      }
      /////////////////////////////
      public JPanel showInterests(int idd) {
        JPanel panel = new JPanel(new GridLayout(0,3));
        String Interesttz = "  ";

        String[] ids = new String[]{
            "Back",
            "Main Menu"
        };
        JLabel filler = new JLabel(" ");

        HashMap<String, JButton> buttons = createButtons(ids);
        for(String id: ids) {
              panel.add(buttons.get(id));
         }
         panel.add(filler);
         buttons.get("Back").addActionListener(ignored -> {
  
            showContent(interestActions());
        });
        
        buttons.get("Main Menu").addActionListener(ignored -> {

            showContent(mainMenu());
        });

            String[] outputs = new String[]{
                
                "Account ID, ","Name, ","Interests "
            };
            
            HashMap<String, JLabel> labels2 = createLabels(outputs);
            
            for(String tag: outputs) {
                panel.add(labels2.get(tag));
            }
            
            List<SearchRecord> searchRecords = dl.searchById(account.getAccountID());

            for (SearchRecord ser : searchRecords){              
                System.out.println(account.getAccountID());
                String[] output1 = new String[]{
                        String.valueOf(ser.getAccountID()), ser.getName()
                        
                        
                    };
                    HashMap<String, JLabel> labels3 = createLabels(output1);
                    for(String tag: output1) {
                            panel.add(labels3.get(tag));
                    }
                    List<Interest> interestz ;
                    if(account.getRoleID()==1){
                        interestz = dl.getStudentInterests(ser.getAccountID());
                    }
                    else if(account.getRoleID()==2){
                        interestz = dl.getFacultyInterests(ser.getAccountID());
                    }
                    else{
                       interestz = dl.getGuestInterests(ser.getAccountID());
                    }

                        for (Interest i : interestz){
                            Interesttz += i.getInterestID()+" "+i.getInterest() + ", ";
                        }

                        if(Interesttz.length() != 0)
                            Interesttz = Interesttz.substring(0, (Interesttz.length() - 2));
                            String[] output2 = new String[]{
                                Interesttz
                            };
                            
                            HashMap<String, JLabel> labels4 = createLabels(output2);
                            for(String tag: output2) {
                                panel.add(labels4.get(tag));
                            }
                        
                
                        
                
                    

            }
            

            this.frame.pack();
        return panel;
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    public String showYourInterests() {

        String out = "";
        out+=("Show Your Interests\n");
        
        int acID = account.getAccountID();
        if(account.getRoleID()==1)
        {
            out+=(this.dl.getStudentInterests(acID).toString());
            out+="\n";
        }
        else if(account.getRoleID()==2)
        {
            out+=(this.dl.getFacultyInterests(acID).toString());
            out+="\n";
        }
        if(account.getRoleID()==3)
        {
            out+=(this.dl.getGuestInterests(acID).toString());
            out+="\n";
        }
        return out;
    }
      //////////////////////////////////////////////////////////////////////////////////////////
    public JPanel addInterest() {
        JPanel panel = new JPanel(new GridLayout(3,1));
        
        String[] ids = new String[]{
            "Back",
            "Add",
        };
        
        HashMap<String, JButton> buttons = createButtons(ids);
        for(String id: ids) {
              panel.add(buttons.get(id));
         }

         String[] tags = new String[]{
                "Interest"
            };
    
            HashMap<String, JLabel> labels = createLabels(tags);
            HashMap<String, JTextField> fields = createTextFields(tags);

            for(String tag: tags) {

                panel.add(labels.get(tag));
                panel.add(fields.get(tag));
            }


         buttons.get("Back").addActionListener(ignored -> {
  
              showContent(interestActions());
          });
        
          
        buttons.get("Add").addActionListener(ignored -> {
            String interest = fields.get("Interest").getText();
            int acID = account.getAccountID();
                if(account.getRoleID()==1)
                {
                    if(this.dl.addStudentInterest(acID,interest) > 0) {

                        showPopup("Successfully Added Interest!");
                    }
                }
                else if(account.getRoleID()==2)
                {
                    if(this.dl.addFacultyInterest(acID,interest) > 0) {

                        showPopup("Successfully Added Interest!");
                    }
                }
                if(account.getRoleID()==3)
                {
                    if(this.dl.addGuestInterest(acID,interest) > 0) {

                        showPopup("Successfully Added Interest!");
                    }
                }
            showContent(interestActions());
        });

      return panel;
        
      }
      //////////////////////////////////////////////////////////////////////////////////////////
      public JPanel removeInterest() {
        JPanel panel = new JPanel(new GridLayout(3,1));
        
        String[] ids = new String[]{
            "Back",
            "Remove",
        };
        
        HashMap<String, JButton> buttons = createButtons(ids);
        for(String id: ids) {
              panel.add(buttons.get(id));
         }

         String[] tags = new String[]{
                "Interest ID"
            };
    
            HashMap<String, JLabel> labels = createLabels(tags);
            HashMap<String, JTextField> fields = createTextFields(tags);

            for(String tag: tags) {

                panel.add(labels.get(tag));
                panel.add(fields.get(tag));
            }


         buttons.get("Back").addActionListener(ignored -> {
  
              showContent(interestActions());
          });
        
          
        buttons.get("Remove").addActionListener(ignored -> {
            try{
            int interestID = Integer.parseInt(fields.get("Interest ID").getText());
            
                if(account.getRoleID()==1)
                {
                if(this.dl.removeStudentInterest(interestID) > 0) {

                    showPopup("Successfully Removed Interest!");
                }
                }
                else if(account.getRoleID()==2)
                {
                    if(this.dl.removeFacultyInterest(interestID) > 0) {

                        showPopup("Successfully Removed Interest!");
                    }
                }
                else if(account.getRoleID()==3)
                {
                    if(this.dl.removeGuestInterest(interestID) > 0) {

                        showPopup("Successfully Removed Interest!");
                    }
                }
            showContent(interestActions());
        }
        catch(Exception e){
            showPopup("Please input a valid Integer for ID");
            showContent(removeInterest());
        }
    });
        

      return panel;
      
        
      }
      /////
      public JPanel abstractActions() {
        JPanel panel = new JPanel(new GridLayout(0,1));
        
        String[] ids = new String[]{
            "Back",
            "View Abstracts",
            "Add an Abstract",
            "Remove an Abstract"
        };
        //System.out.println(account.getAccountID());
        HashMap<String, JButton> buttons = createButtons(ids);
        for(String id: ids) {
              panel.add(buttons.get(id));
         }
         
         buttons.get("Back").addActionListener(ignored -> {
  
              showContent(accountSettings());
          });
        
          buttons.get("View Abstracts").addActionListener(ignored -> {
  
            showContent(showAbstracts(account.getAccountID())); 
        });
          
        buttons.get("Add an Abstract").addActionListener(ignored -> {
  
            showContent(addAbstract());
        });

          buttons.get("Remove an Abstract").addActionListener(ignored -> {
  
            showContent(removeAbstract());
        });
      return panel;
        
      }
      private JPanel removeAbstract() {
        JPanel panel = new JPanel(new GridLayout(3,1));
        
        String[] ids = new String[]{
            "Back",
            "Remove",
        };
        
        HashMap<String, JButton> buttons = createButtons(ids);
        for(String id: ids) {
              panel.add(buttons.get(id));
         }

         String[] tags = new String[]{
                "Abstract ID"
            };
    
            HashMap<String, JLabel> labels = createLabels(tags);
            HashMap<String, JTextField> fields = createTextFields(tags);

            for(String tag: tags) {

                panel.add(labels.get(tag));
                panel.add(fields.get(tag));
            }


         buttons.get("Back").addActionListener(ignored -> {
  
              showContent(abstractActions());
          });
        
          
        buttons.get("Remove").addActionListener(ignored -> {
            try{
            int abstractID = Integer.parseInt(fields.get("Abstract ID").getText());
            
              
                
                    if(this.dl.removeFacultyAbstract(abstractID) > 0) {

                        showPopup("Successfully Removed Abstract!");}
                    
            showContent(abstractActions());
        }
        catch(Exception e){
            showPopup("Please input a valid Integer for ID");
            showContent(removeAbstract());
        }
    });
        

      return panel;
    }

    private JPanel addAbstract() {
        JPanel panel = new JPanel(new GridLayout(3,1));
        
        String[] ids = new String[]{
            "Back",
            "Add",
        };
        
        HashMap<String, JButton> buttons = createButtons(ids);
        for(String id: ids) {
              panel.add(buttons.get(id));
         }

         String[] tags = new String[]{
                "Abstract Title", "Abstract Body"
            };
    
            HashMap<String, JLabel> labels = createLabels(tags);
            HashMap<String, JTextField> fields = createTextFields(tags);

            for(String tag: tags) {

                panel.add(labels.get(tag));
                panel.add(fields.get(tag));
            }


         buttons.get("Back").addActionListener(ignored -> {
  
              showContent(abstractActions());
          });
        
          
        buttons.get("Add").addActionListener(ignored -> {
            String abtitle = fields.get("Abstract Title").getText();
            String abbody = fields.get("Abstract Body").getText();
            int acID = account.getAccountID();
              
                
                    if(this.dl.addFacultyAbstract(acID,abtitle, abbody) > 0) {

                        showPopup("Successfully Added Abstract!");
                    }
            
            showContent(abstractActions());
        });

      return panel;
        
    }

    private JPanel showAbstracts(int accountID) {
        JPanel panel = new JPanel(new GridLayout(0,3));
        String abs = "  ";

        String[] ids = new String[]{
            "Back",
            "Main Menu"
        };
        JLabel filler = new JLabel(" ");

        HashMap<String, JButton> buttons = createButtons(ids);
        for(String id: ids) {
              panel.add(buttons.get(id));
         }
         panel.add(filler);
         buttons.get("Back").addActionListener(ignored -> {
  
            showContent(abstractActions());
        });
        
        buttons.get("Main Menu").addActionListener(ignored -> {

            showContent(mainMenu());
        });

            String[] outputs = new String[]{
                
                "Account ID, ","Name, ","Abstracts "
            };
            
            HashMap<String, JLabel> labels2 = createLabels(outputs);
            
            for(String tag: outputs) {
                panel.add(labels2.get(tag));
            }
            
            List<SearchRecord> searchRecords = dl.searchById(account.getAccountID());

            for (SearchRecord ser : searchRecords){              
                System.out.println(account.getAccountID());
                String[] output1 = new String[]{
                        String.valueOf(ser.getAccountID()), ser.getName()
                        
                        
                    };
                    HashMap<String, JLabel> labels3 = createLabels(output1);
                    for(String tag: output1) {
                            panel.add(labels3.get(tag));
                    }
                        List<Abstract> abstracts = dl.getFacultyAbstracts(ser.getAccountID());

                        for (Abstract i : abstracts){
                            abs += (i.getAbstractID()+" "+i.getTitle() + ", ");
                        }

                        if(abs.length() != 0)
                            abs = abs.substring(0, (abs.length() - 2));
                            String[] output2 = new String[]{
                                abs
                            };
                            
                            HashMap<String, JLabel> labels4 = createLabels(output2);
                            for(String tag: output2) {
                                panel.add(labels4.get(tag));
                            }
                        
                
                        
                
                    

            }
            

            this.frame.pack();
        return panel;
   
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    public JPanel searchByInterest() {
        JPanel panel = new JPanel(new GridLayout(3,1));
        
        String[] ids = new String[]{
                  "Main Menu",
                  "Search Students",
                  "Search Faculty"
        };
        
        HashMap<String, JButton> buttons = createButtons(ids);
        for(String id: ids) {
              panel.add(buttons.get(id));
         }
         
         buttons.get("Main Menu").addActionListener(ignored -> {
  
              showContent(mainMenu());
        });
        
        buttons.get("Search Students").addActionListener(ignored -> {
  
            showContent(searchStudentInterest());
        });

        buttons.get("Search Faculty").addActionListener(ignored -> {

            showContent(searchFacultyInterest());
        });
    
      return panel;
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    public JPanel searchStudentInterest() {
        JPanel panel = new JPanel(new GridLayout(5,2));
        
        String[] ids = new String[]{
            "Back",
            "Search",
        };
        
        HashMap<String, JButton> buttons = createButtons(ids);
        for(String id: ids) {
              panel.add(buttons.get(id));
         }

         String[] tags = new String[]{
                
                "1: ","2: ","3: "
            };
            JLabel title;
            title = new JLabel("Interest to search for") ;
            JLabel title2;
            title2 = new JLabel(": ") ;
            HashMap<String, JLabel> labels = createLabels(tags);
            HashMap<String, JTextField> fields = createTextFields(tags);
            panel.add(title);
            panel.add(title2);
            for(String tag: tags) {

                panel.add(labels.get(tag));
                panel.add(fields.get(tag));
            }


         buttons.get("Back").addActionListener(ignored -> {
  
              showContent(searchByInterest());
          });
        
          
        buttons.get("Search").addActionListener(ignored -> {
            String searches = fields.get("1: ").getText();
            searches += ",";
            searches += fields.get("2: ").getText();
            searches += ",";
            searches += fields.get("3: ").getText();
            
            List<SearchRecord> searchRecords = dl.searchByStudentInterest(searches);
            


            showContent(searchResults(searchRecords, 1));
        });

      return panel;
        
      }
      //////////////////////////////////////////////////////////////////////////////////////////
    public JPanel searchResults(List<SearchRecord> searchRecords, int type) {
        JPanel panel = new JPanel(new GridLayout(0,3));

        String[] ids = new String[]{
            "Back",
            "Search again",
            "Main Menu"
        };
        
        HashMap<String, JButton> buttons = createButtons(ids);
        for(String id: ids) {
              panel.add(buttons.get(id));
         }

         buttons.get("Back").addActionListener(ignored -> {
  
            showContent(searchByInterest());
        });
        buttons.get("Search again").addActionListener(ignored -> {
            if(type ==1){
                showContent(searchStudentInterest());
            }
            if(type ==2){
                showContent(searchFacultyInterest());
            }
            if(type ==3){
                showContent(ERROR());
            }


        });
        buttons.get("Main Menu").addActionListener(ignored -> {

            showContent(mainMenu());
        });
            String[] outputs = new String[]{
                
                "Account ID, ","Name, ","Common Interest "
            };
            
            HashMap<String, JLabel> labels2 = createLabels(outputs);
            
            for(String tag: outputs) {
                panel.add(labels2.get(tag));
            }
            
            
            for (SearchRecord ser : searchRecords) {
                
                String[] output1 = new String[]{
                    String.valueOf(ser.getAccountID()), ser.getName(), ser.getCommonInterests()
                    
                };
                
                HashMap<String, JLabel> labels3 = createLabels(output1);
                for(String tag: output1) {
                    panel.add(labels3.get(tag));
                }

            }


            this.frame.pack();
        return panel;
    }
        //////////////////////////////////////////////////////////////////////////////////////////
        public JPanel searchFacultyInterest() {
            JPanel panel = new JPanel(new GridLayout(5,2));
            
            String[] ids = new String[]{
                "Back",
                "Search",
            };
            
            HashMap<String, JButton> buttons = createButtons(ids);
            for(String id: ids) {
                  panel.add(buttons.get(id));
             }
    
             String[] tags = new String[]{
                    
                    "1: ","2: ","3: "
                };
                JLabel title;
                title = new JLabel("Interest to search for") ;
                JLabel title2;
                title2 = new JLabel(": ") ;
                HashMap<String, JLabel> labels = createLabels(tags);
                HashMap<String, JTextField> fields = createTextFields(tags);
                panel.add(title);
                panel.add(title2);
                for(String tag: tags) {
    
                    panel.add(labels.get(tag));
                    panel.add(fields.get(tag));
                }
    
    
             buttons.get("Back").addActionListener(ignored -> {
      
                  showContent(searchByInterest());
              });
            
              
            buttons.get("Search").addActionListener(ignored -> {
                String searches = fields.get("1: ").getText();
                searches += ",";
                searches += fields.get("2: ").getText();
                searches += ",";
                searches += fields.get("3: ").getText();
                
                List<SearchRecord> searchRecords = dl.searchByFacultyInterest(searches);
                
    
    
                showContent(searchResults(searchRecords, 2));
            });
    
          return panel;
            
          }
    //////////////////////////////////////////////////////////////////////////////////////////
    public JPanel ERROR() {
        JPanel panel = new JPanel(new GridLayout(3,1));
        
        String[] ids = new String[]{
            "Bad",
            "Main Menu"
        };
        
        HashMap<String, JButton> buttons = createButtons(ids);
        for(String id: ids) {
              panel.add(buttons.get(id));
         }
         
        
          buttons.get("Main Menu").addActionListener(ignored -> {
  
            showContent(mainMenu());
        });
          
      
      return panel;
        
      }
    //////////////////////////////////////////////////////////////////////////////////////////
    public JPanel searchByName() {
        JPanel panel = new JPanel(new GridLayout(3,1));
        
        String[] ids = new String[]{
            "Main Menu",
            "Search Students",
            "Search Faculty"
        };
  
            HashMap<String, JButton> buttons = createButtons(ids);
            for(String id: ids) {
                panel.add(buttons.get(id));
            }
   
            buttons.get("Main Menu").addActionListener(ignored -> {

            showContent(mainMenu());
            });
  
            buttons.get("Search Students").addActionListener(ignored -> {

            showContent(searchStudentByName());
            });

            buttons.get("Search Faculty").addActionListener(ignored -> {

                
            showContent(searchFacultyByName());
            });
    
      return panel;
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    public JPanel nameSearchResults(List<SearchRecord> searchRecords, int type) {
        JPanel panel = new JPanel(new GridLayout(0,3));
        String Interesttz = "  ";

        String[] ids = new String[]{
            "Back",
            "Search again",
            "Main Menu"
        };
        
        HashMap<String, JButton> buttons = createButtons(ids);
        for(String id: ids) {
              panel.add(buttons.get(id));
         }

         buttons.get("Back").addActionListener(ignored -> {
  
            showContent(searchByName());
        });
        buttons.get("Search again").addActionListener(ignored -> {
            if(type ==1){
                showContent(searchStudentByName());
            }
            if(type ==2){
                showContent(searchFacultyByName());
            }
            if(type ==3){
                showContent(ERROR());
            }


        });
        buttons.get("Main Menu").addActionListener(ignored -> {

            showContent(mainMenu());
        });

            String[] outputs = new String[]{
                
                "Account ID, ","Name, ","Interest "
            };
            
            HashMap<String, JLabel> labels2 = createLabels(outputs);
            
            for(String tag: outputs) {
                panel.add(labels2.get(tag));
            }
            
            

            for (SearchRecord ser : searchRecords){
                
                
                String[] output1 = new String[]{
                        String.valueOf(ser.getAccountID()), ser.getName()
                        
                        
                    };
                    //System.out.println(String.valueOf(ser.getAccountID())+ ser.getName());
                    HashMap<String, JLabel> labels3 = createLabels(output1);
                    for(String tag: output1) {
                            panel.add(labels3.get(tag));
                    }
                    
                    if( type == 1){
                        List<Interest> interestz = dl.getStudentInterests(ser.getAccountID());

                        for (Interest i : interestz){
                            Interesttz += i.getInterest() + ", ";
                        }
                        
                        if(Interesttz.length() != 0)
                            Interesttz = Interesttz.substring(0, (Interesttz.length() - 2));

                        JLabel interestLabel = new JLabel(Interesttz);
                        panel.add(interestLabel);

                        Interesttz = "";

                    }
                    if( type == 2){
                        List<Interest> interestz = dl.getFacultyInterests(ser.getAccountID());

                        for (Interest i : interestz){
                            Interesttz += i.getInterest() + ", ";
                        }

                        if(Interesttz.length() != 0)
                            Interesttz = Interesttz.substring(0, (Interesttz.length() - 2));

                        JLabel interestLabel = new JLabel(Interesttz);
                        panel.add(interestLabel);

                        Interesttz = "";
                        
                    }

            }
            
        return panel;
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    public JPanel searchFacultyByName() {
        JPanel panel = new JPanel(new GridLayout(5,2));
        
        String[] ids = new String[]{
            "Back",
            "Search",
        };
        
        HashMap<String, JButton> buttons = createButtons(ids);
        for(String id: ids) {
              panel.add(buttons.get(id));
         }

         String[] tags = new String[]{
                
                "First name: ","Last name: "
            };

            HashMap<String, JLabel> labels = createLabels(tags);
            HashMap<String, JTextField> fields = createTextFields(tags);

            for(String tag: tags) {

                panel.add(labels.get(tag));
                panel.add(fields.get(tag));
            }


         buttons.get("Back").addActionListener(ignored -> {
  
              showContent(searchByName());
          });
        
          
        buttons.get("Search").addActionListener(ignored -> {
            String searches = fields.get("First name: ").getText();
            searches += " ";
            searches += fields.get("Last name: ").getText();

            
            List<SearchRecord> searchRecords = dl.searchByFacultyName(searches);
            
            


            showContent(nameSearchResults(searchRecords, 2));
        });
        this.frame.pack();
      return panel;
        
      }
    //////////////////////////////////////////////////////////////////////////////////////////
    public JPanel searchStudentByName() {
        JPanel panel = new JPanel(new GridLayout(5,2));
        
        String[] ids = new String[]{
            "Back",
            "Search",
        };
        
        HashMap<String, JButton> buttons = createButtons(ids);
        for(String id: ids) {
              panel.add(buttons.get(id));
         }

         String[] tags = new String[]{
                
                "First name: ","Last name: "
            };

            HashMap<String, JLabel> labels = createLabels(tags);
            HashMap<String, JTextField> fields = createTextFields(tags);

            for(String tag: tags) {

                panel.add(labels.get(tag));
                panel.add(fields.get(tag));
            }


         buttons.get("Back").addActionListener(ignored -> {
  
              showContent(searchByName());
          });
        
          
        buttons.get("Search").addActionListener(ignored -> {
            String searches = fields.get("First name: ").getText();
            searches += " ";
            searches += fields.get("Last name: ").getText();
            List<SearchRecord> searchRecords = dl.searchByStudentName(searches);
            


            showContent(nameSearchResults(searchRecords, 1));
        });
        this.frame.pack();
      return panel;
        
      }
      ///////
      public JPanel abstractSearchResults(List<SearchRecord> searchRecords, String title) {
        JPanel panel = new JPanel(new GridLayout(0,3));
        

        String[] ids = new String[]{
            "Back",
            "Search again",
            "Main Menu"
        };
        
        HashMap<String, JButton> buttons = createButtons(ids);
        for(String id: ids) {
              panel.add(buttons.get(id));
         }

         buttons.get("Back").addActionListener(ignored -> {
  
            showContent(searchByFaculty());
        });
   
        buttons.get("Main Menu").addActionListener(ignored -> {

            showContent(mainMenu());
        });

            String[] outputs = new String[]{
                
                "Account ID, ","Name", "Abstract Title"
            };
            
            HashMap<String, JLabel> labels2 = createLabels(outputs);
            
            for(String tag: outputs) {
                panel.add(labels2.get(tag));
            }
            
            

            for (SearchRecord ser : searchRecords){
                
                
                String[] output1 = new String[]{
                        String.valueOf(ser.getAccountID()), ser.getName(), title

                        
                        
                    };
                    //System.out.println(String.valueOf(ser.getAccountID())+ ser.getName());
                    HashMap<String, JLabel> labels3 = createLabels(output1);
                    for(String tag: output1) {
                            panel.add(labels3.get(tag));
                    }
                    
                    
                    

                    

                    
               

            }
            
        return panel;
    }
   ////////////////////////////////////////////////////////////////////////////////////////// 
    public JPanel searchByFaculty() {
        JPanel panel = new JPanel(new GridLayout(1,1));
        
        JTextField field = new JTextField("");
        panel.add(field);

        showPopup("Search By Faculty Abstract", panel);

        //validate
        if(field.getText().equals("")){

            showPopupError("Invalid Abstract Name!");
            return mainMenu();
        }
        //records returned
       
        List<SearchRecord> records = this.dl.searchByFacultyAbstract(field.getText());

        //this is the panel to send back
        panel = new JPanel(new GridLayout(0, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        JButton back = new JButton("Back");

        panel.add(back);

        //spacer
        for(int i = 0; i < 9; i++) {

            panel.add(new JLabel());
        }
        String[] outputs = new String[]{
                
            "Name","Abstract Title"
        };
        HashMap<String, JLabel> labels2 = createLabels(outputs);
            
        for(String tag: outputs) {
            panel.add(labels2.get(tag));
        }

        back.addActionListener(ignored -> {

            showContent(mainMenu());
        });
        //spacer
        for(int i = 0; i < 3; i++) {

            panel.add(new JLabel());
        }
        for(SearchRecord record: records) {

            
            JLabel name = new JLabel(String.format("%-5s%s", "[" + record.getAccountID() + "]", record.getName()));
            name.setBorder(new EmptyBorder(0, 0, 0, 15));

            panel.add(name);
            JLabel absname = new JLabel(field.getText());
            panel.add(absname);
                    

            JButton interestBtn = new JButton("Interests");
            JButton abstractBtn = new JButton("Abstracts");
            JButton infoBtn = new JButton("Info");

            panel.add(interestBtn);
            panel.add(abstractBtn);
            panel.add(infoBtn);

            interestBtn.addActionListener(ignored -> {

                showInterestsPopup(record.getAccountID(), record.getName(), 2);
            });

            abstractBtn.addActionListener(ignored -> {

                showFacultyAbstractPopup(record.getAccountID(), record.getName());
            });

            infoBtn.addActionListener(ignored -> {

                showFacultyInfoPopup(record.getAccountID(), record.getName());
            });
        }
       return panel;   
    }
   //////////////////////////////////////////////////////////////////////////////////////////
    
//////////////////////////////////////////////////////////////////////////////////////////
public JPanel idSearchResults(List<SearchRecord> searchRecords, int type, List<Interest> interestz) {
    JPanel panel = new JPanel(new GridLayout(0,3));
    

    String[] ids = new String[]{
        "Back",
        "Search again",
        "Main Menu"
    };
    
    HashMap<String, JButton> buttons = createButtons(ids);
    for(String id: ids) {
          panel.add(buttons.get(id));
     }

     buttons.get("Back").addActionListener(ignored -> {

        showContent(mainMenu());
    });
    buttons.get("Search again").addActionListener(ignored -> {
        
            showContent(searchByID());

    });
    buttons.get("Main Menu").addActionListener(ignored -> {

        showContent(mainMenu());
    });

        String[] outputs = new String[]{
            
            "Account ID, ","Name, ","Interest "
        };
        
        HashMap<String, JLabel> labels2 = createLabels(outputs);
        
        for(String tag: outputs) {
            panel.add(labels2.get(tag));
        }
        
        

        for (SearchRecord ser : searchRecords){
            
            String[] output1 = new String[]{
                String.valueOf(ser.getAccountID()), ser.getName()
                
                
            };
            //System.out.println(String.valueOf(ser.getAccountID())+ ser.getName());
            HashMap<String, JLabel> labels3 = createLabels(output1);
            for(String tag: output1) {
                panel.add(labels3.get(tag));
            }
            

        }
        String Interesttz = "  ";
        for (Interest i : interestz) {
            Interesttz += i.getInterest();
            Interesttz += ", ";
        

        }
        Interesttz = Interesttz.substring(0, (Interesttz.length() - 2));
        String[] output1 = new String[]{
            Interesttz
        };
        
        HashMap<String, JLabel> labels3 = createLabels(output1);
        for(String tag: output1) {
            panel.add(labels3.get(tag));
        }

        this.frame.pack();
    return panel;
}
//////////////////////////////////////////////////////////////////////////////////////////
public JPanel searchByID() {
    JPanel panel = new JPanel(new GridLayout(5,2));
    
    String[] ids = new String[]{
        "Main Menu",
        "Search",
    };
    
    HashMap<String, JButton> buttons = createButtons(ids);
    for(String id: ids) {
          panel.add(buttons.get(id));
     }

     String[] tags = new String[]{
            
            "ID: "
        };

        HashMap<String, JLabel> labels = createLabels(tags);
        HashMap<String, JTextField> fields = createTextFields(tags);

        for(String tag: tags) {

            panel.add(labels.get(tag));
            panel.add(fields.get(tag));
        }


     buttons.get("Main Menu").addActionListener(ignored -> {

          showContent(mainMenu());
      });
    
      
    buttons.get("Search").addActionListener(ignored -> {
        try{
            int search = Integer.parseInt(fields.get("ID: ").getText());
            

            List<SearchRecord> searchRecords = dl.searchById(search);
            
            List<Interest> interesting = dl.getFacultyInterests(search);


            showContent(idSearchResults(searchRecords, 2, interesting));
        }
        catch(Exception e){
            showPopup("Please input a valid Integer for ID");
            showContent(searchByID());
        }
    });
  return panel;
    
  }

//////////////////////////////////////////////////////////////////////////////////////////


    

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
        //get rid of root, change passwordfield text to nothing//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        fields.get("Username").setText("root");
        fields.replace("Password", new JPasswordField("student"));

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
     * Search By Faculty Name (MILES KRASSEN EXAMPLE)
     * @return JPanel
     */
    public JPanel searchFacultyInfo() {

        //setup popup panel
        JPanel panel = new JPanel(new GridLayout(1,1));
        
        JTextField field = new JTextField("");
        panel.add(field);

        showPopup("Search By Faculty Name", panel);

        //validate
        if(field.getText().equals("")){

            showPopupError("Invalid Name!");
            return mainMenu();
        }
        //records returned
        List<SearchRecord> records = this.dl.searchByFacultyName(field.getText());

        //this is the panel to send back
        panel = new JPanel(new GridLayout(0, 4));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        JButton back = new JButton("Back");

        panel.add(back);

        //spacer
        for(int i = 0; i < 7; i++) {

            panel.add(new JLabel());
        }

        back.addActionListener(ignored -> {

            showContent(mainMenu());
        });

        for(SearchRecord record: records) {

            JLabel name = new JLabel(String.format("%-5s%s", "[" + record.getAccountID() + "]", record.getName()));
            name.setBorder(new EmptyBorder(0, 0, 0, 15));

            panel.add(name);

            JButton interestBtn = new JButton("Interests");
            JButton abstractBtn = new JButton("Abstracts");
            JButton infoBtn = new JButton("Info");

            panel.add(interestBtn);
            panel.add(abstractBtn);
            panel.add(infoBtn);

            interestBtn.addActionListener(ignored -> {

                showInterestsPopup(record.getAccountID(), record.getName(), 2);
            });

            abstractBtn.addActionListener(ignored -> {

                showFacultyAbstractPopup(record.getAccountID(), record.getName());
            });

            infoBtn.addActionListener(ignored -> {

                showFacultyInfoPopup(record.getAccountID(), record.getName());
            });
        }

        return panel;
    }

    public void showFacultyInfoPopup(int accountID, String name) {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        Office location = this.dl.getOffice(accountID);
        Contact contact = this.dl.getContact(accountID);

        panel.add(new JLabel("Email: " + contact.getEmail()));
        panel.add(new JLabel("Phone: " + contact.getPhone()));
        panel.add(new JLabel("Building: " + location.getBuilding()));
        panel.add(new JLabel("Office: " + location.getNumber()));

        showPopup(name + " - Info", panel);
    }

    /**
     * Show Interests Popup (MILES KRASSEN EXAMPLE)
     * @param accountID
     * @param name
     * @param role
     */
    public void showInterestsPopup(int accountID, String name, int role) {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        List<Interest> interests = switch (role) {
            case 1 -> this.dl.getStudentInterests(accountID);
            case 2 -> this.dl.getFacultyInterests(accountID);
            default -> this.dl.getGuestInterests(accountID);
        };

        String output = "";

        for(Interest interest: interests) {

            output += interest.getInterest() + ", ";
        }

        if(output.length() != 0)
            output = output.substring(0, output.length() - 2);

        panel.add(new JLabel(output));

        showPopup(name + " - Interests", panel);
    }

    /**
     * Show Faculty Abstract Popup (MILES KRASSEN EXAMPLE)
     * @param accountID
     * @param name
     */
    public void showFacultyAbstractPopup(int accountID, String name) {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(8, 8, 10, 15));
        panel.setBackground(Color.WHITE);

        List<Abstract> abstracts = this.dl.getFacultyAbstracts(accountID);

        for(Abstract anAbstract: abstracts) {

            JPanel titleWrapper = new JPanel(new BorderLayout());
            titleWrapper.setBorder(new EmptyBorder(8, 8, 0, 8));
            titleWrapper.setBackground(Color.WHITE);
            titleWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel title = new JLabel(anAbstract.getTitle());
            titleWrapper.add(title, BorderLayout.PAGE_START);

            panel.add(titleWrapper);

            JTextArea body = new JTextArea(anAbstract.getBody());
            body.setEditable(false);
            body.setLineWrap(true);
            body.setWrapStyleWord(true);
            body.setAlignmentX(Component.LEFT_ALIGNMENT);

            body.setBorder(new EmptyBorder(4,8,8,8));

            panel.add(body);
        }

        JPanel wrapper = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        wrapper.add(panel, constraints);

        JScrollPane scrollPane = new JScrollPane(wrapper, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        showPopup(name + " - Abstracts", scrollPane);
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

        //createRadioPanel() adds and formats the radio buttons into their own panel
        showPopup("Pick a Role", createRadioPanel(roleRadioButtons));

        if(getSelectedRadioValue(roleRadioButtons).equals("")) {

            showPopupError("Invalid Choice", "Must Select a Role!");
            return loginMenu();
        }

        //choose preferred contact radio button popup
        HashMap<String, JRadioButton> preferredContactRadioButtons = createRadioButtons(new String[]{"Phone", "Email"});

        ButtonGroup preferredContactGroup = createButtonGroup(preferredContactRadioButtons);//needed for grouping radio buttons, not used anywhere else.

        //createRadioPanel() adds and formats the radio buttons into their own panel
//        showPopup("Pick a Preferred Contact Method", createRadioPanel(preferredContactRadioButtons));

//        if(getSelectedRadioValue(preferredContactRadioButtons).equals("")) {
//
//            showPopupError("Invalid Choice", "Must Select a Role!");
//            return loginMenu();
//        }

        //tags used as IDs for and labels for creating JLabels and JTextFields
        String[] tags = new String[]{
                "First Name",
                "Last Name",
                "Email",
                "Phone Number",
                "Preferred Contact Method",
                "Building",
                "Office Number",
                "New Password"
        };

        HashMap<String, JLabel> labels = createLabels(tags);
        HashMap<String, JTextField> fields = createTextFields(tags);

        fields.replace("New Password", new JPasswordField(""));

        for(String id: tags) {

            //if not faculty, don't add these choices to the panel
            if(!getSelectedRadioValue(roleRadioButtons).equals("Faculty") && (id.equals("Building") || id.equals("Office Number")))
                continue;

            panel.add(labels.get(id));

            if(!id.equals("Preferred Contact Method")){

                panel.add(fields.get(id));

            }else {

                panel.add(createRadioPanel(preferredContactRadioButtons));
            }
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

            if(preferredContact.equals("")) {

                showPopupError("Invalid Choice", "Must Select a Preferred Contact Method!");
                return;
            }

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
