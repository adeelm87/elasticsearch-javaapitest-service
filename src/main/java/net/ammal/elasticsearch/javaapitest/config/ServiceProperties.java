package net.ammal.elasticsearch.javaapitest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("service")
public class ServiceProperties {

    private final ElasticsearchProperties elasticsearch;

    public ServiceProperties() {
        this.elasticsearch = new ElasticsearchProperties();
    }

    public ElasticsearchProperties getElasticsearch() {
        return elasticsearch;
    }

    public static class ElasticsearchProperties {

        private String hostname;
        private int port;
        private String index;
        private String type;

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
