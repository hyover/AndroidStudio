package com.openclassrooms.quizperso.controller.activity;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.openclassrooms.quizperso.R;
        import com.openclassrooms.quizperso.model.User;

public class MainActivity extends AppCompatActivity {

    private TextView mTitleTextView;
    private EditText mNameEditText;
    private Button mPlayButton;
    private TextView mInfoTextView;
    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private static final String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO"; //fichier xml pour stocké info utilisateur
    private static final String SHARED_PREF_USER_INFO_NAME = "SHARED_PREF_USER_INFO_NAME";
    private  static final String SHARED_PREF_USER_INFO_SCORE = "SHARED_PREF_USER_INFO_SCORE";
    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode && data != null) {
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);

            getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                    .edit()
                    .putInt(SHARED_PREF_USER_INFO_SCORE, score)
                    .apply();

            greetUser();

        }
    }

    private void greetUser () {
        String firstName = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getString(SHARED_PREF_USER_INFO_NAME, null);
        int score = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getInt(SHARED_PREF_USER_INFO_SCORE, -1);

        if (firstName != null) {
            if (score != -1) {
                mTitleTextView.setText(("Re Bonjour " + firstName + " !" + " Ton dernier score était de " + score + " sur 8"));

            } else {
                mTitleTextView.showContextMenu();
            }

            mNameEditText.setText(firstName);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // méthode onCreate est appelée lorsque la 1er activité est créee
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // méthode setContentView permet de déterminer quel fichier layout utiiser

        mTitleTextView = findViewById(R.id.main_textview_title);
        mNameEditText = findViewById(R.id.main_edittext_name);
        mPlayButton = findViewById(R.id.main_button_play);
        mInfoTextView = findViewById(R.id.main_textview_info);

        mPlayButton.setEnabled(false);

        String firstName = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getString(SHARED_PREF_USER_INFO_NAME, null);



        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) { // detecte un changement de texte
                mPlayButton.setEnabled(!s.toString().isEmpty()); //active le button si quelqu'un écrit

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() { // detecte si quelqu'un à cliqué sur le bouton
            @Override
            public void onClick(View v) {
                getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)// stocker name user dans fichier xml
                        .edit()
                        .putString(SHARED_PREF_USER_INFO_NAME, mNameEditText.getText().toString())
                        .apply();

                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);

            }
        });

        greetUser();

    }
}