package org.com.manager.frame;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.widget.TextView;

import org.com.manager.R;
import org.com.manager.util.FrameUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 提醒
 */
public class MyRemindActivity extends Activity {

    @Bind(R.id.time_to_note_title)
    TextView timeToNoteTitle;

    @Bind(R.id.time_to_note_content)
    TextView timeToNoteContent;
    public static final int NOTIFICATION_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_remind);
        ButterKnife.bind(this);

        final NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification n = new Notification();
        n.sound = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "20");
        nm.notify(NOTIFICATION_ID, n);

        String noteTitle = (String) getIntent().getExtras().get(FrameUtils.IT_NOTE_TITLE);
        String noteContent = (String) getIntent().getExtras().get(FrameUtils.IT_NOTE_CONTENT);
        timeToNoteTitle.setText(noteTitle);
        timeToNoteContent.setText(noteContent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            this.overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
