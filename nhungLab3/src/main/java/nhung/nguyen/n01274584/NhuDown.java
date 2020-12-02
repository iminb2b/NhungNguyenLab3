package nhung.nguyen.n01274584;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NhuDown#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NhuDown extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    URL ImageUrl = null;
    InputStream is = null;
    Bitmap bmImg = null;
    ImageView imageView= null;
    ProgressDialog p;
    Handler handle;
    public NhuDown() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NhungFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NhuDown newInstance(String param1, String param2) {
        NhuDown fragment = new NhuDown();
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
        View view=  inflater.inflate(R.layout.nhu_down, container, false);
        Button button=view.findViewById(R.id.asyncTask);
        imageView=view.findViewById(R.id.image);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AsyncTaskExample asyncTask=new AsyncTaskExample();
                asyncTask.execute("https://www.tutorialspoint.com/images/tp-logo-diamond.png");
            }
        });
        return view;
    }
    @Deprecated
    private class AsyncTaskExample extends AsyncTask<String, String, Bitmap> {
        Handler handle = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
               // p.incrementProgressBy(50); // Incremented By Value 2
            }
        };
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            p=new ProgressDialog(getActivity());
            p.setMax(100);
            p.setMessage("Please wait...It is downloading");
            p.setIndeterminate(false);
            p.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            p.setCancelable(false);
            p.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (p.getProgress() <= p.getMax()) {
                            Thread.sleep(200);
                            handle.sendMessage(handle.obtainMessage());
                            if (p.getProgress() == p.getMax()) {
                                p.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {

                ImageUrl = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) ImageUrl
                        .openConnection();
                conn.setDoInput(true);
                conn.connect();
                is = conn.getInputStream();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bmImg = BitmapFactory.decodeStream(is, null, options);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return bmImg;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(imageView!=null) {
                p.hide();
                imageView.setImageBitmap(bitmap);
            }else {
                p.show();
            }
        }
    }
}

