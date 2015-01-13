package com.wheatrenterprises.eric.grubber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Eric on 1/12/2015.
 */
public class ResultsAdapter extends BaseAdapter {

    private Activity context;
    private List<QueryResult> results;
    private TextView textViewPhone;
    private TextView textViewAddress;
    private TextView textViewRatingCount;
    private TextView textViewId;

    public ResultsAdapter(Activity context, List<QueryResult> results){

        this.results = results;
        this.context = context;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_results, null);
        }


        textViewId = (TextView) convertView.findViewById(R.id.textview_result_id);
        textViewAddress = (TextView) convertView.findViewById(R.id.textview_result_address);
        textViewRatingCount = (TextView) convertView.findViewById(R.id.textview_reviews_count);
        textViewPhone = (TextView) convertView.findViewById(R.id.textview_result_phone);
        TextView textViewHood = (TextView) convertView.findViewById(R.id.textview_result_neighbourhoods);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view_result);
        ImageView ratingView = (ImageView) convertView.findViewById(R.id.image_view_ratings);

        Picasso.with(context).load(results.get(position).getImageUrl()).resize(convertDiptoPix(140), convertDiptoPix(140)).into(imageView);
        Picasso.with(context).load(results.get(position).getLargeRatingImgUrl()).into(ratingView);

        textViewId.setText(formatId(position));
        if(results.get(position).getNeighbourhoods().size() > 0)
            textViewHood.setText(results.get(position).getNeighbourhoods().get(0));
        textViewAddress.setText(formatAddress(position));
        textViewRatingCount.setText(results.get(position).getReviewCount() + " reviews");
        textViewPhone.setText(results.get(position).getPhoneNumber());

        final String number = "tel:" + results.get(position).getPhoneNumber();
        final Context cont = context;
        final String address = results.get(position).getAddress();

        textViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(number));
                cont.startActivity(intent);
            }
        });

        textViewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mapUri = "http://maps.google.co.in/maps?q=" + address;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUri));
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    private String formatAddress(int position){

        String source = results.get(position).getAddress();

        return source.substring(2, source.length() - 2).replace("\"", "");
    }

    private String formatId(int position) {
        //format id string
        String formattedId = results.get(position).getId();
        formattedId = formattedId.replace('-', ' ');
        String[] arr = formattedId.split(" ");
        String reformatted = "";
        for(int i = 0; i< arr.length - 1; i++){

            reformatted = reformatted + String.valueOf(arr[i].charAt(0)).toUpperCase() + arr[i].substring(1) + " ";
        }
        return reformatted;
    }

    public int convertDiptoPix(float dips){
        return (int) (dips * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
