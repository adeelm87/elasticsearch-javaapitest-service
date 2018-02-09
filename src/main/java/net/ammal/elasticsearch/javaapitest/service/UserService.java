package net.ammal.elasticsearch.javaapitest.service;

import net.ammal.elasticsearch.javaapitest.mapper.ResponseMapper;
import net.ammal.elasticsearch.javaapitest.model.ResponseJson;
import net.ammal.elasticsearch.javaapitest.model.SearchResponseJson;
import net.ammal.elasticsearch.javaapitest.model.UserInput;
import net.ammal.elasticsearch.javaapitest.repository.UserDocument;
import net.ammal.elasticsearch.javaapitest.repository.UserRepository;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ResponseMapper responseMapper;

    public UserService(UserRepository userRepository, ResponseMapper responseMapper) {
        this.userRepository = userRepository;
        this.responseMapper = responseMapper;
    }

    public ResponseJson createUser(UserInput input) {
        UserDocument document = new UserDocument.Builder()
                .withUserId(input.getUserId())
                .withFirstName(input.getFirstName())
                .withLastName(input.getLastName())
                .withEmailAddress(input.getEmailAddress())
                .withRoles(input.getRoles())
                .build();

        IndexResponse indexResponse = userRepository.indexUser(document);

        return responseMapper.map(indexResponse);
    }

    public ResponseJson createUsersBulk(List<UserInput> users) {
        List<UserDocument> documents = users.stream()
                .map(user -> new UserDocument.Builder()
                            .withUserId(user.getUserId())
                            .withFirstName(user.getFirstName())
                            .withLastName(user.getLastName())
                            .withEmailAddress(user.getEmailAddress())
                            .withRoles(user.getRoles())
                            .build() )
                .collect(Collectors.toList());

        BulkResponse bulkResponse = userRepository.indexUsersBulk(documents);

        return responseMapper.map(bulkResponse);
    }

    public ResponseJson getUser(UUID userId) {
        GetResponse getResponse = userRepository.getUser(userId);

        return responseMapper.map(getResponse);
    }

    public ResponseJson deleteUser(UUID userId) {
        DeleteResponse deleteResponse = userRepository.deleteUser(userId);

        return responseMapper.map(deleteResponse);
    }

    public SearchResponseJson getAllUsers() {
        SearchResponse searchResponse = userRepository.getAllUsers();

        return responseMapper.map(searchResponse);
    }

    public SearchResponseJson getAllUsersWithSpecifiedPermission(String permissionName) {
        SearchResponse searchResponse = userRepository.getAllUsersWithSpecifiedPermission(permissionName);

        return responseMapper.map(searchResponse);
    }
}
