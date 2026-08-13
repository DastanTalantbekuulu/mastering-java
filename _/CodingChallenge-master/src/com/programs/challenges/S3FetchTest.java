/*package com.programs.challenges;

import java.io.IOException;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pepperfry.awsutils.s3.S3Bucket;

public class S3FetchTest {
	

	
	private static S3Bucket bucket;
	
	private static ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) {
		try{
			
			AmazonS3 s3Client = new AmazonS3Client(getBasicCredentials());
			s3Client.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_1));
			bucket = new S3Bucket("crmorderbucket", s3Client);
			
			getData(bucket);
			
			
		}catch(Exception e){
			System.err.println("Error in connection...");
		}

	}
	
	private static void getData(S3Bucket bucket){
		try(S3ObjectInputStream stream = bucket.downloadObject("200001027")){
			CRMOrderBeanOld oldData = mapper.readValue(stream, CRMOrderBeanOld.class);
			System.out.println(mapper.writeValueAsString(stream));
		} catch (IOException e) {
			System.err.println("Error in input stream");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	private static BasicAWSCredentials getBasicCredentials(){
		return new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
	}

}
*/