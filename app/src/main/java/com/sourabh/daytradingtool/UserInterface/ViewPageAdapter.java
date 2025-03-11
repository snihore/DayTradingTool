package com.sourabh.daytradingtool.UserInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.sourabh.daytradingtool.R;

public class ViewPageAdapter extends PagerAdapter {

    private Context context;

    int images[] = {
            R.drawable.illu_risk_manage,
            R.drawable.illu_buy_sell,
            R.drawable.illu_save,
            R.drawable.illu_select_stocks,
            R.drawable.illu_tax
    };

    String tagLines[] = {
      "Manage your Intraday or Investment risk",
      "How much shares you should BUY/SELL",
      "Select stock while save your trade",
      "Plan your trades and execute them in live market",
      "Get Intraday or Investment taxes and charges"
    };

    public ViewPageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return tagLines.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout, container, false);

        ImageView sliderImage = (ImageView)view.findViewById(R.id.slider_image);
        TextView tagLine = (TextView) view.findViewById(R.id.slider_tag_line);


        sliderImage.setImageResource(images[position]);
        tagLine.setText(tagLines[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);
    }
}
