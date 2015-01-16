
package edu.auburn.akm0012.cameraresearch;

import java.io.IOException;

import org.apache.http.RequestLine;

import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

public class MainActivity extends ActionBarActivity {
	
	public static final String logcat_lifecycle = "logcat_lifecycle";
	
	public Camera mCamera;
	
	Thread camera_open_thread = new Thread(new Runnable( ) {
		
		public void run( ) {
			
			safe_camera_open(0);
			
		}
	});
	
	class Preview extends ViewGroup implements SurfaceHolder.Callback {
		
		SurfaceView mSurfaceView;
		SurfaceHolder mHolder;
		
		Preview(Context context) {
			super(context);
			Log.i(MainActivity.logcat_lifecycle, "MainActivity.Preview()");
			
			mSurfaceView = new SurfaceView(context);
			addView(mSurfaceView);
			
			// Install a SurfaceHolder.Callback so we get notified when the 
			// underlying surface is created and destroyed.
			mHolder = mSurfaceView.getHolder();
			mHolder.addCallback(this);
			mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(MainActivity.logcat_lifecycle, "MainActivity.onCreate()");
		super.onCreate(savedInstanceState);
		
		camera_open_thread.start();
		
		setContentView(R.layout.activity_main);
		
	}	
	
	private boolean safe_camera_open(int id) {
		Log.i(MainActivity.logcat_lifecycle, "MainActivity.safeCameraOpen(" + id + ")");
		boolean opened = false;
		
		try {
			release_camera_and_preview();
			mCamera = Camera.open(id);
			opened = (mCamera != null);
		} catch (Exception e) {
			Log.e(getString(R.string.app_name), "failed to open Camera");
			e.printStackTrace();
		}
		
		return opened;
	}
	
	private void release_camera_and_preview() {
		Log.i(MainActivity.logcat_lifecycle, "MainActivity.release_camera_and_preview()");
//		mPreview.setCamera(null);
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}
	
	public void setCamera(Camera camera) {
		Log.i(MainActivity.logcat_lifecycle, "MainActivity.setCamera(" + camera.toString() + ")");
		
		if (mCamera == camera) {
			return; 
		}
		
		stop_preview_and_free_camera();
		
		mCamera = camera;
		
		if (mCamera != null) {
			List<Size> localSizes = mCamera.getParameters().getSupportedPreviewSizes();
			mSupportedPreviewSizes = localSizes;
			requestLayout();
			
			try {
				mCamera.setPreviewDisplay(mHolder);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Important: Call startPreview() to start updating the preview
	        // surface. Preview must be started before you can take a picture.
	        mCamera.startPreview();	
		}
	}
	
	
}




















// @Override
// public boolean onCreateOptionsMenu(Menu menu) {
// // Inflate the menu; this adds items to the action bar if it is present.
// getMenuInflater().inflate(R.menu.main, menu);
// return true;
// }

// @Override
// public boolean onOptionsItemSelected(MenuItem item) {
// // Handle action bar item clicks here. The action bar will
// // automatically handle clicks on the Home/Up button, so long
// // as you specify a parent activity in AndroidManifest.xml.
// int id = item.getItemId();
// if (id == R.id.action_settings) {
// return true;
// }
// return super.onOptionsItemSelected(item);
// }
