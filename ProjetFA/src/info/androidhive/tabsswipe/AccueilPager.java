package info.androidhive.tabsswipe;

import iga.database.externe.CustomProgressDialog;
import iga.database.externe.ServiceHandler;
import info.androidhive.slidingmenu.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class AccueilPager extends Fragment {

	CustomProgressDialog  progressDialog;
	String urlGet="http://android.iganews.ga/php/actualitesAccueil.php";
    int success;
    String message;
    ChargerActualite getData;
    ArrayList<HashMap<String, String>> listDataRatrap;
	
	
	private ViewFlipper viewFlipper;
	private ViewFlipper viewFlipperM;
    private TextView tv=null ;
    private View rootView;
      
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		
		 rootView = inflater.inflate(R.layout.acceuil_pager, container, false);
		 viewFlipper = (ViewFlipper) rootView.findViewById(R.id.view_flipper);
		 viewFlipperM = (ViewFlipper) rootView.findViewById(R.id.view_flipperM);
//		 layoutIng = (LinearLayout)rootView.findViewById(R.id.pageLayoutIng);
//		 layoutManage = (LinearLayout)rootView.findViewById(R.id.pageLayoutManag);
		 tv = (TextView)rootView.findViewById(R.id.infoGeneral);
		 progressDialog = new CustomProgressDialog(getActivity(), R.drawable.loading_throbber);
	     progressDialog.setCancelable(true);
		 listDataRatrap = new ArrayList<HashMap<String, String>>();
	        getData = new ChargerActualite();
	        getData.execute();
		 
		 //tv.getAnimation().
		 
		// tv.setSelected(true);
//		 tv.setMarqueeRepeatLimit(Animation.INFINITE);
//		 Animation anim = AnimationUtils.loadAnimation(getActivity(), R.animator.animationfile);
//		 anim.setRepeatCount(Animation.INFINITE);
//		 tv.setAnimation(anim);
//		 tv.append("\n\n I have a scrollview containing a textview in an Android app. This textview will have text appended to it continuously at set intervals. Scrolling works and the text adds just fine, but what I'd like to do is have the scrollview autoscroll down as text is added.");
//	     slideRightOut = AnimationUtils.loadAnimation(getActivity(), R.animator.out_to_right);
//	     viewFlipper.setInAnimation(slideLeftIn);
        // viewFlipper.setInAnimation(getActivity(), R.animator.in_from_right);
         //viewFlipper.setOutAnimation(getActivity(), R.animator.in_from_right);
//		 viewFlipper.setInAnimation(getActivity(), R.animator.in_from_right);
//        viewFlipper.setOutAnimation(getActivity(), R.animator.out_to_left);
		 viewFlipper.setInAnimation(getActivity(), R.animator.slide_in_up);
	     viewFlipper.setOutAnimation(getActivity(), R.animator.slide_out_up);
	     viewFlipperM.setInAnimation(getActivity(), R.animator.slide_in_up);
	     viewFlipperM.setOutAnimation(getActivity(), R.animator.slide_out_up);
         viewFlipper.setFlipInterval(6000);
         viewFlipperM.setFlipInterval(10000);
         LinearLayout lin = new LinearLayout(getActivity());
         TextView t = new TextView(getActivity());
         t.setText("Semaine de court metrage");
         
         lin.addView(t);
         viewFlipper.addView(lin);
         
         viewFlipper.startFlipping();
         viewFlipperM.startFlipping();
//		
		return rootView;
	}
	
	private class ChargerActualite extends  AsyncTask<Void, Void, Void> {
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
	    
	    nameValuePair.add(new BasicNameValuePair("NULL","NULL"));
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
	                            map.put("TITRE_ACT",values.getString("TITRE_ACT"));
	                            map.put("LIBELLE", values.getString("LIBELLE"));
	                            map.put("NUM_ACT", values.getString("NUM_ACT"));
	                            map.put("NUM_GR", values.getString("NUM_GR"));
	                            map.put("ID_FILI", values.getString("ID_FILI"));
	                            listDataRatrap.add(map);
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
	                    
	            	String str = "<font color='#FF0000'>Infos :   </font>";
	            	tv.setText(Html.fromHtml(str));
	            	tv.append(listDataRatrap.get(0).get("LIBELLE").toString()+getString(R.string.tabulation));
	            	TextView titr = (TextView)rootView.findViewById(R.id.titreManag1);
	            	TextView inf  = (TextView)rootView.findViewById(R.id.textView2M2);
	            	LinearLayout lay = (LinearLayout)rootView.findViewById(R.id.layoutIng1);
	            	LayoutParams paramTitr = titr.getLayoutParams();
	            	LayoutParams paramInfo = inf.getLayoutParams();
	            	LayoutParams paramLayout = lay.getLayoutParams(); 
	            	for(int i=1;i<listDataRatrap.size();i++){
	            		if(Integer.parseInt(listDataRatrap.get(i).get("ID_FILI").toString()) == 1){
	            			viewFlipper.removeAllViews();
	            			break;
	            		}
	            	}
	            	for(int i=1;i<listDataRatrap.size();i++){
	            		if(Integer.parseInt(listDataRatrap.get(i).get("ID_FILI").toString()) != 1){
	            			viewFlipperM.removeAllViews();
	            			break;
	            		}
	            	}
	            	
	            	for(int i=1;i<listDataRatrap.size();i++){
	            		tv.append(Html.fromHtml(str));
	            		tv.append(listDataRatrap.get(i).get("LIBELLE").toString()+getString(R.string.tabulation));
	            		
	            		if(Integer.parseInt(listDataRatrap.get(i).get("ID_FILI").toString()) == 1){
	            			LinearLayout lin = new LinearLayout(getActivity());
	            			lin.setOrientation(LinearLayout.VERTICAL);
	            			lin.setGravity(Gravity.CENTER);
	            			lin.setLayoutParams(paramLayout);
		                    TextView titre = new TextView(getActivity());
		                    titre.setText(listDataRatrap.get(i).get("TITRE_ACT").toString());
		                    titre.setTextColor(Color.parseColor("#191975"));
		                    titre.setLayoutParams(paramTitr);
		                    titre.setTextSize(20);
		                    titre.setTypeface(titr.getTypeface());
		                    
		                    TextView info = new TextView(getActivity());
		                    info.setText(listDataRatrap.get(i).get("LIBELLE").toString());
		                    info.setLayoutParams(paramInfo);
		                    info.setTypeface(inf.getTypeface());
		                    info.setTextSize(15);
		                    info.setLineSpacing(1, 1);
		                    
		                    lin.addView(titre);
		                    lin.addView(info);
		                    viewFlipper.addView(lin);
		                    
	            		}else{
	            			LinearLayout lin = new LinearLayout(getActivity());
	            			lin.setOrientation(LinearLayout.VERTICAL);
	            			lin.setGravity(Gravity.CENTER);
	            			lin.setLayoutParams(paramLayout);
		                    TextView titre = new TextView(getActivity());
		                    titre.setText(listDataRatrap.get(i).get("TITRE_ACT").toString());
		                    titre.setTextColor(Color.parseColor("#191975"));
		                    titre.setLayoutParams(paramTitr);
		                    titre.setTextSize(20);
		                    titre.setTypeface(titr.getTypeface());
		                    
		                    TextView info = new TextView(getActivity());
		                    info.setText(listDataRatrap.get(i).get("LIBELLE").toString());
		                    info.setLayoutParams(paramInfo);
		                    info.setTypeface(inf.getTypeface());
		                    info.setTextSize(15);
		                    info.setLineSpacing(1, 1);
		                    
		                    lin.addView(titre);
		                    lin.addView(info);
		                    viewFlipperM.addView(lin);
	            		}
	            		
	            		
	            	}
	            	
	            }
	            else
	            {
	            	Toast.makeText(getActivity(), "Aucune information à afficher ", Toast.LENGTH_LONG).show();
	            	//Log.d("DataFromDB : ","Erreur");
	            }


	    }
	   
	}
	
}