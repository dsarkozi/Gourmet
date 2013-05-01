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

	public ExceptionFragment()
	{
		// Required empty public constructor
	}
	
	public static ExceptionFragment getInstance(boolean isCaught)
	{
		ExceptionFragment fragment = new ExceptionFragment();
		Bundle args = new Bundle();
		args.putBoolean("isCaught", isCaught);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		super.onCreateDialog(savedInstanceState);
		boolean isCaught = this.getArguments().getBoolean("isCaught", false);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		if (isCaught) builder.setTitle(R.string.caught_exception);
		else builder.setTitle(R.string.uncaught_exception);
		builder.setPositiveButton(android.R.string.ok, null);
		return builder.create();
	}

}
