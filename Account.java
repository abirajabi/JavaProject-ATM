public class Account {
   private int accountNumber; // account number
   private int pin; // PIN for authentication
   private double availableBalance; // funds available for withdrawal
   private double totalBalance; // funds available & pending deposits
   private byte countFalse; //count false while login
   private double withdrawAmount; //max withdraw once program run

   // Account constructor initializes attributes
   public Account(int theAccountNumber, int thePIN, 
      double theAvailableBalance, double theTotalBalance, byte falseNumber
        ,double theWithdrawAmount) {
      accountNumber = theAccountNumber;
      pin = thePIN;
      availableBalance = theAvailableBalance;
      totalBalance = theTotalBalance;
      countFalse = falseNumber;
      withdrawAmount = theWithdrawAmount;
   }

   // determines whether a user-specified PIN matches PIN in Account
   public boolean validatePIN(int userPIN) {
      if (userPIN == pin) {
         return true;
      }
      else {
         return false;
      }
   } 

   // returns available balance
   public double getAvailableBalance() {
      return availableBalance;
   } 

   // returns the total balance
   public double getTotalBalance() {
      return totalBalance;
   } 

   public void credit(double amount) {
     totalBalance = totalBalance - amount;
     availableBalance = availableBalance - amount;
     withdrawAmount += amount;
   }

   public void debit(double amount) {
     totalBalance += amount;
   }

   public int getAccountNumber() {
      return accountNumber;  
   }
   
   public int getCurrentPin(){
       return pin;
   }
   
   public void setNewPin(int newPinNumber){
       pin = newPinNumber;
   }
   
   public byte getFalseNumber(){
      return countFalse;
   }
   
   public void resetFalse(){
       countFalse = 0;
   }
   
   public void incrementFalse(){
      countFalse++; 
   }
   
   public void transferDebit (double amount){
       totalBalance += amount;
       availableBalance += amount;
   }
   
   public double hasWithdraw(){
       return withdrawAmount;
   }
} 