package com.gourmet6;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class ExceptionHandler extends FragmentActivity implements UncaughtExceptionHandler
{
	public ExceptionHandler()
	{
		
	}
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(android.R.color.background_dark);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable exception)
	{
		StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        this.showDialog(false, stackTrace.toString());
	}
	
	public void caughtException(Throwable exception)
	{
		StringWriter stackTrace = new StringWriter();
		exception.printStackTrace(new PrintWriter(stackTrace));
		this.showDialog(true, stackTrace.toString());
	}
	
	public void showDialog(boolean isCaught, String stackTrace)
	{
		ExceptionFragment fragment = ExceptionFragment.getInstance(isCaught, stackTrace);
		fragment.show(getSupportFragmentManager(), "exception_dialog");
	}
	
	public void kill()
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		/*Process.killProcess(Process.myPid());
        System.exit(10);*/
	}
}
