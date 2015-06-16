package Controller;

import java.util.ArrayList;
import java.util.Iterator;

import objectSave.ObjectSaveSingleton;
import Account.Account;
import Account.ProfessorAccount;
import Account.StudentAccount;
import Assignment.Assignment;
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
	
	/**
	 * this searches account by id and password and returns account matched with
	 * @param id
	 * @param password
	 * @return account
	 */
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
	

	public ArrayList<ProfessorAccount> getProfessorAccounts() {
		ArrayList<ProfessorAccount> tempAccounts = new ArrayList<ProfessorAccount>();
		
		Iterator checkAccount = accountList.getAccounts().iterator();
		Account temp;
		while(checkAccount.hasNext()) {
			temp = (Account)checkAccount.next();
			if(temp.isProfessor())
				tempAccounts.add((ProfessorAccount)temp);
		}
		
		return tempAccounts; 
	}
	
	public ArrayList<Assignment> getAssignments() {
		ArrayList<ProfessorAccount> tempAccounts = getProfessorAccounts();
		ArrayList<Assignment> tempAssignments = new ArrayList<Assignment>();
		

		Iterator checkAccount = tempAccounts.iterator();
		Iterator assignment;
		
		ProfessorAccount temp;
		Assignment tempAssignment;
		
		while(checkAccount.hasNext()) {
			temp = (ProfessorAccount)checkAccount.next();
			assignment = temp.getAssignments().iterator();
			
			while(assignment.hasNext()) {
				tempAssignment = (Assignment)assignment.next();
				tempAssignments.add(tempAssignment);
			}
		}
		
		return tempAssignments;
	}
	
	public ArrayList<Assignment> getAssignments(String subject) {
		ArrayList<ProfessorAccount> tempAccounts = getProfessorAccounts();
		ArrayList<Assignment> tempAssignments = new ArrayList<Assignment>();
		

		Iterator checkAccount = tempAccounts.iterator();
		Iterator assignment;
		
		ProfessorAccount temp;
		Assignment tempAssignment;
		
		while(checkAccount.hasNext()) {
			temp = (ProfessorAccount)checkAccount.next();
			if(temp.getSubject().getName().equals(subject)) {
				assignment = temp.getAssignments().iterator();
				
				while(assignment.hasNext()) {
					tempAssignment = (Assignment)assignment.next();
					tempAssignments.add(tempAssignment);
				}
				
				return tempAssignments;
			}
		}
		
		return null;
	}
		
	
	public Assignment getProfAssignment(int contNum) {
		ArrayList<Assignment> tempAssignments = getAssignments();
		
		
		Iterator checkAssignment = tempAssignments.iterator();
		Assignment temp;
		
		while(checkAssignment.hasNext()) {
			temp = (Assignment)checkAssignment.next();
			if(temp.getContNum() == contNum)
				return temp;
		}
		
		return null;
	}
	
	public ArrayList<Assignment> getStudAssignments(String subject) {
		ArrayList<Assignment> tempAssignments = getAssignments(subject);
		ArrayList<Assignment> retAssignments = new ArrayList<Assignment>();
		
		
		Iterator profAssignments = tempAssignments.iterator();
		Iterator studAssignments;
		Assignment pAssignment;
		Assignment sAssignment;
		
		while(profAssignments.hasNext()) {
			pAssignment = (Assignment)profAssignments.next();
			
			studAssignments = pAssignment.getStudentsAssignment().iterator();
			while(studAssignments.hasNext()) {
				sAssignment = (Assignment)studAssignments.next();
				retAssignments.add(sAssignment);
			}
		}
		
		return retAssignments;
	}
	
	public Assignment getStudAssignment(String subject, int contNum) {
		ArrayList<Assignment> tempAssignments = getStudAssignments(subject);
		
		
		Iterator checkAssignment = tempAssignments.iterator();
		Assignment temp;
		
		while(checkAssignment.hasNext()) {
			temp = (Assignment)checkAssignment.next();
			if(temp.getContNum() == contNum)
				return temp;
		}
		
		return null;
	}
}
