package Assignment_Part_B;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/*References*/
/* [1] "Amazon Simple Storage Service", docs.aws.amazon.com [online]							*/
/*		https://docs.aws.amazon.com/AmazonS3/latest/userguide/Welcome.html						*/
/*		Accessed : 22nd May 2021																*/
/* [2] "Working with AWS Credentials",  docs.aws.amazon.com [online]				   			*/
/*		https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html   			*/
/*  	Accessed : 22nd May 2021 													   			*/

public class FileTransfer
{
	public static void main(String args[])
	{
		Regions region = Regions.US_EAST_1;
		String existingBucket = "uploadfilebucket-a1";
		int option = 0;
		
		BasicSessionCredentials creds = new BasicSessionCredentials(
				"ASIAVJDCQO5A26Z3WA4H",
				"asaeCTwdDAKxMRGoDDUuAxozBNpB4Y5eTRaCkGfe",
				"IQoJb3JpZ2luX2VjEHIaCXVzLXdlc3QtMiJIMEYCIQD7Pc1yy+2P6QU3Z0YkFedIRA6tHMEjHMiGwGldrzhocgIhAPsrfrmiC6bzsOo3+tEotlMys+Yro6zTFWY/+yZSrOuRKqgCCBsQABoMMzYzMTMxMzM2NTEzIgzw+jw77nn/P0NRt30qhQLrLBYa2wovM27h+jYKhLiYktnUdMjiZGlOt4LNbt5DeBH9xjn1eg05X4MPAMEGIGuhyd1Ju15FFX5/a3uLNINCmhLfytMBm7zpNypQNd7mQFzKnR3BUi4C3lAn0b+utYrKcvlIONJsR+tYJ9cVHiG//b5IMraC8x5qYhHmRD5Et+spnt0z8plBvb6CaemTATFOQDCKbvoVQW36wiXB+dL90oZ7yo6bNj9yCUWKCpjUVl5S3Q3kRmH92nNyfpirnOt8mT9y7XNO+ppsGWKz48+LQIl02jriGdKrKNcw034dAztx0w2aC6/cT1HNtUaQRImLmChHgjvC5WPruWyeTPOT1ChUhbkw4fi0hQY6nAF+8Ka15cVnqlQdrWJHp33QVfqMEDpxQL/806U0z+5zK1IQmWGo3SpdvsCA7wlJKrm9vpp6GFHW4TltE32WL4Smu+SS+UETQ7GPXRlutqZ0x86tQ/xCgwOVwfFfzNE5IwOIoHFBSTB4Z+mJDIMqz9gY/imAhSX3YLLbvEybzbVztWtrn+xp89d4powv3XO939bpwHAk3HSR2KQFrfM=");
	
		try{
		
			AmazonS3 s3BucketClient = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion(region).build();
			System.out.println("Enter new bucket name : ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String bucketName = reader.readLine();
			
			createNewBucket(s3BucketClient,bucketName);
			
			do
			{
				System.out.println("Select your action from below options.");
				System.out.println("1 : Display access permissions ");
				System.out.println("2 : Disable Public Access for the bucket. ");
				System.out.println("3 : Provide full control to the user. ");
				System.out.println("4 : Transfer file from existing bucket to new bucket. ");
				System.out.println("5 : Exit");
			
				Scanner myInput = new Scanner(System.in);
				option = myInput.nextInt();
				
				switch(option)
				{
					
					case 1 : displayAccessPermissions(s3BucketClient,bucketName);
						     break;
					case 2 : changeAccess(s3BucketClient,bucketName);
						     break;
					case 3 : changeWriteControl(s3BucketClient,bucketName);
						     break;
					case 4 : transferFile(s3BucketClient,existingBucket,bucketName);
						     break;
					default : System.exit(0);
						     break;
				}
			}while(option != 5);
			
			System.exit(0);
				
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	public static void createNewBucket(AmazonS3 s3,String bucket)
	{
		Bucket b = null;
		System.out.println("Creating new bucket "+bucket+"...");
		if(s3.doesBucketExistV2(bucket))
		{
			System.out.println("Bucket with the name "+bucket+" already exists. Choose a different name.");
			System.exit(0);
		}
		else
		{
			try
			{
				b = s3.createBucket(bucket);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
			
		if(b == null)
		{
			System.out.println("Error creating bucket.");
		}
		else
		{
			System.out.println("Bucket created successfully.");
		}
	}	
	
	public static void displayAccessPermissions(AmazonS3 s3,String bucketName)
	{
		AccessControlList acl = s3.getBucketAcl(bucketName);
		List<Grant> permissions = acl.getGrantsAsList();
		
		System.out.println("Displaying access permission of bucket : "+bucketName);
		System.out.println("----------------------------------------Access Permissions----------------------------------------");
		
		for(Grant permission : permissions)
		{
			System.out.println(permission.getPermission().toString());
		}
		System.out.println("--------------------------------------------------------------------------------------------------");
	}
	
	public static void changeAccess(AmazonS3 s3,String bucketName)
	{
		System.out.println("Changing the Access permission : Disable Public Access");
			
		try{
			
			SetPublicAccessBlockRequest accessRequest = new SetPublicAccessBlockRequest();
			PublicAccessBlockConfiguration accessConfig = new PublicAccessBlockConfiguration();
			s3.setPublicAccessBlock(accessRequest.withBucketName(bucketName).withPublicAccessBlockConfiguration(accessConfig.withBlockPublicAcls(true)));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		System.out.println("Public Access disabled for bucket "+bucketName);
	}
	
	public static void changeWriteControl(AmazonS3 s3,String bucketName)
	{
		System.out.println("Providing full-control write access to bucket Owner");
		try{
			
			AccessControlList controlList = s3.getBucketAcl(bucketName);
			CanonicalGrantee grantee = new CanonicalGrantee(s3.getS3AccountOwner().getId());
			controlList.grantPermission(grantee,Permission.FullControl);
			s3.setBucketAcl(bucketName,controlList);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public static void transferFile(AmazonS3 s3,String source, String destination)
	{
		ListObjectsV2Result result = s3.listObjectsV2(source);
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		String fileKey;
		
		if(objects == null)
		{
			System.out.println("No objects present in "+source);
		}
		
		System.out.println("Transferring files from "+source+" to "+destination);
		
		for(S3ObjectSummary object:objects)
		{
			fileKey = object.getKey();
			try
			{
				s3.copyObject(source,fileKey,destination,fileKey);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			s3.deleteObject(source,fileKey);
		}
		System.out.println("File trasnfer successful.");
	}
}