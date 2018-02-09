package net.ammal.elasticsearch.javaapitest.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ammal.elasticsearch.javaapitest.model.ResponseJson;
import net.ammal.elasticsearch.javaapitest.model.SearchResponseJson;
import net.ammal.elasticsearch.javaapitest.repository.UserDocument;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ResponseMapper {

    private final ObjectMapper objectMapper;

    public ResponseMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ResponseJson map(IndexResponse indexResponse) {
        ResponseJson responseJson = null;

        switch (indexResponse.getResult()) {
            case UPDATED:
                responseJson = new ResponseJson("User successfully updated", "UPDATED", null);
                break;

            case CREATED:
                responseJson = new ResponseJson("User successfully created", "CREATED", null);
                break;

            default:
                responseJson = new ResponseJson("User add/update operation failed", "FAILED", null);
        }

        return responseJson;
    }


    public ResponseJson map(GetResponse getResponse) {
        ResponseJson responseJson = null;

        if (getResponse.isExists()) {
            String sourceAsString = getResponse.getSourceAsString();

            responseJson = new ResponseJson("User data retrieved", "SUCCESS", sourceAsString);
        } else {
            responseJson = new ResponseJson( "User does not exist", "FAILED", null);
        }

        return responseJson;
    }

    public ResponseJson map(DeleteResponse deleteResponse) {
        ResponseJson responseJson = null;

        if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
            responseJson = new ResponseJson("Attempt to delete failed, user does not exist", "FAILED", null);
        } else {
            responseJson = new ResponseJson("User deleted", "DELETED", null);
        }

        return responseJson;
    }

    public SearchResponseJson map(SearchResponse searchResponse) {
        long timeTaken = searchResponse.getTook().millis();

        long numberOfHits = searchResponse.getHits().totalHits;

        List<UserDocument> users = new ArrayList<>();

        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for(SearchHit hit : searchHits) {
            String sourceAsString = hit.getSourceAsString();


            try {
                UserDocument userDocument = objectMapper.readValue(sourceAsString, UserDocument.class);
                users.add(userDocument);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return new SearchResponseJson(timeTaken, numberOfHits, users);
    }

    public ResponseJson map(BulkResponse bulkResponse) {
        if(bulkResponse.hasFailures()) {
            return new ResponseJson("Bulk operation failed", "FAILED", null);
        } else {
            return new ResponseJson("Bulk operation performed successfully", "SUCCESS", null);
        }
    }
}
