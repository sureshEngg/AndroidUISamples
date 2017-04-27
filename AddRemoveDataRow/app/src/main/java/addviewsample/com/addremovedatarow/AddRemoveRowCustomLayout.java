package addviewsample.com.addremovedatarow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Suresh on 3/30/2015.
 * A new child is added whenever add button is clicked above the Layout
 */

public class AddRemoveRowCustomLayout extends LinearLayout {
    private final String TAG = "AddRemoveCustomLayout";
    private HashMap<Integer, String> addRemoveDataList = new HashMap<>();
    private int currentPos;
    private Context mContext;

    public AddRemoveRowCustomLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mContext = context;
    }

    //Here a childView is added in DateTimeOption Layout and rendereing it's stuff
    public void addView(int position, String data) {
        ViewHolder viewHolder = null;
        final View view;
        view = ((Activity) mContext).getLayoutInflater().inflate(R.layout.add_remove_row_child_item, null);
        viewHolder = new ViewHolder();
        viewHolder.titleText = (TextView) view.findViewById(R.id.title_text);
        viewHolder.removeImg = (ImageView) view.findViewById(R.id.iv_invite_cancel);
        viewHolder.titleText.setText("I AM ROW " + (position + 1));
        viewHolder.removeImg.setTag(position);
        viewHolder.removeImg.setActivated(true);
        view.setTag(viewHolder);

        viewHolder.removeImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPos = (Integer) view.getTag();
                Log.d(TAG, "onClickCancel : " + currentPos);
                removeView(currentPos);
            }
        });

       this.addView(view);
    }

    public void removeView(final int pos) {
        if (addRemoveDataList.size() > 0) {
            final View viewToRemove = getAddRemoveRowView(pos);
            viewToRemove.animate()
                    .translationY(0)
                    .alpha(0.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            AddRemoveRowCustomLayout.this.removeView(viewToRemove);
                        }
                    });

            addRemoveDataList.remove(pos);
            if (addRemoveDataList.size() == 0) {
                currentPos = 0;
                mapKey = -1;
            }
        }
    }

    private View getAddRemoveRowView(int pos) {
        for (int i = 0; i < this.getChildCount(); i++) {
            View view = this.getChildAt(i);
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if ((Integer) viewHolder.removeImg.getTag() == pos)
                return view;
        }
        return null;
    }

    private int mapKey = -1;

    //Once user want a new Row avaialable
    public void addNewRow() {
        if (addRemoveDataList.size() <= 10) {
            mapKey++;
            addRemoveDataList.put(mapKey, "Put Your Data here");
            addView(mapKey, "Put Your data here");
        } else {
            Toast.makeText(mContext, "OOPS! Maximum row = 10", Toast.LENGTH_LONG).show();
        }
    }

    private class ViewHolder {
        ImageView removeImg;
        TextView titleText;
    }
}