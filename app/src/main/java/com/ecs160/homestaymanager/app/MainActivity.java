package com.ecs160.homestaymanager.app;

        import com.ecs160.homestaymanager.app.RegisterProfile;
        import com.ecs160.homestaymanager.app.EditProfile;
        import com.ecs160.homestaymanager.app.DBTools;

        import java.util.ArrayList;
        import java.util.HashMap;
        import android.os.Bundle;
        import android.app.ListActivity;
        import android.content.Intent;

        import android.view.View;

        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.ListAdapter;
        import android.widget.SimpleAdapter;
        import android.widget.TextView;
        import android.widget.ListView;


public class MainActivity extends ListActivity {

    // The Intent is used to issue that an operation should
    // be performed

    Intent intent;
    TextView profileId;

    // The object that allows me to manipulate the database

    DBTools dbTools = new DBTools(this);

    // Called when the Activity is first called

    protected void onCreate(Bundle savedInstanceState) {
        // Get saved data if there is any

        super.onCreate(savedInstanceState);

        // Designate that activity_main.xml is the interface used

        setContentView(R.layout.activity_main);

        // Gets all the data from the database and stores it
        // in an ArrayList

        ArrayList<HashMap<String, String>> profileList =  dbTools.getAllProfiles();

        // Check to make sure there are profiles to display

        if(profileList.size()!=0) {

            // Get the ListView and assign an event handler to it

            ListView listView = getListView();
            listView.setOnItemClickListener(new OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                    // When an item is clicked get the TextView
                    // with a matching checkId

                    profileId = (TextView) view.findViewById(R.id.profileId);

                    // Convert that profileId into a String

                    String profileIdValue = profileId.getText().toString();

                    // Signals an intention to do something
                    // getApplication() returns the application that owns
                    // this activity

                    Intent  theIndent = new Intent(getApplication(),EditProfile.class);

                    // Put additional data in for EditProfile to use

                    theIndent.putExtra("profileId", profileIdValue);

                    // Calls for EditProfile

                    startActivity(theIndent);
                }
            });

            // A list adapter is used bridge between a ListView and
            // the ListViews data

            // The SimpleAdapter connects the data in an ArrayList
            // to the XML file

            // First we pass in a Context to provide information needed
            // about the application
            // The ArrayList of data is next followed by the xml resource
            // Then we have the names of the data in String format and
            // their specific resource ids

            ListAdapter adapter = new SimpleAdapter( MainActivity.this,profileList, R.layout.profile_entry, new String[] { "profileId","lastName", "firstName"}, new int[] {R.id.profileId, R.id.lastName, R.id.firstName});

            // setListAdapter provides the Cursor for the ListView
            // The Cursor provides access to the database data

            setListAdapter(adapter);
        }
    }

    // When showAddProfile is called with a click the Activity 
    // NewProfile is called

    public void showNewProfile(View view) {
        Intent theIntent = new Intent(getApplication(), RegisterProfile.class);
        startActivity(theIntent);
    }
}