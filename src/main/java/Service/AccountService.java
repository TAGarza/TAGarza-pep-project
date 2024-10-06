package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accDAO;

    // creates an AccountDAO
    public AccountService(){
        accDAO = new AccountDAO();
    }

    // constructor for when AccountDAO is provided
    public AccountService(AccountDAO accDAO){
        this.accDAO = accDAO;
    }

    // create new account
    public Account createAccount(Account account){
        // make sure the account with that username doesn't already exist
        // make sure pass is at least 4 chars long
        // make sure username is not blank
        if(account.getUsername() != "" && account.getPassword().length() >= 4){
            return accDAO.createAccount(account);
        }
        return null;

    }
    public Account verifyAccount(Account account){
        // username and pass should match in order to verify
        /*if(accDAO.retrieveAccount(account.getUsername(), account.getPassword()) == null){
            return null;
        }*/
        //System.out.println("account id: " + account.getAccount_id() + "\naccount user: " + account.getUsername() + "\naccount password: " + account.getPassword());
        return accDAO.retrieveAccount(account);
    }
}
