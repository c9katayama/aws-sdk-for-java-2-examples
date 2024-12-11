package aws_sdk_java_example;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

public class S3ListObjectExample {
	public static void main(String[] args) {
		// 使用するバケット名
		String bucketName = "bucket-name";
		// リストするオブジェクトのプレフィックス
		String prefix = "2024";

		// S3クライアントの作成
		S3Client s3Client = S3Client.builder().region(Region.US_EAST_1).build();
		// リクエストの作成
		ListObjectsV2Request request = ListObjectsV2Request.builder()//
				.bucket(bucketName)// S3バケットを指定
				.prefix(prefix) // プレフィックスを指定
				.build();
		// バケット内のオブジェクトをリストする
		ListObjectsV2Response response = s3Client.listObjectsV2(request);
		// オブジェクトを表示
		for (S3Object s3Object : response.contents()) {
			System.out.println(s3Object.key());
		}
	}
}
