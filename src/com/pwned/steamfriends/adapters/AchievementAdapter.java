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

package com.pwned.steamfriends.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pwned.steamfriends.R;
import com.pwned.steamfriends.item.Achievement;
import com.pwned.utils.AsyncImageLoader;
import com.pwned.utils.Logger;
import com.pwned.utils.AsyncImageLoader.ImageCallback;

public class AchievementAdapter extends ArrayAdapter<Achievement> {

    private ArrayList<Achievement> items;
    private AsyncImageLoader asyncImageLoader;
    private Context ctx;
    
    public AchievementAdapter(Context context, int textViewResourceId, ArrayList<Achievement> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            asyncImageLoader = new AsyncImageLoader();
            ctx = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            Achievement o = items.get(position);
            if (o != null) {
            	if (v == null) {
            		Logger.log("game text",o.getGame());
            		LayoutInflater vi = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            		v = vi.inflate(R.layout.achievement, null);
                }
                    TextView tt = (TextView) v.findViewById(R.id.toptext);
                    TextView bt = (TextView) v.findViewById(R.id.bottomtext);
                    TextView gt = (TextView) v.findViewById(R.id.gametext);
                    //ImageView iv = (ImageView) v.findViewById(R.id.icon);
                    //downloadFile(o.getImage(),iv);
                    
                    final ImageView imageView = (ImageView) v.findViewById(R.id.icon);
                    BitmapDrawable cachedImage = asyncImageLoader.loadDrawable(o.getImage(), new ImageCallback() {
                        public void imageLoaded(BitmapDrawable imageDrawable, String imageUrl) {
                        	try {
								imageView.setImageDrawable(imageDrawable);
							} catch (Exception e) {
								imageView.setImageResource(R.drawable.uiholder);
							}
                        }
                    });
                    try {
						imageView.setImageDrawable(cachedImage);
					} catch (Exception e) {
						imageView.setImageResource(R.drawable.uiholder);
					}
                    
                    if (tt != null) {
                          tt.setText(o.getTitle());
                    }
                    if(bt != null){
                          bt.setText(o.getDescription());
                    }
                    if(gt != null){
                    	
                        gt.setText(o.getGame());
                        if(o.getGame().equalsIgnoreCase("UNLOCKED")){
                        	gt.setTextColor(ctx.getResources().getColor(R.color.green_achievement));
                        } else {
                        	gt.setTextColor(ctx.getResources().getColor(R.color.red_achievement));
                        }
                  }
                    
                    
            }
            return v;
    }      

}
