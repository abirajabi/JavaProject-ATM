public class BankDatabase {
   private Account[] accounts; // array of Accounts
   
   public BankDatabase() {
      accounts = new Account[2]; // just 2 accounts for testing, 1 admin account
      accounts[0] = new Account(1234, 4321, 1000.0, 1200.0, (byte)0, 0);
      accounts[1] = new Account(8765, 5678, 200.0, 200.0, (byte)0, 0); 
   }
   
   private Account getAccount(int accountNumber) {
      for (int i = 0; i<2; i++){
          if (accountNumber == accounts[i].getAccountNumber()){
              return accounts[i];
          }
      }
      return null; // if no matching account was found, return null
   } 

   public boolean authenticateUser(int userAccountNumber, int userPIN) {
      // attempt to retrieve the account with the account number
      Account userAccount = getAccount(userAccountNumber);

      // if account exists, return result of Account method validatePIN
      if (userAccount != null) {
         return userAccount.validatePIN(userPIN);
      }
      else {
         return false; // account number not found, so return false
      }
   }
   
   public boolean blocked(int userAccountNumber){
       Account userAccount = getAccount(userAccountNumber);
       return userAccount.getFalseNumber() >= 3;
   }
   
   public void unblockUser (int userAccountNumber){
       Account userAccount = getAccount(userAccountNumber);
       userAccount.resetFalse();
   }

   public double getAvailableBalance(int userAccountNumber) {
      return getAccount(userAccountNumber).getAvailableBalance();
   } 

   public double getTotalBalance(int userAccountNumber) {
      return getAccount(userAccountNumber).getTotalBalance();
   } 

   public void credit(int userAccountNumber, double amount) {
      getAccount(userAccountNumber).credit(amount);
   }

   public void debit(int userAccountNumber, double amount) {
      getAccount(userAccountNumber).debit(amount);
   }
   
   public void transferDebit(int userAcoountNumber, double amount){
       getAccount(userAcoountNumber).transferDebit(amount);
   }
   
   public void changePinNumber (int userAccountNumber, int newPinNumber){
       Account userAccount = getAccount(userAccountNumber);
       userAccount.setNewPin(newPinNumber);
   }
   
   public int getCurrentPin(int userAccountNumber){
       return getAccount(userAccountNumber).getCurrentPin();
   }
   
   public void incrementFalse(int userAccountNumber){
       getAccount(userAccountNumber).incrementFalse();
   }
   
   public boolean userExist (int userAccountNumber){
       for (int i = 0; i<2; i++){
           if (userAccountNumber == accounts[i].getAccountNumber()){
               return true;
           }
       }
       return false;
   }
   
   public boolean maxWithdraw(int userAccountNumber){
       return getAccount(userAccountNumber).hasWithdraw() >= 200;
   }
} 