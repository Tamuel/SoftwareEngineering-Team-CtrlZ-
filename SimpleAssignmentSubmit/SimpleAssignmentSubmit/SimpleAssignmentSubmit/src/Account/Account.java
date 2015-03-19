package Account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Account implements Serializable{
	private String id;
	private String name;
	private String password;
	private int permission; // Student 0, Professor 1
	private ArrayList<Account> accounts;
	
	public Account() {
		this.accounts = new ArrayList<Account>();
	}
	
	public Account(String id, String password, String name, int permission) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.permission = permission;
		this.accounts = new ArrayList<Account>();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String string) {
		id = string;
	}
	
	public ArrayList<Account> getAccounts() {
		return accounts;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String string) {
		name = string;
	}
	
	public void setPermission(int a) {
		this.permission = a;
	}
	
	public boolean setPassword(String oldPassword, String newPassword) {
		if(this.password == oldPassword) {
			this.password = newPassword;
			return true;
		}
		else
			return false;
	}
	
	public boolean isStudent() {
		if(this.permission == 0)
			return true;
		else
			return false;
	}
	
	public boolean isProfessor() {
		if(this.permission == 1)
			return true;
		else
			return false;
	}
	
	public boolean checkPassword(String string) {
		if(this.password.equals(string))
			return true;
		else
			return false;
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	public boolean checkIdRepeated(String id) {
		Iterator checkAccount = accounts.iterator();
		while(checkAccount.hasNext()) {
			if(((Account)checkAccount.next()).getId().equals(id))
				return false;
		}
		return true;
	}
	
	public Account searchAccount(String id, String password) {
		Iterator checkAccount = accounts.iterator();
		Account temp;
		while(checkAccount.hasNext()) {
			temp = (Account)checkAccount.next();
			if(temp.getId().equals(id) && temp.checkPassword(password))
				return temp;
		}
		return null;
	}
}
