package info.androidhive.slidingmenu;

import iga.database.externe.CustomProgressDialog;
import iga.database.externe.ServiceHandler;
import info.androidhive.tabsswipe.Connexion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class AbsencesFragment extends Fragment {
	CustomProgressDialog  progressDialog;
	String urlGet="http://android.iganews.ga/php/absences.php";
    int success;
    String message;
    ChargerAbsences getData;
    ArrayList<HashMap<String, String>> listDataAbs;
    ListView lv;
	
	public AbsencesFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_absences, container, false);
        progressDialog = new CustomProgressDialog(getActivity(), R.drawable.loading_throbber);
        progressDialog.setCancelable(true);
        
        lv  = (ListView)(rootView.findViewById(R.id.listAbs));
        listDataAbs = new ArrayList<HashMap<String, String>>();
        getData = new ChargerAbsences();
        getData.execute(); 
        return rootView;
    }
	
	
	private class ChargerAbsences extends  AsyncTask<Void, Void, Void> {
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
	    
	    
	    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
	    
	    nameValuePair.add(new BasicNameValuePair("IDENT_ETUD",Connexion.idEtudiant));
	   // nameValuePair.add(new BasicNameValuePair("MOTPASS_ETUD",editpPassword.getText().toString()));
//	    nameValuePair.add(new BasicNameValuePair("col4",col4Valeur.getText().toString()));

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
	                    for(int j=0;j<dataValues.length();j++)
	                    {
	                            JSONObject values = dataValues.getJSONObject(j);
	                         // creating new HashMap
	                            HashMap<String, String> map = new HashMap<String, String>();
	                            map.put("NOM_PROF", "Professeur "+values.getString("NOM_PROF"));
	                            map.put("DATE_ABS", "Professeur "+values.getString("NOM_PROF")+
	                            		" sera absent le "+values.getString("DATE_ABS"));
	                            listDataAbs.add(map);
	                            // return values of col1 in valCol1
//	                            String valCol1= values.getString("col1");
//	                            // return values of col2 in valCol2
//	                            String valCol2= values.getString("col2");
//	                            String valCol3= values.getString("col3");
//	                            String valCol4= values.getString("col4");
	                            //add a string witch contains all of data getted from the response
	                            //myListofData.add(valCol1+" - "+valCol2+" - "+valCol3+" - "+valCol4);
	                           // Log.i("Row "+(j+1), valCol1+" - "+valCol2+" - "+valCol3+" - "+valCol4);
	                    }
	                    
	                    //Traitement mot de passe et nom d'utilisateur
	                    
	                    
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
	                    //Toast.makeText(getActivity(), "Connecté ", Toast.LENGTH_LONG).show();
	                    // show the list view contains the data
//	                    arrayadp=new ArrayAdapter(getApplicationContext(),  android.R.layout.simple_list_item_1, myListofData);                                    
//	                    lv.setAdapter(arrayadp);  
	                   // Log.d("DataFromDB : ",myListofData.toString());
	                    ListAdapter adapter = new SimpleAdapter(getActivity(),listDataAbs,R.layout.row_item_absence,
	                    		new String[]{"NOM_PROF","DATE_ABS"},new int []{R.id.matiere,R.id.infoAbs});
	                    lv.setAdapter(adapter);
	            }
	            else
	            {
	            	Toast.makeText(getActivity(), "Aucune information à afficher ", Toast.LENGTH_LONG).show();
	            	Log.d("DataFromDB : ","Erreur");
	            }


	    }
	   
	}
	
}






