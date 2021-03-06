/*	SteamFriends
*	Copyright (C) 2008-2013  Pwned, LLC
*
*	This program is free software: you can redistribute it and/or modify
*	it under the terms of the GNU General Public License as published by
*	the Free Software Foundation, either version 3 of the License, or
*	(at your option) any later version.
*
*	This program is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*	GNU General Public License for more details.
*
*	You should have received a copy of the GNU General Public License
*	along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/


package com.pwned.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import com.pwned.steamfriends.Constants;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

public class VersionCheck {
	private static Integer versionName;
	private static Integer currentVersionName;
	private static AlertDialog a;
	private static Context context = null;
	private static Thread returnRes = null;
	
	public static void getVersion(Context ctx) throws NameNotFoundException{
		versionName = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0 ).versionCode;	
		context = ctx;
		validateVersion();
	}
	
	private static void validateVersion() {
		a = new AlertDialog.Builder(context).create();
		Thread runOnce = new Thread(){
        	@Override
        	public void run() {
        		Logger.log("start version check", "start check");
        		try {
	        		_versionCheck();
	        	} catch(IOException e){
	        		
	        	}
        	}
    	};
    	runOnce.start();
    	
    	returnRes = new Thread() {

	        @Override
	        public void run() {
	            if(versionName < currentVersionName)
	            	showUpdate();
	        }
	    }; 
    	
	}	
	
	private static void showUpdate(){
    	a.setTitle("A new version is available!");
    	a.setMessage("Click download to get the new version, click Get Later to download it later.");
    	a.setButton("Download", new DialogInterface.OnClickListener() {

    	      public void onClick(DialogInterface dialog, int which) {
    	    	  Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MARKET_URL)); 
    	    	  context.startActivity(myIntent);
    	    	  dialog.dismiss();
    	        return;

    	    } });
    	a.setButton2("Get Later", new DialogInterface.OnClickListener() {

    	      public void onClick(DialogInterface dialog, int which) {
    	    	  dialog.dismiss();
    	        return;

    	    } });
    	a.show();
	}
	
	private static void _versionCheck() throws IOException {
		URL myURL = new URL(Constants.VERSIONCHECK_URL);
        URLConnection ucon = myURL.openConnection();
        InputStream is = ucon.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayBuffer baf = new ByteArrayBuffer(50);
        int current = 0;
        while((current = bis.read()) != -1){
             baf.append((byte)current);
        }
        Logger.log("start version check", new String(baf.toByteArray()));
        try {
        	currentVersionName = Integer.parseInt(new String(baf.toByteArray()));
        } catch (Exception e){
        	currentVersionName = 1; 
        }
		((Activity) context).runOnUiThread(returnRes);
	}
	
}
