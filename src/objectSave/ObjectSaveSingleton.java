package objectSave;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Account.Account;

public class ObjectSaveSingleton {
	private static ObjectSaveSingleton singleton = null;

	static private Account accounts;
	
	private ObjectSaveSingleton()
	{
		if(!loadAccounts()) {
			accounts = new Account();
			System.out.println("새로운 account 객체를 만들었습니다");
			saveAccounts();
		}
		else {
		System.out.println("기존의 account 를 불러왔습니다");
		}
	}
	
	public void saveAccounts() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try{
			fos = new FileOutputStream("accounts.dat");
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject(accounts);
			System.out.println("account 를 저장하였습니다");
		}catch(Exception e){
			
			e.printStackTrace();
		
		}finally{
			if(fos != null) try{fos.close();}catch(IOException e){}
			if(oos != null) try{oos.close();}catch(IOException e){}	
		}
	}
	
	public boolean loadAccounts() {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		try{
			fis = new FileInputStream("accounts.dat");
			ois = new ObjectInputStream(fis);
			
			accounts = (Account)ois.readObject();
		}catch(Exception e){
			System.err.println("해당하는 파일이 존재하지 않습니다");
			e.printStackTrace();
			return false;
		}finally{
			if(fis != null) try{fis.close();}catch(IOException e){}
			if(ois != null) try{ois.close();}catch(IOException e){}
		}
		return true;
	}

	public static ObjectSaveSingleton getInstance()
	{ 
		if(singleton == null)
		{
			singleton  = new ObjectSaveSingleton();
		}
		return singleton;
	}
	
	public Account getAccounts() {
		return accounts;
	}
}
