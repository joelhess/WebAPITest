package com.example.joel.webapitest;

import android.content.Context;
import android.media.Image;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

/**
 * Created by Joel on 10/14/2014.
 */
public class MediaObjectArrayAdapter extends ArrayAdapter<MediaObject> {
    public MediaObjectArrayAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MediaObject mediaObject = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mediaobject_listview_item, parent, false);
        }

        TextView tvTitle =  (TextView)convertView.findViewById(R.id.listviewitem_Title);
        TextView tvAuthor=  (TextView)convertView.findViewById(R.id.listviewitem_Author);
        ImageView ivThumbNail = (ImageView)convertView.findViewById(R.id.imageviewThumbnail);

        tvTitle.setText(mediaObject.Title);
        tvAuthor.setText(mediaObject.Author);

        if (!mediaObject.ThumbnailURL.isEmpty()) {
            ImageLoader loader = RequestQueueSingleton.getInstance(getContext()).getImageLoader();
            loader.get(mediaObject.ThumbnailURL, ImageLoader.getImageListener(ivThumbNail, android.R.drawable.picture_frame, android.R.drawable.picture_frame));
        }
        return convertView;
    }
}
