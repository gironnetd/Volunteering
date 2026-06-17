package com.sc.fr.taoisme.layers.service.mails;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class SendMailTask extends AsyncTask<Void, Void, Boolean> {

	private Callbacks mListener;

	private ProgressDialog statusDialog;
	private List<String> mailTo;
	private String emailSubject;
	private String emailBody;

	public SendMailTask(List<String> mailTo, String emailSubject, String emailBody, Callbacks mListener) {
		this.mailTo = mailTo;
		this.emailSubject = emailSubject;
		this.emailBody = emailBody;
		this.mListener = mListener;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			//Log.i("SendMailTask", "About to instantiate GMail...");
			//publishProgress("Processing input....");
			GMail androidEmail = new GMail( mailTo, emailSubject,
					emailBody);
			//publishProgress("Preparing mail message....");
			androidEmail.createEmailMessage();
			//publishProgress("Sending email....");
			androidEmail.sendEmail();
			//publishProgress("Email Sent.");
			//Log.i("SendMailTask", "Mail Sent.");
		} catch (Exception e) {
			//publishProgress(e.getMessage());
			//Log.e("SendMailTask", e.getMessage(), e);
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean aBoolean) {
		super.onPostExecute(aBoolean);
		if(mListener != null){
			mListener.isEmailSended(aBoolean);
		}
	}

	public SendMailTask(Activity activity) {

  }

	public interface Callbacks {
		void isEmailSended(boolean result);
	}

	protected void onPreExecute() {
//		statusDialog = new ProgressDialog(sendMailActivity);
//		statusDialog.setMessage("Getting ready...");
//		statusDialog.setIndeterminate(false);
//		statusDialog.setCancelable(false);
//		statusDialog.show();
	}




}
