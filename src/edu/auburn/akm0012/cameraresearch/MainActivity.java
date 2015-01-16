
package edu.auburn.akm0012.cameraresearch;

import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.FrameLayout;

public class MainActivity extends ActionBarActivity {
	
	public static final String logcat_lifecycle = "logcat_lifecycle";
	
	public Camera mRearCamera, mFrontCamera;
	public Rear_Camera_Preview rear_preview;
	public Front_Camera_Preview front_preview;
	Context this_context;
	
	Thread camera_open_thread = new Thread(new Runnable( ) {
		
		public void run() {
			
//			safe_camera_open(0);
			mRearCamera = getCameraInstance(0);
			
			rear_preview = new Rear_Camera_Preview(this_context, mRearCamera);
	        FrameLayout backPreview = (FrameLayout) findViewById(R.id.rear_camera_preview);
	        backPreview.addView(rear_preview);
		}
	});
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(MainActivity.logcat_lifecycle, "MainActivity.onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.i("debug", "Number of cameras: " + Camera.getNumberOfCameras());
		
//		this_context = this;
		
//		camera_open_thread.start();
		
        mFrontCamera = getCameraInstance(1); 
        front_preview = new Front_Camera_Preview(this, mFrontCamera);
        FrameLayout frontPreview = (FrameLayout) findViewById(R.id.front_camera_preview);
        frontPreview.addView(front_preview);
		
		mRearCamera = getCameraInstance(0);
		// Create back camera Preview view and set it as the content of our activity.
		rear_preview = new Rear_Camera_Preview(this, mRearCamera);
        FrameLayout backPreview = (FrameLayout) findViewById(R.id.rear_camera_preview);
        backPreview.addView(rear_preview);
	}	
	
	public static Camera getCameraInstance(int cameraId){
	    Camera c = null;
	    try {
	        c = Camera.open(cameraId); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    	Log.e("Error","Camera " + cameraId + " not available! " + e.toString() );
	    }
	    return c; // returns null if camera is unavailable
	}
	
//	private void release_camera_and_preview() {
//		Log.i(MainActivity.logcat_lifecycle, "MainActivity.release_camera_and_preview()");
//		rear_preview.setCamera(null);
//		if (mRearCamera != null) {
//			mRearCamera.release();
//			mRearCamera = null;
//		}
//	}
	
	
	
	
}


//private boolean safe_camera_open(int id) {
//	Log.i(MainActivity.logcat_lifecycle, "MainActivity.safeCameraOpen(" + id + ")");
//	boolean opened = false;
//	
//	try {
//		release_camera_and_preview();
//		mRearCamera = Camera.open(id);
//		opened = (mRearCamera != null);
//	} catch (Exception e) {
//		Log.e(getString(R.string.app_name), "failed to open Camera");
//		e.printStackTrace();
//	}
//	
//	return opened;
//}


//class Preview extends ViewGroup implements SurfaceHolder.Callback {
//
//SurfaceView mSurfaceView;
//SurfaceHolder mHolder;
//List<Size> mSupportedPreviewSizes;
//
//Preview(Context context) {
//	super(context);
//	Log.i(MainActivity.logcat_lifecycle, "MainActivity.Preview()");
//	
//	mSurfaceView = new SurfaceView(context);
//	addView(mSurfaceView);
//	
//	// Install a SurfaceHolder.Callback so we get notified when the 
//	// underlying surface is created and destroyed.
//	mHolder = mSurfaceView.getHolder();
//	mHolder.addCallback(this);
//	mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//}
//
//public void setCamera(Camera camera) {
//	Log.i(MainActivity.logcat_lifecycle, "MainActivity.setCamera(" + camera.toString() + ")");
//	
//	if (mCamera == camera) {
//		return; 
//	}
//	
//	stop_preview_and_free_camera();
//	
//	mCamera = camera;
//	
//	if (mCamera != null) {
//		List<Size> localSizes = mCamera.getParameters().getSupportedPreviewSizes();
//		mSupportedPreviewSizes = localSizes;
//		requestLayout();
//		
//		try {
//			mCamera.setPreviewDisplay(mHolder);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		// Important: Call startPreview() to start updating the preview
//        // surface. Preview must be started before you can take a picture.
//        mCamera.startPreview();	
//	}
//}
//
//@Override
//public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//	// TODO Auto-generated method stub
//	
//}
//
//@Override
//public void surfaceCreated(SurfaceHolder holder) {
//	// TODO Auto-generated method stub
//	
//}
//
//@Override
//public void surfaceDestroyed(SurfaceHolder holder) {
//	// TODO Auto-generated method stub
//	
//}
//
//@Override
//protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
//	// TODO Auto-generated method stub
//	
//}
//
//}














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
