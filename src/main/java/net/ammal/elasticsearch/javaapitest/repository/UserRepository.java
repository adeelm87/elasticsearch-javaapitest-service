package net.ammal.elasticsearch.javaapitest.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.ammal.elasticsearch.javaapitest.config.ServiceProperties;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class UserRepository {

    private final RestHighLevelClient restHighLevelClient;

    private final ObjectMapper objectMapper;

    private final ServiceProperties serviceProperties;

    private final String elasticSearchIndex;

    private final String elasticSearchType;

    public UserRepository(RestHighLevelClient restHighLevelClient,
                          ObjectMapper objectMapper,
                          ServiceProperties serviceProperties) {
        this.restHighLevelClient = restHighLevelClient;
        this.objectMapper = objectMapper;
        this.serviceProperties = serviceProperties;

        elasticSearchIndex = serviceProperties.getElasticsearch().getIndex();
        elasticSearchType = serviceProperties.getElasticsearch().getType();
    }

    public IndexResponse indexUser(UserDocument document) {
        IndexRequest indexRequest = new IndexRequest(elasticSearchIndex, elasticSearchType, document.getUserId().toString());

        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(document);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        indexRequest.source(jsonString, XContentType.JSON);

        IndexResponse indexResponse = null;
        try {
            indexResponse = restHighLevelClient.index(indexRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return indexResponse;
    }

    public BulkResponse indexUsersBulk(List<UserDocument> documents) {
        BulkRequest bulkRequest = new BulkRequest();

        for (UserDocument document : documents) {
            String jsonString = null;
            try {
                jsonString = objectMapper.writeValueAsString(document);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            IndexRequest indexRequest = new IndexRequest(elasticSearchIndex, elasticSearchType, document.getUserId().toString())
                    .source(jsonString, XContentType.JSON);

            bulkRequest.add(indexRequest);
        }

        BulkResponse bulkResponse = null;
        try {
            bulkResponse = restHighLevelClient.bulk(bulkRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bulkResponse;
    }

    public GetResponse getUser(UUID userId) {
        GetRequest getRequest = new GetRequest(elasticSearchIndex, elasticSearchType, userId.toString());

        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return getResponse;
    }

    public DeleteResponse deleteUser(UUID userId) {
        DeleteRequest deleteRequest = new DeleteRequest(elasticSearchIndex, elasticSearchType, userId.toString());

        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = restHighLevelClient.delete(deleteRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return deleteResponse;
    }

    public SearchResponse getAllUsers() {
        SearchRequest searchRequest = new SearchRequest(elasticSearchIndex);
        searchRequest.types(elasticSearchType);

        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            new RuntimeException(e);
        }

        return searchResponse;
    }

    public SearchResponse getAllUsersWithSpecifiedPermission(String permissionName) {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("roles.permissions.permissionName", permissionName));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(100);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest searchRequest = new SearchRequest(elasticSearchIndex);
        searchRequest.types(elasticSearchType);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return searchResponse;
    }
}
