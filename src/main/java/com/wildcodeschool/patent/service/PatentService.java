package com.wildcodeschool.patent.service;

import com.wildcodeschool.patent.entity.Domain;
import com.wildcodeschool.patent.entity.Ipc;
import com.wildcodeschool.patent.DTO.PatentDTO;
import com.wildcodeschool.patent.entity.User;
import com.wildcodeschool.patent.repository.FavoritePatentRepository;
import com.wildcodeschool.patent.repository.IpcRepository;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Service to get patent content
 */
@Service
public class PatentService {

    @Autowired
    private IpcRepository ipcRepository;

    @Autowired
    private BadgerFish badgerFish;

    @Autowired
    private FavoritePatentRepository favoritePatentRepository;

    @Autowired
    OPSBearerToken opsBearerToken;

    @Value("${epo.url}")
    private String epoUrl;

    @Value("${patent.data.url}")
    private String patentDataUrl;

    private HashSet<String> inventorsArrayList ;
    private HashSet<String> domainArrayList = new HashSet<>();
    private HashSet<String> bigDomainnArrayList = new HashSet<>();

    /**
     * get patent content
     * @param document
     * @return
     * @throws Exception
     */
    public  PatentDTO getPatent(JSONObject document, User user) throws Exception {

        // get patent data
        JSONObject biblio = document.getJSONObject("bibliographic-data");
        String patentNumber = getPatentNumber(biblio);
        String title = getTitle(biblio);
        Boolean isFavorite = favoritePatentRepository.findByPublicationCodeAndUser(patentNumber, user).isPresent();
        String releaseDate = getReleaseDate(document);
        String applicantName = getApplicantName(biblio);
        this.inventorsArrayList = new HashSet<>();
        String familyId = document.getString("@family-id");
        String ipc = getIpc(biblio);
        String espaceNetUrl = "https://worldwide.espacenet.com/patent/search/family/"
                + familyId
                + "/publication/"
                + patentNumber
                + "?q=" + patentNumber;
        String patentAbstract = getPatentAbstract(document);
        String figure = getPatentFigure(patentNumber);
        getInventorsArrayList(document);

        if (ipc != null) {
            // get domains names
            getDomainNames(ipc);
        }
        return new PatentDTO( patentNumber, title, isFavorite, releaseDate, applicantName, inventorsArrayList, familyId,
                ipc, domainArrayList, bigDomainnArrayList, espaceNetUrl, patentAbstract, figure );
    }

    /**
     * greturn applicant name
     * @param biblio
     * @return
     */
    public String getApplicantName(JSONObject biblio) {
        JSONObject parties = biblio.getJSONObject("parties");
        JSONObject applicants =  badgerFish.getJSON(parties, "applicants");
        String applicantName = "";
        try {
            applicantName = badgerFish.getJSONArray(applicants, "applicant")
                    .getJSONObject(0)
                    .getJSONObject("applicant-name")
                    .getJSONObject("name")
                    .getString("$");
        } catch (Exception e ) {
            applicantName = "";
        }
        return applicantName;
    }

    /**
     * return patent number
     * @param biblio
     * @return
     */
    private String getPatentNumber(JSONObject biblio) {

        JSONObject publicationReference = biblio.getJSONObject("publication-reference");

        String country = badgerFish.getJSONArray(publicationReference, "document-id")
                .getJSONObject(0)
                .getJSONObject("country")
                .getString("$");

        String docNumber = badgerFish.getJSONArray(publicationReference, "document-id")
                .getJSONObject(0)
                .getJSONObject("doc-number")
                .getString("$");

        return country + docNumber;
    }

    /**
     * return patent abstract
     * @param document
     * @return
     */
    @Nullable
    private String getPatentAbstract(JSONObject document) {

        String patentAbstract = null;
        JSONObject patentAbstractObj = badgerFish.getJSON(document, "abstract");
        if (patentAbstractObj != null) {
            patentAbstract = patentAbstractObj.getJSONObject("p")
                    .getString("$");
        }
        return patentAbstract;
    }

    /**
     * return title if no exception
     * @param biblio
     * @return
     */
    private String getTitle(JSONObject biblio) {

        JSONObject titleObject = badgerFish.getJSON(biblio, "invention-title");
        String title = "";
        try {
            title = titleObject.getString("$");
        } catch (NullPointerException e) {
            title = "";
        }
        return title;
    }

    /**
     *
     * @param biblio
     * @return
     */
    @Nullable
    private String getIpc(JSONObject biblio) {

        JSONObject classificationsIpcr = null;
        try {
            classificationsIpcr = biblio.getJSONObject("classifications-ipcr");
        } catch (JSONException jsonE) {

        }

        String ipc = null;
        if (classificationsIpcr != null) {
            JSONObject classificationIpcr = badgerFish.getJSON(classificationsIpcr, "classification-ipcr");

            String fullIpc = badgerFish.getJSONArray(classificationIpcr, "text")
                    .getJSONObject(0)
                    .getString("$");

            ipc = getIpc(fullIpc);
        }
        return ipc;
    }

    /**
     * return release date
     * @param document
     * @return
     */
    private String getReleaseDate(JSONObject document) {
        String releaseDate = document.getJSONObject("bibliographic-data")
                .getJSONObject("publication-reference")
                .getJSONArray("document-id")
                .getJSONObject(0)
                .getJSONObject("date")
                .getString("$");
        return releaseDate;
    }


    /**
     * Return Ipc Code
     * @param fullIpc
     * @return String
     */
    private String getIpc(String fullIpc) {

        String sharp = String.valueOf(fullIpc.charAt(3));
        if (sharp == "#") {
            return fullIpc.substring(0, 4) ;
        }

        String eightSharp = String.valueOf(fullIpc.charAt(7));
        if (eightSharp == "#") {
            return fullIpc.substring(0, 7);
        }

        String hyphen = String.valueOf(fullIpc.charAt(4));
        if(hyphen == "-") {
            return fullIpc.substring(0, 8);
        }

        return fullIpc.substring(0, 4);
    }

    /**
     * Return domain name
     * @param ipc
     * @return String
     * @throws Exception
     */
    public void getDomainNames(String ipc) throws Exception {
        Ipc ipcObject = null;

        try {
            if(ipcRepository.existsByIpc(ipc)) {
                ipcObject = ipcRepository.findByIpc(ipc).orElseThrow(()-> new Exception());
            } else {
                String subIpc = ipc.substring(0, 3);
                ipcObject = ipcRepository.findByIpc(subIpc).orElseThrow(()-> new Exception());
            }
        } catch ( Exception e) {
            ipcObject = null;
        }

        List<Domain> domains = null;
        if (ipcObject != null) {
            domains = ipcObject.getDomains();
            for (Domain domain: domains) {
                domainArrayList.add(domain.getDomainName());
                bigDomainnArrayList.add(domain.getBigDomainName());
            }
        }

    }

    /**
     * methode to get figure image
     * @param patentNumber
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public String getPatentFigure(String patentNumber) throws URISyntaxException, IOException {

        String contentType = null;
        String imageURL = null;
        /**
         * url to get image url (I know it's weird)
         */
        String url = epoUrl + patentDataUrl + patentNumber + "/images";

        /**
         * get figure array
         */
        JSONArray figuresArray = getFigureArray(url);

        /**
         * get binary figures
         */
        String jImageUrl = null;

        JSONObject drawingFigure = null;
        JSONObject fullDocument = null;
        JSONObject firstPageFigure = null;

        if(figuresArray != null) {
            drawingFigure = badgerFish.getJSONArrayByIndex(figuresArray, 0);
            fullDocument = badgerFish.getJSONArrayByIndex(figuresArray, 1);
            firstPageFigure = badgerFish.getJSONArrayByIndex(figuresArray, 2);
        }

        Map<String, String> optionalHeaders = new HashMap<>();

        if (firstPageFigure != null && Objects.equals(firstPageFigure.getString("@desc"), "FirstPageClipping")) {
            jImageUrl = firstPageFigure.getString("@link");
            contentType = "image/png";
        }
        if (firstPageFigure == null && drawingFigure != null && Objects.equals(drawingFigure.getString("@desc"), "Drawing")) {
            jImageUrl = drawingFigure.getString("@link");
            contentType = "application/tiff";
            optionalHeaders.put("Range", "1");
        }
        if (firstPageFigure == null && drawingFigure == null && fullDocument != null && Objects.equals(fullDocument.getString("@desc"), "FullDocument")) {
            jImageUrl = fullDocument.getString("@link");
            contentType = "application/tiff";
            optionalHeaders.put("Range", "3");
        }
        if (firstPageFigure == null && drawingFigure == null && fullDocument == null) {
            return "";
        }

        /**
         *  return base64 figure
         */
        return getBase64Figure(jImageUrl, imageURL, contentType, optionalHeaders);
    }

    /**
     * fill inventors arraylist
     * @param document
     */
    public void getInventorsArrayList(JSONObject document) {
        inventorsArrayList.clear();
        JSONObject parties = document.getJSONObject("bibliographic-data")
                .getJSONObject("parties");
        JSONArray inventors = badgerFish.getJSONArray(parties, "inventors");

        if (inventors != null) {
            for (int i = 0; i < inventors.length(); i++) {
                JSONArray inventorArray = badgerFish.getJSONArray(inventors.getJSONObject(i), "inventor");
                for (int y = 0; y < inventorArray.length(); y++) {
                    String inventor = null;
                    try {
                        inventor = inventorArray.getJSONObject(y).getJSONObject("inventor-name").getJSONObject("name").getString("$");
                        inventorsArrayList.add(inventor.toUpperCase().replaceAll("\\p{Punct}", ""));
                    } catch (Exception e ) {
                        return;
                    }
                }
            }
        }
    }

    /**
     * get figures array
     * @param url
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public JSONArray getFigureArray(String url) throws URISyntaxException, IOException {

        JSONArray figuresArray = null;
        Response response = opsBearerToken.executeRequest(url, "application/json");
        if (response.code() == 402 || response.code() == 404 || response.code() == 500) {
            return figuresArray;
        }
        String jsonData = response.body().string();
        JSONObject jObjectFromData = new JSONObject(jsonData);
        JSONObject jObject = badgerFish.getJSON(jObjectFromData,"ops:world-patent-data")
                .getJSONObject("ops:document-inquiry");
        JSONObject figures = badgerFish.getJSONArray(jObject, "ops:inquiry-result")
                .getJSONObject(0);
        figuresArray = badgerFish.getJSONArray(figures, "ops:document-instance");

        return figuresArray;
    }

    /**
     * get base 64 String figure
     * @param jImageUrl
     * @param imageURL
     * @param contentType
     * @param optionalHeaders
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String getBase64Figure(String jImageUrl, String imageURL, String contentType, Map<String, String> optionalHeaders) throws IOException, URISyntaxException {

        if (jImageUrl != null) {
            imageURL = epoUrl + jImageUrl;
        }

        if (imageURL != null && contentType != null) {
            Response responseImage = opsBearerToken.executeRequest(imageURL, contentType, optionalHeaders);
            byte[] data = responseImage.body().bytes();

            if (contentType == "application/tiff") {
                InputStream is = new ByteArrayInputStream(data);
                BufferedImage newBi = ImageIO.read(is);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(newBi, "png", baos);
                data = baos.toByteArray();
            }
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(data);
        }
        return "";
    }
}

