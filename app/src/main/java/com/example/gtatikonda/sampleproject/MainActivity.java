package com.example.gtatikonda.sampleproject;


import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;




public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_PERMISSION_STORAGE = 107;
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 106;
    private static final int REQUEST_CODE_PICK_FILE = 100;
    private TextView startDatePickerTextView;
    private TextView endDatePickerTextView;
    private String startDateString;
    private String endDateString;
    private TextView startTimePickerTextView;
    private TextView endTimePickerTextView;
    private ImageView imageView;
    File file;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView=findViewById(R.id.copyRight);
        String string=String.format(getString(R.string.copyRight),"2019");
        textView.setText(string);
        startDateString=Utils.simpleDateFormat.format(Calendar.getInstance().getTime());
        endDateString=Utils.simpleDateFormat.format(Calendar.getInstance().getTime());
        startDatePickerTextView =findViewById(R.id.datePickerTextView);
        endDatePickerTextView =findViewById(R.id.endDatePickerTextView);
        endDatePickerTextView =findViewById(R.id.endDatePickerTextView);
        startTimePickerTextView =findViewById(R.id.startTimePickerTextView);
        imageView=findViewById(R.id.imageView);
        imageButton=findViewById(R.id.uploadImage);
        endTimePickerTextView =findViewById(R.id.endTimePickerTextView);
        startDatePickerTextView.setText(Utils.requiredFormat.format(Calendar.getInstance().getTime()));
        startDatePickerTextView.setOnClickListener(this);
        endDatePickerTextView.setText(Utils.requiredFormat.format(Calendar.getInstance().getTime()));
        endDatePickerTextView.setOnClickListener(this);
        startTimePickerTextView.setText(Utils.requiredTimeFormat.format(Calendar.getInstance().getTime()));
        startTimePickerTextView.setOnClickListener(this);
        endTimePickerTextView.setText(Utils.requiredTimeFormat.format(Calendar.getInstance().getTime()));
        endTimePickerTextView.setOnClickListener(this);
        imageButton.setOnClickListener(this);


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.datePickerTextView:
                //startDatePickerTextView.setError("error");
               // Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();
           /* Utils.getDateFromDatePicker(this, startDateString, startDatePickerTextView, new Utils.DatePickerButtonClickListener() {
                @Override
                public void onDialogButtonClick(boolean isPositiveButton) {
                    startDateString=Utils.getDateString();
                }
            });*/
          // openFileManager();
                ArrayList<String> list= new ArrayList<String>();
                for(int i=0;i<30;i++){
                    list.add("listItem   "+i);
                }
                MyDialogFragment myDialogFragment=MyDialogFragment.newInstance(list);
                myDialogFragment.show(getSupportFragmentManager(),"Dialog");

          /* MyDialogFragment myDialogFragment=new MyDialogFragment();
           myDialogFragment.show(getSupportFragmentManager(),"Dialog");*/
/*
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("List");
                builder.setCancelable(false);
               ArrayList<String> list= new ArrayList<String>();
               for(int i=0;i<30;i++){
                   list.add("listItem   "+i);
               }
                ArrayAdapter arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
                builder.setSingleChoiceItems(arrayAdapter, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                });
                builder.show();*/
                break;
            case R.id.endDatePickerTextView:
                Utils.getDateFromDatePicker(this, endDateString, endDatePickerTextView, new Utils.DatePickerButtonClickListener() {
                    @Override
                    public void onDialogButtonClick(boolean isPositiveButton) {
                        endDateString=Utils.getDateString();
                    }
                });
                break;
            case R.id.endTimePickerTextView:
                Utils.getTimeFromPicker(this,endTimePickerTextView);
                break;
            case R.id.startTimePickerTextView:
                Utils.getTimeFromPicker(this,startTimePickerTextView);
                break;

            case R.id.uploadImage:
                break;

        }
    }
    private void openFileManager() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_STORAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("*/*"); // This is required as stated here //https://stackoverflow.com/questions/21045091/no-activity-found-to-handle-intent-android-intent-action-open-document
            //https://issuetracker.google.com/issues/36986001
//                        intent.putExtra(Intent.EXTRA_MIME_TYPES,
//                                new String[]{Utils.MIME_TYPE_IMAGE,
//                                        Utils.MIME_TYPE_VIDEO,
//                                        Utils.MIME_TYPE_PDF,
//                                        Utils.MIME_TYPE_TEXT,
//                                        Utils.MIME_TYPE_GOOGLE_SHEET,
//                                        Utils.MIME_TYPE_GOOGLE_DOCS,
//                                        Utils.MIME_TYPE_GOOGLE_PPT,
//                                        Utils.MIME_TYPE_MS_WORD_DOC,
//                                        Utils.MIME_TYPE_MS_SHEET_DOC,
//                                        Utils.MIME_TYPE_MS_PPT_DOC});
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            startActivityForResult(intent, REQUEST_CODE_PICK_FILE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case REQUEST_CODE_PERMISSION_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFileManager();
                }
                break;

            case REQUEST_CODE_PERMISSION_CAMERA:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode) {
            case REQUEST_CODE_PICK_FILE:
                if (resultCode == RESULT_OK) {
                    processPickedFile(data);
                }
                break;

        }
    }
    private void processPickedFile(final Intent data) {
        if (data != null && data.getData() != null) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Uri uri = data.getData();
                    try {
                        ContentResolver contentResolver = getContentResolver();
                        String mimeType = contentResolver.getType(uri);
                        final InputStream ips = contentResolver.openInputStream(uri);
                        file=new File(getFilesDir(),checkImageFileName(uri.getLastPathSegment(),mimeType));
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        byte[] buff = new byte[5 * 1024];
                        int len;
                        while ((len = ips.read(buff)) != -1) {
                            fileOutputStream.write(buff, 0, len);
                        }
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    public static String checkImageFileName(String lastPathSegment, String fileType) {
        if (lastPathSegment.contains(":")) {
            lastPathSegment = lastPathSegment.split(":")[1];
            if (lastPathSegment.contains("/")) {
                lastPathSegment = lastPathSegment.substring(lastPathSegment.lastIndexOf("/") + 1);
                boolean isExtension = imageEndsWithExtension(lastPathSegment);
                if (!isExtension) {
                    String imageFileExtension = getImageFileExtension(fileType);
                    lastPathSegment = lastPathSegment + imageFileExtension;
                }
            } else {
                boolean isExtension = imageEndsWithExtension(lastPathSegment);
                if (!isExtension) {
                    String imageFileExtension = getImageFileExtension(fileType);
                    lastPathSegment = lastPathSegment + imageFileExtension;
                }
            }
        } else {
            if (lastPathSegment.contains("/")) {
                lastPathSegment = lastPathSegment.substring(lastPathSegment.lastIndexOf("/") + 1);
                boolean isExtension = imageEndsWithExtension(lastPathSegment);
                if (!isExtension) {
                    String imageFileExtension = getImageFileExtension(fileType);
                    lastPathSegment = lastPathSegment + imageFileExtension;
                }
            } else {
                boolean isExtension = imageEndsWithExtension(lastPathSegment);
                if (!isExtension) {
                    String imageFileExtension = getImageFileExtension(fileType);
                    lastPathSegment = lastPathSegment + imageFileExtension;
                }
            }
        }
        return lastPathSegment;
    }
    private static boolean imageEndsWithExtension(String name) {
        name = name.toLowerCase();
        if (name.endsWith("jpeg"))
            return true;
        if (name.endsWith("jpg"))
            return true;
        if (name.endsWith("png"))
            return true;
        if (name.endsWith("webp"))
            return true;
        if (name.endsWith("bmp"))
            return true;
        if (name.endsWith("gif"))
            return true;
        if (name.endsWith("heic"))
            return true;
        if (name.endsWith("heif"))
            return true;
        return false;
    }
    private static String getImageFileExtension(String mimetype) {

        switch (mimetype) {

            case "image/jpeg":
                return ".jpeg";
            case "image/jpg":
                return ".jpg";
            case "image/png":
                return ".png";
            case "image/webp":
                return ".webp";
            case "image/bmp":
                return ".bmp";
            case "image/gif":
                return ".gif";
            case "image/heic":
                return ".heic";
            case "image/heif":
                return ".heif";
        }
        return "";
    }


}
