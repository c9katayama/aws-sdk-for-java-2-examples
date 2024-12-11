package aws_sdk_java_example;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.costexplorer.CostExplorerClient;
import software.amazon.awssdk.services.costexplorer.model.DateInterval;
import software.amazon.awssdk.services.costexplorer.model.GetCostAndUsageRequest;
import software.amazon.awssdk.services.costexplorer.model.GetCostAndUsageResponse;
import software.amazon.awssdk.services.costexplorer.model.Group;

@SuppressWarnings("unchecked")
public class BillingReportExample {
	public static void main(String[] args) {
		// AWS Cost Explorerクライアントを作成
		// Cost ExplorerはリージョンにUS_EAST_1を指定
		CostExplorerClient client = CostExplorerClient.builder().region(Region.US_EAST_1).build();

		// 現在の年月を取得
		LocalDate now = LocalDate.now();
		String startOfMonth = now.withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE);
		String endOfMonth = now.format(DateTimeFormatter.ISO_DATE);

		// GetCostAndUsageリクエストを作成
		// 取得対象のデータ粒度や期間を設定
		GetCostAndUsageRequest request = GetCostAndUsageRequest.builder()
				.timePeriod(DateInterval.builder().start(startOfMonth).end(endOfMonth).build()).granularity("MONTHLY")
				.metrics("UnblendedCost").groupBy(g -> g.key("SERVICE").type("DIMENSION")).build();

		GetCostAndUsageResponse response = client.getCostAndUsage(request);

		// サービスごとの利用料を表示
		List<Group> groups = response.resultsByTime().get(0).groups();
		System.out.println("AWS請求明細 " + YearMonth.from(now));
		System.out.println("サービス,コスト($)");
		for (Group group : groups) {
			String service = group.keys().get(0);
			String cost = group.metrics().get("UnblendedCost").amount();
			System.out.printf("\"%s\",%s%n", service, cost);
		}
	}
}
