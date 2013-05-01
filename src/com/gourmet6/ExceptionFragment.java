package com.gourmet6;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class ExceptionFragment extends DialogFragment
{

	private static final String IS_CAUGHT = "isCaught";
	private static final String STACK_TRACE = "stackTrace";
	
	public ExceptionFragment()
	{
		// Required empty public constructor
	}
	
	public static ExceptionFragment getInstance(boolean isCaught, String stackTrace)
	{
		ExceptionFragment fragment = new ExceptionFragment();
		Bundle args = new Bundle();
		args.putBoolean(IS_CAUGHT, isCaught);
		args.putString(STACK_TRACE, stackTrace);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		super.onCreateDialog(savedInstanceState);
		boolean isCaught = this.getArguments().getBoolean(IS_CAUGHT, false);
		String stackTrace = this.getArguments().getString(STACK_TRACE);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		if (isCaught) builder.setTitle(R.string.caught_exception);
		else builder.setTitle(R.string.uncaught_exception);
		builder.setMessage(stackTrace);
		builder.setPositiveButton(android.R.string.ok, null);
		return builder.create();
	}

}
