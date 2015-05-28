package Controller;

import java.util.Iterator;

import objectSave.ObjectSaveSingleton;
import Account.Account;
import Account.StudentAccount;

public class AccountController {
	Account accountList;
	
	
	public AccountController(Account account) {
		this.accountList = account;
	}

	// 21 MAR 2015 Tuna
	// add a comment
	/**
	 * if there exists a duplicated id then return false
	 * **/ 
	public boolean checkIdRepeated(String id) {
		Iterator checkAccount = accountList.getAccounts().iterator();
		while(checkAccount.hasNext()) {
			if(((Account)checkAccount.next()).getId().equals(id))
				return false;
		}
		return true;
	}
	
	public boolean changeAccount(Account changeAccount) {
		Account temp = searchAccountByID(changeAccount.getId());
		temp = changeAccount;
		System.out.println("»ðÀÔ °ú¸ñ °¹¼ö " + ((StudentAccount)changeAccount).getSubjects().size());
		System.out.println("°¡Á®¿Â °ú¸ñ °¹¼ö " + ((StudentAccount)temp).getSubjects().size());
		ObjectSaveSingleton.getInstance().saveAccounts();
		return true;
	}
	
	public Account searchAccount(String id, String password) {
		Iterator checkAccount = accountList.getAccounts().iterator();
		Account temp;
		while(checkAccount.hasNext()) {
			temp = (Account)checkAccount.next();
			if(temp.getId().equals(id) && temp.checkPassword(password))
				return temp;
		}
		return null;
	}
	
	public Account searchAccountByID(String id) {
		Iterator checkAccount = accountList.getAccounts().iterator();
		Account temp;
		while(checkAccount.hasNext()) {
			temp = (Account)checkAccount.next();
			if(temp.getId().equals(id))
				return temp;
		}
		return null;
	}
}
