package com.project.between.ViewPagerAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.between.R;
import com.project.between.signInAndUp.SignInActivity;

import java.util.List;


/**
 * Created by JisangYou on 2017-11-09.
 */

public class viewpagerAdapter extends PagerAdapter {
    Context context;
    int[] resource = new int [] {R.drawable.horses, R.drawable.tree};

    public viewpagerAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return resource.length;
    }

    // getView와 같은.....
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 1. 여기서 레이아웃파일을 inflate해서 view로 만든다.

        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(resource[position]);
        ((ViewPager) container).addView(imageView, 0);



        container.addView(imageView);

        return imageView;
    }

    // instantiateItem 에서 리턴된 object가 View가 맞는지 확인
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // 현재 사용하지 않는 View는 제거, 없어도 자동으로 제거해주나, 뷰 안에 있는 내용물들 예를 들어 비트맵 같은 것은 제거가 불가능
    // container : 뷰페이저
    // object : 뷰 아이템 (뷰페이저 안에 있는)

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
//        super.destroyItem(container, position, object);
    }
}


