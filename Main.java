import java.util.Scanner;

public class Main {

    public static void  main(String[] args) throws Exception {

        int menu_num;
        String instance_id;
        
        Scanner num = new Scanner(System.in);
        Scanner id = new Scanner(System.in);
        
        while(true)
        {
        	System.out.println("                                                            ");
        	System.out.println("                                                            ");
        	System.out.println("------------------------------------------------------------");
        	System.out.println("             Amazon AWS Control Panel using SDK             ");
        	System.out.println("                                                            ");
        	System.out.println("        Cloud Computing, Computer Science Department        ");
        	System.out.println("        		     at Chungbuk National University        ");
        	System.out.println("------------------------------------------------------------");
            System.out.println("      1. list instance            2. available zones        ");
            System.out.println("      3. start instance           4. available regions      ");
            System.out.println("      5. stop instance            6. create instance        ");
            System.out.println("      7. reboot instance          8. list images            ");
            System.out.println("                                 99. quit                   ");
            System.out.println("-----------------------------------------------------       ");
            
            System.out.print("Put menu's number: ");
            
            menu_num=num.nextInt();
            
            if(menu_num == 1) {
            	//listInstances(); 
            }
            else if(menu_num == 2) {
            	//listInstances(); 
            }
            else if(menu_num == 3) {
            	
            }
            else if(menu_num == 4) {
            	
            }
            else if(menu_num == 5) {
            	
            }
            else if(menu_num == 6) {
            	
            }
            else if(menu_num == 7) {
            	
            }
            else if(menu_num == 8) {
            	
            }
            else if(menu_num == 99) {
            	break;
            }
            
            /*
            //List Instance
            if(menu_num==1)
            {
            	System.out.println("ListInstance");
            	//ListInstance.listInstance(ec2);
            	
            }
            //Availablezones
            else if(menu_num==2)
            {
            	AvailableZones.availableZone(ec2);
            	
            	
            }
            //Start Instance
            else if(menu_num==3)
            {
            	System.out.println("Put your instance Id");
            	instance_id=id.nextLine();
            	
            	//StartInstance.startIns(ec2,instance_id);
            	
            	//for testing
            	StartInstance.startIns(ec2,instance_test);
            }
            
            
            //AvailableRegions
            else if(menu_num==4)
            {
            	AvailableRegions.availableRegions(ec2);
            }
            //Stop Instance
            else if(menu_num==5)
            {
            	System.out.println("Put your instance Id");
            	instance_id=id.nextLine();
            	
            	StopInstance.stopInstance(ec2,instance_id);
            	
            	//for testing
            	//StopInstance.stopInstance(ec2,instance_test);
            	
            	
            }
            
            //MakeInstance
            else if(menu_num==6)
            {
            	System.out.println("You'll make Instance");
            	MakeInstance.makeInstance(ec2);
            	
            }
            
            //RebootInstance
            else if(menu_num==7)
            {
            	System.out.println("Put your instance Id");
            	instance_id=id.nextLine();
            	
            	RebootInstance.rebootInstance(ec2, instance_id);
            	
            	//for testing
            	//RebootInstance.rebootInstance(ec2, instance_test);
            }
            
            //list Images
            else if(menu_num==8)
            {
            	
            	ListImages.listImages(ec2);
            	
            }
            //Describe Accounts
            else if(menu_num==9)
            {
            	DescribeAccounts.describeAccounts(ec2);
            	
            	System.out.println("Complete");
            	
            }
            */
                      
            
        }
        
        
     
    }
}
