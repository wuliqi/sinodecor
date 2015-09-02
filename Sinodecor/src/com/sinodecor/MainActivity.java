package com.sinodecor;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		HttpUtils http = new HttpUtils();
		String url="http://app.airbest.cn/airbest/weatherAction/getWeatherInfo?cid=39.93:116.40&userId=219";
		http.send(HttpRequest.HttpMethod.GET, url,  new RequestCallBack<String>(){

			@Override
			public void onFailure(HttpException error, String msg) {
				System.out.println("*** error :"+msg);
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> msg) {
				System.out.println("*** success :"+msg.result);
				
			}
			
		});
		

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
