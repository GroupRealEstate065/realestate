package com.hothome.Utility;


public class AwsS3Constant {
	public static final String S3_BASE_URI;
	public static final String BUCKET_NAME = "hothome65";
	
	public static final String BUILDER_DOC = "builder-doc";
	public static final String USER_PHOTOS = "user-photos";
	
	static {
		
		String region = "ca-central-1";
		String pattern = "https://%s.s3.%s.amazonaws.com";
		String uri = String.format(pattern, BUCKET_NAME,region);
		S3_BASE_URI = BUCKET_NAME == null ? "": uri;
	}
	
	
	public static void main(String[] args) {
		System.err.println(S3_BASE_URI);
	}
	
}
