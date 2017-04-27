package addviewsample.com.addremovedatarow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Suresh on 1/14/2015.
 */
public class AddRemoveRowActivity extends Activity {

    private AddRemoveRowCustomLayout mAddRemoveRowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove_row);

        initViews();
    }

    private void initViews() {
        mAddRemoveRowLayout = (AddRemoveRowCustomLayout) findViewById(R.id.add_remove_layout);
        mAddRemoveRowLayout.addNewRow();

        findViewById(R.id.add_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddRemoveRowLayout.addNewRow();
            }
        });
    }
}