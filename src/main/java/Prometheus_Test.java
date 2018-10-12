import io.prometheus.client.*;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;

import java.io.IOException;

public class Prometheus_Test {

    private static void buildMetrics()  {

        Counter counter = Counter.build()
                .name("test_counter")
                .help("Creating counter")
                .labelNames("name", "age")
                .register();
        counter.labels("Jim", "21").inc(10);

        Gauge gauge = Gauge.build()
                .name("test_gauge")
                .help("Creating gauge")
                .labelNames("name", "age")
                .register();
        gauge.labels("Jim", "21").inc(10);
        gauge.labels("Jim", "21").dec(5);

        Histogram histogram = Histogram.build()
                .name("test_histogram")
                .help("Creating histogram")
                .labelNames("name", "age")
                .buckets(2,4,6,8)
                .register();
        histogram.labels("Jim", "21").observe(10);

        Summary summary = Summary.build()
                .name("test_summary")
                .help("Creating summary")
                .labelNames("name", "age")
                .quantile(.65, 0.001)
                .quantile(0.5,0.001)
                .quantile(0.32,0.02)
                .quantile(0.98,0.0001)
                .register();
        summary.labels("Jim", "21").observe(5);




    }

    public static void queryMetric() {

        //instant queries through HTTP API

        String Querystring = "query?query=test_counter";
        QueryHttpAPI query = new QueryHttpAPI();
        query.requestQuery(Querystring);


    }

    public static void conditionalQuery(){

        //queries with conditions through HTTP API

        String QuerystringCondition = "query?query=test_gauge{name=\"Jim\"}";
        QueryHttpAPI query = new QueryHttpAPI();
        query.requestQuery(QuerystringCondition);

    }


    public static void main(String[] args) throws IOException, InterruptedException {
        DefaultExports.initialize();
        HTTPServer server = new HTTPServer(9080);

        buildMetrics();  //Create metrics and insert values

        Thread.sleep(1000);     //Latency due to Prometheus scrape interval

        queryMetric();

        conditionalQuery();
    }
}
