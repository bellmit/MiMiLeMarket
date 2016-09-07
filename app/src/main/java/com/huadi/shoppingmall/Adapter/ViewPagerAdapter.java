package com.huadi.shoppingmall.Adapter;

import java.util.List;

import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.activity.Splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Parcelable;  
import android.support.v4.view.PagerAdapter;  
import android.support.v4.view.ViewPager;  
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;  
  
public class ViewPagerAdapter extends PagerAdapter {  
      

    private List<View> views;
    private Activity activity;
      
    public ViewPagerAdapter (List<View> views,Activity activity){  
        this.views = views; 
        this.activity=activity;
    }  
  

    @Override  
    public void destroyItem(View arg0, int arg1, Object arg2) {  
        ((ViewPager) arg0).removeView(views.get(arg1));       
    }  
  
    @Override  
    public void finishUpdate(View arg0) {  
        // TODO Auto-generated method stub  
          
    }  
  

    @Override  
    public int getCount() {  
        if (views != null)  
        {  
            return views.size();  
        }  
          
        return 0;  
    }  
      
  

    @Override  
    public Object instantiateItem(View arg0, int arg1) {  
          
        ((ViewPager) arg0).addView(views.get(arg1), 0);


        return views.get(arg1);  
    }  
  

    @Override  
    public boolean isViewFromObject(View arg0, Object arg1) {  
        return (arg0 == arg1);  
    }  
  
    @Override  
    public void restoreState(Parcelable arg0, ClassLoader arg1) {  
        // TODO Auto-generated method stub  
          
    }  
  
    @Override  
    public Parcelable saveState() {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
    @Override  
    public void startUpdate(View arg0) {  
        // TODO Auto-generated method stub  
          
    } 
	private void goHome() {

		Intent intent = new Intent(activity, MainActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}  
	private void setGuided() {
		SharedPreferences preferences = activity.getSharedPreferences(
				Splash.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();

		editor.putBoolean("isFirstIn", false);

		editor.commit();
	}	
  
}