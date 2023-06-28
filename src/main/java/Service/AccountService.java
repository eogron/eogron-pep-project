package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account createAccount(Account account) {
        if (account.username == "" 
                || account.password.length() < 4
                || accountDAO.getAccountByUsername(account.username) != null) {
            return null;
        }
        return accountDAO.addAccount(account);
    }
}