package Assignment3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

public class LambdaOperation {

    public static void main(String args[])
    {
        Regions region = Regions.US_EAST_1;
        String bucketName = "sampledatab00866255";
        BasicSessionCredentials creds = new BasicSessionCredentials(
                    "ASIAVJDCQO5AQ7WARSEJ",
                "OX2dugehgwhR35zJCd80bpvMvbs6VbSr1bzdS9X/",
                "FwoGZXIvYXdzEIb//////////wEaDCnVykHLVExS/QLcLCK/AT4k3cMtN8L0SkB681H7ZFcWlDSN9uCtk4sX7tSdUDtRIuV1jnzKpcQUbHrV5ZX4OHYLdPZGFSXxLkzj65VfD7+kJSeH85fjQkzLqNky2FtZsAXNZo0ghDGLw+aFlpvnQpIA6chcZ5iOhZHVaLuUnoNMPHIppXS2HtgfGxJc9XAv4IkbJtufTdPbMTXWGW9bOlxLUJOv0aMdNUwWExhfwSSTI/9cAcxmZAf4bphJ2clFc2CuokelcSGdh9BEWPM6KKOd3oYGMi0b500XpRzLypklN1+4CWMilv9d0e7LCBNtTToviYTbsrV8vkg0uLUVC717VWo=");
        AmazonS3 bucketClient = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion(region).build();

        uploadFiles(bucketName,bucketClient);
    }

    public static void uploadFiles(String bucketName,AmazonS3 bucketClient)
    {
        String directoryPath = "D:/Materiel/Serverless Data Processing/Assignments/B00866255_Jariwala_Akshit_5410_Assignment3/tech";

        File techFolder = new File(directoryPath);
        String fileName = null;
        String[] fileList = techFolder.list();

        try
        {
            for (String file:fileList) {
                fileName = file;
                String filePath = directoryPath +"/"+fileName;
                PutObjectRequest fileObject = new PutObjectRequest(bucketName,fileName,new File(filePath));
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
