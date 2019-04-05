public class Transfer extends Transaction {
    private Keypad keypad;
    private double transferAmount;
    
    public Transfer(int userAccountNumber, Screen atmScreen
            ,BankDatabase atmBankDatabase, Keypad atmKeypad){
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
    }
    
    private boolean amountIsAvailable(int userAccountNumber, double transferAmount){
        BankDatabase bankDatabase = getBankDatabase();
        return bankDatabase.getAvailableBalance(userAccountNumber) >= transferAmount;
    }
    
    @Override
    public void execute(){

        Screen screen = getScreen(); 
        
        screen.displayMessage("\nEnter recepient account number");
        int recepient = keypad.getInput();
        
        if (recepient != getAccountNumber()){
            if (userExist(recepient)){  
                screen.displayMessage("Enter amount to transfer (in dollar): ");
                transferAmount = keypad.getInput();

                if (transferAmount <= 100){
                    if (amountIsAvailable(getAccountNumber(), transferAmount)){
                        performTransfer(recepient, transferAmount);
                        screen.displayMessageLine("\nTransfer Succesfull...");
                    }
                    else {
                        screen.displayMessageLine("\nMax amount to transfer is 100!");
                    }
                }
                else {
                    screen.displayMessageLine("\nSorry, max transfer amount is $100");
                }
            }
            else {
                screen.displayMessageLine("\nOops ... User doesnt exist!");
            }
        }
        else {
            screen.displayMessageLine("\nCannot transfer to yourself!");
        }
    }
    
    private boolean userExist(int userAccountNumber){
        BankDatabase bankDatabase = getBankDatabase();
        return bankDatabase.userExist(userAccountNumber);
    }
    
    private void performTransfer(int recepient, double transferAmount){
        BankDatabase bankDatabase = getBankDatabase();
        bankDatabase.credit(getAccountNumber(), transferAmount);
        bankDatabase.transferDebit(recepient, transferAmount);
    }
}
