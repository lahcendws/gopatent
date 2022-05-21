package com.wildcodeschool.patent.service;

import com.wildcodeschool.patent.DTO.PatentDTO;
import com.wildcodeschool.patent.entity.User;
import com.wildcodeschool.patent.repository.UserRepository;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to give request to the restcontroller for Patent Data from Patent number or a search
 */
@Service
public class EPORequestService {

    @Autowired
    private OPSBearerToken opsBearerToken;
    @Autowired
    private BadgerFish badgerFish;

    @Autowired
    private UserRepository userRepository;

    @Value("${brevet.app.pageSize}")
    private Integer pageSize;

    @Autowired
    private PatentService patentService;

    @Value("${epo.url}")
    private String epoUrl;

    @Value("${patent.data.url}")
    private String patentDataUrl;

    @Value("${patent.search.url}")
    private String patentSearchUrl;


    /**
     * method to get patent by Patent Number
     * @param patentNumber
     * @return
     * @throws Exception
     */
    public PatentDTO getPatentByNumber(String patentNumber, Authentication authentication) throws Exception {
        User user = userRepository.getByEmail(authentication.getName());
        String url = epoUrl + patentDataUrl + patentNumber + "/biblio";
        Response response = opsBearerToken.executeRequest(url, "application/json");
        String jsonData = response.body().string();
        JSONObject jobject = new JSONObject(jsonData);
        JSONObject patentData = jobject.getJSONObject("ops:world-patent-data");
        JSONObject documents = badgerFish.getJSON(patentData, "exchange-documents");
        JSONObject document = badgerFish.getJSON(documents, "exchange-document");
        PatentDTO patentDTO = patentService.getPatent(document, user);
        return patentDTO;
    }

    /**
     * Request to search patents by keyword
     * @param query
     * @return
     * @throws Exception
     */
    public List<PatentDTO> getPatentsByQuery(String query,  Authentication authentication, int pageNumber) throws Exception {
        User user = userRepository.getByEmail(authentication.getName());
        int rangeStart = ((pageNumber -1 )* pageSize) + 1;
        int rangeEnd = (rangeStart + (pageSize)) - 1;
        String range = "&Range=" + rangeStart + "-" + rangeEnd;
        String url = epoUrl + patentSearchUrl +"?q="+ query + range;
        ArrayList<PatentDTO> patentList = new ArrayList<>();

        Response response = opsBearerToken.executeRequest(url, "application/json");

        String jsonData = response.body().string();
        JSONObject jobject = new JSONObject(jsonData);

        JSONObject searchResult = jobject.getJSONObject("ops:world-patent-data")
                .getJSONObject("ops:biblio-search")
                .getJSONObject("ops:search-result");

        JSONArray documents = badgerFish.getJSONArray(searchResult, "exchange-documents");

        for(Object documentObj: documents) {
            JSONObject documentJSONObj = (JSONObject) documentObj;
            JSONObject document = badgerFish.getJSON(documentJSONObj, "exchange-document");
            PatentDTO patentDTO = patentService.getPatent(document, user);
            patentList.add(patentDTO);
        }
        return patentList;
    }
}
