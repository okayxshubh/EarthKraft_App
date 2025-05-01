package com.dit.hp.hospitalapp.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dit.hp.hospitalapp.Modals.ResponsePojoGet;
import com.dit.hp.hospitalapp.Modals.UploadObject;
import com.dit.hp.hospitalapp.enums.TaskType;
import com.dit.hp.hospitalapp.interfaces.AsyncTaskListenerObject;


public class Generic_Async_Get extends AsyncTask<UploadObject,Void , ResponsePojoGet> {


    String outputStr;
    ProgressDialog dialog;
    Context context;
    AsyncTaskListenerObject taskListener;
    TaskType taskType;

    public Generic_Async_Get(Context context, AsyncTaskListenerObject taskListener, TaskType taskType) {
        this.context = context;
        this.taskListener = taskListener;
        this.taskType = taskType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Loading", "Connecting to Server .. Please Wait", true);
        dialog.setCancelable(false);
    }

    @Override
    protected ResponsePojoGet doInBackground(UploadObject... uploadObjects) {
        ResponsePojoGet Data_From_Server = null;
        HttpManager http_manager = null;
        try {
            http_manager = new HttpManager();

            if(uploadObjects[0].getTasktype().toString().equalsIgnoreCase(TaskType.GET_GENDERS.toString())){
                Data_From_Server = http_manager.GetData(uploadObjects[0]);
                return Data_From_Server;
            }

            else if(uploadObjects[0].getTasktype().toString().equalsIgnoreCase(TaskType.GET_REGISTRATION_MODES.toString())){
                Data_From_Server = http_manager.GetData(uploadObjects[0]);
                return Data_From_Server;
            }

            else if(uploadObjects[0].getTasktype().toString().equalsIgnoreCase(TaskType.GET_REFERRED_BY.toString())){
                Data_From_Server = http_manager.GetData(uploadObjects[0]);
                return Data_From_Server;
            }

            else if(uploadObjects[0].getTasktype().toString().equalsIgnoreCase(TaskType.GET_TESTS.toString())){
                Data_From_Server = http_manager.GetData(uploadObjects[0]);
                return Data_From_Server;
            }




        } catch (Exception e) {
            Log.e("Value Saved",e.getLocalizedMessage().toString());
        }
        return Data_From_Server;

    }


    @Override
    protected void onPostExecute(ResponsePojoGet result) {
        super.onPostExecute(result);

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        if (result != null) {
            try {
                taskListener.onTaskCompleted(result, taskType);
            } catch (Exception e) {
                Log.e("OnPostExecute", "OnPostExecute Exception: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            Log.e("OnPostExecute", "Result is NULL");
        }
    }

}




