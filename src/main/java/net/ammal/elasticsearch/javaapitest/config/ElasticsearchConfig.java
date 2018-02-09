package net.ammal.elasticsearch.javaapitest.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    private final ServiceProperties serviceProperties;

    public ElasticsearchConfig(ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    @Bean
    public RestHighLevelClient elasticSearchRestClient() {
        HttpHost httpHost = new HttpHost(
                serviceProperties.getElasticsearch().getHostname(),
                serviceProperties.getElasticsearch().getPort(),
                "http");

        return new RestHighLevelClient(RestClient.builder(httpHost));
    }
}
