package co.webnexs.taxinetrider.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;

import co.webnexs.taxinetrider.Api;
import co.webnexs.taxinetrider.Banners;
import co.webnexs.taxinetrider.R;
import co.webnexs.taxinetrider.RetrofitClient;
import co.webnexs.taxinetrider.UserTypeDetails;
import co.webnexs.taxinetrider.UserTypes;
import co.webnexs.taxinetrider.adapter.FlipperAdapter;
import co.webnexs.taxinetrider.adapter.UserTypesAdapter;
import co.webnexs.taxinetrider.beens.Banner;
import co.webnexs.taxinetrider.beens.CabsAdapter;
import co.webnexs.taxinetrider.beens.CarType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpOptionActivity extends AppCompatActivity {

    Dialog ProgressDialog;
    RotateLoading cusRotateLoading;
    private ArrayList<UserTypeDetails> userTypeDetails;
    private UserTypesAdapter userTypesAdapter;
    RecyclerView rv_type;
    int userTypePosition;
    String UserTypeId;
    Typeface OpenSans_Regular, OpenSans_Bold, regularRoboto, Roboto_Bold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_option);

        OpenSans_Bold = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Bold_0.ttf");
        OpenSans_Regular = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular_0.ttf");
        regularRoboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        Roboto_Bold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");

        ProgressDialog = new Dialog(SignUpOptionActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        ProgressDialog.setContentView(R.layout.custom_progress_dialog);
        cusRotateLoading = (RotateLoading)ProgressDialog.findViewById(R.id.rotateloading_register);
        ProgressDialog.show();

        getUserTypes();
    }

    private void getUserTypes() {

        Api api = RetrofitClient.getRetrofitInstance().create(Api.class);
        Call<UserTypes> call = api.getUserType();
        call.enqueue(new Callback<UserTypes>() {
            @Override
            public void onResponse(Call<UserTypes> call, Response<UserTypes> response) {
                ProgressDialog.dismiss();
                if (response.isSuccessful()) {

                    userTypeDetails = response.body().getUserTypeDetails();
                    rv_type = (RecyclerView) findViewById(R.id.rv_type);
                    userTypesAdapter = new UserTypesAdapter(userTypeDetails);
                    RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SignUpOptionActivity.this, LinearLayoutManager.VERTICAL, false);
                    //  GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
                    rv_type.setLayoutManager(linearLayoutManager);
                    rv_type.setItemAnimator(new DefaultItemAnimator());
                    rv_type.setAdapter(userTypesAdapter);
                    UserTypeId = userTypeDetails.get(userTypePosition).getId();
                    rv_type.addOnItemTouchListener(new HomeActivity.RecyclerTouchListener(SignUpOptionActivity.this,
                            rv_type, new HomeActivity.ClickListener() {
                        @Override
                        public void onClick(View view, final int position) {
                            UserTypeId = userTypeDetails.get(position).getId();
                            if(UserTypeId.equalsIgnoreCase("1")){
                                startActivity(new Intent(SignUpOptionActivity.this, SignUpActivity.class));
                            }else if(UserTypeId.equalsIgnoreCase("2")){
                               // Toast.makeText(SignUpOptionActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpOptionActivity.this, CorporateRegisterActivity.class));
                            }
                        }
                        @Override
                        public void onLongClick(View view, int position) {
                        }
                    }));
                }
            }

            @Override
            public void onFailure(Call<UserTypes> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage()+"Server error, check your internet", Toast.LENGTH_LONG).show();
                ProgressDialog.dismiss();
            }
        });

    }
}