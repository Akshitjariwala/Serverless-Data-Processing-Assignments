package Assignment4.AWSSagemaker;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import org.springframework.boot.SpringApplication;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class AwsSagemakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsSagemakerApplication.class, args);

		Regions region = Regions.US_EAST_1;
		String bucketName = "sourcedatab00866255a4";
		String testbucket = "sourcetestdatab00866255a4";
		BasicSessionCredentials creds = new BasicSessionCredentials(
						"AKIAYLAVVRY6RUIXXCUD",
						"HGTOS4/lsf8J3ArFGYXNvcrGsUq2HjTKCU5zoL7a","");

		AmazonS3 bucketClient = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion(region).build();

		uploadToS3Bucket(bucketName,testbucket,bucketClient);
	}

	public static void uploadToS3Bucket(String bucketName,String bucketName1, AmazonS3 bucketClient)
	{
		String directoryPath = "D:/Materiel/Serverless Data Processing/Assignments/B00866255_Jariwala_Akshit_5410_Assignment4/AWSSagemaker/Dataset/Train";

		String directoryTestPath = "D:/Materiel/Serverless Data Processing/Assignments/B00866255_Jariwala_Akshit_5410_Assignment4/AWSSagemaker/Dataset/Test";

		File trainFolder = new File(directoryPath);
		String fileName = null;
		String[] fileList = trainFolder.list();

		File testFolder = new File(directoryTestPath);
		String testFileName = null;
		String[] testFileList = testFolder.list();

		try
		{
			for (String file:fileList) {
				fileName = file;
				String filePath = directoryPath +"/"+fileName;
				PutObjectRequest fileObject = new PutObjectRequest(bucketName,fileName,new File(filePath));
				bucketClient.putObject(fileObject);
				Thread.sleep(100);
			}

			for (String file:testFileList) {
				testFileName = file;
				String filePath = directoryTestPath +"/"+testFileName;
				PutObjectRequest fileObject = new PutObjectRequest(bucketName1,testFileName,new File(filePath));
				bucketClient.putObject(fileObject);
				Thread.sleep(100);
			}
		}
		catch(InterruptedException ex)
		{
			ex.printStackTrace();
		}

	}

}
