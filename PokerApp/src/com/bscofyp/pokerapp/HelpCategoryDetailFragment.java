package com.bscofyp.pokerapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bscofyp.pokerapp.dummy.HelpContent;

/**
 * A fragment representing a single Help Category detail screen. This fragment
 * is either contained in a {@link HelpCategoryListActivity} in two-pane mode
 * (on tablets) or a {@link HelpCategoryDetailActivity} on handsets.
 */
public class HelpCategoryDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private HelpContent.HelpItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public HelpCategoryDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = HelpContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_helpcategory_detail, container, false);

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			TextView tv = ((TextView) rootView.findViewById(R.id.helpcategory_detail));
			tv.setText(mItem.content);
			tv.setBackgroundColor(getResources().getColor(R.color.green));
			if(mItem.backgroundSrc.length()>0){
				int t = rootView.getResources().getIdentifier("@drawable/"+mItem.backgroundSrc, null,rootView.getContext().getPackageName());
				tv.setBackgroundResource(t);
			}
		}

		return rootView;
	}
}
