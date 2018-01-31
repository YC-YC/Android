package com.example.testpalette;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.zhonghong.glaphics.palette.Palette;
import com.zhonghong.glaphics.palette.Palette.Builder;
import com.zhonghong.glaphics.palette.Palette.PaletteAsyncListener;
import com.zhonghong.glaphics.palette.Palette.Swatch;

public class MainActivity extends Activity {
	
	private static final String TAG = "Test";
	private View mBg;
	private View view1,view2,view3,view4,view5,view6,view7;
	private ImageView imgSource;
	private int index = 0;
	private List<Integer> pics = new ArrayList<Integer>(){
		{
			add(R.drawable.pic1);
			add(R.drawable.pic2);
			add(R.drawable.pic3);
			add(R.drawable.pic4);
			add(R.drawable.pic5);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBg = findViewById(R.id.bg);
		imgSource = (ImageView) findViewById(R.id.img_source);
		view1 = findViewById(R.id.view1);
		view2 = findViewById(R.id.view2);
		view3 = findViewById(R.id.view3);
		view4 = findViewById(R.id.view4);
		view5 = findViewById(R.id.view5);
		view6 = findViewById(R.id.view6);
		view7 = findViewById(R.id.view7);
	}
	
	public void doClick(View view){
		if (index <0 || index >= pics.size()){
			index = 0;
		}
		Log.i(TAG, "start ----");
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), pics.get(index));
//		imgSource.setBackgroundResource(pics.get(index));
		Log.i(TAG, "start 222222");
		Builder builder = Palette.from(bitmap);
		builder.generate(new PaletteAsyncListener() {
			
			@Override
			public void onGenerated(Palette palette) {
				Swatch vibrantSwatch = palette.getVibrantSwatch();
//				Swatch s = palette.getVibrantSwatch();       //获取到充满活力的这种色调
//				Swatch s = palette.getDarkVibrantSwatch();    //获取充满活力的黑
//				Swatch s = palette.getLightVibrantSwatch();   //获取充满活力的亮
//				Swatch s = palette.getMutedSwatch();           //获取柔和的色调
//				Swatch s = palette.getDarkMutedSwatch();      //获取柔和的黑
//				Swatch s = palette.getLightMutedSwatch();    //获取柔和的亮

				Log.i(TAG, "onGenerated ----vibrantSwatch Population = " + vibrantSwatch.getPopulation());
				view1.setBackgroundColor(palette.getVibrantColor(Color.WHITE));
				view2.setBackgroundColor(palette.getDarkVibrantColor(Color.WHITE));
				view3.setBackgroundColor(palette.getLightVibrantColor(Color.WHITE));
				view4.setBackgroundColor(palette.getMutedColor(Color.WHITE));
				view5.setBackgroundColor(palette.getDarkMutedColor(Color.WHITE));
				view6.setBackgroundColor(palette.getLightMutedColor(Color.WHITE));
				
				List<Swatch> swatches = palette.getSwatches();
				int max = 0;
				Swatch largeSwatch = null;
				if (swatches != null){
					for (int i = 0; i < swatches.size(); i++) {
						Swatch swatch = swatches.get(i);
						if (swatch.getPopulation() > max){
							max = swatch.getPopulation();
							largeSwatch = swatch;
						}
						Log.i(TAG, "swatch[" + i + "] Population" + swatch.getPopulation());
					}
				}
				if (largeSwatch != null){
					Log.i(TAG, "large ----Swatch Population = " + largeSwatch.getPopulation());
					view7.setBackgroundColor(largeSwatch.getRgb());
				}
			}
		});
		index++;
	}

}
