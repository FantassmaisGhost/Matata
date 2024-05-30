package com.example.couselling;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RateMe extends AppCompatActivity {
    Button button ;
    RatingBar rate;
    float myRating = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.rating);
        button = findViewById(R.id.submit);
        rate  = findViewById(R.id.ratingBar2);

        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean fromUser) {
                //   int rating = (int) v;
                // String message = null;

                myRating = ratingBar.getRating();

                // switch (rating){
                //   case 1:
                //     message = "Sorry for poor service";
                //    break;
                //case 2:
                //  message = "You need slight improvements";
                // break;
                // case 3:
                //   message = "Above average";
                //  break;
                // case 4:
                //   message = "Niiiiceeee!!!!!!";
                //  break;
                // case 5:
                //   message = "YOU ARE AMAZINGGG!!!!";
                //  break;
                //}
                //Toast.makeText(MainActivity.this,message , Toast.LENGTH_SHORT).show();


            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RateMe.this, String.valueOf(myRating) , Toast.LENGTH_SHORT).show();
            }
        });
    }

}

