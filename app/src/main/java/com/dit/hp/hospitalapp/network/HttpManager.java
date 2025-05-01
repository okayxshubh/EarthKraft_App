package com.dit.hp.hospitalapp.network;


import static com.dit.hp.hospitalapp.LoginScreen.decryptText;

import android.util.Log;

import com.dit.hp.hospitalapp.Modals.ResponsePojoGet;
import com.dit.hp.hospitalapp.Modals.UploadObject;
import com.dit.hp.hospitalapp.utilities.Econstants;
import com.dit.hp.hospitalapp.utilities.EncryptDecrypt;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.HttpsURLConnection;

public class HttpManager {


    EncryptDecrypt ED;
    String decryptedData;

//    public HttpManager() throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException {
//        ED = new EncryptDecrypt();
//    }


    // LOGIN POST DATA
    public ResponsePojoGet LoginPostData(UploadObject data) {

        String decryptedData;
        String encryptedData;
        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        ResponsePojoGet response = null;
        String URL = null;

        try {
            URL = data.getUrl() + data.getMethordName() + (data.getMasterName() != null ? data.getMasterName() : "");
            Log.e("URL Formed", "URL FORMED: " + URL);

            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            conn_.setDoOutput(true);
            conn_.setRequestMethod("POST");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
            conn_.connect();

            OutputStreamWriter out = new OutputStreamWriter(conn_.getOutputStream(), "UTF-8");
            out.write(data.getParam());
            out.flush();
            out.close();

            int HttpResult = conn_.getResponseCode();
            BufferedReader br;
            if (HttpResult != HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), "utf-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
            }
            String line;
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            Log.e("Data from Service", sb.toString());


            encryptedData = sb.toString();
            decryptedData = decryptText(encryptedData);

            Log.e("Decrypted Data: ", decryptedData);

            response = new ResponsePojoGet();
            response = Econstants.createOfflineObject(URL, data.getParam(), decryptedData, Integer.toString(conn_.getResponseCode()), data.getMethordName());

        } catch (Exception e) {
            e.printStackTrace();
            if (conn_ != null) {
                try {
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), conn_.getResponseMessage(), Integer.toString(conn_.getResponseCode()), data.getMethordName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn_ != null) {
                conn_.disconnect();
            }
        }
        return response;
    }


    public ResponsePojoGet GetData(UploadObject data) {
        String decryptedData;
        String encryptedData;
        BufferedReader reader = null;
        URL urlObj = null;
        ResponsePojoGet response = null;
        HttpURLConnection connection = null;

        try {
            StringBuilder SB = new StringBuilder();

            if (data.getAPI_NAME() != null && data.getAPI_NAME().equalsIgnoreCase(Econstants.API_NAME_HRTC)) {
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
                if (data.getParam() != null && !data.getParam().isEmpty()) {
                    SB.append(data.getParam());
                }
            } else {
                SB.append(data.getUrl());
                SB.append(data.getMethordName());
                if (data.getParam() != null && !data.getParam().isEmpty()) {
                    SB.append(data.getParam());
                }
            }

            urlObj = new URL(SB.toString());
            Log.e("URL Formed", "URL FORMED: " + urlObj.toString());

            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();
            InputStream stream = (responseCode == 200) ? connection.getInputStream() : connection.getErrorStream();

            reader = new BufferedReader(new InputStreamReader(stream, "utf-8"));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            encryptedData = sb.toString();
            Log.e("Encrypted Data: ", encryptedData);

            if (responseCode == 200) {
                decryptedData = decryptText(encryptedData);
                Log.e("Decrypted Data: ", decryptedData);
                response = Econstants.createOfflineObject(data.getUrl(), data.getParam(), decryptedData, Integer.toString(responseCode), data.getMethordName());
            } else {
                response = Econstants.createOfflineObject(data.getUrl(), data.getParam(), encryptedData, Integer.toString(responseCode), data.getMethordName());
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    response = Econstants.createOfflineObject(data.getUrl(), data.getParam(), e.getLocalizedMessage(), Integer.toString(connection.getResponseCode()), data.getMethordName());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response;
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

    // Delete Post mapping.. Same but with JSON Body
    public ResponsePojoGet DeleteDataWithJson(UploadObject data) {
        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        ResponsePojoGet response = null;

        try {
            // Construct the DELETE URL
            String URL = data.getUrl() + data.getMethordName();
            Log.e("URL Formed", "URL FORMED: " + URL.toString());

            // Initialize connection
            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            conn_.setRequestMethod("DELETE");
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "application/json");
            conn_.setDoOutput(true); // Enable sending body data

            // Write JSON body if present
            if (data.getParam() != null && !data.getParam().isEmpty()) {
                try (OutputStream os = conn_.getOutputStream()) {
                    byte[] input = data.getParam().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }

            // Handle the response
            int responseCode = conn_.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                // Error response
                BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), "utf-8"));
                String line;
                sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                Log.e("Error Response", sb.toString());
                response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString(), String.valueOf(responseCode), data.getMethordName());
            } else {
                // Success response
                BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
                String line;
                sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                Log.d("Success Response", sb.toString());
                response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString(), String.valueOf(responseCode), data.getMethordName());
            }
        } catch (MalformedURLException e) {
            Log.e("MalformedURLException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        } finally {
            if (conn_ != null) {
                conn_.disconnect();
            }
        }

        return response;
    }

    // POST DATA With Master Name + JSON body
    public ResponsePojoGet PostData(UploadObject data) {
        String URL = null;
        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        ResponsePojoGet response = null;

        String encryptedData;
        String decryptedData;

        try {
            URL = data.getUrl() + data.getMethordName() + (data.getMasterName() != null ? data.getMasterName() : "");
            Log.e("URL Formed", "URL FORMED: " + URL);

            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            conn_.setDoOutput(true);
            conn_.setRequestMethod("POST");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");  // Cuz Backend Accepts Plain Text
//            conn_.setRequestProperty("Content-Type", "application/json");
            conn_.connect();

            OutputStreamWriter out = new OutputStreamWriter(conn_.getOutputStream(), "UTF-8");
            out.write(data.getParam());
            out.flush();
            out.close();

            int HttpResult = conn_.getResponseCode();
            BufferedReader br;
            if (HttpResult != HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), "utf-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
            }

            String line;
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

            Log.e("Data from Service", sb.toString());

            encryptedData = sb.toString();
            decryptedData = decryptText(encryptedData);

            Log.e("Decrypted Data: ", decryptedData);

            response = new ResponsePojoGet();
            response = Econstants.createOfflineObject(URL, data.getParam(), decryptedData, Integer.toString(conn_.getResponseCode()), data.getMethordName());

        } catch (Exception e) {
            e.printStackTrace();
            if (conn_ != null) {
                try {
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), conn_.getResponseMessage(), Integer.toString(conn_.getResponseCode()), data.getMethordName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn_ != null) {
                conn_.disconnect();
            }
        }

        return response;
    }


    // PUT DATA With Master Name + JSON body
    public ResponsePojoGet PutData(UploadObject data) {

        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        JSONStringer userJson = null;

        String URL = null;
        ResponsePojoGet response = null;


        try {

            URL = data.getUrl() + data.getMethordName() + data.getMasterName();
            Log.e("URL Formed", "URL FORMED: " + URL.toString());

            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            conn_.setDoOutput(true);
            conn_.setRequestMethod("PUT");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "application/json");
            conn_.connect();


            OutputStreamWriter out = new OutputStreamWriter(conn_.getOutputStream());
            out.write(data.getParam());
            out.close();

            try {
                int HttpResult = conn_.getResponseCode();
                if (HttpResult != HttpURLConnection.HTTP_OK) {
                    Log.e("Error", conn_.getResponseMessage());
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString(), Integer.toString(conn_.getResponseCode()), data.getMethordName());
                    return response;


                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString(), Integer.toString(conn_.getResponseCode()), data.getMethordName());

                }

            } catch (Exception e) {
                data.getScanDataPojo().setUploaddToServeer(false);
                response = new ResponsePojoGet();
                response = Econstants.createOfflineObject(URL, data.getParam(), conn_.getResponseMessage(), Integer.toString(conn_.getResponseCode()), data.getMethordName());
                return response;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn_ != null)
                conn_.disconnect();
        }
        return response;
    }

    // DELETE MAPPING
    public ResponsePojoGet DeleteData(UploadObject data) {

        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        JSONStringer userJson = null;

        String URL = null;
        ResponsePojoGet response = null;


        try {

            URL = data.getUrl() + data.getMethordName() + data.getMasterName();
            Log.e("URL Formed", "URL FORMED: " + URL.toString());

            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            conn_.setDoOutput(true);
            conn_.setRequestMethod("DELETE");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "application/json");
            conn_.connect();


            if (Econstants.isNotEmpty(data.getParam())) {
                OutputStreamWriter out = new OutputStreamWriter(conn_.getOutputStream());
                out.write(data.getParam());
                out.close();
            }


            try {
                int HttpResult = conn_.getResponseCode();
                if (HttpResult != HttpURLConnection.HTTP_OK) {
                    Log.e("Error", conn_.getResponseMessage());
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString(), Integer.toString(conn_.getResponseCode()), data.getMethordName());
                    return response;


                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsePojoGet();
                    response = Econstants.createOfflineObject(URL, data.getParam(), sb.toString(), Integer.toString(conn_.getResponseCode()), data.getMethordName());

                }

            } catch (Exception e) {
                data.getScanDataPojo().setUploaddToServeer(false);
                response = new ResponsePojoGet();
                response = Econstants.createOfflineObject(URL, data.getParam(), conn_.getResponseMessage(), Integer.toString(conn_.getResponseCode()), data.getMethordName());
                return response;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn_ != null)
                conn_.disconnect();
        }
        return response;
    }



}
