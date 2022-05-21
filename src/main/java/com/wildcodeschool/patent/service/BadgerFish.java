package com.wildcodeschool.patent.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * service to work around BadgerFIsh problems passing from XML to JSON through the API
 */
@Service
public class BadgerFish {

    /**
     *      * to use when we need a JSONObject and may have a JSONArray
     * @param object
     * @param attribute
     * @return
     */
    public JSONObject getJSON(JSONObject object, String attribute) {
        JSONObject jsonObject;
        try {
            jsonObject = object.getJSONObject(attribute);
        } catch(Exception e) {
            try {
                JSONArray resArray = object.getJSONArray(attribute);
                jsonObject = resArray.getJSONObject(0);
            } catch (Exception f) {
                jsonObject = null;
            }
        }
        return jsonObject;
    }

    /**
     * to use when we need a JSONArray and may have a JSONObject
     * @param object
     * @param attribute
     * @return
     */
    public JSONArray getJSONArray(JSONObject object, String attribute) {
        JSONArray jsonArray;
        try {
            jsonArray = object.getJSONArray(attribute);
        } catch (Exception e) {
            try {
                JSONObject o = object.getJSONObject(attribute);
                jsonArray = new JSONArray();
                jsonArray.put(o);
            } catch (Exception f) {
                jsonArray = null;
            }

        }
        return jsonArray;
    }

    public JSONObject getJSONArrayByIndex(JSONArray array, int index) {
        JSONObject jsonObject = null;
        try {
            jsonObject = array.getJSONObject(index);
        } finally {
            return jsonObject;
        }
    }
}
