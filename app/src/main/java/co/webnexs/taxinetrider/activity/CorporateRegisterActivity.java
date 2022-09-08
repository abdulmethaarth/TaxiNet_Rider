package co.webnexs.taxinetrider.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.loading.rotate.RotateLoading;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import co.webnexs.taxinetrider.ImageFilePath;
import co.webnexs.taxinetrider.R;
import co.webnexs.taxinetrider.beens.RegUser;
import co.webnexs.taxinetrider.countrypicker.Country;
import co.webnexs.taxinetrider.countrypicker.CountryPickerCallbacks;
import co.webnexs.taxinetrider.countrypicker.CountryPickerDialog;
import co.webnexs.taxinetrider.utils.Common;
import de.hdodenhof.circleimageview.CircleImageView;

public class CorporateRegisterActivity extends AppCompatActivity {

    TextView edit_country_code;
    EditText edit_name, edit_mobile, edit_email,edit_password, edit_cpassword;
    Uri tempUri;
    CheckBox chk;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private static final int CAMERA_REQUEST = 1888;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    CircleImageView img_add_image;
    TextView alreadyUser,edit_dob;
    Dialog ProgressDialog;
    RotateLoading cusRotateLoading;
    Dialog OpenCameraDialog;
    CountryPickerDialog countryPicker;
    String userImage,name,email,password,phone_no,dob,gender;
    private RegUser regUser = new RegUser();
    Button Register;
    Bitmap bitmap;
    private static final String TAG = "CorporateRegisterActivity";
    private android.app.ProgressDialog pDialog;
    boolean connected = false;
    DatePickerDialog picker;
    int value;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corporate_register);

        Register = (Button) findViewById(R.id.signup);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_mobile = (EditText) findViewById(R.id.edit_mobile);
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_password = (EditText) findViewById(R.id.edit_password);
        edit_cpassword = (EditText) findViewById(R.id.edit_cpassword);
        edit_dob = (TextView) findViewById(R.id.edit_dob);
        img_add_image = (CircleImageView) findViewById(R.id.img_add_image);
        alreadyUser = (TextView)findViewById(R.id.alreadyUser);
        radioGroup=(RadioGroup)findViewById(R.id.radiogrup);
        chk=(CheckBox)findViewById(R.id.terms_conditions);
        edit_country_code = (TextView) findViewById(R.id.country_code);

        edit_country_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countryPicker =
                        new CountryPickerDialog(CorporateRegisterActivity.this, new CountryPickerCallbacks() {
                            @Override
                            public void onCountrySelected(Country country, int flagResId) {
                                // TODO handle callback
                                Log.d("country", country.getDialingCode());
                                Log.d("country", new Locale(getResources().getConfiguration().locale.getLanguage(),
                                        country.getIsoCode()).getDisplayCountry());
                                edit_country_code.setText("+" + country.getDialingCode());
                                //+new Locale(getResources().getConfiguration().locale.getLanguage(),country.getIsoCode()).getDisplayCountry()
                                Common.CountryCode = country.getDialingCode();
                                edit_mobile.requestFocus();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.showSoftInput(edit_mobile, InputMethodManager.SHOW_IMPLICIT);
                                    }
                                }, 100);
                            }
                        });

                countryPicker.show();
            }
        });

        //   showDate(year, month+1, day);
        edit_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int pYear = c.get(Calendar.YEAR);
                int pMonth = c.get(Calendar.MONTH);
                int pDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CorporateRegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                edit_dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                               /* final Calendar c = Calendar.getInstance();
                                int mHour = c.get(Calendar.HOUR_OF_DAY);
                                int mMinute = c.get(Calendar.MINUTE);*/
                            }
                        }, pYear, pMonth, pDay);
                datePickerDialog.show();
            }
        });

        img_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStoragePermission();
                OpenCameraDialog = new Dialog(CorporateRegisterActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
                OpenCameraDialog.setContentView(R.layout.camera_dialog_layout);

                RelativeLayout layout_open_camera = (RelativeLayout) OpenCameraDialog.findViewById(R.id.layout_open_camera);
                layout_open_camera.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        OpenCameraDialog.cancel();

                        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                        } else {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        }
                    }
                });

                RelativeLayout layout_open_gallery = (RelativeLayout) OpenCameraDialog.findViewById(R.id.layout_open_gallery);
                layout_open_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        OpenCameraDialog.cancel();
                        Intent gi = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        gi.setType("image/*");
                        startActivityForResult(gi, RESULT_LOAD_IMAGE);
                    }
                });

                RelativeLayout layout_open_cancel = (RelativeLayout) OpenCameraDialog.findViewById(R.id.layout_open_cancel);
                layout_open_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OpenCameraDialog.cancel();
                    }
                });

                OpenCameraDialog.show();
            }
        });



        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boolean checked = ((CheckBox) v).isChecked();
                chk.setChecked(false);
                // Check which checkbox was clicked

                // Do your coding
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CorporateRegisterActivity.this);
                alertDialogBuilder.setTitle("Terms & Conditions.");
                alertDialogBuilder.setMessage(R.string.terms_conditions);
                alertDialogBuilder.setPositiveButton("Agree",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(CorporateRegisterActivity.this,"Terms & conditions applied.",Toast.LENGTH_LONG).show();
                                chk.setChecked(true);
                            }
                        });

                alertDialogBuilder.setNegativeButton("Disagree",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        chk.setChecked(false);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }

        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_name.getText().toString().isEmpty()) {
                    Common.showMkError(CorporateRegisterActivity.this, getResources().getString(R.string.please_enter_name));
                    return;
                }
                else if (edit_email.getText().toString().isEmpty()) {
                    Common.showMkError(CorporateRegisterActivity.this, getResources().getString(R.string.please_enter_email));
                    return;
                }
                else if(edit_email.getText().toString().trim().length() != 0 && !isValidEmail(edit_email.getText().toString().trim())){
                    Common.showMkError(CorporateRegisterActivity.this, getResources().getString(R.string.please_enter_valid_email));
                    return;
                }else if (edit_password.getText().toString().isEmpty()) {
                    Common.showMkError(CorporateRegisterActivity.this, getResources().getString(R.string.please_enter_password));
                    return;
                } else if (edit_cpassword.getText().toString().isEmpty()) {
                    Common.showMkError(CorporateRegisterActivity.this, getResources().getString(R.string.please_enter_confirm_password));
                    return;
                }  else if (!edit_cpassword.getText().toString().equals(edit_password.getText().toString())) {
                    Common.showMkError(CorporateRegisterActivity.this, "Confirm password is doesn't match");
                    return;
                }  else if (edit_mobile.getText().toString().isEmpty()) {
                    Common.showMkError(CorporateRegisterActivity.this, getResources().getString(R.string.please_enter_mobile));
                    return;
                } else if (edit_dob.getText().toString().isEmpty()) {
                    Common.showMkError(CorporateRegisterActivity.this, getResources().getString(R.string.please_enter_date));
                    return;
                } else if (chk.isChecked()==false) {
                    Common.showMkError(CorporateRegisterActivity.this, getResources().getString(R.string.please_check_terms));
                    return;
                }else if (bitmap == null) {
                    Common.showMkError(CorporateRegisterActivity.this, getResources().getString(R.string.please_check_image));
                    return;
                }else {
                    //signup();
                    Toast.makeText(CorporateRegisterActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            img_add_image.setImageBitmap(bitmap);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try {
                String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpeg";
                File imageFile = new File(mPath);

                FileOutputStream outputStream = new FileOutputStream(imageFile);
                int quality = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                outputStream.flush();
                outputStream.close();
                String filePath = imageFile.getPath();
                Bitmap ssbitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                img_add_image.setImageBitmap(ssbitmap);
                userImage = filePath;

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                //String path = saveImage(bitmap);
                Toast.makeText(CorporateRegisterActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                img_add_image.setImageBitmap(bitmap);
                userImage = ImageFilePath.getPath(CorporateRegisterActivity.this, data.getData());

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(CorporateRegisterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}