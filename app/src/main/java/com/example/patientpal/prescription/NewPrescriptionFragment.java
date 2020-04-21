package com.example.patientpal.prescription;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.patientpal.R;
import com.example.patientpal.model.Prescription;
import com.example.patientpal.services.VolleySingletonRequestQueue;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class NewPrescriptionFragment extends Fragment{

    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 103;

    private ImageButton takePictureBtn, choosePicFromGalleryBtn, retryButton, sendButton;
    private ImageView prescriptionImage;
    private AutoCompleteTextView autoTextPharmacy;
    private EditText instructions;

    private String mImageFileName;
    private Uri mContentURI;

    //API
    RequestQueue mRequestQueue;

    String currentPhotoPath;


    //Firebase Storage
    private StorageReference mStorageRef;

    private static final String[] pharmaciesAvailable = new String[]{ "Joes Pharmacy", "McElroys Pharmacy", "Your Local Pharmacies", "Bobs Pharmacy", "Boots", "Jockos Pharmacy", "Macho Meds Pharm", "Snake oil supplies"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.new_prescription_frag_layout, container, false);

        //instantiation
        takePictureBtn = v.findViewById(R.id.ImageButtonTakePhoto);
        choosePicFromGalleryBtn = v.findViewById(R.id.ImageButtonChooseFromGallery);
        prescriptionImage = v.findViewById(R.id.prescription_photo_area);
        autoTextPharmacy = v.findViewById(R.id.autoCompletePharmacy);
        retryButton = v.findViewById(R.id.retryBtn);
        retryButton.setVisibility(View.GONE);
        sendButton = v.findViewById(R.id.sendPrescriptionBtnView);

        //Probably be of pharmacy objects
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, pharmaciesAvailable);
        autoTextPharmacy.setAdapter(adapter);


        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermission();
            }
        });

        choosePicFromGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });


        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prescriptionImage.setImageDrawable(null);
                choosePicFromGalleryBtn.setVisibility(View.VISIBLE);
                takePictureBtn.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.GONE);
            }
        });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImageToStorage();
                //Toast.makeText(getContext(), "Send Button Hit", Toast.LENGTH_SHORT).show();
            }
        });


        instructions = v.findViewById(R.id.rxinstructions);

        //Instantaite the Storage reference
        mStorageRef = FirebaseStorage.getInstance().getReference();
        return v;
    }


    ////Methods
    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);


        }else {
            //Permission is already granted
            System.out.println("Calling dispatch Picture Intent");
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                System.out.println("Calling dispatch Picture Intent");
                dispatchTakePictureIntent();
            }else{
                Toast.makeText(getContext(), "Camera access needed to take picture", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CAMERA_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK) {
                System.out.println("Current photo path: " + currentPhotoPath);
                takePictureBtn.setVisibility(View.GONE);
                choosePicFromGalleryBtn.setVisibility(View.GONE);
                File imageFile = new File(currentPhotoPath);
                mImageFileName = imageFile.getName();
                mContentURI = Uri.fromFile(imageFile);
                prescriptionImage.setImageURI(mContentURI);
                retryButton.setVisibility(View.VISIBLE);
            }
        }

        if(requestCode == GALLERY_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK) {

                mContentURI = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                mImageFileName = "JPEG_" + timeStamp + "."+getFileExtension(mContentURI);
                prescriptionImage.setImageURI(mContentURI);

                takePictureBtn.setVisibility(View.GONE);
                choosePicFromGalleryBtn.setVisibility(View.GONE);
            }
        }
    }

    private String getFileExtension(Uri contentUri) {
        ContentResolver c = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }


    private File createImageFile() throws IOException {
        System.out.println("create image file mthd created");
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        System.out.println("Image absolute path " + image.getAbsolutePath());
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }



    private void dispatchTakePictureIntent() {
        System.out.println("Dispatch Take picture Intent called");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    public void postAPrescription(String imagePathStr) throws JSONException {


        mRequestQueue = VolleySingletonRequestQueue.getInstance(getContext()).getRequestQueue();
        Prescription rx = new Prescription();

        rx.setPharmacyNameStr(autoTextPharmacy.toString());
        rx.setPictureURL(imagePathStr);
        rx.setInstructions(instructions.getText().toString());
        rx.setPrescriptionCreationTime(new Date().getTime());

        JSONObject jsonBody = new JSONObject();

        jsonBody.put("instructions", rx.getInstructions());
        jsonBody.put("prescriptionCreationTime", rx.getPrescriptionCreationTime());
        jsonBody.put("rxImageURI", rx.getPictureURL());


        JsonObjectRequest jsonOBJReq = new JsonObjectRequest(Request.Method.POST, getString(R.string.spring_boot_url) + "mobile/prescriptionPost", jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(), "Prescription Sent", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        mRequestQueue.add(jsonOBJReq);

    }


    public void uploadImageToStorage(){

        final StorageReference storageRef = mStorageRef.child("prescriptionImages/" + UUID.randomUUID() + ".jpg");

        //Toast.makeText(getContext(), mContentURI.toString(), Toast.LENGTH_SHORT).show();
        storageRef.putFile(mContentURI)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(getContext(), "Successful post to firebase" + uri.toString(), Toast.LENGTH_SHORT).show();

                                try {
                                    postAPrescription(uri.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Upload has failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }




}
