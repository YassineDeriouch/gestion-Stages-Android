package com.example.myapplication.activities.gestion_comptes.gestion_etd;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Entities.Document;
import com.example.myapplication.Entities.Etudiant;
import com.example.myapplication.R;
import com.example.myapplication.activities.Login_ETD_Activity;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.activity_creer_compte_respo;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_import_doc_etd extends AppCompatActivity {

    private ImageView btn_import_file_byETD;
    public int idEt = Login_ETD_Activity.idE;
    public static File file;
    private static ProgressDialog progressDialog;
    private static final int PICKFILE_RESULT_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_doc_etd);
        btn_import_file_byETD = findViewById(R.id.import_doc_etd_iv);

        getSupportActionBar().hide();

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        btn_import_file_byETD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importFiles();
            }
        });

    }

    protected void showProgressDialog(String title, String msg) {
        progressDialog = ProgressDialog.show(this, title, msg, true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void dismissProgressDialog() {
        progressDialog.dismiss();
    }



    public void importFiles() {
        Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickIntent.setType("*/*");
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        pickIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(pickIntent, "Open with ..."), PICKFILE_RESULT_CODE);
    }
public static String fn ;
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
                fn = file.getName();
                path = createCopyAndReturnRealPath(getApplicationContext(),uri);
                System.out.println("p==="+ path);
                try {
                    uploadFiletoApi(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("path","path is null");
                Toast.makeText(activity_import_doc_etd.this, "erreur de lectur : null path" ,Toast.LENGTH_SHORT);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Nullable
    public static String createCopyAndReturnRealPath(Context context, Uri uri) {
        final ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null)
            return null;

        // Create file path inside app's data dir
        String filePath = context.getApplicationInfo().dataDir + File.separator + fn;
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



    @TargetApi(Build.VERSION_CODES.O)
    public void uploadFiletoApi (String path) throws IOException {
        file = new File(path);
        showProgressDialog("Importer un document", "Importation en cours...");

        String fname = file.getName();
        System.out.println("fname "+fname);
        Document document = new Document();

        String extension = fname.substring(fname.lastIndexOf(".") + 1, fname.length());
        String ftype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

        RequestBody requestBody = RequestBody.create(MediaType.parse(ftype), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        System.out.println("filetoupload==" +fileToUpload);

        document.setFileName(fname);
        document.setFileType(ftype);
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
        document.setDateEnvoi(sqlDate);

        byte bytes[] = new byte[(int) file.length()];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        DataInputStream dis = new DataInputStream(bis);
        dis.readFully(bytes);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        document.setFichier(fileContent);

        if (!extension.equals("") || !fname.contains("..")) {
            Retrofit_Instance retrofit_instance = new Retrofit_Instance();
            Api_Calls_Interface api_calls_interface = retrofit_instance.getRetrofit().create(Api_Calls_Interface.class);
            api_calls_interface.uploadDoc(idEt, fileToUpload).enqueue(new Callback<Document>() {
                @Override
                public void onResponse(Call<Document> call, Response<Document> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Votre document a été envoyé avec succès  ", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Erreur d'envoi", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Document> call, Throwable t) {
                    Toast.makeText(activity_import_doc_etd.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    System.out.println("erreur import retrofit on failure");
                }
            });
        } else {
            progressDialog.dismiss();
            AlertDialog alertDialog = new AlertDialog.Builder(activity_import_doc_etd.this).create();
            alertDialog.setTitle("Information");
            alertDialog.setMessage("Veuillez importer un fichier valide");
            alertDialog.setIcon(R.drawable.ic_baseline_error_24);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        }

    }
}