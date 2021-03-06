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

package com.pwned.steamfriends.item;

public class GroupDetail {
	private String title;
	private String description;
	private String date;
	
	public void setDate(String d){
		this.date = d;
	}
	public void setDescription(String d){
		this.description = d;
	}
	public void setTitle(String t){
		this.title = t;
	}
	
	public String getDate() {return date;}
	public String getDescription() {return description;}
	public String getTitle() {return title;}
}
