package com.example.myapplication.utils;

import com.example.myapplication.Entities.Admin;
import com.example.myapplication.Entities.Document;
import com.example.myapplication.Entities.Etablissement;
import com.example.myapplication.Entities.Etudiant;
import com.example.myapplication.Entities.Niveau;
import com.example.myapplication.Entities.Professeur;
import com.example.myapplication.Entities.ResponsableStage;
import com.example.myapplication.Entities.Reunion;
import com.example.myapplication.R;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api_Calls_Interface {
    /**
     * comptes
     */

    //ADMIN
    @POST("/Admin/Login/{email}/{password}")
    Call<Admin> LoginAdmin(@Path("email") String email, @Path("password") String password);

    //respo
    @POST("/ResponsableStage/Login/{email}/{password}")
    Call<ResponsableStage> LoginRespo(@Path("email") String email, @Path("password") String password);
    @POST("/ResponsableStage/save")
    Call<ResponsableStage> saveRespo(@Body ResponsableStage responsableStage);
    @GET("/ResponsableStage/find/All")
    Call<List<ResponsableStage>> FindAllRespo();
    @GET("/ResponsableStage/find/{id}")
    Call<ResponsableStage> FindRespoById(@Path("id") int id);
    @PATCH("/ResponsableStage/update/{id}")
    Call<ResponsableStage> modifierRespo(@Body ResponsableStage responsableStage, @Path("id") int id);
    @DELETE("/ResponsableStage/delete/{id}")
    Call<ResponsableStage> supprimerResponsableStage(@Path("id") int id);
    @DELETE("/ResponsableStage/delete/All")
    Call<ResponsableStage> supprimerAllResponsableStages();


    /////////
//ETD
   // @Headers({"Content-Type:multipart/form-data"})
    @Multipart
    @POST("Etudiant/save/uploadExcel")
    Call<Etudiant> saveEtdFromExcel(@Part MultipartBody.Part file);
    @POST("/Etudiant/Login/{email}/{password}")
    Call<Etudiant> LoginEtd(@Path("email") String email, @Path("password") String password);
    @PUT("/Etudiant/update/{id}")
    Call<Etudiant> modifierEtudiant(@Body Etudiant etudiant, @Path("id") int id);
    @GET("/Etudiant/find/{id}")
    Call<Etudiant> FindEtudiantbyId(@Path("id") int id);
    @GET("/Etudiant/find/All")
    Call<List<Etudiant>> recupererAllEntudiants();
    @GET("/Etudiant/find/Niveau={nbNiveau}")
    Call<List<Etudiant>> recupererEtudiantByNiveau(@Path("nbNiveau") int nbNiveau);
    @DELETE("Etudiant/delete/{id}")
    Call<Etudiant> supprimerETD(@Path("id") int id);
    @DELETE("Etudiant/delete/All")
    Call<Etudiant> supprimerAllEtudiants();


    //PROF
    @POST("/Professeur/save")
    Call<Professeur> saveProf(@Body Professeur professeur);
    @POST("/Professeur/Login/{email}/{password}")
    Call<Professeur> LoginProf(@Path("email") String email, @Path("password") String password);
    @POST("/Professeur/affecter/prof_etd")
    Call<Professeur> affecterPROF_ETD(@Body Professeur professeur,@Query("idP") int idP);
    @PUT("/Professeur/update/{id}")
    Call<Professeur> modifierProf(@Body Professeur professeur, @Path("id") int id);
    @GET("/Professeur/find/{id}")
    Call<Professeur> FindProfById(@Path("id") int id);
    @GET("/Professeur/find/All")
    Call<List<Professeur>> recupererAllProfs();
    @DELETE("/Professeur/delete/{id}")
    Call<Professeur> supprimerProfesseur(@Path("id") int id);
    @DELETE("/Professeur/delete/All")
    Call<Professeur> supprimerAllProfesseurs();



    ////////////////////////////////////

    /**
     * etab,niv
     */

    //Etab
    @POST("/Etablissement/save")
    Call<Etablissement> save(@Body Etablissement etablissement);
    @GET("/Etablissement/find/All")
    Call<List<Etablissement>> recupererAllEtablissement();
    @GET("/Etablissment/find/{id}")
    Call<Etablissement> recupererEtablissementParId(@Path("id") int id);
    @DELETE("/Etablissment/delete/All")
    Call<Etablissement> supprimerEtablissements();
    @DELETE("/Etablissment/delete/{id}")
    Call<Etablissement> supprimerEtablissement(@Path("id") int id);
    @PUT("/Etablissement/update/{id}")
    Call<Etablissement> modifierEtablissment(@Body Etablissement etablissement, @Path("id") int id);

    //niveau
    @POST("/Niveau/save")
    Call<Niveau> save(@Body Niveau niveau);
    @GET("/Niveau/find/All")
    Call<List<Niveau>> recupererAllNiveaux();
    @DELETE("/Niveau/delete/{id}")
    Call<Niveau> delete(@Path("id") int id);
    @DELETE("/Niveau/delete/all")
    Call<Niveau> deleteAll();
    @PUT("/Niveau/update/{id}")
    Call<Niveau> modifierNiveau(@Body Niveau niveau, @Path("id") int id);

    //document
    @Multipart
    @POST("Document/upload/file/by={ID}")
    Call<Document> uploadDoc(@Path("ID") int ID,@Part MultipartBody.Part file);
    @GET("/Document/find/all/by/etudiants")
    Call<List<Document>> findAllDocumentByETD();
    @GET("/Document/download/{id}")
    Call<Document> downloadFile(@Path("id") int id);

    //reunion
    @POST("/Reunion/organiser/{idE}/{idP}")
    Call<Reunion> organiserReunion(@Body Reunion reunion, @Path("idE") int idE, @Path("idP") int idP);
    @GET("/Reunion/findByETD/{idE}")
    Call<Reunion> recupererReunionByETD(@Path("idE") int idE);
    @GET("/Reunion/findByPROF/{idP}")
    Call<List<Reunion>> recupererReunionByPROF(@Path("idP") int idP);
}
