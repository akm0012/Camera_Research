/**
 * 
 */
package edu.auburn.akm0012.cameraresearch;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * A basic camera preview class
 * 
 * @author Andrew K. Marshall
 */
public class Front_Camera_Preview extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
	private Camera mCamera;
	
	/**
	 * @param context
	 */
	public Front_Camera_Preview(Context context, Camera camera) {
		super(context);
		Log.i(MainActivity.logcat_lifecycle, "Front_Camera_Preview.Rear_Camera_Preview()");
		
		mCamera = camera;
		
		// Install a SurfaceHolder.Callback so we get notified when the 
		// underlying surface is created and destroyed
		mHolder = getHolder();
		mHolder.addCallback(this);
		// Deprecated setting, but required on Android Versions prior to 3.0
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// The surface has been created, now tell the camera where to draw the preview.
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
			Log.e("Error", "Error setting up the Camera preview: " + e.getMessage());
		}
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// Blank, do this in the MainActivity 
		
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
          // preview surface does not exist
          return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
          // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.e("Error", "Error starting camera preview: " + e.getMessage());
        }
		
	}
}
