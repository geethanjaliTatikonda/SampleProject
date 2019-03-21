package com.example.gtatikonda.sampleproject;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by gtatikonda on 2/1/2019.
 */

public class NetWorkAdapter {
    Context context;

    public NetWorkAdapter(Context context){
        this.context=context;

    }
     public void UploadImage(){
        new ImageAsync("").execute();
     }


     public class ImageAsync extends AsyncTask<Void,Void,String>{
         String url;

         ImageAsync(String url){
             this.url=url;
         }

         @Override
         protected String doInBackground(Void... voids) {
             return null;
         }

         @Override
         protected void onPostExecute(String s) {
             super.onPostExecute(s);
         }
     }




}
