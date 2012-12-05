package com.example.embedeed_project;

import android.app.Activity;

public class Const extends Activity{
	public static final String DATABASE_NAME;
	public static final String DATABASE_PATH;
	static {
		DATABASE_NAME = "posDB.db";
		DATABASE_PATH = "/data/data/com.example.embedeed_project/databases/posDB.db";
	}
}
