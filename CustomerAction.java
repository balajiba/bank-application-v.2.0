package Customer;
import java.util.Scanner;
import java.util.regex.Pattern;

import Main.Main;
import Manager.ManagerAction;

public class CustomerAction 
{
	// BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
	Scanner sc = new Scanner(System.in);
	static int indexValue = 0;
	int option = 0;
	static int customerId;
	public  int userId;
	ManagerAction manager = new ManagerAction();

	// Existing Customer Login

	public void customerLogin() 
	{
		boolean flag=false;
		
		do 
		{
		try {
		System.out.println("Enert Customer Id");
		userId = sc.nextInt();
		System.out.println("Enter Password");
		sc.nextLine();
		String password = sc.nextLine();

		if (manager.customerLoginValidation(userId, password)) 
		{
			customerOperation();
			flag=true;
		} else {
			System.out.println("Incorrect Id or Password");
		}
		
		
		}
		catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Enter Valid Input");
				sc.next();
			}
		}while(!flag);
	}

	
	
	public void createNewCustomerAccount() 
	{
		System.out.println("Select Branch");
		int branchId = manager.selectBranch();

		System.out.println("Slect Account Type");
		int accountType = selectAccountType();
	
		sc.nextLine();
		System.out.println("Enter Aadhar Number");
		String aadharNumber = sc.nextLine();
		
		if (!manager.checkAlreadyExistingCustomer(aadharNumber)) 
		{
			
			
			System.out.println("Enter Your Name");
			String name = sc.nextLine();
		
			System.out.println("Enter Your MailId");
			String mailId = sc.nextLine();
			
			if(isValidMail(mailId))
			{
				System.out.println("Enter Mobile Number");
				String mobileNumber = sc.nextLine();
		
				customerId=manager.generateCustomerId();
			
				Customer newCustomer = new Customer(customerId, name, null, aadharNumber, mailId, mobileNumber);

			

				System.out.println("Create New password to Your Account");
				String password = sc.nextLine();

				System.out.println("Enter Confirm Password");
				String confirmPassword = sc.nextLine();

				if (password.equals(confirmPassword)) {
				
					newCustomer.setpassword(confirmPassword);

					if (manager.addNewCustomer(newCustomer))
					{
						System.out.println("Enter Initial Amount ");
						double amount = sc.nextDouble();

					if (accountType == 2 && amount < 1000)
					{
						
						System.out.println("Your Savings Account Must Maintain minimun Amount rs.1000");
						
					}

					else 
					{
						
						manager.addAccount(customerId, accountType, branchId, amount);
						System.out.println("your customer id is :--> " + customerId);
						System.out.println();
						System.out.println("Please Login here");
						customerLogin();
					}
				}

			}
			else {
				System.out.println("Password Must Same");

			}
		} 
			
		else
			{
				System.out.println("Invalid MailId");
			
			}
		}
			else
			{
			
			System.out.println("You have Already Account");
			System.out.println("Please Add New Account With Your Exisiting Account");
			customerLogin();
		}

	}

//select Account type
	public int selectAccountType() 
	{

		System.out.println("1.Current Account");
		System.out.println("2.Savings Account");
		System.out.println("3.Loan Account");

		int accounttype = readInt(0, 3);
		return accounttype;

	}

	public void customerOperation()

	{
		System.out.println("\nWelcome to Zoho Bank");
		System.out.println("1.Banking");
		System.out.println("2.View Account Details");
		System.out.println("3.View Transaction History");
		System.out.println("4.View Profile");
		System.out.println("5.Add Account");
		System.out.println("6.Close Account");
		System.out.println("7.Logout");

		option = readInt(0, 7);
		
		switch (option)
		{
		case 1:
			bankingOperations();
			break;
		case 2:
			
			manager.displayCustomerAccountDetails(userId);
			customerOperation();
			break;
		case 3:
			int index=displayListofAccounts();
			manager.viewAccountSummary(userId, index);
			customerOperation();
			break;
		case 4:
			manager.viewCustomerProfile(userId);
			customerOperation();
			break;
		case 5:
			addAccount();
			bankingOperations();
			break;
		case 6:
			manager.closeAccount(userId);
			customerOperation();
			break;
		case 7:
			Main.startProgram();
			break;
		}

	}



	private void addAccount() 
	{
		boolean flag=true;
	
		do
		{
			try {
		System.out.println("Select Branch");
		int branch = manager.selectBranch();
		System.out.println("Select Account Type");
		int accountType = selectAccountType();
		do {
		System.out.println("Enter Initial Amount ");
		double amount=sc.nextDouble();
		if (accountType == 2 && amount < 1000)
		{
			System.out.println("Your Savings Account Must Maintain minimun Amount rs.1000");
			
		}
		else
		{
		manager.addAccount(customerId, accountType, branch, amount);
		customerOperation();
		flag=false;
		}
		}while(flag);
		}
			catch(Exception e)
			{
				System.out.println("Enter Valid Input");
				sc.next();
			}
	
	}while(flag);
	}


	private void bankingOperations() 
	{
		
		//Display list of Accounts
		
	
		System.out.println("1.Cash WithDraw");
		System.out.println("2.Cash Deposit");
		System.out.println("3.Money Transfer to Another Account");
		System.out.println("4.Cancel Transaction");
		int choice = readInt(0, 4);
		System.out.println("Enter Amount");
		double cash=sc.nextDouble();
		
		int index=displayListofAccounts();
		
		
		switch (choice) 
		{
		case 1:
			manager.makeCustomerTransaction(userId,index,cash,choice,0);
			customerOperation();
			break;
		case 2:
			manager.makeCustomerTransaction(userId,index,cash,choice,0);
			customerOperation();
			break;
		case 3:
			
			System.out.println("Enter  Beneficiary Account Number" );
			int beneficiaryAccountNumber=sc.nextInt();
			manager.makeCustomerTransaction(userId,index,cash,choice,beneficiaryAccountNumber);
			customerOperation();
			break;
		case 4:
			customerOperation();
			break;
			

		}
	}

	private int displayListofAccounts()
	{
		manager.displayCustomerAccountDetails(userId);
		
		System.out.println("Select  Account");
		option=sc.nextInt();
		return option;
	}
	
	public static boolean isValidMail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

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
				stop=false;
				sc.next();
			}
		}
		return choice;
	}

//	public double getAmount()
//	{
//		System.out.println("Enter Amount");
//		double amount=sc.nextDouble();
//		return amount;
//	}
//	
//	

	
	
}
