public class ATM {
   private boolean userAuthenticated; // whether user is authenticated
   private int currentAccountNumber; // current user's account number
   private Screen screen; // ATM's screen
   private Keypad keypad; // ATM's keypad
   private CashDispenser cashDispenser; // ATM's cash dispenser
   private BankDatabase bankDatabase; // account information database
   private DepositSlot atmDepositSlot; //deposit slot
   private Admin theAdmin;

   // constants corresponding to main menu options
   private static final int BALANCE_INQUIRY = 1;
   private static final int WITHDRAWAL = 2;
   private static final int DEPOSIT = 3;
   private static final int CHANGE_PIN = 4;
   private static final int TRANSFER = 5;
   private static final int EXIT = 6;

   // no-argument ATM constructor initializes instance variables
   public ATM() {
      userAuthenticated = false; // user is not authenticated to start
      currentAccountNumber = 0; // no current account number to start
      screen = new Screen(); // create screen
      keypad = new Keypad(); // create keypad 
      cashDispenser = new CashDispenser(); // create cash dispenser
      bankDatabase = new BankDatabase(); // create acct info database
      atmDepositSlot = new DepositSlot(); //create deposit slot
      theAdmin = new Admin(bankDatabase, cashDispenser);
   }

   // start ATM 
   public void run() {
      // welcome and authenticate user; perform transactions
      while (true) {
         // loop while user is not yet authenticated
         while (!userAuthenticated) {
            screen.displayMessageLine("\nWelcome!");       
            authenticateUser(); // authenticate user
            //checkUserStatus();
         }
         if (!bankDatabase.blocked(currentAccountNumber) && userAuthenticated){
           performTransactions();
         }
         else {
             screen.displayMessageLine("\nSorry your number was blocked");
         }
         
          // user is now authenticated
         userAuthenticated = false; // reset before next ATM session
         currentAccountNumber = 0; // reset before next ATM session
         screen.displayMessageLine("\nThank you! Goodbye!");
      }
   }
   
   // attempts to authenticate user against database
   private void authenticateUser() {
      screen.displayMessage("\nPlease enter your account number: ");
      int accountNumber = keypad.getInput(); // input account number
      
        if (accountNumber != 0){
            screen.displayMessage("\nEnter your PIN: "); // prompt for PIN
            int pin = keypad.getInput(); // input PIN

            // set userAuthenticated to boolean value returned by database
            userAuthenticated = 
               bankDatabase.authenticateUser(accountNumber, pin);

            // check whether authentication succeeded
            if (userAuthenticated) {
               currentAccountNumber = accountNumber; // save user's account #
            } 
            else {
               screen.displayMessageLine(
                  "Invalid account number or PIN. Please try again.");
               bankDatabase.incrementFalse(accountNumber);
            } 
        }
        else {
            callAdmin();
        }
   } 

   // display the main menu and perform transactions
   private void performTransactions() {
      // local variable to store transaction currently being processed
      Transaction currentTransaction = null;
      
      boolean userExited = false; // user has not chosen to exit
      
      // loop while user has not chosen option to exit system
      while (!userExited) {
         // show main menu and get user selection
         int mainMenuSelection = displayMainMenu();
         

         // decide how to proceed based on user's menu selection
         switch (mainMenuSelection) {
            // user chose to perform one of three transaction types
            case BALANCE_INQUIRY: {
               // initialize as new object of chosen type
               currentTransaction = createTransaction(mainMenuSelection);
               currentTransaction.execute(); // execute transaction
               break; 
            }
            case WITHDRAWAL: {
                currentTransaction = createTransaction(mainMenuSelection);
                currentTransaction.execute();
                break;
            }
            case DEPOSIT: {
                currentTransaction = createTransaction(mainMenuSelection);
                currentTransaction.execute();
                break;
            }
            case CHANGE_PIN:{
                currentTransaction = createTransaction(mainMenuSelection);
                currentTransaction.execute();
                break;
            }
            case TRANSFER: {
                currentTransaction = createTransaction(mainMenuSelection);
                currentTransaction.execute();
                break;
            }
            case EXIT: {
               // user chose to terminate session
               screen.displayMessageLine("\nExiting the system...");
               userExited = true; // this ATM session should end
               break;
            }
            default: // 
               screen.displayMessageLine(
                  "\nYou did not enter a valid selection. Try again.");
               break;
         }
      } 
   } 

   // display the main menu and return an input selection
   private int displayMainMenu() {
      screen.displayMessageLine("\nMain menu:");
      screen.displayMessageLine("1 - View my balance");
      screen.displayMessageLine("2 - Withdraw cash");
      screen.displayMessageLine("3 - Deposit funds");
      screen.displayMessageLine("4 - Change PIN number");
      screen.displayMessageLine("5 - Transfer");
      screen.displayMessageLine("6 - Exit\n");
      screen.displayMessage("Enter a choice: ");
      return keypad.getInput(); // return user's selection
   } 
         
   private Transaction createTransaction(int type) {
      Transaction temp = null; 
          
      switch (type) {
         case BALANCE_INQUIRY: {
            temp = new BalanceInquiry(
               currentAccountNumber, screen, bankDatabase);
            break; 
         }
         case WITHDRAWAL: {
            temp = new Withdrawal(
                currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
            break;
         }
         case DEPOSIT: {
             temp = new Deposit(currentAccountNumber, screen, bankDatabase, keypad
                     , atmDepositSlot);
             break;
         }
         case CHANGE_PIN: {
             temp = new ChangePinNumber(currentAccountNumber, screen, bankDatabase 
                     ,keypad);
             break;
         }
         case TRANSFER: {
             temp = new Transfer(currentAccountNumber, screen 
                     ,bankDatabase, keypad);
             break;
         }
      }
      return temp;
   }
   
   private void callAdmin(){
       theAdmin.runAdmin();
   }
}