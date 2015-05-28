package Account;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;

import common.Permission;

public class Account implements Serializable{
	
	private String id;
	private String name;
	private String password;
	private InetAddress clientAddress;
	private int numberOfNotice;
	private Permission permission; // Student 0, Professor 1
	private boolean onLine = false; // Determine whether account is on-line
	private ArrayList<Account> accounts;
	
	public Account() {
		this.accounts = new ArrayList<Account>();
	}
	
	// 21 MAR 2015 Tuna
	// change 4th parameter "int permission" to "Permission permission"
	public Account(String id, String password, String name, Permission permission) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.permission = permission;
		this.accounts = new ArrayList<Account>();
		this.numberOfNotice = 0;
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
	
	// 21 MAR 2015 Tuna
	// Change parameter "int a" to "Permission permission"
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
	public boolean setPassword(String oldPassword, String newPassword) {
		if(this.password == oldPassword) {
			this.password = newPassword;
			return true;
		}
		else
			return false;
	}
	
	// 21 MAR 2015 Tuna
	// change (int)0 to (enum)PermissionType.STUDENT
	public boolean isStudent() {
		if(this.permission == Permission.STUDENT)
			return true;
		else
			return false;
	}
	
	// 21 MAR 2015 Tuna
	// change (int)1 to (enum)PermissonType.PROFESSOR
	public boolean isProfessor() {
		if(this.permission == Permission.PROFESSOR)
			return true;
		else
			return false;
	}
	
	// 21 MAR 2015 Tuna
	// Change parameter "String string" to "String password"
	public boolean checkPassword(String password) {
		if(this.password.equals(password))
			return true;
		else
			return false;
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	
	public boolean isOnLine() {
		return onLine;
	}

	public void setOnLine(boolean onLine) {
		this.onLine = onLine;
	}

	public InetAddress getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(InetAddress clientAddress) {
		this.clientAddress = clientAddress;
	}

	/**
	 * haveNotice() : Declare whether notice is exist, return number of users notice
	 */
	public int haveNotice() {
		return numberOfNotice;
	}
}
