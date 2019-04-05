public class Admin {
    private Screen screen;
    private Keypad keypad;
    private CashDispenser cashDispenser;
    private BankDatabase bankDatabase;
    
    private static final int UNBLOCK = 1;
    private static final int VIEW_DISPENSER = 2;
    private static final int FILL_DISPENSER = 3;
    private static final int EXIT = 4;
    
    public Admin (BankDatabase theBankDatabase, CashDispenser theCashDispenser){
        screen = new Screen();
        keypad = new Keypad();
        bankDatabase = theBankDatabase;
        cashDispenser = theCashDispenser;
    }
    
    //admin password
    private static final int PIN = 2971;

    public void runAdmin(){
        screen.displayMessage("\nEnter your PIN: ");
        int inputPIN = keypad.getInput();
        
        if (isAdmin(inputPIN)){
            performAdminMode();
        }
        else {
            screen.displayMessageLine("\nSorry you're not an admin"
                    + "\nYoure'not alowwed to admin mode");
        }
    }
    
    private boolean isAdmin(int adminPIN){
        return adminPIN == PIN;
    }
    
    private int viewAdminMenu(){
        screen.displayMessageLine("\nChoose one of this menu");
        screen.displayMessageLine("1 - Unblock a user");
        screen.displayMessageLine("2 - View Cash Dispenser");
        screen.displayMessageLine("3 - Fill Cash Dispenser");
        screen.displayMessageLine("4 - Exit\n");
        screen.displayMessage("Enter a choice: ");
        return keypad.getInput();
    }
    
    private void performAdminMode(){
        boolean adminExited = false;
        
        while (!adminExited){
            int adminMenuSelection = viewAdminMenu();
            
            switch(adminMenuSelection){
                case UNBLOCK: {
                    unblockUser();
                    break;
                }
                case VIEW_DISPENSER: {
                    viewRemainCash();
                    break;
                }
                case FILL_DISPENSER: {
                    fillCashDispenser();
                    break;
                }
                case EXIT: {
                    screen.displayMessageLine("\nExiting admin mode...");
                    adminExited = true;
                    break;
                }
            }
        }
    }
    
    private void viewRemainCash(){
        screen.displayMessageLine("\nRemaining $20 in the dispenser: "
                    + cashDispenser.getRemainCash());
    }
    
    private void fillCashDispenser(){
        screen.displayMessage("\nPlease enter amount of $20 you want to fill: ");
        int fillAmount = keypad.getInput();
        
        cashDispenser.fillDispenser(fillAmount);
        screen.displayMessageLine("\nDispenser filled with " + fillAmount 
                + " amount of $20");
    }
    
    private void unblockUser(){
        screen.displayMessage("\nEnter user account number: ");
        int userAccountNumber = keypad.getInput();
        
        if (bankDatabase.userExist(userAccountNumber)){
            bankDatabase.unblockUser(userAccountNumber);
            screen.displayMessageLine("Unblocking user ... ");
            screen.displayMessageLine("User unblocked!");
        }
        else {
            screen.displayMessageLine("User doesn't exist!");
        }
    }
}
