package com.project.newview;

import android.content.Context;


import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.mobileconnectors.cognito.*;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitosync.model.Dataset;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * Created by sri on 4/17/2017.
 */

public class ManagerClass {

    CognitoCachingCredentialsProvider credentialsProvider = null;
    CognitoSyncManager syncManager = null;

    AmazonS3Client s3Client = null;
    TransferUtility transferUtility = null;


    public CognitoCachingCredentialsProvider getCredentials(Context context) {
        credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-west-2:1b1d6c16-20ba-4482-a865-b014932ae60f", // Identity Pool ID
                Regions.US_WEST_2// Region
        );

        syncManager = new CognitoSyncManager(context, Regions.US_WEST_2, credentialsProvider);
        Dataset dataset = (Dataset) syncManager.openOrCreateDataset("Mydataset");
        //dataset.put("mykey","myvalue");
       // dataset.synchronize(new DefaultSyncCallback());
        return credentialsProvider;

    }

    public AmazonS3Client inits3client(Context context){
        if (credentialsProvider == null) {
            getCredentials(context);
            s3Client = new AmazonS3Client(credentialsProvider);
            s3Client.setRegion(Region.getRegion(Regions.US_WEST_2));
        }
        return s3Client;
    }

    public TransferUtility checktransferutility(AmazonS3Client s3Client, Context context) {
        if (transferUtility == null) {
            transferUtility = new TransferUtility(s3Client, context);
            return transferUtility;
        } else {
            return transferUtility;
        }

    }
}
