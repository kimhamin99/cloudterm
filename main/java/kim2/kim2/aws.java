package kim2.kim2;

import java.util.Scanner;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.CreateTagsResult;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DryRunResult;
import com.amazonaws.services.ec2.model.DryRunSupportedRequest;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.RebootInstancesRequest;
import com.amazonaws.services.ec2.model.RebootInstancesResult;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.Tag;

public class aws {
/*
* Cloud Computing, Data Computing Laboratory
* Department of Computer Science
* Chungbuk National University
*/
 static AmazonEC2 ec2;
 
 		private static void init() throws Exception {
/*
* The ProfileCredentialsProvider will return your [default]
* credential profile by reading from the credentials file located at
* (~/.aws/credentials).
*/
 			ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
 			try {
				credentialsProvider.getCredentials();
				} catch (Exception e) {
				throw new AmazonClientException(
				"Cannot load the credentials from the credential profiles file. " +
				"Please make sure that your credentials file is at the correct " +
				"location (~/.aws/credentials), and is in valid format.",
				e);
				}
				ec2 = AmazonEC2ClientBuilder.standard()
				.withCredentials(credentialsProvider)
				.withRegion("us-east-2") /* check the region at AWS console */
				.build();
 	}

 		public static void main(String[] args) throws Exception {
			init();
			Scanner menu = new Scanner(System.in);
			Scanner id_string = new Scanner(System.in);
			Scanner a= new Scanner(System.in);//instance_id
			Scanner b= new Scanner(System.in);//ami_id
			Scanner c= new Scanner(System.in);//name
			int number = 0;
			String instance_id, ami_id, name = null;
			
			while(true)
			{
			System.out.println("                                                            ");
			System.out.println("                                                            ");
			System.out.println("------------------------------------------------------------");
			System.out.println("           Amazon AWS Control Panel using SDK               ");
			System.out.println("                                                            ");
			System.out.println("        Cloud Computing, Computer Science Department        ");
			System.out.println("                        at Chungbuk National University     ");
			System.out.println("------------------------------------------------------------");
			System.out.println("      1. list instance            2. available zones        ");
			System.out.println("      3. start instance           4. available regions      ");
			System.out.println("      5. stop instance            6. create instance        ");
			System.out.println("      7. reboot instance          8. list images            ");
			System.out.println("                                 99. quit                   ");
			System.out.println("------------------------------------------------------------");
			System.out.print("Enter an integer: ");
			number = menu.nextInt();
			
			switch(number) {
			case 1:
				listInstances();
				break;
			case 2:
				break;
			case 3:
				System.out.print("Enter an id: ");
				instance_id = a.nextLine();
				startInstance(instance_id);
				break;
			case 5:
				System.out.print("Enter an id: ");
				instance_id = a.nextLine();
				stopInstance(instance_id);
				break;
			case 6:
				System.out.print("Enter an ami_id: ");
				ami_id = b.nextLine();
				System.out.print("Enter an name: ");
				name = c.nextLine();
				createInstance(ami_id, name);
			case 99:
				break;
			}
			if(number == 99) {
				System.out.print("The End");
				break;
			}
		}
	}//main
 		
 	   //1. list instance
		public static void listInstances() {
			System.out.println("Listing instances....");
			boolean done = false;
			
			DescribeInstancesRequest request = new DescribeInstancesRequest();
			
			while(!done) {
				DescribeInstancesResult response = ec2.describeInstances(request);
				for(Reservation reservation : response.getReservations()) {
				for(Instance instance : reservation.getInstances()) {
				System.out.printf(
				"[id] %s, " +
				"[AMI] %s, " +
				"[type] %s, " +
				"[state] %10s, " +
				"[monitoring state] %s",
				instance.getInstanceId(),
				instance.getImageId(),
				instance.getInstanceType(),
				instance.getState().getName(),
				instance.getMonitoring().getState());
					}
					System.out.println();
				}
				
			request.setNextToken(response.getNextToken());
			if(response.getNextToken() == null) {
			done = true;
				}
			}
		}//listInstances
		
		//3. start 
		public static void startInstance(String instance_id){
	        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

	        DryRunSupportedRequest<StartInstancesRequest> dry_request =
	            () -> {
	            StartInstancesRequest request = new StartInstancesRequest()
	                .withInstanceIds(instance_id);

	            return request.getDryRunRequest();
	        };

	        DryRunResult dry_response = ec2.dryRun(dry_request);

	        if(!dry_response.isSuccessful()) {
	            System.out.printf(
	                "Failed dry run to start instance %s", instance_id);

	            throw dry_response.getDryRunResponse();
	        }

	        StartInstancesRequest request = new StartInstancesRequest()
	            .withInstanceIds(instance_id);

	        ec2.startInstances(request);

	        System.out.printf("Successfully started instance %s", instance_id);
	    }

		//5. stop 
		public static void stopInstance(String instance_id)
	    {
	        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

	        DryRunSupportedRequest<StopInstancesRequest> dry_request =
	            () -> {
	            StopInstancesRequest request = new StopInstancesRequest()
	                .withInstanceIds(instance_id);

	            return request.getDryRunRequest();
	        };

	        DryRunResult dry_response = ec2.dryRun(dry_request);

	        if(!dry_response.isSuccessful()) {
	            System.out.printf(
	                "Failed dry run to stop instance %s", instance_id);
	            throw dry_response.getDryRunResponse();
	        }

	        StopInstancesRequest request = new StopInstancesRequest()
	            .withInstanceIds(instance_id);

	        ec2.stopInstances(request);

	        System.out.printf("Successfully stop instance %s", instance_id);
	    } //stop
		
		//6. create 
		public static void createInstance(String ami_id, String name) {
			 final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

		        RunInstancesRequest run_request = new RunInstancesRequest()
		            .withImageId(ami_id)
		            .withInstanceType(InstanceType.T1Micro)
		            .withMaxCount(1)
		            .withMinCount(1);

		        RunInstancesResult run_response = ec2.runInstances(run_request);

		        String reservation_id = run_response.getReservation().getInstances().get(0).getInstanceId();

		        Tag tag = new Tag()
		            .withKey("Name")
		            .withValue(name);

		        CreateTagsRequest tag_request = new CreateTagsRequest()
		            .withResources(reservation_id)
		            .withTags(tag);

		        CreateTagsResult tag_response = ec2.createTags(tag_request);

		        System.out.printf(
		            "Successfully started EC2 instance %s based on AMI %s",
		            reservation_id, ami_id);
		    }//create
		
		//7. reboot
		public static void reboot(String instance_id) {
			final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

	        RebootInstancesRequest request = new RebootInstancesRequest()
	            .withInstanceIds(instance_id);

	        RebootInstancesResult response = ec2.rebootInstances(request);

	        System.out.printf(
	            "Successfully rebooted instance %s", instance_id);
		}//reboot
}//aws
