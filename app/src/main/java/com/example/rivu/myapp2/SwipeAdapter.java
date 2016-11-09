package com.example.rivu.myapp2;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SwipeAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;


    public SwipeAdapter(Context context) {
        mContext = context;

    }

    @Override
    public int getCount() {
        return  MainActivity.getSize();
    }

    // Returns true if a particular object (page) is from a particular page
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(RelativeLayout)object);
    }

    // This method should create the page for the given position passed to it as an argument.
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // Inflate the layout for the page
        mLayoutInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.activity_details, container, false);
        TextView tv = (TextView) itemView.findViewById(R.id.textView4);
        TextView t2 = (TextView)itemView.findViewById(R.id.textView2);
        tv.setText("DETAILS ARE \n"+MainActivity.listTitles.get(position).description);
        t2.setText("TITLE IS \n "+MainActivity.listTitles.get(position).title);
        // ...
        // Add the page to the container
        container.addView(itemView);
        return itemView;
    }

    // Removes the page from the container for the given position.
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);

    }
}
