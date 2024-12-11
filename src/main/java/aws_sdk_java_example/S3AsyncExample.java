package aws_sdk_java_example;

import java.util.concurrent.CompletableFuture;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

public class S3AsyncExample {
	public static void main(String[] args) {
		// 非同期クライアントの作成
		S3AsyncClient client = S3AsyncClient.builder().region(Region.AP_NORTHEAST_1).build();
		// リクエストの作成
		ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
		// 非同期API呼び出し
		CompletableFuture<ListBucketsResponse> response = client.listBuckets(listBucketsRequest);
		// 結果を非同期で受け取り
		response.thenAccept(res -> {
			res.buckets().forEach(bucket -> System.out.println(bucket.name()));
		}).exceptionally(exception -> {
			// エラーハンドリング
			exception.printStackTrace();
			return null;
		});

		response.join();
	}
}
