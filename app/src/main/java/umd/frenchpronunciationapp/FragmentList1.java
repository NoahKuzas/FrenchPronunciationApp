package umd.frenchpronunciationapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import umd.frenchpronunciationapp.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class FragmentList1 extends Fragment implements AbsListView.OnItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String click;
    TextView text1;
    private ImageButton button1;
    private ImageButton button2;
    ArrayList <String> phrases = new ArrayList<String> ();
    MediaRecorder myAudioRecorder = new MediaRecorder();
    private String outputFile = null;
    Button play;
    Button stop;
    Button record;


    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static FragmentList1 newInstance(String param1, String param2) {
        FragmentList1 fragment = new FragmentList1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentList1() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content
        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        button1 = (ImageButton) view.findViewById(R.id.male);// place id of your button1.

        button2 = (ImageButton) view.findViewById(R.id.female);// place id of your button2.

        play=(Button) view.findViewById(R.id.play);
        stop=(Button)view.findViewById(R.id.stop);
        record=(Button)view.findViewById(R.id.record);



        addListenerOnButtonRecord();
        addListenerOnButtonStop();
        addListenerOnButtonPlay();
        addListenerOnButtonMale();
        addListenerOnButtonFemale();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setSource () {
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        stop.setEnabled(false);
        play.setEnabled(false);



        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);
    }

    public void addListenerOnButtonRecord () {
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setSource();
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                record.setEnabled(false);
                stop.setEnabled(true);

                //Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void addListenerOnButtonPlay () {
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException,SecurityException,IllegalStateException {
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(outputFile);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.setDataSource (outputFile);
                    m.prepare();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                //stop.setEnabled(true);

                //Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });
    }



    public void addListenerOnButtonStop () {
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                try {
                    myAudioRecorder.stop();
                    myAudioRecorder.reset();
                    myAudioRecorder.release();
                    myAudioRecorder = null;
                }
                catch (RuntimeException stopException){
                    //handle cleanup here
                }
                stop.setEnabled(false);
                play.setEnabled(true);

                //Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
            }
        });
    }


    public void readInPhrases () throws IOException
    {
        String str;

        InputStream myStream = getResources().openRawResource(R.raw.phrases);
        BufferedReader reader = new BufferedReader(new InputStreamReader(myStream));
        while ((str = reader.readLine()) != null)
        {
            phrases.add(str);
        }


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
        text1 = (TextView) getView ().findViewById (R.id.phrase);


        try {
            readInPhrases();

        }
        catch (IOException ex)
        {
            // catches overidden error ioexecption
        }

        //MediaPlayer mp500 = new MediaPlayer ();
        //mp500.setAudioSessionId(R.raw.g1);

        MediaPlayer mp1 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g1));
        MediaPlayer mp2 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g2));
        MediaPlayer mp3 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g3));
        MediaPlayer mp4 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g4));
        MediaPlayer mp5 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g5));
        MediaPlayer mp6 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g6));
        MediaPlayer mp7 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g7));
        MediaPlayer mp8 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g8));
        MediaPlayer mp9 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g9));
        MediaPlayer mp10 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g10));
        MediaPlayer mp11 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g11));
        MediaPlayer mp12 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g12));
        MediaPlayer mp13 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g13));
        MediaPlayer mp14 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g14));
        MediaPlayer mp15 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g15));
        MediaPlayer mp16 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g16));
        MediaPlayer mp17 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g17));
        MediaPlayer mp18 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g18));
        MediaPlayer mp19 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g19));
        MediaPlayer mp20 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g20));
        MediaPlayer mp21 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g21));
        MediaPlayer mp22 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g22));
        MediaPlayer mp23 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g23));
        MediaPlayer mp24 = MediaPlayer.create (getActivity().getApplicationContext(), (R.raw.g24));


        Intent myIntent = new Intent(view.getContext(), Fragment1.class);

        switch (position){
            default:
            case 0:
                //myIntent.putExtra ("currentPhrase", phrases.get (0));
                //startActivity(myIntent);
                text1.setText (phrases.get(0));
                // to incorperate male voice, do this and have male and female buttons
                // and a variable that holds true or false if button was pressed
                if (click == "f")
                mp1.start();

                //mp... .start ();
                break;
            case 1:
                text1.setText(phrases.get(1));
                if (click == "f")
                mp2.start ();

                break;
            case 2:
                text1.setText(phrases.get(2));
                if (click == "f")
                mp3.start ();

                break;
            case 3:
                text1.setText(phrases.get(3));
                if (click == "f")
                mp4.start ();

                break;
            case 4:
                text1.setText(phrases.get(4));
                if (click == "f")
                mp5.start ();

                break;
            case 5:
                text1.setText(phrases.get(5));
                if (click == "f")
                mp6.start ();

                break;
            case 6:
                text1.setText(phrases.get(6));
                if (click == "f")
                mp7.start ();

                break;
            case 7:
                text1.setText(phrases.get(7));
                if (click == "f")
                mp8.start ();

                break;
            case 8:
                text1.setText(phrases.get(8));
                if (click == "f")
                mp9.start ();

                break;
            case 9:
                text1.setText(phrases.get(9));
                if (click == "f")
                mp10.start ();

                break;
            case 10:
                text1.setText(phrases.get(10));
                if (click == "f")
                mp11.start ();

                break;
            case 11:
                text1.setText (phrases.get (11));
                if (click == "f")
                mp12.start ();

                break;
            case 12:
                text1.setText (phrases.get(12));
                if (click == "f")
                    mp13.start();

                    break;
            case 13:
                text1.setText(phrases.get(13));
                if (click == "f")
                    mp14.start ();

                    break;
            case 14:
                text1.setText(phrases.get(14));
                if (click == "f")
                    mp15.start ();

                    break;
            case 15:
                text1.setText(phrases.get(15));
                if (click == "f")
                    mp16.start ();

                    break;
            case 16:
                text1.setText(phrases.get(16));
                if (click == "f")
                    mp17.start ();

                    break;
            case 17:
                text1.setText(phrases.get(17));
                if (click == "f")
                    mp18.start ();

                    break;
            case 18:
                text1.setText(phrases.get(18));
                if (click == "f")
                    mp19.start ();

                    break;
            case 19:
                text1.setText(phrases.get(19));
                if (click == "f")
                    mp20.start ();

                    break;
            case 20:
                text1.setText(phrases.get(20));
                if (click == "f")
                    mp21.start ();

                    break;
            case 21:
                text1.setText(phrases.get(21));
                if (click == "f")
                    mp22.start ();

                    break;
            case 22:
                text1.setText(phrases.get(22));
                if (click == "f")
                    mp23.start ();

                    break;
            case 23:
                text1.setText (phrases.get (23));
                if (click == "f")
                    mp24.start ();

                    break;
        }
    }

    public void addListenerOnButtonMale ()
    {
        button1.setBackgroundResource((R.drawable.malelogo));
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                click = "m";
            }
        });
    }

    public void addListenerOnButtonFemale ()
    {

        button2.setBackgroundResource(R.drawable.femalelogo);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                click = "f";
            }
        });
    }
    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }





}
