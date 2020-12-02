package nhung.nguyen.n01274584;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NhuSet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NhuSet extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RadioButton radioButton;
    RadioGroup radioGroup;
    Switch aSwitch;
    int s=0;
    String bg="";
    Boolean sw=true;
    //Seek bar object
    private SeekBar seekBar;

    //Variable to store brightness value
    private int brightness;
    //Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    //Window object, that will store a reference to the current window
    private Window window;
    public NhuSet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NhuSet newInstance(String param1, String param2) {
        NhuSet fragment = new NhuSet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.nhung_set, container, false);

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        int cBrightness = Settings.System.getInt(getContext().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,0);
        seekBar.setProgress(cBrightness);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkButton(view);
            }
        });
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataBase", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        aSwitch = view.findViewById(R.id.sw);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sw = true;
                }else{
                    sw = false;
                }
            }
        });
        final Button apply = view.findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply(view);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                s = seekBar.getProgress();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return view;
    }
    public  void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = v.findViewById(radioId);
        bg = radioButton.getText().toString();

    }
    public void apply(View v){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataBase", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("seekbar", s);
        editor.putString("bachground", bg);
        editor.putBoolean("orientation",sw);
        editor.commit();
        LinearLayout ln = v.findViewById(R.id.fragment);
        if(bg.equals("Green")) {
            ln.setBackgroundColor(Color.GREEN);
        }else if(bg.equals("Pink")){
            ln.setBackgroundColor(Color.RED);
        }
        Context context = getContext();
        boolean canWrite = Settings.System.canWrite(context);
        if (canWrite){

            Settings.System.putInt(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            Settings.System.putInt(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS, s);
        }else{
            Intent intent = new Intent (Settings.ACTION_MANAGE_WRITE_SETTINGS);
            context.startActivity(intent);
        }
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}