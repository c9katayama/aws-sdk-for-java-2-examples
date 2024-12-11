package aws_sdk_java_example;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeSecurityGroupsRequest;
import software.amazon.awssdk.services.ec2.model.DescribeSecurityGroupsResponse;
import software.amazon.awssdk.services.ec2.model.IpPermission;
import software.amazon.awssdk.services.ec2.model.SecurityGroup;

public class EC2SecurityGroupExample {

	public static void main(String[] args) {
		Ec2Client client = Ec2Client.builder().region(Region.AP_NORTHEAST_1).build();
		// セキュリティグループの一覧を取得
		DescribeSecurityGroupsRequest request = DescribeSecurityGroupsRequest.builder().build();
		DescribeSecurityGroupsResponse response = client.describeSecurityGroups(request);

		// SSHポートの空いたセキュリティグループを探す
		for (SecurityGroup sg : response.securityGroups()) {
			boolean isPort22Open = sg.ipPermissions().stream().anyMatch(permission -> isPortOpen(permission, 22));
			if (isPort22Open) {
				System.out.println("Security Group ID: " + sg.groupId());
				System.out.println("  Name: " + sg.groupName());
			}
		}
	}

	// ポートが開いているかを確認する
	private static boolean isPortOpen(IpPermission permission, int port) {
		// ポート範囲をチェック
		return permission.fromPort() != null//
				&& permission.toPort() != null//
				&& permission.fromPort() <= port//
				&& permission.toPort() >= port;
	}
}
