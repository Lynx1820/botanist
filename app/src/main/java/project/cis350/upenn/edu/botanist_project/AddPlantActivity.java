package project.cis350.upenn.edu.botanist_project;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A plant info entry screen for adding a new plant.
 */
public class AddPlantActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Keep track of the creation task to ensure we can cancel it if requested.
     */
    private PlantAddTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView typeView;
    private EditText nameView;
    private View mProgressView;
    private View mEntryFormView;
    private List<String> species = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
        // Set up the creation form.
        typeView = (AutoCompleteTextView) findViewById(R.id.plant_type);
        populateAutoComplete();

        nameView = (EditText) findViewById(R.id.plant_name);
        nameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                attemptEntry();
                return true;
            }
        });

        Button plantAddButton = (Button) findViewById(R.id.plant_add_button);
        plantAddButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptEntry();
            }
        });

        mEntryFormView = findViewById(R.id.entry_form);
        mProgressView = findViewById(R.id.entry_progress);
    }

    private void populateAutoComplete() {
        try {
            List<PlantInfo> info = PlantInfo.readInfoFromJson(getAssets().open("plant_info.json"));
            for (PlantInfo pi : info) {
                species.add(pi.name);
            }
        } catch(IOException e) {
            e.printStackTrace();
            finish();
        }
//        getLoaderManager().initLoader(0, null, this);
        addTypesToAutoComplete(species);
    }


    /**
     * Attempts to create the plant specified by the form.
     * If there are form errors (invalid species, missing fields, etc.), the
     * errors are presented and no actual creation attempt is made.
     */
    private void attemptEntry() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        typeView.setError(null);
        nameView.setError(null);

        // Store values at the time of the login attempt.
        String type = typeView.getText().toString();
        String name = nameView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        View addokafocusView = null;

        // Check if the User entered a name.
        if (TextUtils.isEmpty(name)) {
            nameView.setError(getString(R.string.error_field_required));
            focusView = nameView;
            cancel = true;
        } else if (!isNameValid(name)) {
            nameView.setError(getString(R.string.error_invalid_name));
            focusView = nameView;
            cancel = true;
        } else if (!isNameUnique(name)) {
            nameView.setError(getString(R.string.error_name_used));
            focusView = nameView;
            cancel = true;
        }

        // Check for a valid type.
        if (TextUtils.isEmpty(type)) {
            typeView.setError(getString(R.string.error_field_required));
            focusView = typeView;
            cancel = true;
        } else if (!isTypeValid(type)) {
            typeView.setError(getString(R.string.error_invalid_type));
            focusView = typeView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new PlantAddTask(type, name);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isTypeValid(String type) {
        for (String n : species) {
            if (type.equals(n)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNameValid(String name) {
        return name.length() > 2 && name.length() < 17;
    }

    private boolean isNameUnique(String name) {
        List<Plant> plants = FetchPlantData.getPlants(getApplicationContext(), MenuActivity.owner);
        for (Plant p : plants) {
            if (p.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Shows the progress UI and hides the entry form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mEntryFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mEntryFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mEntryFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mEntryFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> types = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            // TODO: __
//            types.add(cursor.getString());
            cursor.moveToNext();
        }
        addTypesToAutoComplete(species);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        // TODO: complete
    }

    private void addTypesToAutoComplete(List<String> types) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(AddPlantActivity.this,
                        android.R.layout.simple_dropdown_item_1line, types);
        adapter.setNotifyOnChange(true);
        typeView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous creation task used to authenticate
     * the plant.
     */
    public class PlantAddTask extends AsyncTask<Void, Void, Boolean> {

        private final Plant plant;

        PlantAddTask(String type, String name) {
            plant = new Plant(name, type);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // add plant to database
            FetchPlantData plantAdaptor = new FetchPlantData(getApplicationContext()).open();
            return plantAdaptor.insertEntry(MenuActivity.owner, plant);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            Intent i = new Intent();
            i.putExtra("result", success.booleanValue());
            i.putExtra("createdPlant", plant);
            setResult(1, i);
            finish();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

