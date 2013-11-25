package com.example.brickbreaker;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class BrickSurfaceView extends GLSurfaceView {
	
	public BrickSurfaceView(Context context){
        super(context);
        setEGLContextClientVersion(2);
        
        // Set the Renderer and config for drawing on the GLSurfaceView
        setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
        setRenderer(new BrickRenderer());
    }
}
