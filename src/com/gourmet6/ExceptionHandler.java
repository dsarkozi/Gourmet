package com.gourmet6;

import java.io.PrintWriter;
import java.io.StringWriter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Process;

public class ExceptionHandler
{
	
	public static void caughtException(Context context, Throwable exception)
	{
		StringWriter stackTrace = new StringWriter();
		exception.printStackTrace(new PrintWriter(stackTrace));
		showDialog(context, true, stackTrace.toString());
	}
	
	public static void showDialog(Context context, boolean isCaught, String stackTrace)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (isCaught) builder.setTitle(R.string.caught_exception);
		else builder.setTitle(R.string.uncaught_exception);
		builder.setMessage(stackTrace);
		builder.setPositiveButton(android.R.string.ok, new OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				kill();
			}
		});
		Dialog alert = builder.create();
		alert.show();
	}
	
	public static void kill()
	{
		/*Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		MainGourmet.this.startActivity(intent);*/
		Process.killProcess(Process.myPid());
        System.exit(10);
	}
}
