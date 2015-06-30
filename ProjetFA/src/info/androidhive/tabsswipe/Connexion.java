package info.androidhive.tabsswipe;

import iga.database.externe.CustomProgressDialog;
import iga.database.externe.ServiceHandler;
import info.androidhive.slidingmenu.MainActivity;
import info.androidhive.slidingmenu.R;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Connexion extends Fragment {
	
	
	//variable  base de donnée
		CustomProgressDialog  progressDialog;
	    //String urlGet="http://projetuteure.ga/rar/affichage_bd.php";
	    String urlGet="http://android.iganews.ga/php/affichage.php";

	    GetDataAsyncTask getData;
	    String message;
	    public static String idEtudiant;
	    public static String UtilisateurConnecte="Utilisateur";
	    public static int codefiliere; //0==management,1=ingénierie
	    int success;
	    ListView lv;
	    public List<String> myListofData ;
	    
	    
	    EditText editNom = null;
	    EditText editpPassword = null;
	    //public TextView nomUser = null;
	    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.connexion, container, false);
		//View rootViewSecondActivity = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);		
		Button buttonLogin = (Button) rootView.findViewById(R.id.btnLogin);
        editNom = (EditText)(rootView.findViewById(R.id.edit_nom_user));
        editpPassword = (EditText)(rootView.findViewById(R.id.edit_mot_passe));
        //nomUser = (TextView)(rootViewSecondActivity.findViewById(R.id.nom_user));
		buttonLogin.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String nomSaisi = editNom.getText().toString();
                String password = editpPassword.getText().toString();
                if(!nomSaisi.equals("")  && !password.equals("")){
                	Log.d("Fragment People ","conn base");
                	progressDialog = new CustomProgressDialog(getActivity(), R.drawable.loading_throbber);
                    progressDialog.setCancelable(true);
                	//a decommenter
                    myListofData = new ArrayList<String>();
                    getData=new GetDataAsyncTask();
                    getData.execute();
//                    try{
//                    	 
//                    	 Log.i("Nom récus ",nomSaisi);
//                    	 Log.i("Etat success : ",success+"");
//                            Intent secondActivity = new Intent(getActivity(),MainActivity.class);
//            				getActivity().finish();
//            				startActivity(secondActivity);
//            				UtilisateurConnecte=nomSaisi;
//                    }catch(Exception e){
//                    	Log.e("Connexion ","EREUUUURRRR");
////         				nomUser.setText(nom);
////         				Log.i("Nom Userrrr  ",nomUser.getText().toString());
//                    	
//                    }
                       	
                }
				
			}
			
		});
		
		return rootView;
	}
	
		
	
	

	private class GetDataAsyncTask extends  AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
                Log.i("add", "onPreExecute");
                super.onPreExecute();
                progressDialog.show();
        }
       
        @Override
        protected Void doInBackground(Void... params) {
                Log.i("add", " start doInBackground");
                ServiceHandler sh = new ServiceHandler();
               
                // Making a request to url and getting response
       // String jsonStr = sh.makeServiceCall(urlGet, ServiceHandler.GET);
        
        
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        
        nameValuePair.add(new BasicNameValuePair("IDENT_ETUD",editNom.getText().toString()));
        nameValuePair.add(new BasicNameValuePair("MOTPASS_ETUD",editpPassword.getText().toString()));
//        nameValuePair.add(new BasicNameValuePair("col4",col4Valeur.getText().toString()));

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(urlGet, ServiceHandler.POST,nameValuePair);

        Log.d("Response: ",jsonStr);
               
        if (jsonStr != null) {
        try {
                
        		JSONObject jsonObj = new JSONObject(jsonStr);
                // return value of success
                success=jsonObj.getInt("success");
               // Log.i("success", String.valueOf(success));
                if (success==0)
                {
                        // success=0 ==> there is a string = message
                        message=jsonObj.getString("message");
                        Log.i("message", message);
                        
                }
                else if (success==1)
                {
                // success=1 ==> there is an array of data = valeurs
                        JSONArray dataValues = jsonObj.getJSONArray("valeurs");
                        // loop each row in the array
                        //obligatoire pour parser les données retourné par json
//                        for(int j=0;j<dataValues.length();j++)
//                        {
                                JSONObject values = dataValues.getJSONObject(0);
                                // return values of col1 in valCol1
//                                String valCol1= values.getString("col1");
//                                // return values of col2 in valCol2
//                                String valCol2= values.getString("col2");
//                                String valCol3= values.getString("col3");
//                                String valCol4= values.getString("col4");
                                //add a string witch contains all of data getted from the response
                                //myListofData.add(valCol1+" - "+valCol2+" - "+valCol3+" - "+valCol4);
                               // Log.i("Row "+(j+1), valCol1+" - "+valCol2+" - "+valCol3+" - "+valCol4);
//                        }
                        
                        //Traitement mot de passe et nom d'utilisateur
                        
                        if(editNom.getText().toString().equals(values.get("IDENT_ETUD")) && editpPassword.getText().toString().equals(values.get("MOTPASS_ETUD"))){
//                        	System.out.println("Resultat requete ");
//                            System.out.println("val 1"+values.getString("col2"));
//                            System.out.println("val 2"+values.getString("col3"));
                        	//=========ICI code pour la connexion
                        	Log.i("Connexion success ","connecté");
                        	 Intent secondActivity = new Intent(getActivity(),MainActivity.class);
             				getActivity().finish();
             				startActivity(secondActivity);
             				idEtudiant=values.getString("IDENT_ETUD");
             				UtilisateurConnecte=values.getString("PRENOM_ETUD")+"  "+values.getString("NOM_ETUD");
             				
             				//traintement de la filiere
             				Log.i("filière", values.get("ID_FILI")+" code fi= "+codefiliere);
             				if(Integer.parseInt(values.get("ID_FILI").toString()) != 1){
             					codefiliere=0;
             					Log.i("filière", values.get("groupe")+" code fi= "+codefiliere);
             				}else{
             					Log.i("filière", "management : "+codefiliere);
             					codefiliere=1;
             				}
                        	
                        }
                }
                             
        } catch (JSONException e) {
                e.printStackTrace();
        }
        } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

        Log.i("add", " end doInBackground");
        return null;
        }
       
        @Override
        protected void onPostExecute(Void result) {
                Log.i("add", "onPostExecute");
                super.onPostExecute(result);
                if (progressDialog.isShowing())
                {
                        progressDialog.dismiss();
                }
                if(success==1)
                {
                        Toast.makeText(getActivity(), "Connecté ", Toast.LENGTH_LONG).show();
                        // show the list view contains the data
//                        arrayadp=new ArrayAdapter(getApplicationContext(),  android.R.layout.simple_list_item_1, myListofData);                                    
//                        lv.setAdapter(arrayadp);  
                       // Log.d("DataFromDB : ",myListofData.toString());
                }
                else
                {
                	Toast.makeText(getActivity(), "Erreur de connection ", Toast.LENGTH_LONG).show();
                	Log.d("DataFromDB : ","Erreur");
                }


        }
       
}
}
