package knowledge.hood.door2mart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import knowledge.hood.door2mart.Models.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Account extends AppCompatActivity {
    TextView usermobile;
    TextView tv;
    CardView logout, cardView2, cardView3, cardView5;

    TextView head_userName,head_useraddress, head_pincode;


    ImageButton pers_info_edit;

    String user_id;

    EditText input_name, input_house, input_road, input_city, input_pincode;

//    ImageView usr_img, usr_img_upload, usr_mob_update;

//    private static final int STORAGE_PERMISSION_CODE= 4655;
//    private  int IMAGE_PICK_REQUEST = 1;
//    private Uri filepath;
//    private Bitmap bitmap;
//
//    public static final String UPLOAD_URL = "https://emanational-barrel.000webhostapp.com/user/add=333";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);



        pers_info_edit= findViewById(R.id.pers_info_edit);
        cardView2 = findViewById(R.id.cardView2);
        cardView3 = findViewById(R.id.cardView3);
        logout = findViewById(R.id.logout);
        cardView5= findViewById(R.id.cardView5);

//        tv2= findViewById(R.id.tv2);

        usermobile= findViewById(R.id.usermobile);

        input_name=findViewById(R.id.input_name);
        input_house= findViewById(R.id.input_house);
        input_road=findViewById(R.id.input_road);
        input_city=findViewById(R.id.input_city);
        input_pincode=findViewById(R.id.input_pincode);


        head_userName=findViewById(R.id.head_userName);
        head_useraddress= findViewById(R.id.head_useraddress);
        head_pincode=findViewById(R.id.head_pincode);

        SharedPreferences sharedPreferences = getSharedPreferences("credential",MODE_PRIVATE);
        usermobile.setText(String.format("+91- %s",sharedPreferences.getString("user_mobile","000000000")));
        user_id= (sharedPreferences.getString("user_id","00"));

        getUserDetails(user_id);

//        usr_img= findViewById(R.id.usr_img);
//        usr_img_upload =  findViewById(R.id.usr_img_upload);
//        usr_mob_update = findViewById(R.id.usr_mob_update);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor =sharedPreferences.edit();
                editor.remove("user_mobile");
                editor.remove("user_id");
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), LoginUser.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getApplicationContext().startActivity(intent);

            }
        });

        pers_info_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.contains("user_mobile")) {
                    input_name.setText(head_userName.getText().toString());
                    input_pincode.setText(head_pincode.getText().toString());
                    cardView2.setVisibility(View.GONE);
                    cardView3.setVisibility(View.GONE);
                    logout.setVisibility(View.GONE);
                    cardView5.setVisibility(View.VISIBLE);
                }
                else
                    startActivity(new Intent(getApplicationContext(), LoginUser.class));
            }
        });

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.progress).setVisibility(View.VISIBLE);
                String address= input_house.getText().toString()+", "+input_road.getText().toString()+", "+
                        input_city.getText().toString();
                Call<List<UserModel>> update_call= apicontroler
                        .getInstance()
                        .getapi()
                        .updateUser(user_id,input_name.getText().toString(),address.toString(),input_pincode.getText().toString());

                update_call.enqueue(new Callback<List<UserModel>>() {
                    @Override
                    public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                       List<UserModel> data =  response.body();
                       if (data!=null){
                           head_userName.setText(data.get(0).getUser_name().toString());
                           head_useraddress.setText(data.get(0).getUser_address().toString());
                           head_pincode.setText(data.get(0).getUser_pincode().toString());
                           cardView2.setVisibility(View.VISIBLE);
                           cardView3.setVisibility(View.VISIBLE);
                           logout.setVisibility(View.VISIBLE);
                           cardView5.setVisibility(View.GONE);
                           input_name.setText("");
                           input_house.setText("");
                           input_road.setText("");
                           input_city.setText("");
                           input_pincode.setText("");
                           findViewById(R.id.progress).setVisibility(View.GONE);
                           Toast.makeText(Account.this, "Details Updated Succesfully", Toast.LENGTH_SHORT).show();
                       }
                       else
                           Toast.makeText(Account.this, "Something went wrong please try again", Toast.LENGTH_SHORT).show();
                            findViewById(R.id.progress).setVisibility(View.GONE);
                    }
                    @Override
                    public void onFailure(Call<List<UserModel>> call, Throwable t) {
                        Toast.makeText(Account.this, t.toString(), Toast.LENGTH_SHORT).show();
                        findViewById(R.id.progress).setVisibility(View.GONE);
                    }
                });

            }
        });

        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Account.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
                cardView2.setVisibility(View.VISIBLE);
                cardView3.setVisibility(View.VISIBLE);
                logout.setVisibility(View.VISIBLE);
                cardView5.setVisibility(View.GONE);
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ViewOrder.class));
            }
        });


//        usr_img_upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                requestStoragePermission();
//              //  uploadImage();
//            }
//        });

    }

    private void getUserDetails(String user_id) {

        Call<List<UserModel>> getUserdetails_call= apicontroler
                            .getInstance()
                            .getapi()
                            .getUser_details(user_id.toString());

        getUserdetails_call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                List<UserModel> data =  response.body();
                if(data!=null)
                {
//                    String nam= .toString();
//                    String ads= .toString();
//                    String pin= data.get(0).getUser_pincode().toString();
                    if(data.get(0).getUser_name()!=null && data.get(0).getUser_address()!=null && data.get(0).getUser_pincode()!=null)
                    {
                        head_userName.setText(data.get(0).getUser_name().toString());
                        head_useraddress.setText(data.get(0).getUser_address().toString());
                        head_pincode.setText(data.get(0).getUser_pincode().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(Account.this, "Failed to Connect with Server", Toast.LENGTH_SHORT).show();
            }
        });


    }

//    private void requestStoragePermission(){
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
//        {
//            ShowFileChooser();
//            return;
//        }
//
//        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
//            //If the user has denied the permission previously your code will come to this block
//            //Here you can explain why you need this permission
//            //Explain here why you need this permission
//        }
//
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            ShowFileChooser();
//
//        } else {
//            Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private void ShowFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICK_REQUEST);
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == IMAGE_PICK_REQUEST && data != null && data.getData() != null) {
//
//            filepath = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
//                Intent intent = new Intent(this, Crop_Image.class);
//                ByteArrayOutputStream bs = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.WEBP,50,bs);
//                intent.putExtra("byteArray", bs.toByteArray());
//                this.startActivity(intent);
//                usr_img.setImageBitmap(bitmap);
//                tv.setText(filepath.toString());
//                String path = getPath(filepath);
//                tv2.setText(path);
//                // Toast.makeText(getApplicationContext(),getPath(filepath),Toast.LENGTH_LONG).show();
//            } catch (Exception ex) {
//
//            }
//        }
//    }
//
//    private String getPath(Uri uri) {
//
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        cursor.moveToFirst();
//        String document_id = cursor.getString(0);
//        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
//        cursor = getContentResolver().query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + "=?", new String[]{document_id}, null
//        );
//        cursor.moveToFirst();
//        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//        cursor.close();
//        return path;
//    }

//    private void uploadImage() {
//        String path = getPath(filepath);
//        try {
//            String uploadId = UUID.randomUUID().toString();
//            new MultipartUploadRequest(this, uploadId, UPLOAD_URL).addFileToUpload(path, "upload").addParameter("t1", name).addParameter("t2", email)
//                    .setNotificationConfig(new UploadNotificationConfig())
//                    .setMaxRetries(3)
//                    .startUpload();
//
//        } catch (Exception ex) {
//
//
//        }
//
//    }

}