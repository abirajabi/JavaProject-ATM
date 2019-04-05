// Withdrawal.java
// Represents a withdrawal ATM transaction

public class Withdrawal extends Transaction {
   private int amount;      // amount to withdraw
   private Keypad keypad;   // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser

   // constant corresponding to menu option to cancel
   private final static int CANCELED = 6;

   // Withdrawal constructor
   public Withdrawal(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      CashDispenser atmCashDispenser) {

      // initialize superclass variables
      super(userAccountNumber, atmScreen, atmBankDatabase);
      keypad = atmKeypad;
      cashDispenser = atmCashDispenser;
   }

   // perform transaction
   @Override
   public void execute() {
       BankDatabase bankDatabase = getBankDatabase();
       Screen screen = getScreen();
       amount = displayMenuOfAmounts();
       
       if (!bankDatabase.maxWithdraw(getAccountNumber())){
            if (amount != 0){
                if (cashDispenser.isSufficientCashAvailable(amount)){

                     cashDispenser.dispenseCash(amount);
                     if (bankDatabase.getAvailableBalance(getAccountNumber())
                             >= amount){
                        bankDatabase.credit(getAccountNumber(), amount);
                     screen.displayMessageLine("\nYour cash has been dispended. Please"
                             + " take your cash now."); 
                     }
                     else {
                         screen.displayMessageLine("\nInsufficient funds in your account"
                                 + "\nPlease choose a smaller amount");
                     }
                }
            }
        }
       else {
           screen.displayMessageLine("\nSorry you have withdraw $200 today ...");
       }
       
    } 

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   private int displayMenuOfAmounts() {
      int userChoice = 0;           // local variable to store return value

      Screen screen = getScreen();  // get screen reference
      
      // array of amounts to correspond to menu numbers
      int[] amounts = {0, 20, 40, 60, 100, 200};

      // loop while no valid choice has been made
      while (userChoice == 0) {
         // display the withdrawal menu
         screen.displayMessageLine("\nWithdrawal Menu:");
         screen.displayMessageLine("1 - $20");
         screen.displayMessageLine("2 - $40");
         screen.displayMessageLine("3 - $60");
         screen.displayMessageLine("4 - $100");
         screen.displayMessageLine("5 - $200");
         screen.displayMessageLine("6 - Cancel transaction");
         screen.displayMessage("\nChoose a withdrawal amount: ");

         int input = keypad.getInput(); // get user input through keypad

         // determine how to proceed based on the input value
         // if the user chose a withdrawal amount
         // (i.e., chose option 1, 2, 3, 4 or 5), return the
         // corresponding amount from amounts array
         
         switch (input) {
            case 1: {
                userChoice = amounts[input];
                break;
            }
            case 2: {
                userChoice = amounts[input];
                break;
            }
            case 3: {
                userChoice = amounts[input];
                break;
            }
            case 4: {
                userChoice = amounts[input];
                break;
            }
            case 5:
                userChoice = amounts[input];
                break;       
            case CANCELED:                  // the user chose to cancel
               userChoice = CANCELED;       // save user's choice
               screen.displayMessageLine("\nCanceling transaction...");
               break;
            default: // the user did not enter a value from 1-6
               screen.displayMessageLine(
                  "\nInvalid selection. Try again.");
         } 
      }
      
      if (userChoice != 6){
          return userChoice;
      }
      return 0;
   }

} 