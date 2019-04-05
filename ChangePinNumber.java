public class ChangePinNumber extends Transaction{
    
    private final Keypad keypad;
    
    public ChangePinNumber (int userAccountNumber, Screen atmScreen,
         BankDatabase atmBankDatabase, Keypad atmKeypad){
      
      // initialize superclass variables
      super(userAccountNumber, atmScreen, atmBankDatabase);
      keypad = atmKeypad;
    }
    
    @Override
    public void execute(){
        Screen screen = getScreen();
        boolean pinChanged = false;
        BankDatabase bankDatabase = getBankDatabase();
        
        while (!pinChanged){
            screen.displayMessage("\nEnter your old PIN number: ");
            int userOldPin = keypad.getInput();
            
            if (bankDatabase.authenticateUser(getAccountNumber(), userOldPin)){
                screen.displayMessage("\nEnter your new PIN: ");
                int newPinNumber = keypad.getInput();
                
                if (newPinNumber != bankDatabase.getCurrentPin(getAccountNumber())){
                    bankDatabase.changePinNumber(getAccountNumber() 
                            ,newPinNumber);
                    screen.displayMessageLine("\nYour PIN has changed");
                    screen.displayMessageLine("\nYour PIN now " + 
                            bankDatabase.getCurrentPin(getAccountNumber()));
                    pinChanged = true;
                }
                else {
                    screen.displayMessageLine("\nPIN number cannot same as the "
                            + "old one");
                }
            }
            else {
                screen.displayMessageLine("\nYour old PIN was wrong");
            }
        }
    }
}
