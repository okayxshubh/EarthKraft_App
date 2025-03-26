package com.dit.hp.hospitalapp.network.network;


import android.util.Log;

import com.dit.hp.hospitalapp.Modals.ResponsePojoGet;
import com.dit.hp.hospitalapp.Modals.UploadObject;
import com.dit.hp.hospitalapp.utilities.Econstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpManager {

//    CryptographyAES AES = new CryptographyAES();
//
//    EncryptDecrypt ED;
//
//    public HttpManager() throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException {
//        ED = new EncryptDecrypt();
//    }

    // Without JWT Token
    public ResponsePojoGet GetDataWithoutJWT(UploadObject data) throws IOException {
        BufferedReader reader = null;
        URL urlObj = null;
        ResponsePojoGet response = null;
        HttpURLConnection connection = null;

        try {
            if (data.getAPI_NAME() != null && data.getAPI_NAME().equalsIgnoreCase(Econstants.API_NAME_HRTC)) {
                StringBuilder SB = new StringBuilder();

                SB.append(data.getUrl());
                SB.append(data.getMethordName());

                if (data.getStatus() != null) {
                    SB.append(Econstants.status);
                    SB.append(data.getStatus());
                }
                if (data.getMasterName() != null) {
                    SB.append(Econstants.masterName);
                    SB.append(data.getMasterName());
                }

                if (data.getParentId() != null) {
                    SB.append(Econstants.parentId);
                    SB.append(data.getParentId());
                }

                if (data.getSecondaryParentId() != null) {
                    SB.append(Econstants.secondaryParentId);
                    SB.append(data.getSecondaryParentId());
                }

                if (data.getParam() != null) {
                    SB.append(data.getParam());
                }

                urlObj = new URL(SB.toString());
                Log.e("URL Formed", "URL FORMED: " + urlObj.toString());

            } else {
                if (data.getParam() == null || data.getParam().isEmpty()) {
                    urlObj = new URL(data.getUrl() + data.getMethordName());
                } else {
                    urlObj = new URL(data.getUrl() + data.getMethordName() + data.getParam());
                }
            }

            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            if (connection.getResponseCode() != 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                connection.disconnect();
                Log.e("String=== ", sb.toString());
                Log.e("D Sring=== ", sb.toString());
                response = Econstants.createOfflineObject(data.getUrl(), data.getParam(), sb.toString(), Integer.toString(connection.getResponseCode()), data.getMethordName());

                return response;
            } else {

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                connection.disconnect();
                Log.e("String=== ", sb.toString());
                response = Econstants.createOfflineObject(data.getUrl(), data.getParam(), sb.toString(), Integer.toString(connection.getResponseCode()), data.getMethordName());
                return response;
            }

        } catch (Exception e) {
            e.printStackTrace();
            response = Econstants.createOfflineObject(data.getUrl(), data.getParam(), e.getLocalizedMessage(), Integer.toString(connection.getResponseCode()), data.getMethordName());

            return response;
        } finally {
            if (reader != null) {
                try {
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    response = Econstants.createOfflineObject(data.getUrl(), data.getParam(), e.getLocalizedMessage(), Integer.toString(connection.getResponseCode()), data.getMethordName());
                    return response;
                }
            }
        }
    }

    // GET Data Master + Param in URL
    public ResponsePojoGet GetData(UploadObject data) throws IOException {
        BufferedReader reader = null;
        URL urlObj = null;
        ResponsePojoGet response = null;
        HttpURLConnection connection = null;

        try {
            if (data.getAPI_NAME() != null && data.getAPI_NAME().equalsIgnoreCase(Econstants.API_NAME_HRTC)) {
                StringBuilder SB = new StringBuilder();

                SB.append(data.getUrl());
                SB.append(data.getMethordName());

                if (data.getStatus() != null) {
                    SB.append(Econstants.status);
                    SB.append(data.getStatus());
                }
                if (data.getMasterName() != null) {
                    SB.append(Econstants.masterName);
                    SB.append(data.getMasterName());
                }

                if (data.getParentId() != null) {
                    SB.append(Econstants.parentId);
                    SB.append(data.getParentId());
                }

                if (data.getSecondaryParentId() != null) {
                    SB.append(Econstants.secondaryParentId);
                    SB.append(data.getSecondaryParentId());
                }

                if (data.getParam() != null) {
                    SB.append(data.getParam());
                }

                urlObj = new URL(SB.toString());
                Log.e("URL Formed", "URL FORMED: " + urlObj.toString());

            } else {
                if (data.getParam() == null || data.getParam().isEmpty()) {
                    urlObj = new URL(data.getUrl() + data.getMethordName());
                } else {
                    urlObj = new URL(data.getUrl() + data.getMethordName() + data.getParam());
                }
            }

            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);


            if (connection.getResponseCode() != 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                connection.disconnect();
                Log.e("String=== ", sb.toString());
                Log.e("D Sring=== ", sb.toString());
                response = Econstants.createOfflineObject(data.getUrl(), data.getParam(), sb.toString(), Integer.toString(connection.getResponseCode()), data.getMethordName());

                return response;
            } else {

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                connection.disconnect();
                Log.e("String=== ", sb.toString());
                response = Econstants.createOfflineObject(data.getUrl(), data.getParam(), sb.toString(), Integer.toString(connection.getResponseCode()), data.getMethordName());
                return response;
            }

        } catch (Exception e) {
            e.printStackTrace();
            response = Econstants.createOfflineObject(data.getUrl(), data.getParam(), e.getLocalizedMessage(), Integer.toString(connection.getResponseCode()), data.getMethordName());

            return response;
        } finally {
            if (reader != null) {
                try {
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    response = Econstants.createOfflineObject(data.getUrl(), data.getParam(), e.getLocalizedMessage(), Integer.toString(connection.getResponseCode()), data.getMethordName());
                    return response;
                }
            }
        }
    }

    // POST / GET Data with JSON Body.. Add JSON Body in Object Param
    public ResponsePojoGet GetDataWithJsonBody(UploadObject data) throws IOException {
        BufferedReader reader = null;
        URL urlObj = null;
        ResponsePojoGet response = null;
        HttpURLConnection connection = null;

        try {
            // Construct URL
            if (data.getAPI_NAME() != null && data.getAPI_NAME().equalsIgnoreCase(Econstants.API_NAME_HRTC)) {
                urlObj = new URL(data.getUrl() + data.getMethordName());
            } else {
                urlObj = new URL(data.getUrl() + data.getMethordName());
            }
            Log.e("URL Formed", "URL FORMED: " + urlObj.toString());


            // Open Connection
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("POST"); // Changed to POST
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setDoOutput(true); // Allow sending request body

            // Add JSON Body
            if (data.getParam() != null && !data.getParam().isEmpty()) {
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = data.getParam().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }

            // Handle Response
            int responseCode = connection.getResponseCode();
            InputStream inputStream = (responseCode == 200) ? connection.getInputStream() : connection.getErrorStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            response = Econstants.createOfflineObject(
                    data.getUrl(),
                    data.getParam(),
                    sb.toString(),
                    Integer.toString(responseCode),
                    data.getMethordName()
            );

        } catch (Exception e) {
            e.printStackTrace();
            response = Econstants.createOfflineObject(
                    data.getUrl(),
                    data.getParam(),
                    e.getLocalizedMessage(),
                    (connection != null) ? Integer.toString(connection.getResponseCode()) : "500",
                    data.getMethordName()
            );
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response;
    }



}
