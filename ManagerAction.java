package Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Account.Account;
import Account.BankingOperations;
import Branch.Branch;
import Customer.Customer;
import Main.Main;

public class ManagerAction {
	
	private static HashMap<Integer,Manager> managerDetails=new HashMap<Integer,Manager>();
	private static HashMap<Integer,ArrayList<Account>> customerAccount=new HashMap<>();
	public static HashMap<Integer,Branch> branchList=new HashMap<>();
	static HashMap<Integer, Customer> customerList=new HashMap<>();
	static HashMap<Integer,Transaction> transactionHistory=new HashMap<>();
	static int branchId=4;
	private static int newcustomerId=100;
	private static int accountNumber=100000;
	static int index=0;
	static int transactionId=1000;
	String branchName;
	Scanner sc=new Scanner(System.in);
	static
	{
		
		Branch branch1=new Branch(1,"Adayar");
		Branch branch2=new Branch(2,"Egmore");
		Branch branch3=new Branch(3,"Potheri");
		branchList.put(branch1.getBranchId(), branch1);
		branchList.put(branch2.getBranchId(), branch2);
		branchList.put(branch3.getBranchId(), branch3);
		
	}
	public  void loadAdminValue()
	{
		Manager manager1=new Manager(500,"manager1","manager","manager@1.com","9090908809");
		Manager manager2=new Manager(600,"manager2","manager","manager@2.com","9029209022");
		Manager manager3=new Manager(700,"manager3","manager","manager@3.com","9029233022");
		managerDetails.put(manager1.getId(), manager1);
		managerDetails.put(manager2.getId(), manager2);
		managerDetails.put(manager3.getId(), manager3);
		
	}

	
	public  void managerLogin()
	{
		
		boolean stop=true;
		
		while(stop)
		{
		System.out.println("Enter Your Id");
		int managerId=sc.nextInt();
		System.out.println("Enter your Password");
		sc.nextLine();
		String password=sc.nextLine();
		
		for(Map.Entry<Integer, Manager> man:managerDetails.entrySet())
		{
			Manager value=man.getValue();
			if(value.getId()==managerId && value.getPassword().equals(password))
			{
				managerOperations();
				stop=false;
			}
		}
		if(stop)
		{
			System.out.println("Incorrect Name/Password");
		}
		
		}
		
		
	}
	
	public void managerOperations()
	{
		System.out.println("Zoho Bank");
		System.out.println("1.View Customer Account Details");
		System.out.println("2.View customer Account Transaction History");
		System.out.println("3.View Highest Account Balance in All Branches");
		System.out.println("4.View List of Branches");
		System.out.println("5.Add New Branch");
		System.out.println("6.Logout");
		
		int choice=readInt(0,6);
		
		switch(choice)
		{
		case 1:
			viewCustomerAccountDeatails();
			managerOperations();
			break;
			
		case 2:
			viewCustomerAccountSummary();
			managerOperations();
			break;
		case 3:
			viewHighestAccountBalance();
			managerOperations();
			break;
			
		case 4:
			displayBranch();
			managerOperations();
			break;
			
			
		case 5:
			addNewBranch();
			managerOperations();
			break;
		case 6:
			Main.startProgram();
			break;
			
		
		}

	}
	

private void viewCustomerAccountDeatails() 
{
	
	System.out.println("1.Search Customer Details");
	System.out.println("2.View All Customer Account Details");
	System.out.println("3.Main Menu");
	
	int choice=readInt(0,3);
	
	
	if(choice==1)
	{
		searchCustomerAccountDetails();
		viewCustomerAccountDeatails();
		
	}
	else if(choice==2)
	{
		viewAllCustomer();
		viewCustomerAccountDeatails();
	}
	else if(choice==3)
	{
		managerOperations();
	}
		
		
	}

private void viewAllCustomer() 
{
	String type=null;
	
	System.out.println("AccountNumber"+"\t"+"Balance"+"\t"+"Account Type"+"\t"+"BranchName");
	for(ArrayList<Account> e:customerAccount.values())
	{
		for(Account account:e)
		{
			
				for(Branch b : branchList.values())
				{
					if(b.getBranchId() == account.getBranchId())
					{
						branchName=b.getBranchName();
						break;
					}
				}
				{
					type=returnAccountType(account.getAccountType());
				
				System.out.println(account.getAccountNumber()+"  \t  "+account.getBalance()+"  \t "+type+"\t"+branchName);
			
				index++;
			}
			
		}
	
}
}

public String returnAccountType(int n)
{
	if(n==1)
	{
		  return "Current Account";
	}
	else if(n==2)
	{
		 return "Savings Account";
	}
	else
	{
		 return "Loan Account";
	}
	
}


public void searchCustomerAccountDetails()
{
	boolean flag=true;
	String type=null;
	index=1;
	System.out.println("Enter Customer Account Number");
	int searchAccountNumber=sc.nextInt();
	System.out.println("AccountNumber"+"\t"+"Balance"+"\t"+"Account Type"+"\t"+"BranchName");
	for(ArrayList<Account> e:customerAccount.values())
	{
		for(Account account:e)
		{
			if(account.getAccountNumber()==searchAccountNumber)
			{
				flag=false;
				for(Branch b : branchList.values())
				{
					if(b.getBranchId() == account.getBranchId())
					{
						branchName=b.getBranchName();
						break;
					}
				}
				{
					type=returnAccountType(account.getAccountType());
				
				System.out.println("\n"+index+"."+account.getAccountNumber()+"  \t  "+account.getBalance()+"  \t "+type+"\t"+branchName);
			
				index++;
			}
		
			}
		}
		if(flag)
		{
			System.out.println("Account number Doesn't Exist");
		}
	
	}

}




public  int selectBranch() 
{
	
	for (Map.Entry<Integer,Branch> map:branchList.entrySet())
	{
		Branch branch=map.getValue();
		
		System.out.println(branch.getBranchId() + "." + branch.getBranchName());
	}
	int branchId = readInt(0, ManagerAction.branchList.size());
	return branchId;
}


//check customer login access	
	
	public boolean customerLoginValidation(int custId,String password)
	{
		
		boolean flag=false;
		for(Map.Entry<Integer,Customer> cust:customerList.entrySet())
		{
			Customer value=cust.getValue();
			if(value.getCustomerId()==custId && value.getpassword().equals(password))
					{
					 flag=true;
					 break;
					}
		}
		if(flag)
		{
			return true;
		}
		else
		{
			return false;
		}
	
	}
	
	
	
	
//check customer already have an account	
	public boolean checkAlreadyExistingCustomer(String aadharNumber)
	{
		
		boolean flag=false;
		for(Map.Entry<Integer,Customer> cust:customerList.entrySet())
		{
			Customer value=cust.getValue();
			
			if(value.getaadharNumber().equals(aadharNumber))
					{
					 flag=true;
					 break;
					}
		}
		if(flag)
		{
			return true;
		}
		else
		{
			return false;
		}
		
		
	}
//customer view	
public void viewCustomerProfile(int customerId)
{
	System.out.println("Name"+"\t"+"AadharNumber"+"\t"+"MailId"+"\t"+"MobileNumber");

	for(Map.Entry<Integer, Customer> map:customerList.entrySet())
	{
		Customer cus=map.getValue();
		if(cus.getCustomerId()==customerId)
		System.out.println(cus.getcustomerName()+"\t"+cus.getaadharNumber()+"\t"+cus.getMailId()+"\t"+cus.getmobileNumber());
	}
}
	
	
	
//add new customer to hashmap
	public boolean addNewCustomer(Customer cus)
	{
		customerList.put(cus.getCustomerId(),cus);
		return true;
	}	

	public int generateCustomerId()
	{
		return ++newcustomerId;
	}
	
	
	public int generteAccountNumber()
	{
		return ++accountNumber;
	}
	
//addAccount
	
	public boolean addAccount(int customerId,int accountType,int branchId,double amount)
	{
	
		accountNumber=generteAccountNumber();
		
		Account account=new Account(accountNumber,accountType,branchId,amount);
		
		ArrayList<Account> accountList = new ArrayList<Account>();
		
		if(customerAccount.containsKey(customerId))
		{
		
			accountList = customerAccount.get(customerId);
			if(accountType==3)
			{
				accountList.add(account);
				System.out.println("Successfully Account was Created....");
				System.out.println("Your Account Number is:--> "+accountNumber);
				
				makeTransactionHistory(accountNumber,"Opening Account",amount,amount);
				
				return true;
				
			}
			
			else if(checkSameBranchSameAccountType(customerId,accountType,branchId))
			
			{
				accountList.add(account);
				System.out.println("Successfully Account was Created....");
				System.out.println("Your Account Number is:--> "+accountNumber);
				
				makeTransactionHistory(accountNumber,"Opening Account",amount,amount);
				
				return true;
			
			}
			else
			{
				System.out.println("Sorry,  Cannot open Same Account Type in Same Branch ");
				return false;
			}
			//System.out.println(accountList.get(customerId));
		}
		else 
		{
			accountList.add(account);
			customerAccount.put(customerId, accountList);
			System.out.println("Successfully Account was Created");
			System.out.println("\nYour Account Number is:--> "+accountNumber);
			
			makeTransactionHistory(accountNumber,"Opening Account",amount,amount);
			return true;
		}
	}
	
	
public boolean checkSameBranchSameAccountType(int customerId,int accountType,int branchId)
	{
		boolean flag=true;
		for(Account a:customerAccount.get(customerId))
		{
			if(a.getBranchId()==branchId && a.getAccountType()==accountType)
			{
				flag=false;
				return flag;
			}
		}
		return flag;
		
	}
	
//display for customer purpose
public void displayCustomerAccountDetails(int customerId)
	{
		String type = null;
		index=1;
		
		System.out.println("\nYour All Account");
		
		System.out.println("AccountNumber"+" \t "+"Account Type"+" \t "+"Balance"+"  \t"+"BranchName" );
		
		for(Account ac:customerAccount.get(customerId))
		{
			for(Branch b : branchList.values())
			{
				if(b.getBranchId() == ac.getBranchId())
				{
					branchName=b.getBranchName();
					break;
				}
			}
			{
			type=returnAccountType(ac.getAccountType());
			System.out.println("\n"+index+"."+ac.getAccountNumber()+"  \t  "+type+"  \t "+ac.getBalance()+"\t"+branchName);
		
			index++;
		}
		}
		
	}
	
	
	
//Add new Branch 	
	public void addNewBranch()

	{
		System.out.println("Enter New Branch Name");
		sc.nextLine();
		String branchName=sc.nextLine();
		Branch branch=new Branch(branchId,branchName);
		branchList.put(branch.getBranchId(), branch);
		System.out.println("Successfully Branch Added");
		branchId++;

		
	}
	
//view branch details
	
	public void displayBranch()
	{
		System.out.println("Branch Code"+"   "+"Branch Name");
		for (Map.Entry<Integer, Branch> map:branchList.entrySet())
		{
			Branch branch=map.getValue();
			
			System.out.println(branch.getBranchId() + "\t \t" + branch.getBranchName());
			index++;
		}
	}


	
//close account
	public void closeAccount(int customerId)
	{
		displayCustomerAccountDetails(customerId);
		System.out.println("Which Account You Want to Close?");
		int choice=readInt(0,branchList.size());
		
		
		System.out.println("Are Sure to Close This Account(Y/N)");
		char c = sc.next().charAt(0);
		if(Character.toLowerCase(c)=='y')
		{
			ArrayList<Account> accountList=customerAccount.get(customerId);
			accountList.remove(choice-1);
			System.out.println("Your Account Was Successfully Closed");
		}
		else
		{
			System.out.println("Last Transaction was Cancelled");
		}
	}
	
	
public void makeCustomerTransaction(int userId,int accountIndexValue,double amount,int option,int beneficiaryAccountNumber)
	{
		
		Account account =customerAccount.get(userId).get(accountIndexValue-1);
		//int acntNumber=acc.getAccountNumber();
		BankingOperations banking=new BankingOperations(account);
		//display options
		
		if(option==1)
		{
			if(banking.accountWithDraw(amount))
			{
				
				makeTransactionHistory(account.getAccountNumber(),"Withdraw",amount,account.getBalance());
			}
			
		}
		else if(option==2)
		{
			if(banking.accountDeposit(amount))
			{
				makeTransactionHistory(account.getAccountNumber(),"Deposit",amount,account.getBalance());
			}

		}
		else if(option==3)
		{
			boolean flag=true;
			
			for(ArrayList<Account> e:customerAccount.values())
			{
				for(Account beneficiaryAccount:e)
				{
					if(beneficiaryAccount.getAccountNumber()==beneficiaryAccountNumber)
					{
						flag=false;
						if(banking.fundTrasfer(beneficiaryAccount, amount))
						{
							makeTransactionHistory(account.getAccountNumber(),"Debited",amount,account.getBalance());
							makeTransactionHistory(beneficiaryAccount.getAccountNumber(),"Credited",amount,beneficiaryAccount.getBalance());
							break;
						}
						
						
					}
				}
				if(flag)
				{
					System.out.println("BeneficiaryAccountNumber Doesn't Exist");
				}
			}
			
			
			
		}

	}

//view Account summary for manager view

public void viewCustomerAccountSummary()
{
	System.out.println("Enter Account Number");
	sc.nextLine();
	
	int transactionAccountNumber=sc.nextInt();
	
	System.out.println("TransactionId"+"\t"+"Description"+"\t"+"Amount"+"\t"+"Balance");
	
	for(Map.Entry<Integer,Transaction> summary:transactionHistory.entrySet())
	{
		Transaction transaction=summary.getValue();

		if(transactionAccountNumber==transaction.getAccountNumber())
		{
			System.out.println(transaction.getTransactionId()+" \t\t"+transaction.getTransactionMethod()+"  \t "+transaction.getAmount()+" \t"+transaction.getBalance());
		}
		
	}
}


//customer view	
	public void viewAccountSummary(int userId,int accountIndexValue)
	{
		
		Account account =customerAccount.get(userId).get(accountIndexValue-1);
		System.out.println("TransactionId"+"\t"+"Description"+"\t"+"Amount"+"\t"+"Balance");
		
		for(Map.Entry<Integer,Transaction> summary:transactionHistory.entrySet())
		{
			Transaction transaction=summary.getValue();
	
			if(account.getAccountNumber()==transaction.getAccountNumber())
			{
				System.out.println(transaction.getTransactionId()+" \t"+transaction.getTransactionMethod()+" \t"+transaction.getAmount()+" \t"+transaction.getBalance());
			}
			
		}

		
	}
	
	
	public void makeTransactionHistory(int accNumber,String transactionMethod,double amount,double balance)
	{
		
		Transaction history=new Transaction(transactionId,accNumber,transactionMethod,amount,balance);
		transactionHistory.put(history.getTransactionId(), history);
		transactionId++;
	}
	
	
	public void viewHighestAccountBalance()
	{
		double max=0;
		int highestBalanceAccountHolder=0;
		for(int i=0;i<=customerAccount.size();i++)
		{
			max=0;
			highestBalanceAccountHolder=0;
		
		for(ArrayList<Account> account:customerAccount.values())
		{
			for(Account id:account)
			{
				if(id.getBranchId()==i+1)
				{
					if(max<id.getBalance())
					{
						max=id.getBalance();
						highestBalanceAccountHolder=id.getAccountNumber();
					}
					
				}
			}
			
		}
		System.out.println(highestBalanceAccountHolder+"  "+max);
	}
		
		//return highestBalanceAccountHolder;
		
		
		
	}
	
	
//	public int searchBranch(String branchName)
//	{
//		for(int i = 0; i < branchList.size(); i++)
//		{
//			Branch temp_branch = branchList.get(i);
//			String temp_branch_name = temp_branch.getBranchName();
//			if(temp_branch_name.equals(branchName))
//				return i;
//		}
//		return -1;
//	}
	
	
	public int readInt(int min, int max) 
	{

		boolean stop = false;
		int choice = 0;
		while (!stop) {
			try {
				choice = sc.nextInt();
				if (choice >= min && choice <= max) {
					stop = true;
					break;

				} else {
					System.out.println("Enter Correct Input");
				}
			} catch (Exception e) {
				System.out.println("Enter Valid Number");
			}
		}
		return choice;
	}
	
	
}
