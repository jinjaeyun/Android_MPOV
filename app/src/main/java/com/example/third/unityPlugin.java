package com.example.third;

import android.content.Intent;
import android.os.Bundle;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

public class unityPlugin extends UnityPlayerActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        UnityPlayer.UnitySendMessage("MainCamera", intent.getStringExtra("theater"), intent.getStringExtra("seat"));
    }

}
