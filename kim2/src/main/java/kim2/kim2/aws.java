package kim2.kim2;

import java.util.List;
import java.util.Scanner;
import com.amazonaws.util.XpathUtils;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.CreateTagsResult;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeImagesRequest;
import com.amazonaws.services.ec2.model.DescribeImagesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DryRunResult;
import com.amazonaws.services.ec2.model.DryRunSupportedRequest;
import com.amazonaws.services.ec2.model.Image;
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
import com.amazonaws.services.ec2.model.MonitorInstancesRequest;
import com.amazonaws.services.ec2.model.UnmonitorInstancesRequest;
import com.amazonaws.services.ec2.model.Region;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.DescribeRegionsResult;

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
			Scanner d= new Scanner(System.in);//owner
			int number = 0;	
			String instance_id, ami_id, name = null, ownerid;
			
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
			System.out.println("      9. Monitor instance        10. UnMonitor instance     ");
			System.out.println("                                 99. quit                   ");
			System.out.println("------------------------------------------------------------");
			System.out.print("Enter an integer: ");
			number = menu.nextInt();
			
			switch(number) {
			case 1:
				listInstances();
				break;
			case 2:
				availableZone();
				break;
			case 3:
				System.out.print("Enter an id: ");
				instance_id = a.nextLine();
				startInstance(instance_id);
				break;
			case 4:
				availableRegions();
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
				break;
			case 7:
				System.out.print("Enter an id: ");
				instance_id = a.nextLine();
				reboot(instance_id);
				break;
			case 8:
				System.out.print("Enter an ownerid: ");
				ownerid = d.nextLine();
				listImages(ownerid);
				break;
			case 9:
				System.out.print("Enter an id: ");
				instance_id = a.nextLine();
				monitorInstance(instance_id);
				break;				
			case 10:
				System.out.print("Enter an id: ");
				instance_id = a.nextLine();
				unmonitorInstance(instance_id);
				break;	
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
		
		//2. available zone
		public static void availableZone()
		{

			DescribeAvailabilityZonesResult zones_response =
				    ec2.describeAvailabilityZones();

				for(AvailabilityZone zone : zones_response.getAvailabilityZones()) {
				    System.out.printf(
				        "Found availability zone %s " +
				        "with status %s " +
				        "in region %s \n",
				        zone.getZoneName(),
				        zone.getState(),
				        zone.getRegionName());
				}
			System.out.println("Complete");
		}//available zone
		
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
		
		//4. available regions
		public static void availableRegions()
		{
			DescribeRegionsResult regions_response = ec2.describeRegions();

			for(Region region : regions_response.getRegions()) {
			    System.out.printf(
			        "Found region %s " +
			        "with endpoint %s \n",
			        region.getRegionName(),
			        region.getEndpoint());
			}
			
			System.out.println("Complete");		
		} //available regions

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
			
			final String USAGE =
		            "To run this example, supply an instance name and AMI image id\n" +
		            "Ex: CreateInstance <instance-name> <ami-image-id>\n";
	     		        
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
		
		//8.list images
		public static  void listImages(String ownerid)
		{
			
			DescribeImagesRequest request = new DescribeImagesRequest();
			DescribeImagesResult response = ec2.describeImages(request);
			
			List<Image> images = response.getImages();
			
			if(ownerid != null && !ownerid.isEmpty()) {
				request = request.withOwners(ownerid);
			}
			  DescribeImagesResult result=ec2.describeImages(request);
			  if (result != null) {
			    images=result.getImages();
			  }
		    /*if (images.isEmpty()) {
		    	System.out.println("Empty Image");  
		    }
		    else
		    {
		    	for(Image image:images)
		    	
		    	System.out.println(image.getImageId());
		    }
		    */
						
			System.out.println("Complete");
			
		}//list images
		
		//9. monitor
		public static void monitorInstance(String instance_id)
	    {
	        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

	        DryRunSupportedRequest<MonitorInstancesRequest> dry_request =
	            () -> {
	            MonitorInstancesRequest request = new MonitorInstancesRequest()
	                .withInstanceIds(instance_id);

	            return request.getDryRunRequest();
	        };

	        DryRunResult dry_response = ec2.dryRun(dry_request);

	        if (!dry_response.isSuccessful()) {
	            System.out.printf(
	                "Failed dry run to enable monitoring on instance %s",
	                instance_id);

	            throw dry_response.getDryRunResponse();
	        }

	        MonitorInstancesRequest request = new MonitorInstancesRequest()
	                .withInstanceIds(instance_id);

	        ec2.monitorInstances(request);

	        System.out.printf(
	            "Successfully enabled monitoring for instance %s",
	            instance_id);
	    }//monitor
		
		//10. unmonitor
		public static void unmonitorInstance(String instance_id)
	    {
	        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

	        DryRunSupportedRequest<UnmonitorInstancesRequest> dry_request =
	            () -> {
	            UnmonitorInstancesRequest request = new UnmonitorInstancesRequest()
	                .withInstanceIds(instance_id);

	            return request.getDryRunRequest();
	        };

	        DryRunResult dry_response = ec2.dryRun(dry_request);

	        if (!dry_response.isSuccessful()) {
	            System.out.printf(
	                "Failed dry run to disable monitoring on instance %s", instance_id);

	            throw dry_response.getDryRunResponse();
	        }

	        UnmonitorInstancesRequest request = new UnmonitorInstancesRequest()
	            .withInstanceIds(instance_id);

	        ec2.unmonitorInstances(request);

	        System.out.printf(
	            "Successfully disabled monitoring for instance %s", instance_id);
	    }//unmonitor
}//aws
