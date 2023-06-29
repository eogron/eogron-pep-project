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
        if (account.username == "" || account.password.length() < 4) {
            return null;
        } else {
            return accountDAO.addAccount(account);
        }
    }

    public Account login(Account acc) {
        Account login_acc = accountDAO.getAccountByUsername(acc.username);
        if (login_acc == null) {
            return null;
        }

        if (login_acc.password.equals(acc.password)) {
            return login_acc;
        } else {
            return null;
        }
    }
}