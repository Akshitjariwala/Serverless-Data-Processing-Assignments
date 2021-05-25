package Assignment_Part_B;

import java.io.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;

/*References*/
/* [1] "Amazon Simple Storage Service", docs.aws.amazon.com [online]							*/
/*		https://docs.aws.amazon.com/AmazonS3/latest/userguide/Welcome.html						*/
/*		Accessed : 22nd May 2021																*/
/* [2] "Working with AWS Credentials",  docs.aws.amazon.com [online]				   			*/
/*		https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html   			*/
/*  	Accessed : 22nd May 2021 													   			*/

public class App 
{
    public static void main( String[] args )
    {
        Regions region = Regions.US_EAST_1;
		String bucketName = "uploadfilebucket-a1";
		String bucketKey = "Akshit";
		String filePath = "C:\\Users\\Akshit\\Desktop\\Materiel\\Serverless Data Processing\\Assignments\\Akshit.txt";
		BasicSessionCredentials creds = new BasicSessionCredentials(
					"ASIAVJDCQO5A26Z3WA4H",
					"asaeCTwdDAKxMRGoDDUuAxozBNpB4Y5eTRaCkGfe",
					"IQoJb3JpZ2luX2VjEHIaCXVzLXdlc3QtMiJIMEYCIQD7Pc1yy+2P6QU3Z0YkFedIRA6tHMEjHMiGwGldrzhocgIhAPsrfrmiC6bzsOo3+tEotlMys+Yro6zTFWY/+yZSrOuRKqgCCBsQABoMMzYzMTMxMzM2NTEzIgzw+jw77nn/P0NRt30qhQLrLBYa2wovM27h+jYKhLiYktnUdMjiZGlOt4LNbt5DeBH9xjn1eg05X4MPAMEGIGuhyd1Ju15FFX5/a3uLNINCmhLfytMBm7zpNypQNd7mQFzKnR3BUi4C3lAn0b+utYrKcvlIONJsR+tYJ9cVHiG//b5IMraC8x5qYhHmRD5Et+spnt0z8plBvb6CaemTATFOQDCKbvoVQW36wiXB+dL90oZ7yo6bNj9yCUWKCpjUVl5S3Q3kRmH92nNyfpirnOt8mT9y7XNO+ppsGWKz48+LQIl02jriGdKrKNcw034dAztx0w2aC6/cT1HNtUaQRImLmChHgjvC5WPruWyeTPOT1ChUhbkw4fi0hQY6nAF+8Ka15cVnqlQdrWJHp33QVfqMEDpxQL/806U0z+5zK1IQmWGo3SpdvsCA7wlJKrm9vpp6GFHW4TltE32WL4Smu+SS+UETQ7GPXRlutqZ0x86tQ/xCgwOVwfFfzNE5IwOIoHFBSTB4Z+mJDIMqz9gY/imAhSX3YLLbvEybzbVztWtrn+xp89d4powv3XO939bpwHAk3HSR2KQFrfM=");
		
		try
		{
			AmazonS3 s3BucketClient = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion(region).build();
		
			System.out.println("Uploading file to Amazon bucket "+bucketName+"...");
		
			PutObjectRequest fileObject = new PutObjectRequest(bucketName,bucketKey,new File(filePath));
		
			s3BucketClient.putObject(fileObject);
		
			System.out.println("Upload complete.");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	
    }
}
