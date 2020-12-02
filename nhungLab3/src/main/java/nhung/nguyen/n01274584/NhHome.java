package nhung.nguyen.n01274584;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NhHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NhHome extends Fragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int mDate, mMonth, mYear;
    public NhHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NhHome newInstance(String param1, String param2) {
        NhHome fragment = new NhHome();
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
        View view = inflater.inflate(R.layout.nh_home, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataBase", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        Calendar calendar = Calendar.getInstance();
        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.courses,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        //String currentTime = Time.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        TextView textViewDate = view.findViewById(R.id.date);
         textViewDate.setText(currentDate);
       // final TextView textView= view.findViewById(R.id.txt1);
        EditText editText;
        Button btn;
        Dialog dialog;
        btn = view.findViewById(R.id.btn);
        final EditText dateTXT;
        ImageView cal;
        dateTXT = view.findViewById(R.id.txt);
        cal = view.findViewById(R.id.cal);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar Cal= Calendar.getInstance();
                mDate = Cal.get(Calendar.DATE);
                mMonth = Cal.get(Calendar.MONTH);
                mYear = Cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        dateTXT.setText(date+"-"+month+"-"+year);
                        editor.putString("date",date+"-"+month+"-"+year);
                        editor.commit();
                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.show();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataBase", Context.MODE_PRIVATE);

                String msg = sharedPreferences.getString("date","");
                msg+= "\n";
                msg+= sharedPreferences.getString("Course","");
                //textView.setText(msg);
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("");
                alert.setMessage(msg    );
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.create().show();
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String s = parent.getSelectedItem().toString();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("dataBase", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Course",s);
        editor.commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}