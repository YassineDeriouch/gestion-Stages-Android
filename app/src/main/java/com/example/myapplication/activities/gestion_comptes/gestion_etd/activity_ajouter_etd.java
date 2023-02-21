package com.example.myapplication.activities.gestion_comptes.gestion_etd;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.Entities.Etudiant;
import com.example.myapplication.R;

import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_ajouter_etd extends AppCompatActivity {

    private static final int PICKFILE_RESULT_CODE = 1;
    public ImageView importFileView;
    public File file;
    private static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_etd);

        getSupportActionBar().hide();

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        importFileView = findViewById(R.id.importExcelEtd_iv);
        importFileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openFileChooser(view);
                importFiles();

            }
    });

}

    protected void showProgressDialog(String title, String msg) {
        progressDialog = ProgressDialog.show(this, title, msg, true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void dismissProgressDialog() {
        progressDialog.dismiss();
    }


    public void importFiles() {
        Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickIntent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        pickIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(pickIntent, "Open with ..."), PICKFILE_RESULT_CODE);
    }

    @Nullable
    public static String createCopyAndReturnRealPath(Context context, Uri uri) {
        final ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null)
            return null;

        // Create file path inside app's data dir
        String filePath = context.getApplicationInfo().dataDir + File.separator + "temp_file.xlsx";
        File file = new File(filePath);
        try {
            InputStream inputStream = ((ContentResolver) contentResolver).openInputStream(uri);
            if (inputStream == null)
                return null;
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                outputStream.write(buf, 0, len);
            outputStream.close();
            inputStream.close();
        } catch (IOException ignore) {
            return null;
        }
        return file.getAbsolutePath();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            System.out.println("uri =============="+uri);
            String path = createCopyAndReturnRealPath(getApplicationContext(),uri);
            System.out.println("path ==============="+path);
            Uri uri1 = Uri.parse(path);
            if(path!=null){
                file = new File(uri.getPath());
                System.out.println("if path!null file = "+file.toString()+"#### name= "+file.getName());
               path = createCopyAndReturnRealPath(getApplicationContext(),uri);
                System.out.println("p==="+ path);
                  uploadFiletoApi(path);
            } else {
                Log.d("path","path is null");
                Toast.makeText(activity_ajouter_etd.this, "erreur de lectur : null path" ,Toast.LENGTH_SHORT);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

}


       public void uploadFiletoApi (String path) {
        file = new File(path);
           showProgressDialog("Importer le ficher excel", "Importation en cours...");
           RequestBody requestBody = RequestBody.create(MediaType.parse("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), file);
       MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

           System.out.println("filetoupload==" +fileToUpload);

           String fname = file.getName();
        System.out.println("fname "+fname);

        String extension = fname.substring(fname.lastIndexOf(".") + 1, fname.length());
        if (extension.equals("xls")||extension.equals("xlsx")) {
            Retrofit_Instance retrofit_instance = new Retrofit_Instance();
            Api_Calls_Interface api_calls_interface = retrofit_instance.getRetrofit().create(Api_Calls_Interface.class);
            api_calls_interface.saveEtdFromExcel(fileToUpload).enqueue(new Callback<Etudiant>() {
                @Override
                public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "la liste des étudiants importée avec succès ", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(activity_ajouter_etd.this, activity_get_etd.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Erreur d'importation", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Etudiant> call, Throwable t) {
                    Toast.makeText(activity_ajouter_etd.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    System.out.println("erreur import retrofit on failure");
                }
            });
        } else {
            progressDialog.dismiss();
            AlertDialog alertDialog = new AlertDialog.Builder(activity_ajouter_etd.this).create();
                alertDialog.setTitle("Information");
                alertDialog.setMessage("Veuillez importer un fichier excel de format valide '.xls' ou '.xlsx' !");
                alertDialog.setIcon(R.drawable.ic_baseline_error_24);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
            }

    }
}