package co.webnexs.taxinetrider.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import co.webnexs.taxinetrider.BaseResponse;
import co.webnexs.taxinetrider.R;
import co.webnexs.taxinetrider.UserTypeDetails;
import co.webnexs.taxinetrider.beens.CabsAdapter;
import co.webnexs.taxinetrider.beens.CarType;

public class UserTypesAdapter extends RecyclerView.Adapter<UserTypesAdapter.CustomViewHolder> {
    private List<UserTypeDetails> userTypeDetails;
    private AdapterView.OnItemClickListener listener;
    public Context context;
    public ArrayList<CabsAdapter> dataItems;
    int row_index;


    public UserTypesAdapter(List<UserTypeDetails> carTypes) {
        this.userTypeDetails = carTypes;
    }

    @Override
    public UserTypesAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_type_layout, parent, false);

        return new UserTypesAdapter.CustomViewHolder(itView);
    }

    @Override
    public void onBindViewHolder(UserTypesAdapter.CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        UserTypeDetails gobase = userTypeDetails.get(position);
        holder.txt_type.setText(gobase.getType());
    }

    @Override
    public int getItemCount() {
        return userTypeDetails.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_type;


        public CustomViewHolder(View itView) {
            super(itView);
            txt_type = (TextView) itView.findViewById(R.id.txt_type);

        }
    }

}
