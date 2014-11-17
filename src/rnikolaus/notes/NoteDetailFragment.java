package rnikolaus.notes;

import java.text.DateFormat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;


import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A fragment representing a single Note detail screen. This fragment is either
 * contained in a {@link NoteListActivity} in two-pane mode (on tablets) or a
 * {@link NoteDetailActivity} on handsets.
 */
public class NoteDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private Note mItem;

	private TextView dateDisplay;
	
	private EditText editTitle;

	private EditText editMessage;

	private NoteDAO noteDAO;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public NoteDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		noteDAO=new NoteDAO(getActivity().getApplicationContext());
		noteDAO.open();
		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = noteDAO.getNoteById(getArguments().getLong(
					ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_note_detail,
				container, false);
		Button saveButton =(Button) rootView.findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				noteDAO.modify(mItem.getId(), 
						editTitle.getText().toString(), 
						editMessage.getText().toString());
				NavUtils.navigateUpTo(getActivity(),
						new Intent(getActivity(), NoteListActivity.class));
				
			}
		});
		Button deleteButton=(Button) rootView.findViewById(R.id.deleteButton);
		deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				noteDAO.deleteNote(mItem.getId());
				NavUtils.navigateUpTo(getActivity(),
						new Intent(getActivity(), NoteListActivity.class));
			}
		});
		dateDisplay = (TextView) rootView.findViewById(R.id.dateDisplay);
		editTitle = (EditText) rootView.findViewById(R.id.editTitle);
		editMessage = (EditText) rootView.findViewById(R.id.editMessage);
		// Show the dummy content as text in a TextView.
		if (mItem != null) {			
			DateFormat df = DateFormat.getDateTimeInstance();
			dateDisplay.setText(df.format(mItem.getDate()));
			editTitle.setText(mItem.getTitle());	
			editMessage.setText(mItem.getMessage());			
		}

		return rootView;
	}
	@Override
	  public void onResume() {
	    noteDAO.open();
	    super.onResume();
	  }

	  @Override
	  public void onPause() {
		noteDAO.close();
	    super.onPause();
	  }
	
}
