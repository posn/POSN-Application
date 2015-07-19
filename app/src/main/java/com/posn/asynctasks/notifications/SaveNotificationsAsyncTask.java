package com.posn.asynctasks.notifications;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.posn.datatypes.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class SaveNotificationsAsyncTask extends AsyncTask<String, String, String>
   {
      private ProgressDialog pDialog;

      private Context context;
      private String filePath;

      private ArrayList<Post> wallData;


      public SaveNotificationsAsyncTask(Context context, String filePath, ArrayList<Post> wallData)
         {
            super();
            this.context = context;
            this.filePath = filePath;

            this.wallData = wallData;
         }


      // Before starting background thread Show Progress Dialog
      @Override
      protected void onPreExecute()
         {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Saving Data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
         }


      // Checking login in background
      protected String doInBackground(String... params)
         {
            System.out.println("SAVING WALL POSTS!!!");

            JSONArray wallPostList = new JSONArray();

            try
               {
                  for(int i = 0; i < wallData.size(); i++)
                     {
                        Post post = wallData.get(i);
                        wallPostList.put(post.createJOSNObject());
                     }

                  JSONObject object = new JSONObject();
                  object.put("posts", wallPostList);

                  String jsonStr = object.toString();

                  FileWriter fw = new FileWriter(filePath);
                  BufferedWriter bw = new BufferedWriter(fw);
                  bw.write(jsonStr);
                  bw.close();

               }
            catch (JSONException e)
               {
                  e.printStackTrace();
               }
            catch (IOException e)
               {
                  e.printStackTrace();
               }

            return null;
         }


      // After completing background task Dismiss the progress dialog
      protected void onPostExecute(String file_url)
         {
            // dismiss the dialog once done
            pDialog.dismiss();
         }
   }