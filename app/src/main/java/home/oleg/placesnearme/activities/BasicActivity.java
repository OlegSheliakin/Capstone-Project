package home.oleg.placesnearme.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import home.oleg.placesnearme.R;

public class BasicActivity extends AppCompatActivity {

    public static final String EXTRA_DATA_SECTION = "home.oleg.placesnearme.activities.BasicActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        String[] titles = getResources().getStringArray(R.array.titles_array);
        ListView listView = findViewById(R.id.listView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.basic_list_item, titles);

        if (listView != null) {
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(BasicActivity.this, MapActivity.class);
                    String[] sections = getResources().getStringArray(R.array.sections_array);

                    intent.putExtra(EXTRA_DATA_SECTION, sections[position]);
                    startActivity(intent);
                }
            });
        }
    }
}
