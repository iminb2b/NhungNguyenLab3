package nhung.nguyen.n01274584;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class NguSet extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}