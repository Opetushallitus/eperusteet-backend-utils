package fi.vm.sade.eperusteet.utils.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.vm.sade.javautils.http.OphHttpClient;
import fi.vm.sade.javautils.http.OphHttpEntity;
import fi.vm.sade.javautils.http.OphHttpRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;

@Slf4j
@Service
public class OphClientHelper {

    @Autowired
    RestClientFactory restClientFactory;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> List<T> getList(String serviceUrl, String url, Class<T> clazz) {
        String result = execute(serviceUrl, url, null, HttpGet.METHOD_NAME);
        return parseList(result, clazz);
    }

    public <T> T get(String serviceUrl, String url, Class<T> clazz) {
        String result = execute(serviceUrl, url, null, HttpGet.METHOD_NAME);
        return parseResult(result, clazz);
    }

    public void post(String serviceUrl, String url) {
        post(serviceUrl, url, Object.class);
    }

    public <T> T post(String serviceUrl, String url, Class<T> clazz) {
        return post(serviceUrl, url, null, clazz);
    }

    public <T> T post(String serviceUrl, String url, Object content, Class<T> clazz) {
        String result = execute(serviceUrl, url, content, HttpPost.METHOD_NAME);
        return parseResult(result, clazz);
    }

    public <T> List<T> postAsList(String serviceUrl, String url, Object content, Class<T> clazz) {
        String result = execute(serviceUrl, url, content, HttpPost.METHOD_NAME);
        return parseList(result, clazz);
    }

    private String execute(String serviceUrl, String url, Object content, String httpMethod) {
        OphHttpClient client = restClientFactory.get(serviceUrl, true);

        try {
            OphHttpRequest.Builder builder = new OphHttpRequest.Builder(httpMethod, url);
            if(content != null) {
                builder = builder.addHeader("Content-Type", "application/json;charset=UTF-8")
                            .setEntity(new OphHttpEntity.Builder()
                            .content(objectMapper.writeValueAsString(content))
                            .contentType(ContentType.APPLICATION_JSON)
                            .build());
            }

            OphHttpRequest request = builder.build();

            return client.<String>execute(request)
                    .expectedStatus(SC_OK, SC_CREATED)
                    .mapWith(text -> text)
                    .orElse(null);
        } catch (JsonProcessingException e) {
            throw new OphClientException("kutsu-epaonnistui", e);
        }
    }

    private <T> T parseResult(String result, Class<T> clazz) {
        try {
            if(StringUtils.isEmpty(result)) {
                return null;
            }
            return objectMapper.readValue(result, clazz);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new OphClientException("parsinta-epaonnistui");
        }

    }

    private <T> List<T> parseList(String result, Class<T> clazz) {
        try {
            if(StringUtils.isEmpty(result)) {
                return Collections.emptyList();
            }

            return  objectMapper.readValue(result, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new OphClientException("parsinta-epaonnistui");
        }

    }

}
