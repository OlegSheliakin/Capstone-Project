package home.oleg.placesnearme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BasicActivity extends AppCompatActivity {

    public static final String EXTRA_DATA_NAME = "home.oleg.placesnearme.BasicActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic2);

        String [] titles = getResources().getStringArray(R.array.titles_array);
        ListView listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(BasicActivity.this, MapActivity.class);
                String [] sections = getResources().getStringArray(R.array.sections_array);

                intent.putExtra(EXTRA_DATA_NAME, sections[position]);
                startActivity(intent);
            }
        });
    }
}
