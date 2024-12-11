package aws_sdk_java_example;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

public class S3SyncExample {
	public static void main(String[] args) {
		S3Client client = S3Client.builder().region(Region.AP_NORTHEAST_1).build();

		// リクエストの作成
		ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
		// API呼び出しと結果の取得
		ListBucketsResponse response = client.listBuckets(listBucketsRequest);

		response.buckets().forEach(System.out::println);

	}
}
