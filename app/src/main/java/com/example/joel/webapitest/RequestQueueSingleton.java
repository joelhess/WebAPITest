package com.example.joel.webapitest;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Joel on 10/14/2014.
 */
public class RequestQueueSingleton {
    private static RequestQueueSingleton mInstance;

    public static RequestQueueSingleton getInstance(Context context) {

        if (mInstance == null)
            mInstance = new RequestQueueSingleton(context);

        return mInstance;
    }

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    static Context mCtx;

    private RequestQueueSingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public RequestQueue getRequestQueue()
    {
        if (mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }

        return mRequestQueue;
    }
    public <T> void AddToRequestQueue(Request<T> request)
    {
        mRequestQueue.add(request);
    }

    public ImageLoader getImageLoader()
    {
        return mImageLoader;
    }
}
