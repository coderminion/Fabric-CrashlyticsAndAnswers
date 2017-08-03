package coderminion.com.fabricanalytics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;

import org.json.JSONException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        Button fatal = (Button)findViewById(R.id.fatal);
        Button nonfatal = (Button)findViewById(R.id.nonfatal);

        fatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We dont need to log fatal exception these type of exceptions will get logged automatically
                throwRuntimeException();
            }
        });

        nonfatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We need to log exception saperately by using Crashlytics.logException(e);function
                //Crashlytics.logException(e); Will log in relese mode you should not that.
                // TODO: Use your own attributes to track content views in your app
                Answers.getInstance().logContentView(new ContentViewEvent()
                        .putContentName("Nonfatal Pressed")
                        .putContentType("Button")
                        .putContentId("1234")
                        .putCustomAttribute("Screen Orientation", "Landscape"));//Add custom attribute

                throwJsonException();
            }
        });
    }



    private void throwRuntimeException() {
    throw new RuntimeException();
    }


    //Crashlytics.logException(e); Will log in relese mode you should not that.
    private void throwJsonException() {
        try
        {
          throw new JSONException("Some JSON Exception ocurred");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
    }

    //This Comes in answers
    // TODO: Move this method and use your own event name to track your key metrics
    public void onKeyMetric() {
        // TODO: Use your own string attributes to track common values over time
        // TODO: Use your own number attributes to track median value over time
        Answers.getInstance().logCustom(new CustomEvent("Some Button Pressed")
                .putCustomAttribute("Category", "MainScreen")
                .putCustomAttribute("Length", "Somelenght"));
    }



}
