package com.project.newview;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ramya on 4/20/2017.
 */

public class GenerateKeys {
    Context context;
    ArrayList<HashMap<String, Object>> Keysholder= new ArrayList<>();
    GenerateKeys(Context context){
        this.context=context;
    }

    public class downloadkeys extends AsyncTask<Void, Void, Void>{
        Dialog dialog;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog= ProgressDialog.show(context,"Loading", "downloading Keys");
        }



        @Override
        protected Void doInBackground(Void... params) {
            ManagerClass managerClass= new ManagerClass();
            managerClass.getCredentials(context);
            AmazonS3Client s3Client=managerClass.inits3client(context);
            List<S3ObjectSummary> s3ObjectSummaries = s3Client.listObjects("utils.mybucket").getObjectSummaries();
            for(S3ObjectSummary summary:s3ObjectSummaries){
                HashMap<String, Object> maps= new HashMap<>();
                maps.put("key", summary.getKey());
                Keysholder.add(maps);

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            dialog.dismiss();

        }
    }
}

