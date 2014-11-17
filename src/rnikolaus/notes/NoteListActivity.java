package rnikolaus.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * An activity representing a list of Notes. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link NoteDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link NoteListFragment} and the item details (if present) is a
 * {@link NoteDetailFragment}.
 * <p>
 * This activity also implements the required {@link NoteListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class NoteListActivity extends FragmentActivity implements
		NoteListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private NoteDAO noteDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_list);
		noteDAO = new NoteDAO(getApplicationContext());
		if (findViewById(R.id.note_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((NoteListFragment) getSupportFragmentManager().findFragmentById(
					R.id.note_list)).setActivateOnItemClick(true);
		}
	}


	/**
	 * Callback method from {@link NoteListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(long id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putLong(NoteDetailFragment.ARG_ITEM_ID, id);
			NoteDetailFragment fragment = new NoteDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.note_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, NoteDetailActivity.class);
			detailIntent.putExtra(NoteDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add_note) {
			create();
			return true;
		}else if(id==R.id.action_save_to_file){
			save();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void save() {
		// TODO Auto-generated method stub
		String s="";
		for (Note note:noteDAO.listNotes()){
			s+=note.getId();
			s+=";";
			s+=note.getTitle();
			s+=";";
			s+=note.getMessage();
			s+="\n";
		}
		
	}


	public void create(){
		Note n =noteDAO.createNote("new title", "new message");
		
		((NoteListFragment) getSupportFragmentManager().findFragmentById(
				R.id.note_list)).getArrayAdapter().insert(n, 0);
				
		
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
