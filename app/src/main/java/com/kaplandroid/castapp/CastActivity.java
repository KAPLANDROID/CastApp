package com.kaplandroid.castapp;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaLoadOptions;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.*;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.images.WebImage;
import com.kaplandroid.castapp.model.Channel;

import java.util.Calendar;

public class CastActivity extends AppCompatActivity {

    private CastContext mCastContext;

    private CastSession mCastSession;
    private SessionManager mSessionManager;
    private final SessionManagerListener mSessionManagerListener = new SessionManagerListenerImpl();

    private class SessionManagerListenerImpl implements SessionManagerListener {
        @Override
        public void onSessionStarting(Session session) {

        }

        @Override
        public void onSessionStarted(Session session, String sessionId) {
            invalidateOptionsMenu();
        }

        @Override
        public void onSessionStartFailed(Session session, int i) {

        }

        @Override
        public void onSessionEnding(Session session) {

        }

        @Override
        public void onSessionResumed(Session session, boolean wasSuspended) {
            invalidateOptionsMenu();
        }

        @Override
        public void onSessionResumeFailed(Session session, int i) {

        }

        @Override
        public void onSessionSuspended(Session session, int i) {

        }

        @Override
        public void onSessionEnded(Session session, int error) {
            //   finish();
        }

        @Override
        public void onSessionResuming(Session session, String s) {

        }
    }

    private AdapterView lvChannelList;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mCastContext = CastContext.getSharedInstance(this);
        mSessionManager = mCastContext.getSessionManager();
        mCastSession = mSessionManager.getCurrentCastSession();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast);

        lvChannelList = findViewById(R.id.lvChannelList);

        //noinspection unchecked
        lvChannelList.setAdapter(new ArrayAdapter<>(CastActivity.this, android.R.layout.simple_list_item_1, ChannelListDB.channelList));

        lvChannelList.setOnItemClickListener((parent, view, position, id) -> {
            index = position;
            new Thread(() -> {
                ChannelListDB.updateTokens();
                runOnUiThread(() -> startCast(ChannelListDB.channelList[position]));
            }).start();
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> {
            index = (index > 0) ? index - 1 : ChannelListDB.channelList.length - 1;
            new Thread(() -> {
                ChannelListDB.updateTokens();
                runOnUiThread(() -> startCast(ChannelListDB.channelList[index]));
            }).start();
        });
        findViewById(R.id.btnNext).setOnClickListener(v -> {
            index = (index < ChannelListDB.channelList.length - 1) ? index + 1 : 0;
            new Thread(() -> {
                ChannelListDB.updateTokens();
                runOnUiThread(() -> startCast(ChannelListDB.channelList[index]));
            }).start();
        });
    }

    private void startCast(Channel channel) {
        mCastSession = mSessionManager.getCurrentCastSession();

        if (mCastSession != null) {

            MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);

            movieMetadata.putString(MediaMetadata.KEY_TITLE, channel.getName());
            movieMetadata.putString(MediaMetadata.KEY_SUBTITLE, Calendar.getInstance().getTime().toString());
            movieMetadata.addImage(new WebImage(Uri.parse("http://www.kaplandroid.com/kplndrd.png")));

            MediaInfo mediaInfo = new MediaInfo.Builder(channel.getLink())
                    .setStreamType(MediaInfo.STREAM_TYPE_LIVE)
                    .setContentType("videos/mp4")
                    .setMetadata(movieMetadata)
                    .setStreamDuration(0)
                    .build();
            RemoteMediaClient remoteMediaClient = mCastSession.getRemoteMediaClient();
            remoteMediaClient.load(mediaInfo, new MediaLoadOptions.Builder()
                    .setAutoplay(true)
                    .setPlayPosition(0).build());

            mCastSession.getRemoteMediaClient().seek(0);
        } else {
            showIntroductoryOverlay();
        }
    }

    private MenuItem mediaRouteMenuItem;

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.browse, menu);
        mediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), menu, R.id.media_route_menu_item);
        return true;
    }

    @Override
    protected void onResume() {
        mCastSession = mSessionManager.getCurrentCastSession();
        mSessionManager.addSessionManagerListener(mSessionManagerListener);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSessionManager.removeSessionManagerListener(mSessionManagerListener);
        mCastSession = null;
    }

    private IntroductoryOverlay mIntroductoryOverlay;

    private void showIntroductoryOverlay() {
        if (mIntroductoryOverlay != null) {
            mIntroductoryOverlay.remove();
        }
        if ((mediaRouteMenuItem != null) && mediaRouteMenuItem.isVisible()) {
            new Handler().post(() -> {
                mIntroductoryOverlay = new IntroductoryOverlay.Builder(
                        CastActivity.this, mediaRouteMenuItem)
                        .setTitleText("Connect To Chrome Cast")
                        .setOnOverlayDismissedListener(
                                () -> mIntroductoryOverlay = null)
                        .build();
                mIntroductoryOverlay.show();
            });
        }
    }
}
