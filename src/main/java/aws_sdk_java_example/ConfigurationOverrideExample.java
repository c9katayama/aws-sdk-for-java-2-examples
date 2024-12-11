package aws_sdk_java_example;

import java.time.Duration;

import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.retry.RetryPolicy;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

public class ConfigurationOverrideExample {
	public static void main(String[] args) {

		S3Client client = S3Client.builder()//
				.overrideConfiguration(ClientOverrideConfiguration.builder()//
						.apiCallAttemptTimeout(Duration.ofSeconds(60))//
						.apiCallTimeout(Duration.ofMinutes(5))//
						.retryPolicy(RetryPolicy.builder().numRetries(3).build())//
						.build())//
				.region(Region.AP_NORTHEAST_1).build();

		// リクエストの作成
		ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
		// API呼び出しと結果の取得
		ListBucketsResponse response = client.listBuckets(listBucketsRequest);
		response.buckets().forEach(System.out::println);

	}
}
