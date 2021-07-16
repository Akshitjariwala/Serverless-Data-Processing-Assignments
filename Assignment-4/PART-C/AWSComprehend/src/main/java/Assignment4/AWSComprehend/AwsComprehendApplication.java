package Assignment4.AWSComprehend;

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
public class AwsComprehendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsComprehendApplication.class, args);

		Regions region = Regions.US_EAST_1;
		String bucketName = "twitterdatabucketb00866255";
		BasicSessionCredentials creds = new BasicSessionCredentials(
						"ASIAVWEZTLUW7C3VOLLW",
						"wcZBLLDzclk3f3Ww6mvYBTr05sKg8HBdQ6ZDHBCO",
						"FwoGZXIvYXdzEPj//////////wEaDN5qNs+ATdFp80mc3yK/AVe5I7grHoC4AeFMvvviOY+u+HSsMmqbQOtDxjnQsD+l/q3C7DqNLmg54luzVdSqVuNFXT/P6M3IHIZwDysHksFZHimyTtAmfQ7PnXjREgLq6Mz5hhhadI4vx7CmxsC3AW5kbnfnIgr0sI3s9sY5+h7Pmv7NTloM6LfUDNj9XlZpUWNZ9NtYI1WT4rYvOJKrfxsTp0KeXGAx4olOdQPibWHw/bzJrS/PA3DmPnXnPx6lTTuxLNL7YUUSjp0QBIxMKOXKr4cGMi1e+TsOhjSWVj3OHV2CJVfiSHCHyosjSTYHEWn6l+Bp7go4QRMrfHKP37e+0k4=");
		AmazonS3 bucketClient = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion(region).build();

		uploadToTwitterDataBucket(bucketName,bucketClient);
	}

	public static void uploadToTwitterDataBucket(String bucketName,AmazonS3 bucketClient) {

		String path = "D:/Materiel/Serverless Data Processing/Assignments/B00866255_Jariwala_Akshit_5410_Assignment4/AWSComprehend/file_mongo_tweets.txt";

		File tweetFile = new File(path);
		String fileName = "file_mongo_tweets.txt";
		try{
				PutObjectRequest fileObject = new PutObjectRequest(bucketName,fileName,tweetFile);
				bucketClient.putObject(fileObject);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
