package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class AccueilManagementFragment extends Fragment {
	
	//public HomeFragment(){}
	private ViewFlipper viewFlipper;
    private float lastX;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        //View rootView = inflater.inflate(R.layout.acceuil_pager, container, false);
		View rootView = inflater.inflate(R.layout.acceuil_management, container, false);
		
        
        viewFlipper = (ViewFlipper) rootView.findViewById(R.id.view_flipper);
        TextView textView= (TextView) rootView.findViewById(R.id.InfoGeneralMang);
        textView.setSelected(true);
        viewFlipper.setInAnimation(getActivity(), R.animator.in_from_right);
        viewFlipper.setOutAnimation(getActivity(), R.animator.out_to_left);
        viewFlipper.setFlipInterval(6000);
        viewFlipper.startFlipping();
       
         
        return rootView;
    }
	
	
	 // Method to handle touch event like left to right swap and right to left swap
    public boolean onTouchEvent(MotionEvent touchevent) 
    {
                 switch (touchevent.getAction())
                 {
                        // when user first touches the screen to swap
                         case MotionEvent.ACTION_DOWN: 
                         {
                             lastX = touchevent.getX();
                             break;
                        }
                         case MotionEvent.ACTION_UP: 
                         {
                             float currentX = touchevent.getX();
                             
                             // if left to right swipe on screen
                             if (lastX < currentX) 
                             {
                                  // If no more View/Child to flip
                                 if (viewFlipper.getDisplayedChild() == 0)
                                     break;
                                 
                                 // set the required Animation type to ViewFlipper
                                 // The Next screen will come in form Left and current Screen will go OUT from Right 
                                 viewFlipper.setInAnimation(getActivity(), R.animator.in_from_left);
                                 viewFlipper.setOutAnimation(getActivity(), R.animator.out_to_right);
                                 // Show the next Screen
                                 viewFlipper.showNext();
                             }
                             
                             // if right to left swipe on screen
                             if (lastX > currentX)
                             {
                                 if (viewFlipper.getDisplayedChild() == 1)
                                     break;
                                 // set the required Animation type to ViewFlipper
                                 // The Next screen will come in form Right and current Screen will go OUT from Left 
                                 viewFlipper.setInAnimation(getActivity(), R.animator.in_from_right);
                                 viewFlipper.setOutAnimation(getActivity(), R.animator.out_to_left);
                                 // Show The Previous Screen
                                 viewFlipper.showPrevious();
                             }
                             break;
                         }
                 }
                 return false;
    }
}
