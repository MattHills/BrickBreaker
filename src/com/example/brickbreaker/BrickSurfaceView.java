package com.example.brickbreaker;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class BrickSurfaceView extends GLSurfaceView {
	
	public BrickSurfaceView(Context context){
        super(context);
        setEGLContextClientVersion(2);
        
        
        
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(new BrickRenderer());
    }
}
