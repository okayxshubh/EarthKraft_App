package com.dit.hp.hospitalapp.utilities;

import com.dit.hp.hospitalapp.Modals.GenderPojo;
import com.dit.hp.hospitalapp.Modals.LoginUser;
import com.dit.hp.hospitalapp.Modals.ReferredBy;
import com.dit.hp.hospitalapp.Modals.RegistrationMode;
import com.dit.hp.hospitalapp.Modals.SuccessResponse;
import com.dit.hp.hospitalapp.Modals.TestsPojo;
import com.dit.hp.hospitalapp.Modals.TestsTypePojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParse {


    public static SuccessResponse getSuccessResponse(String data) throws JSONException {

        JSONObject responseObject = new JSONObject(data);
        SuccessResponse sr = new SuccessResponse();
        sr.setStatus(responseObject.optString("STATUS"));
        sr.setMessage(responseObject.optString("MSG"));
        sr.setData(responseObject.optString("RESPONSE"));

        return sr;
    }



//    public static SuccessResponse getSuccessResponse(String data) throws JSONException {
//        JSONObject responseObject = new JSONObject(data);
//        SuccessResponse sr = new SuccessResponse();
//
//        sr.setStatus(String.valueOf(responseObject.optInt("STATUS", 0)));
//        sr.setMessage(responseObject.optString("MSG", ""));
//
//        if (responseObject.has("RESPONSE")) {
//            Object response = responseObject.get("RESPONSE");
//            if (response instanceof JSONObject) {
//                sr.setData(response.toString()); // JSONObject
//            } else if (response instanceof String) {
//                sr.setData((String) response);    // String (example: Password Doesn't Match!)
//            }
//        } else {
//            sr.setData(""); // default empty if RESPONSE missing
//        }
//
//        return sr;
//    }




    // Parse response for decrypted Data Key
//    public static SuccessResponse getDecryptedSuccessResponse(String data) throws JSONException {
//        AESCrypto aesCrypto = new AESCrypto();
//        JSONObject responseObject = new JSONObject(data);
//
//        SuccessResponse sr = new SuccessResponse();
//        sr.setStatus(responseObject.optString("STATUS"));
//        sr.setMessage(responseObject.optString("MSG"));
//
//        String encryptedData = responseObject.optString("RESPONSE");
//        try {
//            // Decrypt and set data as JSON array string
//            String decryptedData = aesCrypto.decrypt(encryptedData);
//            sr.setData(decryptedData); // Set decrypted data directly
//            Log.i("Decrypted data array", decryptedData);
//        } catch (Exception e) {
//            Log.e("Decryption Error", "Error while decrypting data: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return sr;
//    }



    public static LoginUser parseLoginUser(String response) throws JSONException {
        JSONObject responseObject = new JSONObject(response);
        JSONObject userPojoObject = responseObject.getJSONObject("userPojo");

        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(userPojoObject.optInt("user_id"));
        loginUser.setUserName(userPojoObject.optString("user_name"));
        loginUser.setMobileNumber(userPojoObject.optLong("mobile_number"));
        loginUser.setFirstName(userPojoObject.optString("firstName"));
        loginUser.setLastNmae(userPojoObject.optString("lastName"));
        loginUser.setRoleId(responseObject.optInt("roleId"));
        loginUser.setRoleName(responseObject.optString("roleName"));

        return loginUser;
    }



    public static List<GenderPojo> parseGender(String response) throws JSONException {
        List<GenderPojo> itemList = new ArrayList<>();

        JSONArray dataArray = new JSONArray(response); // response is a JSONArray, parse directly

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject obj = dataArray.getJSONObject(i);

            GenderPojo item = new GenderPojo();
            item.setGenderId(obj.optInt("genderId"));
            item.setGenderName(obj.optString("genderName"));
            itemList.add(item);
        }
        return itemList;
    }



    public static List<RegistrationMode> parseRegistrationModes(String response) throws JSONException {
        List<RegistrationMode> itemList = new ArrayList<>();

        JSONArray dataArray = new JSONArray(response); // response is a JSONArray, parse directly

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject obj = dataArray.getJSONObject(i);

            RegistrationMode item = new RegistrationMode();
            item.setRegisrationModeId(obj.optInt("registrationModeId"));
            item.setRegisrationModeName(obj.optString("registrationModeName"));
            itemList.add(item);
        }
        return itemList;
    }

    public static List<ReferredBy> parseReferredBy(String response) throws JSONException {
        List<ReferredBy> itemList = new ArrayList<>();

        JSONArray dataArray = new JSONArray(response); // response is a JSONArray

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject obj = dataArray.getJSONObject(i);

            ReferredBy item = new ReferredBy();
            item.setReferredBYId(obj.optInt("referredbyId"));
            item.setReferredByName(obj.optString("referredbyDoctorname"));
            item.setReferredByHospitalName(obj.optString("referredbyHospitalname"));
            item.setReferredByAddress(obj.optString("referredbyAddress"));
            itemList.add(item);
        }
        return itemList;
    }


    public static List<TestsPojo> parseTests(String response) throws JSONException {
        List<TestsPojo> itemList = new ArrayList<>();

        JSONArray dataArray = new JSONArray(response); // Parse the response as a JSONArray

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject obj = dataArray.getJSONObject(i);

            TestsPojo item = new TestsPojo();
            item.setTestId(obj.optInt("testId"));
            item.setTestName(obj.optString("testName"));
            item.setTestDescription(obj.optString("testDescription"));
            item.setNormalrange(obj.optString("normalrange"));
            item.setTestprice(obj.optString("testprice"));

            // Parsing nested "testtype_id" into TestsTypePojo
            JSONObject testTypeObj = obj.optJSONObject("testtype_id");
            if (testTypeObj != null) {
                TestsTypePojo testType = new TestsTypePojo();
                testType.setTestTypeId(testTypeObj.optInt("testTypeId"));
                testType.setTestTypeName(testTypeObj.optString("testTypeName"));

                item.setTestsTypePojo(testType);
            }

            itemList.add(item);
        }
        return itemList;
    }






}
