package com.wheatrenterprises.eric.grubber;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SettingsFragment extends DialogFragment {

    ViewGroup container;
    EditText editText;
    SharedPreferences sharedPreferences;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();

        return fragment;
    }
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.container = container;
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        sharedPreferences = getActivity().getSharedPreferences("MySharedPreferences", 0);
        final SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

        RadioButton rbChosen = (RadioButton) view.findViewById(R.id.radio_button_chosen_location);
        RadioButton rbCurrent = (RadioButton) view.findViewById(R.id.radio_button_current_location);

        //radio button ids for onCheckChanged
        final int chosenId = rbChosen.getId();
        final int currentId = rbCurrent.getId();

        editText = (EditText) view.findViewById(R.id.edit_text_chosen_location);
        editText.setText(sharedPreferences.getString("Location", ""));
        final EditText et = editText;

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                               @Override
                                               public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                                                   if (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                                                       InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                                                       in.hideSoftInputFromWindow(textView.getWindowToken(),
                                                               InputMethodManager.HIDE_NOT_ALWAYS);

                                                       return true;

                                                   }
                                                   return false;
                                               }
                                           });

                editText.setOnFocusChangeListener(new View.OnFocusChangeListener()

                                                  {
                                                      @Override
                                                      public void onFocusChange(View v, boolean hasFocus) {

                                                          if (!hasFocus) {
                                                              sharedPreferencesEditor.putString("Location", et.getText().toString());
                                                              sharedPreferencesEditor.commit();
                                                          }
                                                      }
                                                  }
                );

            RadioGroup rg = (RadioGroup) view.findViewById(R.id.radio_button_group);
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()

            {
                @Override
                public void onCheckedChanged (RadioGroup group,int checkedId){

                if (chosenId == checkedId) {
                    sharedPreferencesEditor.putString("LocationType", "Chosen");
                    sharedPreferencesEditor.commit();

                    et.setEnabled(true);
                    et.setVisibility(View.VISIBLE);
                } else {
                    sharedPreferencesEditor.putString("LocationType", "Current");
                    sharedPreferencesEditor.commit();
                    et.setEnabled(false);
                    et.setVisibility(View.INVISIBLE);
                }
            }
            }

            );

            if(sharedPreferences.getString("LocationType","Current").

            equals("Chosen")

            )
                    rbChosen.setChecked(true);
            else
                    rbCurrent.setChecked(true);

            // Inflate the layout for this fragment
            return view;
        }


        @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Location", editText.getText().toString());
        editor.commit();
    }
}
