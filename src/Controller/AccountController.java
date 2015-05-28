package Controller;

import java.util.Iterator;

import objectSave.ObjectSaveSingleton;
import Account.Account;
import Account.ProfessorAccount;
import Account.StudentAccount;
import Assignment.Subject;

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
	
	public Subject searchSubject(String professorName, String subjectName) {
		Iterator checkAccount = accountList.getAccounts().iterator();
		Account temp;
		while(checkAccount.hasNext()) {
			temp = (Account)checkAccount.next();
			if(temp.isProfessor() && temp.getName().equals(professorName) &&
					((ProfessorAccount)temp).getSubject().getName().equals(subjectName))
				return ((ProfessorAccount)temp).getSubject();
		}
		return null;
	}
}
