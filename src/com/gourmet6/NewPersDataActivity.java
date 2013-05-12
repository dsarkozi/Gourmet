package com.gourmet6;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewPersDataActivity extends Activity {

	private DBHandler dbHand;
	private Gourmet g;
	private Client currentCli;
	private static final String TITLE = "Edit profile";
	
	
	private String newMdpS;
	private String newMdpComfS;
	private String newNameS;
	private String newPhoneS;
	private String newMailS;
	private String actualMdpS;
	
	private EditText newMdp;
	private EditText newMdpComf;
	private EditText newName;
	private EditText newPhone;
	private EditText newMail;
	private EditText actualMdp;
	
	private TextView missingField;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_pers_data);
		setTitle(TITLE);
		dbHand = new DBHandler(this);
		g = (Gourmet)getApplication();
		currentCli = g.getClient();
		
		
		Button changeButton;
		//r�cup�ration du bouton des r�servations et commandes
		//r�cup�ration des champs d'entr�es
		newMdp  = (EditText)findViewById(R.id.etMdp);
		newMdpComf  = (EditText)findViewById(R.id.etMdpConf);
		newName  = (EditText)findViewById(R.id.etNewName);
		newPhone  = (EditText)findViewById(R.id.etNewPhone);
		newMail  = (EditText)findViewById(R.id.etNewMail);
		actualMdp  = (EditText)findViewById(R.id.etActualMdp);
		
		missingField  = (TextView)findViewById(R.id.missingField);

		changeButton = (Button)findViewById(R.id.changeButton);
        //�couteur sur le clic du bouton
		changeButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		//remplir les champs strings private par ceux entr�s par l'utilisateur
        		newMdpS= newMdp.getText().toString();
        		newMdpComfS= newMdpComf.getText().toString();
        		newNameS= newName.getText().toString();
        		newPhoneS= newPhone.getText().toString();
        		newMailS= newMail.getText().toString();
        		actualMdpS= actualMdp.getText().toString();
        		
        		//effacer les champs dans lesquels il a mis les nouveau identifiants
        		newMdp.setText("");
        		newMdpComf.setText("");
        		newName.setText("");
        		newPhone.setText("");
        		newMail.setText("");
        		actualMdp.setText("");        		
        		//v�rifier que le mot de passe actuel est mis et bon
        		if(actualMdpS.equals("")){
        			missingField.setText("Actual password required.");
        			return;
        		}
        		if(!dbHand.checkPassword(currentCli.getEmail(), actualMdpS)){
        			missingField.setText("Acual password is wrong.");
        			return;
        		}
        		//voir les champs remplis par l'utilisateur(changements a faire)+effectuer ces changements
        		String un="(none)", deux="", trois="", quatre="";
        		if(!newMdpS.equals("")){
        			if(newMdpComfS.equals(""))
        			{
        				missingField.setText("You must confirm the new password !");
        				return;
        			}
        			if(!newMdpComfS.equals(newMdpS))
        			{
        				missingField.setText("The new password and its confirmation aren't identical !");
        				return;
        			}
        			dbHand.changePassword(currentCli.getEmail(), newMdpS);
        			un ="Your password has been changed. \n";
        		}
        		if(!newNameS.equals("")){
        			dbHand.changeName(currentCli.getEmail(), newNameS);
        			g.getClient().setName(newNameS);
        			deux ="Your name has been changed. \n";
        		}
        		if(!newPhoneS.equals("")){
        			dbHand.changeTel(currentCli.getEmail(), newPhoneS);
        			g.getClient().setPhone(newPhoneS);
        			trois ="Your phone number has been changed. \n";
        		}
        		if(!newMailS.equals("")){
        			dbHand.changeMail(currentCli.getEmail(), newMailS);
        			g.getClient().setEmail(newMailS);
        			quatre ="Your email has been changed.";
        		}
        		missingField.setText("The following changes have been carried out:\n"+un+deux+trois+quatre);
        	}
        });
	}
}
